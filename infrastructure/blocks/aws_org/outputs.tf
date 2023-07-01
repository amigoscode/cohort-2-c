output "organization_arn" {
  value = startswith(var.existing_organization_arn, "arn") ? var.existing_organization_arn : aws_organizations_organization.organization[0].arn
}

output "organizational_units" {
  value = local.all_ou_attributes
}

output "accounts" {
  value = local.all_account_attributes
}