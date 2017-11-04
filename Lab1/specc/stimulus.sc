#include <stdio.h>
#include <sim.sh>

import "i_send";
import "i_sender";

#define IMG_SIZE 7220
#define NUM_IMG  100
#define WAIT_TIME 1000


behavior Stimulus(in char* file_name, i_send start, i_sender time, inout unsigned char img[IMG_SIZE]) {	
	unsigned char start_sig;
	unsigned long long start_time;

	int getint(FILE* fd)
	{
	   int c, i;
	   char dummy[10000];

	   c = getc(fd);
	   while (1) // find next integer 
	   {
	      if (c == '#')    // if we're at a comment, read to end of line
	         fgets(dummy, 9000, fd);
	      if (c == EOF){
	         printf("Image is not binary PGM.\n");
		 return 1;
		}
	      if (c >= '0' && c <= '9')
	         break;   // found what we were looking for
	      c = getc(fd);
	   }

	   // we're at the start of a number, continue until we hit a non-number 
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

	   if ((fd = fopen(file_name, "rb")) == NULL)
	   		return 1;

	   header[0] = fgetc(fd);
	   header[1] = fgetc(fd);
	   if (!(header[0] == 'P' && header[1] == '5')){
	      printf("Image %s does not have binary PGM header.\n", file_name);
	      return 1;
	   }

	   getint(fd);
	   getint(fd);
	   getint(fd);


	   if (fread(img, 1, IMG_SIZE, fd) == 0){
	      printf("Image %s is wrong size.\n", file_name);
	      return 1;
	   }

	   fclose(fd);
	   return 0;
	}

	void main(void){
		start_sig = 1;
		if(get_image() == 0){			
			int i;
			for(i = 0; i < NUM_IMG; ++i){
				start.send();
				start_time = now();
				time.send(&start_time, sizeof(start_time));
				waitfor(WAIT_TIME);
			}
		}
	}
};
