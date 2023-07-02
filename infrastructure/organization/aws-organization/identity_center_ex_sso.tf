data "aws_ssoadmin_instances" "sso" {}
data "aws_identitystore_group" "id_group" {
  for_each          = toset(local.groups)
  identity_store_id = local.identity_store_id

  alternate_identifier {
    unique_attribute {
      attribute_path  = "DisplayName"
      attribute_value = each.key
    }
  }
}

resource "aws_ssoadmin_permission_set" "permission_sets" {
  for_each = {
    for permission in var.sso_permissions : permission.name => permission
  }
  #  for_each = var.sso_permissions
  instance_arn     = tolist(data.aws_ssoadmin_instances.sso.arns)[0]
  name             = each.value.name
  description      = each.value.description
  session_duration = each.value.session_duration
}
resource "aws_ssoadmin_account_assignment" "account_assignment" {
  for_each           = local.account_group_assignments
  instance_arn       = local.sso_instance_arn
  permission_set_arn = aws_ssoadmin_permission_set.permission_sets[each.value.permission_set_name].arn
  principal_id       = data.aws_identitystore_group.id_group[each.value.group].id
  target_id          = each.value.account
  principal_type     = "GROUP"
  target_type        = "AWS_ACCOUNT"
}

resource "aws_ssoadmin_customer_managed_policy_attachment" "managed_policy_attachment" {
  for_each           = local.managed_policy_arns
  instance_arn       = local.sso_instance_arn
  managed_policy_arn = each.value.policy_arn
  permission_set_arn = aws_ssoadmin_permission_set.permission_sets[each.value.permission_set_name].arn
}

locals {
  managed_policies = flatten([
    for permission in var.sso_permissions : [
      for policy in permission.managed_policies : {
        permission_set_name = permission.name
        policy_arn          = policy
      }
    ]
  ])

  managed_policy_arns = {
    for policy in local.managed_policies :
    "${policy.permission_set_name}.${policy.policy_arn}" => policy
  }

  sso_instance_arn  = tolist(data.aws_ssoadmin_instances.sso.arns)[0]
  identity_store_id = tolist(data.aws_ssoadmin_instances.sso.identity_store_ids)[0]
  #####################################################################################################################
  # setproduct() function creates a Cartesian product of all accounts' ids and groups
  #####################################################################################################################
  account_groups = flatten([
    for permission in var.sso_permissions : [
           // TODO: call aws_org to get accounts' ids by accounts name
      for account_group in setproduct(permission.aws_accounts, permission.sso_groups) : {
        permission_set_name = permission.name
        account             = account_group[0]
        group               = account_group[1]
      }
    ]
  ])

  account_group_assignments = {
    for account_group in local.account_groups :
    "${account_group.permission_set_name}.${account_group.account}.${account_group.group}" => account_group
  }

  groups = distinct([for account_group in local.account_groups : account_group.group])
}
