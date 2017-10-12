#include <stdio.h>
#include<sim.sh>

import "c_imtoken_queue";
import "c_tmtoken_queue";

behavior Monitor(in char * file_name, i_tmtoken_receiver stimulus_time, i_imtoken_myreceiver img_in){
	const int IMG_SIZE = 7220;
	unsigned long long start_time = 0;
	unsigned long long elapsed_time = 0;
	unsigned char img[IMG_SIZE];
	bit[8*sizeof(unsigned long long int)] temp;
	FILE* fd;


	void main(void){
		while(true){
			stimulus_time.receive(&temp);
			start_time = temp;
			img_in.receive(img);

			elapsed_time = now() - start_time;

			printf("Delay time: %llu\n", elapsed_time);

			
			if((fd = fopen(file_name, "wb")) == NULL){
		   		printf("Can't open the output file to write\n");
		   		continue;
		   }

		   fprintf(fd, "%s\n%d %d\n%d\n", "P5", 76, 95, 255);

			fwrite(img, IMG_SIZE, 1, fd);
			fclose(fd);			
		}
	}
};
