# ------------------------------------------------------------------------
# Makefile for lab 1
# ------------------------------------------------------------------------
#



# --- macros -------------------------------------------------------------

ALL	= main

SCC	= scc
SCCOPT	= -vv -w -g -d
RM	= rm -f


# --- SpecC rules --------------------------------------------------------

.SUFFIXES:
.SUFFIXES:	.sc .cc .o

.sc.cc:
	$(SCC) $* -sc2cch $(SCCOPT)

.cc.o:
	$(SCC) $* -cc2o $(SCCOPT)

.o:
	$(SCC) $* -o2out $(SCCOPT)

.cc:
	$(SCC) $* -cc2out $(SCCOPT)

.sc:
	$(SCC) $* -sc2out $(SCCOPT)


# --- targets ------------------------------------------------------------

all:	$(ALL)

clean:
	-$(RM) *.bak *.BAK
	-$(RM) *.si *.sir *.cc *.h *.o
	-$(RM) $(ALL) *.exe

test:	$(ALL)
	set -e;					\
	for file in $(ALL); do ./$$file ; done


# --- EOF ----------------------------------------------------------------
