provider "aws" {
  profile = var.master_aws_profile
  region  = var.master_account_default_region
  #####################################################################################################################
  # Default Tag(s) which are attached to each resource capable of having a tag. Default tag(s) can be overridden by a
  #  in-place tag definition;
  #####################################################################################################################
  default_tags {
    tags = {
      Environment  = var.default_tag_environment
      CreatedVia   = var.default_tag_created_by
      Organization = var.default_org_tag
    }
  }
}