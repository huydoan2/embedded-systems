
#define X_SIZE 76
#define Y_SIZE 95
#define unsigned char uchar

behavior setup_brightness_lut(i_sender port_arr_out){
	int thresh = 20;
	int form = 6;
	uchar bp[516];

	void main(void){
		int k;
		float temp;

		uchar* mid_bp = bp + 258;

		for (k = -256; k<257; k++) {
			temp = ((float)k) / ((float)thresh);
			temp = temp*temp;
			
			if (form == 6)
				temp = temp*temp*temp;
			temp = 100.0*exp(-temp);
			*(mid_bp + k) = (uchar)temp;
		}

		/* Send bp to susan_edges */
		port_arr_out.send(bp, 516);
	
	}
}