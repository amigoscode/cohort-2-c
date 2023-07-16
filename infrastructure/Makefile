SHELL:=/bin/bash
.ONESHELL:
check-yes:
	@echo -n "${DANGER}Are you sure? [y/N] " && read ans && [ $${ans:-N} == y ]
desc-org:
	aws organizations describe-organization
list-acc:
	aws organizations list-accounts
list-ou:
	aws organizations list-organizational-units-for-parent --parent-id $$(cut -c7- <<< $$(aws organizations list-roots | grep "Id: r-"))
untrack-terralock:
	find ./ -name $(_EXTENSION) | xargs \;
	find ./ -name $(_EXTENSION) -exec git rm --cache --dry-run {} | xargs printf $(_DANGER) \;
	make check-yes && find ./ -name $(_EXTENSION) -exec git rm --cache {} \;

_SUCCESS := "\033[32m[%s]\033[0m %s\n"
SUCCESS := '\033[0;32m'
_DANGER := "\033[31m[%s]\033[0m %s\n"
DANGER := '\033[0;31m'
_EXTENSION := "*.lock.hcl"