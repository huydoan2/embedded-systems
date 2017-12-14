# -----------------------------------
# Makefile for Susan Edge Detection example
# ----------------------------------- 

# --- Macros

SCC = scc
CMP = diff -s
RM  = rm -f

SCFILES = snn.sh neuron_model.sh synaptic_model.sh PE_replay.sc main.sc

SIRFILES = PE_replay.sir main.sir

SCCOPTS = -vv -ww -g -d

EXEFILE = main

#--- Rules

.SUFFIXES: .sc .sir

.sc.sir:
	$(SCC) $* -sc2sir $(SCCOPTS)

#--- Build process

all: $(EXEFILE) 

$(EXEFILE): $(SIRFILES)
	$(SCC) $(EXEFILE) -sc2out $(SCCOPTS)
#$(RM) *.sir

clean:
	$(RM) *~ *.si *.o *.h *.cc out*.pgm
	$(RM) $(SIRFILES) 
	$(RM) $(EXEFILE) $(EXEFILE_PAR)

test:	$(EXEFILE)
	./$(EXEFILE)


