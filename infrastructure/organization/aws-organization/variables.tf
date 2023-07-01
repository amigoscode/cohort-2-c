variable "master_name" {
  type = string
}
variable "master_account_id" {
  type = number
}
variable "master_account_alias" {
  type = string
}
variable "master_iam_admin_alias" {
  type = string
}
variable "organization_name" {
  type    = string
  default = "rental-cars"
}
variable "master_aws_profile" {
  type        = string
  sensitive   = true
  description = "AWS Access Credentials"
}
variable "master_account_default_region" {
  type        = string
  default     = "us-east-2"
  description = "default region of master account"
}
variable "default_tag_created_by" {
  type    = string
  default = "Terraform"
}
variable "default_tag_environment" {
  type    = string
  default = "Master-Account-General"
}
variable "default_org_tag" {
  type    = string
  default = "Car-Rental"
}

variable "base_email" {
  type        = string
  description = "beginning of the email address that a +suffix@gmail.com is attached to"
  default     = "some-email"
}

variable "level_1_ous" {
  type        = map(string)
  default     = { "f" = "foundational", "a" = "additional" }
  description = "level 1 of aws organizational units"
}

variable "level_2_ous" {
  type        = map(string)
  default     = { "s" = "security", "a" = "infrastructure", "sb" = "sandboxes" }
  description = "level 2 of aws organizational units"
}

variable "feature_set" {
  type        = string
  default     = "ALL"
  nullable    = false
  description = "The feature set of the organization. One of 'ALL' or 'CONSOLIDATED_BILLING'."
}

variable "aws_service_access_principals" {
  type = list(string)
  default = [
    "cloudtrail.amazonaws.com",
    "config.amazonaws.com"
  ]
  nullable    = false
  description = "A list of AWS service principal names for which you want to enable integration with your organization."
}
variable "enabled_policy_types" {
  type        = list(string)
  default     = ["SERVICE_CONTROL_POLICY"]
  nullable    = false
  description = "A list of enabled organizations policy types"
}

variable "accounts" {
  type = map(object({
    name                       = string,
    email_suffix               = string,
    close_on_deletion          = bool,
    iam_user_access_to_billing = bool,
    role_name                  = string,
    s3_bucket_prefix           = string,
  }))
  default = {
    SANDBOX = {
      name                       = "SANDBOX",
      email_suffix               = "commonsandbox@gmail.com",
      close_on_deletion          = false,
      iam_user_access_to_billing = true,
      role_name                  = "OrganizationAccountAccessRole",
      s3_bucket_prefix           = "sandbox"
    },
    PROD = {
      name                       = "PROD",
      email_suffix               = "prod@gmail.com",
      close_on_deletion          = false,
      iam_user_access_to_billing = true,
      role_name                  = "OrganizationAccountAccessRole",
      s3_bucket_prefix           = "prod"
    },
    STAGING = {
      name                       = "STAGING",
      email_suffix               = "staging@gmail.com",
      close_on_deletion          = false,
      iam_user_access_to_billing = true,
      role_name                  = "OrganizationAccountAccessRole",
      s3_bucket_prefix           = "staging"
    }
    PROD = {
      name                       = "PROD",
      email_suffix               = "prod@gmail.com",
      close_on_deletion          = false,
      iam_user_access_to_billing = true,
      role_name                  = "OrganizationAccountAccessRole",
      s3_bucket_prefix           = "prod"
    },
    STAGING = {
      name                       = "STAGING",
      email_suffix               = "staging@gmail.com",
      close_on_deletion          = false,
      iam_user_access_to_billing = true,
      role_name                  = "OrganizationAccountAccessRole",
      s3_bucket_prefix           = "staging"
    },
    DEV = {
      name                       = "DEV",
      email_suffix               = "dev@gmail.com",
      close_on_deletion          = false,
      iam_user_access_to_billing = true,
      role_name                  = "OrganizationAccountAccessRole",
      s3_bucket_prefix           = "staging"
    }

  }
  description = "aws accounts deployed from master account"
}