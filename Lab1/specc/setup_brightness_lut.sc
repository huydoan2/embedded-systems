#include <math.h>
#define X_SIZE 76
#define Y_SIZE 95

import "c_queue";
behavior setup_brightness_lut(i_sender port_arr_out){
	int thresh = 20;
	int form = 6;
	unsigned char bp[516];

	void main(void){
		int k;
		float temp;

		int mid_bp = 258;
 
                bp[0] = 0x0a8;
                bp[1] = 0x0e3;

		for (k = -256; k<257; k++) {
			temp = ((float)k) / ((float)thresh);
			temp = temp*temp;
			
			if (form == 6)
				temp = temp*temp*temp;
			temp = 100.0*exp(-temp);
			bp[mid_bp + k] = (unsigned char)temp;
		}

		/* Send bp to susan_edges */
		port_arr_out.send(bp, 516);
	
	}
};
