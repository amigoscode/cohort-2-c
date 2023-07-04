module "aws_budgeting_total_budget" {
  source          = "../../modules/aws_budgeting"
  budget_settings = [var.budget_settings["BUDGET_TOTAL"]]
  notification    = [var.budget_notification_settings["TOTAL_NOTIFICATION_SETTINGS"]]
}