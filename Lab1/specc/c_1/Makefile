# ------------------------------------------------------------------------
# Makefile for lab 1
# ------------------------------------------------------------------------
#



# --- macros -------------------------------------------------------------

ALL	= susan_edge_detector

SCC	= scc
SCCOPT	= -vv -w -g -d
RM	= rm -f

CMP	= diff -s
GOLDFILE = golden.pgm
INFILE = input_small.pgm
OUTFILE = output_edge.pgm

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
	-$(RM) susan_edge_detector $(OUTFILE)

test:	
	./susan_edge_detector $(INFILE) $(OUTFILE) -e
	 $(CMP) $(OUTFILE) $(GOLDFILE)


# --- EOF ----------------------------------------------------------------
