/*
* SANDBOX s3 bucket infra. Use it with SSO (Identity Center) from you local machine.
* The setting is not as generic as it could be and is here to only give it a try to collect common variables, which
* could be the base for Terragrunt setting for DEV, STAGING and PROD accounts.
*/

resource "aws_s3_bucket" "multimedia_buckets" {
  for_each      = toset(var.resize_bucket_prefix)
  bucket        = lower("${var.organization_name}-${var.account_name}-${each.value}")
  force_destroy = true
  tags = {
    name      = each.value
    createdBy = var.default_tag_created_by
    env       = var.default_tag_environment
    org       = var.default_org_tag

  }
}

resource "aws_s3_bucket_public_access_block" "public_access_full_allow" {
  for_each                = aws_s3_bucket.multimedia_buckets
  bucket                  = each.value.id
  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}
resource "aws_s3_bucket_policy" "multimedia_bucket_policy" {
  for_each = aws_s3_bucket.multimedia_buckets
  bucket   = each.value.id
  policy   = data.aws_iam_policy_document.basic_images_policy[each.value.tags.name].json
}
##################################################################################################################
# This CORS setting is temporary and is intended to be used in a sandbox mode with sso temp credentials to locally run the app.
# Later on it's going to be adjusted for static web hosting and Cloud Front.
###################################################################################################################
resource "aws_s3_bucket_cors_configuration" "multimedia_bucket_cors" {
  for_each = aws_s3_bucket.multimedia_buckets
  bucket   = each.value.id

  cors_rule {
    id              = "${each.value.bucket_domain_name}-cors-rule-allow-all-origins-get"
    allowed_headers = ["*"]
    allowed_methods = ["GET"]
    allowed_origins = ["*"]
    expose_headers  = ["ETag"]
    max_age_seconds = 3600
  }
}

data "aws_iam_policy_document" "basic_images_policy" {
  for_each = aws_s3_bucket.multimedia_buckets
  statement {
    sid    = "AllowGetImagesForAnyone"
    effect = "Allow"
    principals {
      type        = "*"
      identifiers = ["*"]
    }
    actions   = ["s3:GetObject"]
    resources = local.available_domains_by_arns[each.value.arn]
  }
}

locals {
  available_domains_by_arns = {
    for i in aws_s3_bucket.multimedia_buckets : i.arn => flatten([
      for r in var.available_domains : [
        format("%s/%s/*", i.arn, r)
      ]
    ])
  }
}

output "available_domains_by_arn" {
  value = local.available_domains_by_arns
}

locals {
  #  sa = [
  #    for i in aws_s3_bucket.multimedia_buckets :
  #    {
  #      arn = i.arn
  #      resources = flatten([
  #        for r in var.available_domains : [
  #        format("%s/%s/*", i.arn, r)]
  #      ])
  #    }
  #  ]
  #  s = [
  #    for i in aws_s3_bucket.multimedia_buckets : [
  #
  #      for a in var.available_domains : {
  #        arn       = i.arn
  #        resources = format("%s/%s/*", i.arn, a)
  #      }
  #    ]
  #  ]
}
