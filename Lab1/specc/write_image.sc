#include <stdio.h>

import "i_sender";
import "i_receiver";

behavior WriteImage(i_receiver img_in, i_sender img_out){
	const int IMG_SIZE = 7220;
	unsigned char img[IMG_SIZE];

	void main(void){
		while(true){
			img_in.receive(img, IMG_SIZE);
			img_out.send(img, IMG_SIZE);
		}
	}

};