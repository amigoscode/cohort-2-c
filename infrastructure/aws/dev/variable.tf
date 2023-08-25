variable "local_user_profile" {
  type        = string
  sensitive   = true
  description = "AWS Access Credentials"
}

variable "default_region" {
  type    = string
  default = "us-east-2"
}