#include <stdio.h>
#include <stdlib.h>
import "c_queue";

#define size 76 * 95

behavior put_image(i_receiver port_r_img, i_sender port_s_img)
{
	unsigned char img[size];

	void main()
	{
		port_r_img.receive(&img, size);
		port_s_img.send(img, size);
	}
};
