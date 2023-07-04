variable "budget_settings" {
  type = list (object({
    name              = string
    budget_type       = string
    limit_amount      = string
    limit_unit        = string
    time_unit         = string
  }))
  default = [
    {
  name              = "monthly-budget"
  budget_type       = "COST"
  limit_amount      = "2"
  limit_unit        = "USD"
  time_unit         = "MONTHLY"
    }
  ]
}
variable "notification" {
  type = list (object({
    comparison_operator        = string
    threshold                  = number
    threshold_type             = string
    notification_type          = string
    subscriber_email_addresses = list(string)
  }))
  default =[
     {
      comparison_operator        = "GREATER_THAN"
      threshold                  = 100
      threshold_type             = "PERCENTAGE"
      notification_type          = "FORECASTED"
      subscriber_email_addresses = ["test@example.com"]
    }
  ]
}