# -----------------------------------
# Makefile for Susan Edge Detection example
# ----------------------------------- 

# --- Macros

SCC = scc
CMP = diff -s
RM  = rm -f

SCFILES = c_uchar7220_queue.sc c_int7220_queue.sc\
        i_os_api.sc speccos.sc setup_brightness_lut.sc susan_edges.sc detect_edges.sc susan_thin.sc edge_draw.sc read_image.sc\
	write_image.sc stimulus.sc monitor.sc design.sc susan_edge_detector.sc

SIRFILES = c_uchar7220_queue.sir c_int7220_queue.sir\
        i_os_api.sir speccos.sir setup_brightness_lut.sir susan_edges.sir detect_edges.sir susan_thin.sir edge_draw.sir read_image.sir\
	write_image.sir design.sir stimulus.sir monitor.sir susan_edge_detector.sir

SCCOPTS = -vv -ww -g -d #-par

EXEFILE = susan_edge_detector

GOLDFILE = golden.pgm
TESTFILE = out.pgm

#--- Rules

.SUFFIXES: .sc .sir

.sc.sir:
	$(SCC) $* -sc2sir $(SCCOPTS)

#--- Build process

all: $(EXEFILE) 

$(EXEFILE): $(SIRFILES)
	$(SCC) $(EXEFILE) -sc2out $(SCCOPTS)
	$(RM) *.sir

clean:
	$(RM) *~ *.si *.o *.h *.cc out*.pgm
	$(RM) $(SIRFILES) 
	$(RM) $(EXEFILE) $(EXEFILE_PAR)

test:	$(EXEFILE)
	./$(EXEFILE)
	$(CMP) $(TESTFILE) $(GOLDFILE)


