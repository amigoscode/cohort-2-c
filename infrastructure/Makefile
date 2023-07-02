SHELL:=/bin/bash
.ONESHELL:

desc-org:
	aws organizations describe-organization
list-acc:
	aws organizations list-accounts
list-ou:
	aws organizations list-organizational-units-for-parent --parent-id $$(cut -c7- <<< $$(aws organizations list-roots | grep "Id: r-"))


