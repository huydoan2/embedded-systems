
#include <stdio.h>

import "c_queue";
import "c_double_handshake";

import "susan.sc";
import "read_image.sc";
import "write_image.sc";


behavior design(i_receiver env_to_design, i_sender design_to_env){

   const unsigned long IMG_SIZE = 7220; 

   unsigned char img_in[IMG_SIZE] = {0};

   c_double_handshake design_to_read_image_control;
   c_queue read_image_to_susan(IMG_SIZE);

   c_queue susan_to_write_image(IMG_SIZE);
   c_queue write_image_to_design(IMG_SIZE);

   
   //Definition of individual behaviors

   ReadImage r_image      (
                           design_to_read_image_control,
			   img_in,
			   read_image_to_susan
                           );
   
   susan      s_image	   (
			   read_image_to_susan,
			   susan_to_write_image
                           );

   WriteImage w_image     (
			   susan_to_write_image,
			   write_image_to_design
                           );

	
   void main(void)
   {
      
      unsigned char img_out[IMG_SIZE] = {0};
      char start = 1;     
 
      //Receive and send 1 image
      env_to_design.receive(img_in, IMG_SIZE);
      design_to_read_image_control.send(&start, 1); 
      char start = 0;     
      
      par{

      r_image.main();
      s_image.main();
      w_image.main();

      }		
	
      write_image_to_design.receive(img_out, IMG_SIZE);	
      design_to_env.send(img_out, IMG_SIZE);

   }

};
