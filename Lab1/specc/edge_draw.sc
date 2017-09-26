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
		for(i = 0; i < size; i++)
		{
			port_r_img.receive(&img[i], sizeof(unsigned char));
		}

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

		for(k = 0; k < size; k++)
			port_s_img.send(img + k, sizeof(unsigned char));
	}
};
