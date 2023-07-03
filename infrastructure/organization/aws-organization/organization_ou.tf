module "aws_org_ous" {
  source  = "../../modules/aws_org_ous"
  root_id = aws_organizations_organization.organization.roots[0].id

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
                name = var.accounts["DEV"].name,
                key  = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["i"]}-${var.accounts["DEV"].name}",
                accounts = [
                  {
                    name                              = var.accounts["DEV"].name
                    key                               = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["i"]}-${var.accounts["DEV"].name}-${var.accounts["DEV"].name}"
                    email                             = "${var.base_email}-fi-${var.accounts["DEV"].email_suffix}"
                    allow_iam_users_access_to_billing = var.accounts["DEV"].iam_user_access_to_billing
                    role_name                         = var.accounts["DEV"].role_name
                    close_on_deletion                 = var.accounts["DEV"].close_on_deletion
                  }
                ]
              },
              {
                name = var.accounts["STAGING"].name,
                key  = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["i"]}-${var.accounts["STAGING"].name}",
                accounts = [
                  {
                    name                              = var.accounts["STAGING"].name
                    key                               = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["i"]}-${var.accounts["STAGING"].name}-${var.accounts["STAGING"].name}"
                    email                             = "${var.base_email}-fi-${var.accounts["STAGING"].email_suffix}"
                    allow_iam_users_access_to_billing = var.accounts["STAGING"].iam_user_access_to_billing
                    role_name                         = var.accounts["STAGING"].role_name
                    close_on_deletion                 = var.accounts["STAGING"].close_on_deletion
                  }
                ]
              },
              {
                name = var.accounts["PROD"].name,
                key  = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["i"]}-${var.accounts["PROD"].name}",
                accounts = [
                  {
                    name                              = var.accounts["PROD"].name
                    key                               = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["i"]}-${var.accounts["PROD"].name}-${var.accounts["PROD"].name}"
                    email                             = "${var.base_email}-fi-${var.accounts["PROD"].email_suffix}"
                    allow_iam_users_access_to_billing = var.accounts["PROD"].iam_user_access_to_billing
                    role_name                         = var.accounts["PROD"].role_name
                    close_on_deletion                 = var.accounts["PROD"].close_on_deletion
                  }
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
          },
          {
            name = var.level_2_ous["sb"],
            key  = "${var.organization_name}-${var.level_1_ous["a"]}-${var.level_2_ous["sb"]}"
            accounts = [
              {
                name                              = var.accounts["SANDBOX"].name
                key                               = "${var.organization_name}-${var.level_1_ous["f"]}-${var.level_2_ous["i"]}-${var.level_2_ous["sb"]}-${var.accounts["SANDBOX"].name}"
                email                             = "${var.base_email}-fi-${var.accounts["SANDBOX"].email_suffix}"
                allow_iam_users_access_to_billing = var.accounts["SANDBOX"].iam_user_access_to_billing
                role_name                         = var.accounts["SANDBOX"].role_name
                close_on_deletion                 = var.accounts["SANDBOX"].close_on_deletion
              }
            ]
          }
        ]
      }
    ]
  }
}
output "all_accounts_details" {
  value = module.aws_org_ous.accounts
}