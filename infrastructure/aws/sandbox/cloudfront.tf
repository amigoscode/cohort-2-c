resource "aws_cloudfront_distribution" "s3_multimedia_distribution" {
  for_each = aws_s3_bucket.multimedia_buckets
  enabled = true
  is_ipv6_enabled = false
  comment = "test distribution for SANDBOX account"
  lambda_function_association = {

  }

  origin {
    domain_name = each.value.bucket_regional_domain_name
    origin_access_control_id = aws_cloudfront_origin_access_control.multimedia_cf_access_control.id
    origin_id ="${var.s3_multimedia_origin_id_prefix}-${each.value.bucket_domain_name}"
  }

  price_class = "PriceClass_200"

  restrictions {
    geo_restriction {
      restriction_type = "whitelist"
      locations        = ["US", "CA", "GB", "DE"]
    }
  }

  logging_config {
    include_cookies = false
    bucket          = "mylogs.s3.amazonaws.com"
    prefix          = "myprefix"
  }
  aliases = ["mysite.example.com", "yoursite.example.com"]
  viewer_certificate {
    cloudfront_default_certificate = true
  }
  default_cache_behavior {
    target_origin_id       =  "${var.s3_multimedia_origin_id_prefix}-${each.value.bucket_domain_name}"
    allowed_methods = ["DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"]
    cached_methods = ["GET", "HEAD"]
    viewer_protocol_policy = "allow-all"
    min_ttl = 0
    default_ttl = 3600
    max_ttl = 86400
    lambda_function_association {
      event_type = ""
      lambda_arn = ""
      include_body = false
    }

    forwarded_values {
      query_string = false

      cookies {
        forward = "none"
      }
  }
  }

  ordered_cache_behavior {
    path_pattern     = "/content/immutable/*"
    allowed_methods  = ["GET", "HEAD", "OPTIONS"]
    cached_methods   = ["GET", "HEAD", "OPTIONS"]
    target_origin_id = "${var.s3_multimedia_origin_id_prefix}-${each.value.bucket_domain_name}"

    forwarded_values {
      query_string = false
      headers      = ["Origin"]

      cookies {
        forward = "none"
      }
    }

    min_ttl                = 0
    default_ttl            = 86400
    max_ttl                = 31536000
    compress               = true
    viewer_protocol_policy = "redirect-to-https"
  }

  ordered_cache_behavior {
    path_pattern     = "/content/*"
    allowed_methods  = ["GET", "HEAD", "OPTIONS"]
    cached_methods   = ["GET", "HEAD"]
    target_origin_id = "${var.s3_multimedia_origin_id_prefix}-${each.value.bucket_domain_name}"

    forwarded_values {
      query_string = false

      cookies {
        forward = "none"
      }
    }

    min_ttl                = 0
    default_ttl            = 3600
    max_ttl                = 86400
    compress               = true
    viewer_protocol_policy = "redirect-to-https"
  }
  }

resource "aws_cloudfront_origin_access_control" "multimedia_cf_access_control" {
  name                              = ""
  origin_access_control_origin_type = ""
  signing_behavior                  = ""
  signing_protocol                  = ""
}

resource "aws_cloudfront_cache_policy" "example" {
  name        = "example-policy"
  comment     = "test comment"
  default_ttl = 50
  max_ttl     = 100
  min_ttl     = 1
  parameters_in_cache_key_and_forwarded_to_origin {
    cookies_config {
      cookie_behavior = "whitelist"
      cookies {
        items = ["example"]
      }
    }
    headers_config {
      header_behavior = "whitelist"
      headers {
        items = ["example"]
      }
    }
    query_strings_config {
      query_string_behavior = "whitelist"
      query_strings {
        items = ["example"]
      }
    }
  }
}