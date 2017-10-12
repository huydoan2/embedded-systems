#include <stdio.h>

import "c_imtoken_queue";
import "c_bptoken_queue";
import "c_rtoken_queue";

import "setup_brightness_lut.sc";
import "susan_edges.sc";
import "susan_thin.sc";
import "edge_draw.sc";


behavior susan(i_imtoken_myreceiver read_image_to_susan, i_imtoken_mysender susan_to_write_image){

   //Channels for internal communication
   const unsigned long X_SIZE = 76;
   const unsigned long Y_SIZE = 95;
   const unsigned long R_SIZE = sizeof(int)*7220; 
   const unsigned long IMG_SIZE = 7220; 
   const unsigned long BP_SIZE = 516;
   const unsigned long QUEUE_SIZE = 1; 

   c_bptoken_myqueue c_setup_brightness_lut_to_susan_edges_bp(QUEUE_SIZE);

   c_imtoken_myqueue c_susan_edges_to_susan_thin_img(QUEUE_SIZE);
   c_imtoken_myqueue c_susan_edges_to_susan_thin_mid(QUEUE_SIZE);
   c_rtoken_myqueue c_susan_edges_to_susan_thin_r(QUEUE_SIZE);
   
   c_imtoken_myqueue c_susan_thin_to_edge_draw_img(QUEUE_SIZE);
   c_imtoken_myqueue c_susan_thin_to_edge_draw_mid(QUEUE_SIZE);

   
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
      
      while(true)
      {   
         par{		
           	S_B_lut.main();
      		S_edges.main();
      		S_thin.main();
      		E_draw.main();
	    }
      }
   }

};

behavior susan_fsm(i_imtoken_myreceiver read_image_to_susan, i_imtoken_mysender susan_to_write_image)
{
	susan S0(read_image_to_susan, susan_to_write_image);

	void main(void)
	{
		fsm {S0: goto S0;}
	}	
};


