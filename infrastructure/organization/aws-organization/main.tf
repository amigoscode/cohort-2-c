terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.5.0"
    }
  }
  ####################################################################################################################
  # Definition of organization and workspace inside of Terraform Cloud, which is completely optional.
  # As the matter of fact we are provisioning master account infrastructure, there's no way to store the current
  # infrastructure state (CIS) in a S3 bucket due to not having any. That's why we're making use of Terraform Cloud
  # that provides a functionality of storing and locking a state for our master account. BTW, it also provides a
  # functionality of executing changes both remotely and locally.
  ####################################################################################################################
  cloud {
    organization = "car-rental"
    ##################################################################################################################
    # Workspaces are provided by Terraform Cloud, which can make it easy to manage states for different environments.
    ##################################################################################################################
    workspaces {
      name = "car-rental-organization-master"
    }
  }
  required_version = ">= 0.13.1"

}
