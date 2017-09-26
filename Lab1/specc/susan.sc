#include <stdio.h>

#define X_SIZE 76
#define Y_SIZE 95
#define ARR_QUEUE_SIZE 3610 
#define INT_QUEUE_SIZE 6

import "c_queue"

behavior Main(){
   /* {{{ vars */
	char filename[80],
		*tcp;

   FILE   *ofp;

   float  dt = 4.0;
   int    *r,
		argindex = 3,
		bt = 20,
		principle = 0,
		thin_post_proc = 1,
		three_by_three = 0,
		drawing_mode = 0,
		max_no_edges = 2650,
		i,
		x_size, y_size;
   
   // uchar in[X_SIZE * Y_SIZE];
   // uchar mid[X_SIZE * Y_SIZE];
   // uchar bp[516];
   // int   r[X_SIZE*Y_SIZE];

   c_queue c_arr_main_get_image(50);
   c_queue c_int_main_get_image(INT_QUEUE_SIZE);
   c_queue c_int_main_susan_edges(INT_QUEUE_SIZE);

   c_queue c_arr_get_image_susan_edges(ARR_QUEUE_SIZE);
   
   c_queue c_arr_setup_brightness_lut_susan_edges(ARR_QUEUE_SIZE);

   c_queue c_arr_susan_edges_susan_thin(QUEUE_SIZE);
   c_queue c_int_susan_edges_susan_thin(QUEUE_SIZE);
   c_queue c_arr_susan_thin_edge_draw(QUEUE_SIZE);
   c_queue c_int_susan_thin_edge_draw(QUEUE_SIZE);
   c_queue c_arr_edge_draw_put_image(QUEUE_SIZE);
   c_queue c_int_edge_draw_put_image(QUEUE_SIZE);

   c_queue c_arr_main_put_image(50);

   get_image spec_get_image(c_arr_main_get_image,
							c_arr_get_image_susan_edges);

   setup_brightness_lut spec_setup_brightness_lut(c_arr_setup_brightness_lut_susan_edges);

	susan_edges spec_susan_edges(c_arr_setup_brightness_lut_susan_edges,	/* for in */
								 c_arr_get_image_susan_edges,				/* for bp */
								 c_int_main_susan_edges,					/* for max_no */
								 c_arr_susan_edges_susan_thin,
								 c_int_susan_edges_susan_thin);
	
	susan_thin spec_susan_thin(c_arr_susan_edges_susan_thin,
							   c_int_susan_edges_susan_thin,
							   c_arr_susan_thin_edge_draw,
							   c_int_susan_thin_edge_draw);

	edge_draw spec_edge_draw(c_arr_susan_thin_edge_draw,
							   c_int_susan_thin_edge_draw,
							   c_arr_edge_draw_put_image,
							   c_int_edge_draw_put_image);

	put_image(c_arr_edge_draw_put_image,
			  c_int_edge_draw_put_image,
			  c_arr_main_put_image);

	
	int main(int argc, char argv[]){
		if (argc<3){
			printf("Enter input and output files\n");
			return 0;
		}

		par{
			spec_getimage.main();
			spec_setup_brightness_lut.main();
			spec_susan_edges.main();
			spec_susan_thin.main();
			spec_edge_draw.main();
			spec_put_image.main();
		}

		c_arr_main_get_image.send(filename, 80);

		retunr 0;
	}

};