#######################################################################################################################
# S3 Buckets to save and lock terraform state for each account except for master. (The latter one is managed by TF Cloud)
#######################################################################################################################
resource "aws_s3_bucket" "buckets" {
  for_each = var.accounts
  bucket   = lower("${var.organization_name}-backend-state-${each.value.name}")
  lifecycle {
    prevent_destroy = true
  }

}
#######################################################################################################################
# Blocks any public access to buckets containing states explicitly.
#######################################################################################################################
resource "aws_s3_bucket_public_access_block" "public_access" {
  for_each                = aws_s3_bucket.buckets
  bucket                  = each.value.id
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
  for_each = aws_s3_bucket.buckets
  bucket   = each.value.id

  versioning_configuration {
    status = "Enabled"
  }
}

#######################################################################################################################
# Encryption at rest.
#######################################################################################################################
resource "aws_s3_bucket_server_side_encryption_configuration" "buckets_encryption" {
  for_each = aws_s3_bucket.buckets
  bucket   = each.value.bucket


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
  for_each     = var.accounts
  name         = "${var.organization_name}_${each.value.name}_state_locking"
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
    BackendAccount = each.value.name
  }
}
