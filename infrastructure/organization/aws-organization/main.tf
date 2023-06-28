terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.5.0"
    }
  }
  cloud {
    organization = "car-rental"

    workspaces {
      name = "car-rental-organization-master"
    }
  }
  required_version = ">= 0.13.1"
}
