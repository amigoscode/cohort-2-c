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
  default     = { "s" = "security", "a" = "infrastructure" }
  description = "level 2 of aws organizational units"
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
      email_suffix               = "sandbox@gmail.com",
      close_on_deletion          = true,
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
  }
  description = "aws accounts deployed from master account"
}