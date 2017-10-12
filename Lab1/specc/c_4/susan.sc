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

   c_queue c_setup_brightness_lut_to_susan_edges_bp(BP_SIZE);

   c_queue c_susan_edges_to_susan_thin_img(IMG_SIZE);
   c_queue c_susan_edges_to_susan_thin_mid(IMG_SIZE);
   c_queue c_susan_edges_to_susan_thin_r(R_SIZE);
   
   c_queue c_susan_thin_to_edge_draw_img(IMG_SIZE);
   c_queue c_susan_thin_to_edge_draw_mid(IMG_SIZE);

   
   //Definition of individual behaviors
   setup_brightness_lut S_B_lut(
                           c_setup_brightness_lut_to_susan_edges_bp
                           );

   susan_edges S_edges     (
                           read_image_to_susan, 
                           c_setup_brightness_lut_to_susan_edges_bp, 
                           c_susan_edges_to_susan_thin_img, 
                           c_susan_edges_to_susan_thin_mid, 
                           c_susan_edges_to_susan_thin_r
                           );
   
   susan_thin S_thin       (
                           c_susan_edges_to_susan_thin_img, 
                           c_susan_edges_to_susan_thin_mid, 
                           c_susan_edges_to_susan_thin_r, 
                           c_susan_thin_to_edge_draw_img,
                           c_susan_thin_to_edge_draw_mid
                           );

   edge_draw E_draw        (
                           c_susan_thin_to_edge_draw_img,
                           c_susan_thin_to_edge_draw_mid,
                           susan_to_write_image
                           );

	
   void main(void)
   {
      
      unsigned char img_in[IMG_SIZE];
      unsigned char img_out[IMG_SIZE];

      while(true) {
         S_B_lut.main();
         S_edges.main();
         S_thin.main();
         E_draw.main();
      }

   }

};
