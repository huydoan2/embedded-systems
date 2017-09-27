import "c_queue";

#define X_SIZE 76
#define Y_SIZE 95
#define unsigned char uchar

behavior get_image(i_receiver port_arr_in,
					i_sender port_arr_out)
{
	char filename[100];
	uchar img[X_SIZE*Y_SIZE];
	/*
	int getint(FILE *fd){
	   int c, i;
	   char dummy[10000];

	   c = getc(fd);
	   while (1) /* find next integer */
	   {
		  if (c == '#')    /* if we're at a comment, read to end of line */
			 fgets(dummy, 9000, fd);
		  if (c == EOF)
			 exit_error("Image %s not binary PGM.\n", "is");
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
	*/

	void main(void){
		port_arr_in.receive(filename, 80);
		/*
		FILE *fd;
		char header[100];
		int tmp;

		if((fd = fopen(filename, "r") == NULL){
			printf("Can't input image %s.\n", filename);
			exit();
		}

		header[0] = fgetc(fd);
		header[1] = fgetc(fd);
		if (!(header[0] == 'P' && header[1] == '5')){
			printf("Image %s does not have binary PGM header.\n", filename);
			exit();
		}

		getint(fd);
		getint(fd);
		tmp = getint(fd);

		if (fread(in, 1, X_SIZE * Y_SIZE, fd) == 0){
			print("Image %s is wrong size.\n", filename);
			exit();		
		}

		fclose(fd);
		*/

		port_arr_in.receive(img, X_SIZE*Y_SIZE);
		port_arr_out.send(img, X_SIZE*Y_SIZE);
	}
}