#include <stdio.h>

#define X_SIZE 76
#define Y_SIZE 95
#define IMG_SIZE 3610 
#define R_SIZE 6
#define BP_SIZE 1500
#define MID_SIZE 3610

import "c_queue";
import "get_image";
import "setup_brightness_lut";
import "susan_edges";
import "susan_thin";
import "edge_draw";
import "put_image";


behavior susan(i_receiver env_to_susan, i_sender susan_to_env){

   //Channels for internal communication
   
   c_queue env_to_susan(IMG_SIZE);
   c_queue susan_to_env(IMG_SIZE);

   c_queue c_get_image_to_susan_edges_uchar(IMG_SIZE);
   c_queue c_get_image_to_edge_draw_uchar(IMG_SIZE);

   c_queue c_setup_brightness_lut_to_susan_edges_uchar(BP_SIZE);

   c_queue c_susan_edges_to_susan_thin_uchar(MID_SIZE);
   c_queue c_susan_edges_to_susan_thin_int(R_SIZE);
   
   c_queue c_susan_thin_to_edge_draw_uchar(MID_SIZE);

   c_queue c_edge_draw_to_put_image(IMG_SIZE);

   
   //Definition of individual behaviors
   get_image G_image       (
                           c_env_to_get_image_uchar, 
                           c_get_image_to_susan_edges_uchar,
                           c_get_image_to_edge_draw_uchar
                           );

   setup_brightness_lut S_B_lut(
                           c_setup_brightness_lut_to_susan_edges_uchar
                           );

   susan_edges S_edges     (
                           c_get_images_to_susan_edges_uchar, 
                           c_setup_brightness_lut_to_susan_edges_uchar, 
                           c_susan_edges_to_susan_thin_uchar, 
                           c_susan_edges_to_susan_thin_int
                           );
   
   susan_thin S_thin       (
                           c_susan_edges_to_susan_thin_uchar, 
                           c_susan_edges_to_susan_thin_int, 
                           c_susan_thin_to_edge_draw_uchar
                           );

   edge_draw E_draw        (
                           c_get_image_to_edge_draw_uchar,
                           c_susan_thin_to_edge_draw_uchar,
                           c_edge_draw_to_put_image_uchar
                           );

   put_image P_image       (
                           c_edge_draw_to_put_image_uchar,
                           c_susan_to_env
                           );

	
   int main(void)
   {
      
      uchar img_in[X_SIZE * Y_SIZE];
      uchar img_out[X_SIZE * Y_SIZE];

      env_to_susan.read(img_in, X_SIZE*Y_SIZE);
      env_to_susan.send(img_in, X_SIZE*Y_SIZE);

		par{
			G_image.main();
			S_B_lut.main();
			S_edges.main();
			S_thin.main();
			E_draw.main();
			P_image.main();
		}

      susan_to_env.read(img_out, X_SIZE*Y_SIZE);
      susan_to_env.send(img_out, X_SIZE*Y_SIZE);
		
      return 0;
	}

};
