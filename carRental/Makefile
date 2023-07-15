SHELL:=/bin/bash
.ONESHELL:
check-yes:
	@echo -en "${DANGER}Are you sure? [y/N] " && read ans && [ $${ans:-N} == y ]
dbd:
	docker-compose down && docker-compose rm -fs
dbu:
	docker-compose up -d
dsp:
	docker system prune
dnp:
	docker network prune
mcp:
	mvn clean package
untrack-iml:
	find ./ -name $(_EXTENSION) | xargs
	find ./ -name $(_EXTENSION) -exec git rm --cache --dry-run {} | xargs printf $(_DANGER) "" \;
	make check-yes && find ./ -name $(_EXTENSION) -exec git rm --cache {} \;

_SUCCESS := "\033[32m[%s]\033[0m %s\n"
SUCCESS := '\033[0;32m'
_DANGER := "\033[31m[%s]\033[0m %s\n"
DANGER := '\033[0;31m'
_EXTENSION := "*.iml"