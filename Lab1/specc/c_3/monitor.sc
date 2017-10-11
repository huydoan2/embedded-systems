#include <stdio.h>
#include<sim.sh>

import "i_receiver";

behavior Monitor(in char * file_name, i_receiver stimulus_time, i_receiver img_in){
	const int IMG_SIZE = 7220;
	unsigned long long start_time = 0;
	unsigned long long elapsed_time = 0;
	unsigned char img[IMG_SIZE];
	FILE* fd;


	void main(void){
		while(true){
			stimulus_time.receive(&start_time, sizeof(start_time));
			img_in.receive(img, IMG_SIZE);

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
