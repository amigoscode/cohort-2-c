#resource "aws_organizations_organizational_unit" "foundational_ou" {
#  name      = "foundational"
#  parent_id = aws_organizations_organization.org.roots[0].id
#}
#
#resource "aws_organizations_organizational_unit" "additional_ou" {
#  name      = "additional"
#  parent_id = aws_organizations_organization.org.roots[0].id
#}
#
#resource "aws_organizations_organizational_unit" "security_ou" {
#  name      = "security"
#  parent_id = aws_organizations_organizational_unit.foundational_ou.id
#}
#
#resource "aws_organizations_organizational_unit" "infrastructre_ou" {
#  name      = "infrastructure"
#  parent_id = aws_organizations_organizational_unit.foundational_ou.id
#}
#resource "aws_organizations_organizational_unit" "prod_sec" {
#  name      = "prod-s"
#  parent_id = aws_organizations_organizational_unit.security_ou.id
#}
#resource "aws_organizations_organizational_unit" "prod_infra" {
#  name      = "prod-i"
#  parent_id = aws_organizations_organizational_unit.infrastructure_ou.id
#}
#
#resource "aws_organizations_organizational_unit" "staging_sec" {
#  name      = "staging-s"
#  parent_id = aws_organizations_organizational_unit.security_ou.id
#}
#resource "aws_organizations_organizational_unit" "staging_infra" {
#  name      = "staging-i"
#  parent_id = aws_organizations_organizational_unit.infrastructure_ou.id
#}
#
#resource "aws_organizations_organizational_unit" "sandbox_sec" {
#  name      = "sandbox-s"
#  parent_id = aws_organizations_organizational_unit.security_ou.id
#}
#resource "aws_organizations_organizational_unit" "sandbox_infra" {
#  name      = (lookup(var.accounts, "SANDBOX-I")).organization_unit
#  parent_id = aws_organizations_organizational_unit.infrastructure_ou.id
#}
