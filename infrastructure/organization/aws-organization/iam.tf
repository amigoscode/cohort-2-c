#{
#"Version": "2012-10-17",
#"Statement": [
#{
#"Sid": "AWSCloudTrailCreateLogStream2014110",
#"Effect": "Allow",
#"Action": [
#"logs:CreateLogStream"
#],
#"Resource": [
#"arn:aws:logs:us-east-1:141488247317:log-group:aws-cloudtrail-logs-141488247317-94f877dc:log-stream:141488247317_CloudTrail_us-east-1*"
#]
#},
#{
#"Sid": "AWSCloudTrailPutLogEvents20141101",
#"Effect": "Allow",
#"Action": [
#"logs:PutLogEvents"
#],
#"Resource": [
#"arn:aws:logs:us-east-1:141488247317:log-group:aws-cloudtrail-logs-141488247317-94f877dc:log-stream:141488247317_CloudTrail_us-east-1*"
#]
#}
#]
#}