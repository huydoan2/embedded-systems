#include <stdio.h>

import "c_queue";
import "setup_brightness_lut.sc";
import "susan_edges.sc";
import "susan_thin.sc";
import "edge_draw.sc";


behavior susan(i_receiver read_image_to_susan, i_sender susan_to_write_image){

   //Channels for internal communication
   const unsigned long X_SIZE = 76;
   const unsigned long Y_SIZE = 95;
   const unsigned long R_SIZE = sizeof(int)*7220; 
   const unsigned long IMG_SIZE = 7220; 
   const unsigned long BP_SIZE = 516;

   c_queue c_susan_to_edge_draw_uchar(IMG_SIZE);

   c_queue c_setup_brightness_lut_to_susan_edges_uchar(BP_SIZE);
   c_queue c_susan_to_susan_edges_uchar(IMG_SIZE);

   c_queue c_susan_edges_to_susan_thin_uchar(IMG_SIZE);
   c_queue c_susan_edges_to_susan_thin_int(R_SIZE);
   
   c_queue c_susan_thin_to_edge_draw_uchar(IMG_SIZE);

   c_queue c_edge_draw_to_susan_uchar(IMG_SIZE);

   
   //Definition of individual behaviors
   setup_brightness_lut S_B_lut(
                           c_setup_brightness_lut_to_susan_edges_uchar
                           );

   susan_edges S_edges     (
                           c_susan_to_susan_edges_uchar, 
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
                           c_susan_to_edge_draw_uchar,
                           c_susan_thin_to_edge_draw_uchar,
                           c_edge_draw_to_susan_uchar
                           );

	
   void main(void)
   {
      
      unsigned char img_in[IMG_SIZE];
      unsigned char img_out[IMG_SIZE];

      read_image_to_susan.receive(img_in, IMG_SIZE);
      c_susan_to_susan_edges_uchar.send(img_in, IMG_SIZE); 
      
		
      S_B_lut.main();
      S_edges.main();
      S_thin.main();
    
      c_susan_to_edge_draw_uchar.send(img_in, IMG_SIZE); 
      E_draw.main();
	
      c_edge_draw_to_susan_uchar.receive(img_out, IMG_SIZE);	
      susan_to_write_image.send(img_out, IMG_SIZE);

   }

};
