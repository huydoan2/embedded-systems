#include <stdio.h>

import "c_queue";
import "susan";

behavior Main(){
	char filename[80];
	const int SIZE = 7220;
	unsigned char img[7220];
	c_queue c_arr_main_susan(3610);
	susan susan_module(c_arr_main_susan);

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

	   if ((fd = fopen(filename, "rb")) == NULL)
	   		return

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

	   c_arr_main_susan.send(img, SIZE);
   }
};
