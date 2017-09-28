#include <stdio.h>

import "c_queue";
import "susan.sc";


behavior Main(){
	char filename[80];
	const unsigned long SIZE = 7220;
	unsigned char img[SIZE];
	c_queue env_to_susan(SIZE);
	c_queue susan_to_env(SIZE);
	susan susan_module(env_to_susan, susan_to_env);

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

	int main(int argc, char * argv[]){
		FILE* fd;

		char header[100];
	    int  tmp;

	   if ((fd = fopen(argv[1], "rb")) == NULL)
	   		return 1;

	   /* {{{ read header */

	   header[0] = fgetc(fd);
	   header[1] = fgetc(fd);
	   if (!(header[0] == 'P' && header[1] == '5')){
	      printf("Image %s does not have binary PGM header.\n", filename);
	      return 1;
	   }

	   getint(fd);
	   getint(fd);
	   getint(fd);

	   /* }}} */

	   if (fread(img, 1, SIZE, fd) == 0){
	      printf("Image %s is wrong size.\n", filename);
	      return 1;
	   }

	   fclose(fd);

	   env_to_susan.send(img, SIZE); 
           susan_module.main(); 
           susan_to_env.receive(&img, SIZE);


	   if((fd = fopen(argv[2], "wb")) == NULL){
	   		printf("Can't open the output file to write\n");
	   		return 1;
	   }

	   fprintf(fd, "%s\n%d %d\n%d\n", "P5", 76, 95, 255);
		fwrite(img, SIZE, 1, fd);
		fclose(fd);

		return 0;
   }
};
