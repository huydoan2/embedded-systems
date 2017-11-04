#include <stdio.h>

import "c_handshake";
import "c_queue";
import "design.sc";
import "stimulus.sc";
import "monitor.sc";


behavior Main() {
	const unsigned long IMG_SIZE = 7220;
	const unsigned long TIME_SIZE = 2*sizeof(unsigned long long);
	unsigned char img[IMG_SIZE];
	char* input_filename;
	char* output_filename;
	c_handshake stimulus_to_design_start;
	c_queue stimulus_to_monitor(TIME_SIZE);
	c_queue design_to_monitor(IMG_SIZE);

	Stimulus stimulus_module(input_filename, stimulus_to_design_start, stimulus_to_monitor, img);
	Design design_module(stimulus_to_design_start, img, design_to_monitor);
	Monitor monitor_module(output_filename, stimulus_to_monitor, design_to_monitor);

	int main(int argc, char * argv[]) {
		input_filename = argv[1];
		output_filename = argv[2];
 
 		par {
			stimulus_module.main();
			design_module.main();
			monitor_module.main();
		}

		printf("Exiting.\n");
		return 0;
	}
};
