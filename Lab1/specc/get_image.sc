#include <stdio.h>
import "c_queue";

behavior get_image(i_receiver port_arr_in,
					i_sender port_arr_out)
{
	char filename[100];
	const int SIZE = 7220;
	unsigned char img[SIZE];

	void main(void){
		port_arr_in.receive(img, SIZE);
		port_arr_out.send(img, SIZE);
	}
};
