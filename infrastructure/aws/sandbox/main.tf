terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.5.0"
    }

  }
  ####################################################################################################################
  # bucket "${var.organization_name}-backend-state-${var.account_name}"
  # dynamodb_table"${var.organization_name}_${var.account_name}_state_locking"
  # When using SSO / Identity Center, which suits working procedure from local machine (not for CI/CD pipeline),
  # be aware, that backend configuration needs "profile" field to be declared. So consider using Terragrunt, if you want
  # to set backend fields dynamically. Another big issue is, that TF and TG don't understand profiles pointing to SSO
  # sessions in config files, that's why duplicate in profiles sso_start_url and sso_region fields just as in session.
  # Furthermore, should you have already initialized backend before introducing sso, use -reconfigure flag with terraform init
  # command. Run command down below to acquire new session token, in case the old one's expired.
  # aws sso login --profile SANDBOX-ADMIN
  ####################################################################################################################

  backend "s3" {
    bucket         = "rental-cars-backend-state-sandbox"
    key            = "tf-infra/terraform.tfstate"
    region         = "us-east-2"
    dynamodb_table = "rental-cars_sandbox_state_locking"
    encrypt        = true
    profile        = "SANDBOX-ADMIN"
  }
  required_version = ">= 0.13.1"
}
provider "aws" {
  profile = var.local_user_profile
  region  = var.default_region
}