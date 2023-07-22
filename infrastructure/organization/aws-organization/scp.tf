locals {
  org_units_ids_by_name = {
    for i in module.aws_org_ous.organizational_units :
    i.name => i.id...
  }
}
output "org_units_by_ids" {
  value = local.org_units_ids_by_name
}

resource "aws_organizations_policy" "scp_basic_root" {
  name        = "BasicRootSCP"
  content     = data.aws_iam_policy_document.basic_root_policies_doc.json
  type        = "SERVICE_CONTROL_POLICY"
  description = "prevent sharing accounts outside of AWS Organization and leave organization"
}

resource "aws_organizations_policy" "scp_basic_sandbox" {
  name        = "BasicSandboxSCP"
  content     = data.aws_iam_policy_document.basic_sandbox_policies_doc.json
  type        = "SERVICE_CONTROL_POLICY"
  description = "Allow only t2.micro EC2"
}

resource "aws_organizations_policy_attachment" "sandbox_ou" {
  for_each  = toset(local.org_units_ids_by_name[var.level_2_ous["sb"]])
  policy_id = aws_organizations_policy.scp_basic_sandbox.id
  target_id = each.value
}

resource "aws_organizations_policy_attachment" "root" {
  policy_id = aws_organizations_policy.scp_basic_root.id
  target_id = aws_organizations_organization.organization.roots[0].id
}

data "aws_iam_policy_document" "scp_require_micro_instances_t2any_t3any" {
  statement {
    sid    = "RequireMicroEC2InstanceTypeT2AnyT3Any"
    effect = "Deny"
    actions = [
      "ec2:RunInstances"
    ]

    resources = [
      "arn:aws:ec2:*:*:instance/*"
    ]
    condition {
      test     = "ForAnyValue:StringNotLike"
      variable = "ec2:InstanceType"
      values   = ["t2.*", "t3.*"]
    }
  }
}

data "aws_iam_policy_document" "basic_root_policies_doc" {
  statement {
    sid    = "PreventExternalSharing"
    effect = "Deny"
    actions = [
      "ram:CreateResourceShare",
      "ram:UpdateResourceShare"
    ]

    resources = [
      "*"
    ]
    condition {
      test     = "ForAnyValue:Bool"
      variable = "ram:RequestedAllowsExternalPrincipals"
      values   = ["true"]
    }
  }
  statement {
    sid    = "PreventLeaveOrganization"
    effect = "Deny"
    actions = [
      "organizations:LeaveOrganization"
    ]
    resources = ["*"]
  }
}

data "aws_iam_policy_document" "basic_sandbox_policies_doc" {
  statement {
    sid    = "RequireMicroEC2InstanceTypeT2micro"
    effect = "Deny"
    actions = [
      "ec2:RunInstances"
    ]

    resources = [
      "arn:aws:ec2:*:*:instance/*"
    ]
    condition {
      test     = "ForAnyValue:StringNotEquals"
      variable = "ec2:InstanceType"
      values   = ["t2.micro"]
    }
  }
}