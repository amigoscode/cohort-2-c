variable "organization_name" {
  type    = string
  default = "rental-cars"
}
variable "local_user_profile" {
  type        = string
  sensitive   = true
  description = "AWS Access Credentials"
}

variable "default_region" {
  type    = string
  default = "us-east-2"
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
  default = "sandbox-foundational-infra"
}
variable "default_org_tag" {
  type    = string
  default = "Car-Rental"
}

variable "target_account_id" {
  type     = number
  nullable = false
}

variable "account_name" {
  type     = string
  nullable = false
  default  = "SANDBOX"
}

variable "resize_bucket_prefix" {
  type    = list(any)
  default = ["original", "browser", "mobile"]
}

variable "available_domains" {
  type    = list(string)
  default = ["images", "videos", "sounds"]
}