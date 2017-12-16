# -----------------------------------
# Makefile for Susan Edge Detection example
# ----------------------------------- 

# --- Macros

SCC = scc
GCC = g++
CFLAGS = -std=c++0x -g
RM  = rm -f

SCFILES = snn.sh neuron_model.sh synaptic_model.sh PE_replay.sc main.sc

CPARTFILES = partition.cpp
COPTFILES = optimize.cpp

SIRFILES = PE_replay.sir main.sir

SCCOPTS = -vv -ww -g 

EXEFILE = main
PARTEXE = partition
OPTEXE = optimize

#--- Rules

.SUFFIXES: .sc .sir

.sc.sir:
	$(SCC) $* -sc2sir $(SCCOPTS)

#--- Build process

all: $(EXEFILE) $(PARTEXE) $(OPTEXE)

$(EXEFILE): $(SIRFILES)
	$(SCC) $(EXEFILE) -sc2out $(SCCOPTS)
#$(RM) *.sir

$(PARTEXE): $(CPARTFILES)
	$(GCC) $(CFLAGS) $(CPARTFILES) -o $(PARTEXE)

$(OPTEXE): $(COPTFILES)
	$(GCC) $(CFLAGS) $(COPTFILES) -o $(OPTEXE)

clean:
	$(RM) *~ *.si *.o *.h *.cc out*.pgm
	$(RM) $(SIRFILES) 
	$(RM) $(EXEFILE) $(EXEFILE_PAR)
	$(RM) $(PARTEXE) $(OPTEXE)

run:
	./partition
	./$(EXEFILE)
	./optimize
	mv connectivity_matrix_mod_out connectivity_matrix_mod
	mv spike_matrix_mod_out spike_matrix_mod
	./$(EXEFILE)


