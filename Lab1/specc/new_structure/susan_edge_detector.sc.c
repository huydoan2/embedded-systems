#include <stdio.h>

import "c_handshake";
import "c_queue";
import "susan";

#define IMG_SIZE 7220

behavior Stimulus(i_send start, int img[IMG_SIZE]) {

	int getint(FILE* fd)
	{
	   int c, i;
	   char dummy[10000];

	   c = getc(fd);
	   while (1) /* find next integer */
	   {
	      if (c == '#')    /* if we're at a comment, read to end of line */
	         fgets(dummy, 9000, fd);
	      if (c == EOF){
	         printf("Image is not binary PGM.\n");
		 return 1;
		}
	      if (c >= '0' && c <= '9')
	         break;   /* found what we were looking for */
	      c = getc(fd);
	   }

	   /* we're at the start of a number, continue until we hit a non-number */
	   i = 0;
	   while (1) {
	      i = (i * 10) + (c - '0');
	      c = getc(fd);
	      if (c == EOF) return (i);
	      if (c<'0' || c>'9') break;
	   }

	   return (i);
	}

	int get_image(){
		FILE* fd;

		char header[100];	    

	   if ((fd = fopen(argv[1], "rb")) == NULL)
	   		return 1;

	   /* {{{ read header */

	   header[0] = fgetc(fd);
	   header[1] = fgetc(fd);
	   if (!(header[0] == 'P' && header[1] == '5')){
	      printf("Image %s does not have binary PGM header.\n", file_name);
	      return 1;
	   }

	   getint(fd);
	   getint(fd);
	   getint(fd);

	   /* }}} */

	   if (fread(img, 1, IMG_SIZE, fd) == 0){
	      printf("Image %s is wrong size.\n", file_name);
	      return 1;
	   }

	   fclose(fd);
	   return 0;
	}

	void main(void) {
		if(get_image() == 0)
			start.send();
	}
};

behavior Monitor(i_receiver img_in) {

	unsigned char img[IMG_SIZE];

	int put_image() {

	   FILE* fd;

	   if((fd = fopen(argv[2], "wb")) == NULL){
			printf("Can't open the output file to write\n");
			return 1;
	   }

	   fprintf(fd, "%s\n%d %d\n%d\n", "P5", 76, 95, 255);

	   fwrite(img, IMG_SIZE, 1, fd);
	   fclose(fd);

		return 0;
	}

	void main(void) {
		img_in.receive(img, IMG_SIZE);
		put_image();
	}
};

behavior Main() {
	int img[IMG_SIZE];
	c_handshake stimulus_to_susan;
	c_queue susan_to_monitor(IMG_SIZE);

	Stimulus stimulus_module(stimulus_to_susan, img);
	susan susan_module(stimulus_to_susan, susan_to_monitor, img);
	Monitor monitor_module(susan_to_monitor);

	int main(void) {
		par {
			stimulus_module.main();
			susan_module.main();
			monitor_module.main();
		}

		printf("Exiting.\n");
		return 0;
	}
};