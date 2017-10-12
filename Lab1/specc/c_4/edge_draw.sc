#include <stdio.h>

import "c_imtoken_queue";

#define x_size 76
#define size 7220

behavior edge_draw(i_imtoken_myreceiver port_r_img, i_imtoken_myreceiver port_r_mid, i_imtoken_mysender port_s_img)
{
	int i, index, midi;
	unsigned char img[size];
	unsigned char mid[size];

	void main()
	{
		
		port_r_img.receive(img);
		port_r_mid.receive(mid);

		midi = 0;

		for(i = 0; i < size; i++)
		{
			if(mid[midi] < 8)
			{
				index = midi - x_size - 1;
				img[index++] = 255; img[index++] = 255; img[index] = 255; index += x_size - 2;
				img[index++] = 255; index++; img[index] = 255; index += x_size - 2;
				img[index++] = 255; img[index++] = 255; img[index] = 255;
			}
			midi++;
		}
	
		midi = 0;
  		for (i=0; i<size; i++)
  		{
    			if (mid[midi]<8) 
      				img[midi] = 0;
    			midi++;
  		}

		port_s_img.send(img);
	}
};
