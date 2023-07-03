#######################################################################################################################
# https://github.com/infrablocks/terraform-aws-organization/blob/main/variables.tf
#
# added: enabled_policy_types, role_name, close_on_deletion and excluded organization creation from the module
#######################################################################################################################

#############
# This insane nesting down below showcases the limitations of terraform as no tree or graph structure is present.
# A better implementations of aws_organizations in hashicorp/aws official provider could deliver an improvement.
############

variable "root_id" {
  type        = string
  nullable    = false
  description = "id of organization root"
  validation {
    condition = startswith(var.root_id, "r-")
    error_message = "wrong root_id"
  }
}

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