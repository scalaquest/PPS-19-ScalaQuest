SHELL=/bin/bash

.PHONY: create-release
create-release:
	git checkout -b release/$(version) && \
	git tag $(version) -a  && \
	git push --set-upstream origin release/$(version) --follow-tags