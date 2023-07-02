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
      name = "prod"
    }
  }
}
provider "aws" {
  profile = var.master_aws_profile
  region  = var.master_account_default_region
  #####################################################################################################################
  # Down below we're assuming a cross account organization role "OrganizationAccountAccessRole" which was created whilst
  # setting up the AWS Organization. This gives the admin an opportunity to create resources by using his/her identity.
  #####################################################################################################################
  assume_role {
    role_arn     = "arn:aws:iam::${var.target_account_id}:role/${var.cross_account_role_name}"
    session_name = "terraform"
  }
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

#######################################################################################################################
# S3 Buckets to save and lock terraform state for each account except for master. (The latter one is managed by TF Cloud)
#######################################################################################################################
resource "aws_s3_bucket" "bucket" {
  bucket = lower("${var.organization_name}-backend-state-${var.account_name}")
  lifecycle {
    prevent_destroy = true
  }

}
#######################################################################################################################
# Blocks any public access to buckets containing states explicitly.
#######################################################################################################################
resource "aws_s3_bucket_public_access_block" "public_access" {
  bucket                  = aws_s3_bucket.bucket.id
  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true

}
#######################################################################################################################
# Having versioning gives an opportunity to keep track of state's versions. However the versioning is attended by a
# the following concern: bucket's size gets bigger, because all versions are saved entirely - no incremental versioning.
# Once enabled, versioning cannot be disabled, unless the bucket is deleted completely.
#######################################################################################################################
resource "aws_s3_bucket_versioning" "buckets_version" {
  bucket = aws_s3_bucket.bucket.id

  versioning_configuration {
    status = "Enabled"
  }
}

#######################################################################################################################
# Encryption at rest.
#######################################################################################################################
resource "aws_s3_bucket_server_side_encryption_configuration" "buckets_encryption" {
  bucket = aws_s3_bucket.bucket.id


  rule {

    bucket_key_enabled = true
    apply_server_side_encryption_by_default {
      sse_algorithm = "AES256"
    }
  }
}
#######################################################################################################################
# DynamoDB row that "locks" and "unlocks" state, which prevents from simultaneous access.
#######################################################################################################################
resource "aws_dynamodb_table" "terraform_lock" {
  name         = lower("${var.organization_name}_${var.account_name}_state_locking")
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "LockID"
  lifecycle {
    prevent_destroy = true
  }
  attribute {
    name = "LockID"
    type = "S"
  }
  tags = {
    BackendAccount = var.account_name
  }
}