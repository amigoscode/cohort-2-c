resource "aws_organizations_organization" "organization" {
  feature_set                   = var.feature_set
  aws_service_access_principals = var.aws_service_access_principals
  enabled_policy_types          = var.enabled_policy_types
}

output "organization_arn" {
  value = aws_organizations_organization.organization.arn
}
output "organization_id" {
  value = aws_organizations_organization.organization.id
}

output "organization_root_id" {
  value = aws_organizations_organization.organization.roots[0].id
}



