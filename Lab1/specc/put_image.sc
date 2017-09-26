#include <stdio.h>
#include <stdlib.h>
import "c_queue";

#define size 76 * 95
#define  exit_error(IFB,IFC) { fprintf(stderr,IFB,IFC); exit(0); }

behavior put_image(i_receiver port_r_img)
{
	FILE  *fd;
	char* filename = "out.pgm";
	int i;
	unsigned char img[size];

	void main()
	{
		for(i = 0; i < size; i++)
			port_r_img.receive(&img[i], sizeof(unsigned char));

	    if ((fd = fopen(filename, "w")) == NULL)
	    	exit_error("Can't output image%s.\n", filename);

	    fprintf(fd, "P5\n");
	    fprintf(fd, "%d %d\n", 76, 95);
	    fprintf(fd, "255\n");

	    if (fwrite(img, size, 1, fd) != 1)
	        exit_error("Can't write image %s.\n", filename);

	    fclose(fd);
	}
};
