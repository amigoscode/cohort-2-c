resource "aws_s3_bucket" "buckets" {
  for_each = var.accounts
  bucket   = "${var.organization_name}/backend-state/${each.value.name}"
  lifecycle {
    prevent_destroy = true
  }

}

resource "aws_s3_bucket_public_access_block" "public_access" {
  for_each                = aws_s3_bucket.buckets
  bucket                  = each.value.id
  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true

}

resource "aws_s3_bucket_versioning" "buckets_version" {
  for_each = aws_s3_bucket.buckets
  bucket   = each.value.id

  versioning_configuration {
    status = "Enabled"
  }
}

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