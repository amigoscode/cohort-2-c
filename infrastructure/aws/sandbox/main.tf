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
  ####################################################################################################################

  backend "s3" {
    bucket         = "rental-cars-backend-state-sandbox"
    key            = "tf-infra/terraform.tfstate"
    region         = "us-east-2"
    dynamodb_table = "rental-cars_sandbox_state_locking"
    encrypt        = true
  }
  required_version = ">= 0.13.1"
}
provider "aws" {
  profile = var.local_user_profile
  region  = var.default_region
}