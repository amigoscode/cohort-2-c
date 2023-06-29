#resource "aws_organizations_account" "accounts" {
#  for_each                   = var.accounts
#  email                      = "${var.base_email}${each.value.email_suffix}"
#  name                       = each.value.name
#  close_on_deletion          = each.value.close_on_deletion
#  iam_user_access_to_billing = each.value.iam_user_access_to_billing
#  role_name                  = each.value.role_name
#
#}
#
