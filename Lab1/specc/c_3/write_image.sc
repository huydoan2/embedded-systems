#include <stdio.h>

import "c_imtoken_queue";

behavior WriteImage(i_imtoken_myreceiver img_in, i_imtoken_mysender img_out){
	const int IMG_SIZE = 7220;
	unsigned char img[IMG_SIZE];

	void main(void){
		while(true){
			img_in.receive(img);
			img_out.send(img);
		}
	}

};
