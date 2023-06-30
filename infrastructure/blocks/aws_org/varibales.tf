#######################################################################################################################
# https://github.com/infrablocks/terraform-aws-organization/blob/main/variables.tf
#
# added: enabled_policy_types, role_name, close_on_deletion
#######################################################################################################################
variable "feature_set" {
  type        = string
  default     = "ALL"
  nullable    = false
  description = "The feature set of the organization. One of 'ALL' or 'CONSOLIDATED_BILLING'."
}

variable "aws_service_access_principals" {
  type        = list(string)
  default     = ["cloudtrail.amazonaws.com", "config.amazonaws.com"]
  nullable    = false
  description = "A list of AWS service principal names for which you want to enable integration with your organization."
}
variable "enabled_policy_types" {
  type        = list(string)
  default     = []
  nullable    = false
  description = "A list of enabled organizations policy types"
}

#############
# This insane nesting down below showcases the limitations of terraform as no tree or graph structure is present.
# A better implementations of aws_organizations in hashicorp/aws official provider could deliver an improvement.
############

variable "organization" {
  type = object({
    accounts = optional(list(object({
      name                              = string,
      key                               = string,
      email                             = string,
      allow_iam_users_access_to_billing = bool,
      role_name                         = string,
      close_on_deletion                 = string,
    })), [])
    units = optional(list(object({
      name = string,
      key  = string,
      accounts = optional(list(object({
        name                              = string,
        key                               = string,
        email                             = string,
        allow_iam_users_access_to_billing = bool,
        role_name                         = string,
        close_on_deletion                 = string,
      })), [])
      units = optional(list(object({
        name = string,
        key  = string,
        accounts = optional(list(object({
          name                              = string,
          key                               = string,
          email                             = string,
          allow_iam_users_access_to_billing = bool,
          role_name                         = string,
          close_on_deletion                 = string,

        })), [])
        units = optional(list(object({
          name = string,
          key  = string,
          accounts = optional(list(object({
            name                              = string,
            key                               = string,
            email                             = string,
            allow_iam_users_access_to_billing = bool,
            role_name                         = string,
            close_on_deletion                 = string,
          })), [])
          units = optional(list(object({
            name = string,
            key  = string,
            accounts = optional(list(object({
              name                              = string,
              key                               = string,
              email                             = string,
              allow_iam_users_access_to_billing = bool,
              role_name                         = string,
              close_on_deletion                 = string,
            })), [])
            units = optional(list(object({
              name = string,
              key  = string,
              accounts = optional(list(object({
                name                              = string,
                key                               = string,
                email                             = string,
                allow_iam_users_access_to_billing = bool,
                role_name                         = string,
                close_on_deletion                 = string,
              })), [])
            })), [])
          })), [])
        })), [])
      })), [])
    })), [])
  })
  default     = {}
  nullable    = false
  description = "The organization with the tree of organizational units and accounts to construct. Defaults to an object with an empty list of units and accounts"
}