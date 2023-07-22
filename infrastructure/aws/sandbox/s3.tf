resource "aws_s3_bucket" "car_images_buckets" {
  for_each      = toset(var.resize_bucket_prefix)
  bucket        = "${var.organization_name}${each.value}/cars"
  force_destroy = true
}

resource "aws_s3_bucket_public_access_block" "public_access_full_block" {
  for_each                = aws_s3_bucket.car_images_buckets
  bucket                  = each.value.id
  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}
