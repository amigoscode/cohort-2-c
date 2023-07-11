resource "aws_budgets_budget" "budget" {
  name              = var.budget_settings[0].name
  budget_type       = var.budget_settings[0].budget_type
  limit_amount      = var.budget_settings[0].limit_amount
  limit_unit        = var.budget_settings[0].limit_unit
  time_unit         = var.budget_settings[0].time_unit

#  cost_filter {
#    name = "Service"
#    values = [
#      "Amazon Elastic Compute Cloud - Compute",
#    ]
#  }

  notification {
    comparison_operator        = var.notification[0].comparison_operator
    threshold                  = var.notification[0].threshold
    threshold_type             = var.notification[0].threshold_type
    notification_type          = var.notification[0].notification_type
    subscriber_email_addresses = var.notification[0].subscriber_email_addresses
  }
}