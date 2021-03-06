NO_COLOR=\x1b[0m
OK_COLOR=\x1b[32;01m
ERROR_COLOR=\x1b[31;01m
WARN_COLOR=\x1b[33;01m

all: Makefile.dep compile

clean:
	-rm out/*.nss out/*.ncs out/*.ndb Makefile.dep $(autogen)

NCC=../../../build/nwnnsscomp
NCCFLAGS=-g -c -q -o

subdirs := $(shell find . -type d)

corefiles := $(wildcard ../../gamedata/override/*.nss)

headers := $(foreach d,$(subdirs),$(wildcard $(d)/*.h $(d)/*.nh))
scripts := $(foreach d,$(subdirs),$(wildcard $(d)/*.n))
preprocessed := $(addprefix out/,$(addsuffix .nss, $(notdir $(basename $(scripts)))))

# $(corefiles) all .nss files that are provided by NWN itself

# $(headers)   array of header files (.nh, .h) with extension
# $(scripts)   array of all script files (.n) with extension
# $(preprocessed) array of ALL script files that were preprocessed

include Makefile.dep

# From Makefile.dep:
# $(objects)   array of script files that are compileable (not includes)

autogen := appearance.h out/draconic.nss

out/draconic.nss: gen_language_file ../../hak/client_2da/spells.2ds
	make -C ../../hak/client_2da/ spells.2ds
	./gen_language_file > out/draconic.nss

draconic.nss: draconic.n

appearance.h: generate_appearance_h
	./generate_appearance_h > appearance.h

Makefile.dep: $(autogen) $(headers) $(scripts)
	@echo "Building dependencies: Makefile.dep .."
	@./build_dep_list.rb -g stddef.h $(headers) $(scripts) > Makefile.dep

out/%.nss:
	@set -e; (gcc -undef -Wall -P -E -nostdinc -include stddef.h -I. -x c -std=c99 $< |\
		gpp +z -C +n --nostdinc -I. --include "gpp.h" > out/`basename $< .n`.nss) && \
		echo " $(OK_COLOR)[OK]$(NO_COLOR) gpp $<" || rm out/`basename $< .n`.nss

out/%.ncs:
	@cd out/ && $(NCC) $(NCCFLAGS) ../../../gamedata $< && \
		echo " $(OK_COLOR)[OK]$(NO_COLOR) ncc $<"

compile: $(preprocessed) $(objects)

.tags: $(headers) $(scripts) $(corefiles)
	@ctags --language-force=c --totals --c-kinds=cdefgmnpstuvx --fields=fksmnSzt $(corefiles) $(scripts) $(headers) .tags

show-stale:
	@for x in *.n[cs]s; do [ -f `echo $$x |cut -d. -f1`.n ] || echo $$x; done
