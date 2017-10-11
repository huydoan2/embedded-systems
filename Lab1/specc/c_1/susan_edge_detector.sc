#include <stdio.h>
#include <sim.sh>

import "c_handshake";
import "c_queue";
import "susan";

#define NUM_IMG  100
#define WAIT_TIME 1000
#define WAIT_DELAY 300

behavior Stimulus(char* file_name, i_send start, unsigned char img[7220], unsigned long long start_time) {
	const unsigned long IMG_SIZE = 7220;

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

	int read_image(){
		FILE* fd;

		char header[100];	    

	   if ((fd = fopen(file_name, "rb")) == NULL)
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
		int i;
		if(read_image() == 0) {
			waitfor(WAIT_TIME);
			start.send();
			start_time = now();
			for(i = 1; i < NUM_IMG; i++)
			{
				waitfor(WAIT_DELAY);
				start.send();
				start_time = now();
			}
		}
	}
};

behavior Monitor(char* file_name, i_receiver img_in, unsigned long long start_time) {
	const unsigned long IMG_SIZE = 7220;
	unsigned long long elapsed_time;
	unsigned char img[IMG_SIZE];

	int write_image() {

	   FILE* fd;

	   if((fd = fopen(file_name, "wb")) == NULL){
			printf("Can't open the output file to write\n");
			return 1;
	   }

	   fprintf(fd, "%s\n%d %d\n%d\n", "P5", 76, 95, 255);

	   fwrite(img, IMG_SIZE, 1, fd);
	   fclose(fd);

		return 0;
	}

	void main(void) {
		int i;
		for(i = 0; i < NUM_IMG; i++) {
			img_in.receive(img, IMG_SIZE);
			elapsed_time = now() - start_time;
			printf("Now in loop %d, Elapsed_time is %llu\n", i, elapsed_time);
			write_image();
		}
	}
};

behavior Main() {
	unsigned long long start_time;
	const unsigned long IMG_SIZE = 7220;
	unsigned char img[IMG_SIZE];
	char* input_filename;
	char* output_filename;
	c_handshake stimulus_to_susan;
	c_queue susan_to_monitor(IMG_SIZE);

	Stimulus stimulus_module(input_filename,stimulus_to_susan, img, start_time);
	susan susan_module(stimulus_to_susan, susan_to_monitor, img);
	Monitor monitor_module(output_filename, susan_to_monitor, start_time);

	int main(int argc, char * argv[]) {
		input_filename = argv[1];
		output_filename = argv[2];
 
		par {
			stimulus_module.main();
			susan_module.main();
			monitor_module.main();
		}

		printf("Exiting.\n");
		return 0;
	}
};