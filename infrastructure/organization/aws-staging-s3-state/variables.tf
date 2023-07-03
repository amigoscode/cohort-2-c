variable "master_account_id" {
  type = number
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
  default = "Staging-foundational-infra"
}
variable "default_org_tag" {
  type    = string
  default = "Car-Rental"
}

variable "target_account_id" {
  type     = number
  nullable = false
}

variable "cross_account_role_name" {
  type     = string
  nullable = false
  default  = "OrganizationAccountAccessRole"
}
variable "account_name" {
  type     = string
  nullable = false
  default  = "STAGING"
}