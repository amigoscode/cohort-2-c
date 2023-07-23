#remote_state {
#  backend  = "s3"
#  generate = {
#    path      = "_backend.tf"
#    if_exists = "overwrite"
#  }
#  config = {
#    bucket         = "rental-cars-backend-state-${path_relative_to_include()}"
#    region         = "us-east-2"
#    key            = "tf-infra/terraform.tfstate"
#    encrypt        = true
#    dynamodb_table = "rental-cars_${path_relative_to_include()}_state_locking"
#    profile        = "${path_relative_to_include()}-ADMIN"
#  }
#}

locals {
  # Load account, region and environment variables
  account_vars      = read_terragrunt_config(find_in_parent_folders("account.hcl"))
  region_vars       = read_terragrunt_config(find_in_parent_folders("region.hcl"))
  environment_vars  = read_terragrunt_config(find_in_parent_folders("env.hcl"))

  # Extract the variables we need with the backend configuration
  aws_region      = local.region_vars.locals.aws_region
  environment     = local.environment_vars.locals.environment
  state_bucket    = local.environment_vars.locals.state_bucket
  dynamodb_table  = local.environment_vars.locals.dynamodb_table
}

generate "backend" {
  path      = "_backend.tf"
  if_exists = "overwrite_terragrunt"
  contents = <<EOF
terraform {
  backend "s3" {
    bucket         = "rental-cars-backend-state-$local_user_profile}"
    key            = "tf-infra/terraform.tfstate"
    region         = "us-east-2"
    encrypt        = true
    dynamodb_table = "rental-cars_${local_user_profile}_state_locking"
    profile        = "${local_user_profile}-ADMIN"
  }
}
variable "local_user_profile" {}
EOF
}

generate "provider_config" {
  path      = "_config.tf"
  if_exists = "overwrite"
  contents  = <<EOF
  provider "aws" {
  profile = var.local_user_profile
  region  = var.default_region
  }
  variable "default_region" {}
  variable "local_user_profile" {}
EOF
}

terraform {
  extra_arguments "common_vars" {
    commands = get_terraform_commands_that_need_vars()
    required_var_files = [
    find_in_parent_folders("common.tfvars"),
    ]
  }
}
