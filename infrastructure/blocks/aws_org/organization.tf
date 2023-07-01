#######################################################################################################################
# https://github.com/infrablocks/terraform-aws-organization/blob/main/organization.tf
#
# added enabled policy types


resource "aws_organizations_organization" "organization" {
  count                         = startswith(var.existing_master_account_roots_id, "r-") ? 0 : 1
  feature_set                   = var.feature_set
  aws_service_access_principals = var.aws_service_access_principals
  enabled_policy_types          = var.enabled_policy_types

  lifecycle {
    prevent_destroy = true
  }
}