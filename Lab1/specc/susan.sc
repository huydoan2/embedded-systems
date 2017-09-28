#include <stdio.h>

import "c_queue";
import "get_image.sc";
import "setup_brightness_lut.sc";
import "susan_edges.sc";
import "susan_thin.sc";
import "edge_draw.sc";
import "put_image.sc";


behavior susan(i_receiver env_to_susan, i_sender susan_to_env){

   //Channels for internal communication
   const unsigned long X_SIZE = 76;
   const unsigned long Y_SIZE = 95;
   const unsigned long IMG_SIZE = 7220; 
   const unsigned long R_SIZE = 7220;
   const unsigned long BP_SIZE = 516;

   c_queue susan_to_get_image(IMG_SIZE);
   c_queue put_image_to_susan(IMG_SIZE);

   c_queue c_get_image_to_susan_edges_uchar(IMG_SIZE);
   c_queue c_get_image_to_edge_draw_uchar(IMG_SIZE);

   c_queue c_setup_brightness_lut_to_susan_edges_uchar(BP_SIZE);

   c_queue c_susan_edges_to_susan_thin_uchar(IMG_SIZE);
   c_queue c_susan_edges_to_susan_thin_int(R_SIZE);
   
   c_queue c_susan_thin_to_edge_draw_uchar(IMG_SIZE);

   c_queue c_edge_draw_to_put_image_uchar(IMG_SIZE);

   
   //Definition of individual behaviors
   get_image G_image       (
                           susan_to_get_image, 
                           c_get_image_to_susan_edges_uchar,
                           c_get_image_to_edge_draw_uchar
                           );

   setup_brightness_lut S_B_lut(
                           c_setup_brightness_lut_to_susan_edges_uchar
                           );

   susan_edges S_edges     (
                           c_get_image_to_susan_edges_uchar, 
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
                           put_image_to_susan
                           );

	
   void main(void)
   {
      
      unsigned char img_in[IMG_SIZE];
      unsigned char img_out[IMG_SIZE];

      env_to_susan.receive(img_in, IMG_SIZE);
      susan_to_get_image.send(img_in, IMG_SIZE);

		
      G_image.main();
      S_B_lut.main();
      S_edges.main();
      S_thin.main();
      E_draw.main();
      P_image.main();
		

      put_image_to_susan.receive(img_out, X_SIZE*Y_SIZE);
      susan_to_env.send(img_out, X_SIZE*Y_SIZE);
		
	}

};
