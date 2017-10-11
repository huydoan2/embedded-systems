
#include <stdio.h>

import "c_queue";
import "i_send";

import "susan.sc";
import "read_image.sc";
import "write_image.sc";


behavior design(i_send start, i_receiver env_to_design, i_sender design_to_env){

   const unsigned long IMG_SIZE = 7220; 

   unsigned char img_in[IMG_SIZE] = {0};

   c_queue read_image_to_susan(IMG_SIZE);
   c_queue susan_to_write_image(IMG_SIZE);
   

   
   //Definition of individual behaviors

   ReadImage r_image      (
            start,
			   img_in,
			   read_image_to_susan
                           );
   
   susan      s_image	   (
			   read_image_to_susan,
			   susan_to_write_image
                           );

   WriteImage w_image     (
			   susan_to_write_image,
			   design_to_env
                           );

	
   void main(void)
   {
      par{

      r_image.main();
      s_image.main();
      w_image.main();

      }		
	
   }

};
