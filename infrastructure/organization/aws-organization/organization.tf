data "aws_organizations_organization" "org" {
}

module "aws_org" {
  source = "../../blocks/aws_org"
  existing_master_account_roots_id = data.aws_organizations_organization.org.roots[0].id
  existing_organization_arn = data.aws_organizations_organization.org.arn
  feature_set = "ALL"
  enabled_policy_types = [
    "SERVICE_CONTROL_POLICY"
  ]
  aws_service_access_principals = [
    "cloudtrail.amazonaws.com",
    "config.amazonaws.com"
  ]
  for_each = var.accounts

  organization = {
    units = [

      {
        name = var.level_1_ous["f"],
        key  = "${var.organization_name}-${var.level_1_ous["f"]}"
        units = [
          {
            name = var.level_2_ous["s"],
            key  = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["s"]}"

          },
          {
            name = var.level_2_ous["i"],
            key  = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["i"]}"
            units = [
              {
                name = each.value.name,
                key  = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["i"]}-${each.value.name}"
                accounts = [{
                  name                              = var.accounts[each.value.name].name
                  key                               = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["i"]}-${each.value.name}-${var.accounts[each.value.name].name}"
                  email                             = "${var.base_email}-fi-${var.accounts[each.value.name].email_suffix}"
                  allow_iam_users_access_to_billing = var.accounts[each.value.name].iam_user_access_to_billing
                  role_name                         = var.accounts[each.value.name].role_name
                  close_on_deletion                 = var.accounts[each.value.name].close_on_deletion
                  },


                ]
              }
            ]
          }
        ]
      },
      {
        name = var.level_1_ous["a"]
        key  = "${var.organization_name}-${var.level_1_ous["a"]}"
        units = [
          {
            name = var.level_2_ous["s"],
            key  = "${var.organization_name}-${var.level_1_ous["a"]}-${var.level_2_ous["s"]}"

          },
          {
            name = var.level_2_ous["i"],
            key  = "${var.organization_name}-${var.level_1_ous["a"]}-${var.level_2_ous["i"]}"
            units = [
              {
                name = each.value.name,
                key  = "${var.organization_name}-${var.level_1_ous["a"]}-${var.level_2_ous["i"]}-POLICY-STAGING"
              }
            ]
          }
        ]
      }
    ]
  }
}
