#include <stdio.h>
import "c_queue";

#define x_size 76
#define size 76 * 95

behavior edge_draw(i_receiver port_r_img, i_receiver port_r_mid, i_sender port_s_img)
{
	int i, j, k, index;
	unsigned char img[size];
	unsigned char mid[size];

	void main()
	{
		port_r_img.receive(&img, size);

		for(j = 0; j < size; j++)
		{
			port_r_mid.receive(&mid[j], sizeof(unsigned char));
			if(mid[j] < 8)
			{
				index = j - x_size - 1;
				img[index++] = 255; img[index++] = 255; img[index] = 255; index += x_size - 2;
				img[index++] = 255; index++; img[index] = 255; index += x_size - 2;
				img[index++] = 255; img[index++] = 255; img[index] = 255;
			}
		}

		port_s_img.send(img, size);
	}
};
