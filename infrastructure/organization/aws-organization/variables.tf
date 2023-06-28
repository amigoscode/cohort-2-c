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
variable "master_aws_profile" {
  type = string
  sensitive = true
}
variable "master_account_default_region" {
  type = string
}