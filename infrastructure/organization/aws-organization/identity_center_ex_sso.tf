#######################################################################################################################
#
#
# Just a recap for creating AWS IAM Identity Center (Successor to SSO) with terraform:
# 1) Even having "sso.amazonaws.com" as principal don't prevent you from enabling IAM Identity Center in master account
# manually.
# 2) Enable IAM Identity Center in THE SAME REGION as DEFAULT REGION variable. Otherwise you'll get error " reading
# SSO Instances: AccessDeniedException  User: arn:aws:iam::MASTER_ACCOUNT_ID:user/MASTER_ACCOUNT_ALIAS is not authorized
# to perform: sso:ListInstances "
#######################################################################################################################
data "aws_ssoadmin_instances" "sso" {}

resource "aws_identitystore_group" "id_groups" {
  for_each          = toset(local.groups)
  display_name      = each.key
  identity_store_id = local.identity_store_id
}

resource "aws_ssoadmin_permission_set" "permission_sets" {
  for_each = {
    for permission in var.sso_permissions : permission.name => permission
  }
  instance_arn     = local.sso_instance_arn
  name             = each.value.name
  description      = each.value.description
  session_duration = each.value.session_duration
}
resource "aws_ssoadmin_account_assignment" "account_assignment" {
  for_each           = local.account_group_assignments
  instance_arn       = local.sso_instance_arn
  permission_set_arn = aws_ssoadmin_permission_set.permission_sets[each.value.permission_set_name].arn
  principal_id       = aws_identitystore_group.id_groups[each.value.group].group_id
  target_id          = each.value.account
  principal_type     = "GROUP"
  target_type        = "AWS_ACCOUNT"
}

resource "aws_ssoadmin_managed_policy_attachment" "managed_policy_attachment" {
  for_each           = local.managed_policy_arns
  instance_arn       = local.sso_instance_arn
  managed_policy_arn = each.value.policy_arn

  permission_set_arn = aws_ssoadmin_permission_set.permission_sets[each.value.permission_set_name].arn
}

resource "aws_identitystore_user" "users" {
  identity_store_id = local.identity_store_id
  for_each          = var.team_members
  display_name      = each.value.display_name
  user_name         = each.value.user_name

  name {
    given_name  = split(" ", each.value.display_name)[0]
    family_name = split(" ", each.value.display_name)[1]
  }

  emails {
    value   = each.value.emails["0"]
    primary = true
  }
}

resource "aws_identitystore_group_membership" "group_memberships" {
  identity_store_id = local.identity_store_id
  for_each = {
    for record in local.memberships :
    "${record.group_id}.${record.user_name}" => record
  }
  group_id  = each.value.group_id
  member_id = each.value.user_id
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
      for account_group in setproduct(permission.aws_accounts, permission.sso_groups) : {
        permission_set_name = permission.name
        account             = local.account_ids[account_group[0]].id
        group               = account_group[1]
      }
    ]
  ])

  account_group_assignments = {
    for account_group in local.account_groups :
    "${account_group.permission_set_name}.${account_group.account}.${account_group.group}" => account_group
  }

  groups = distinct([for account_group in local.account_groups : account_group.group])

  account_ids = {
    for i in module.aws_org_ous.accounts :
    i.name => {
      id   = i.id
      name = i.name
    }
  }
  memberships = flatten([
    for group in aws_identitystore_group.id_groups : [
      for user in aws_identitystore_user.users : {
        group_id   = group.group_id
        group_name = group.display_name
        user_id    = user.user_id
        user_name  = user.display_name
      }
    ]
  ])
}

output "memberships" {
  value = {
    for_each = {
      for record in local.memberships :
      "${record.group_id}.${record.user_name}" => record
  } }
}
