
#include <stdio.h>

import "c_imtoken_queue";
import "i_send";

import "susan.sc";
import "read_image.sc";
import "write_image.sc";


behavior Design(i_receive start, in unsigned char img_in[7220], i_imtoken_mysender design_to_env){

   const unsigned long IMG_SIZE = 7220; 
   const unsigned long QUEUE_SIZE = 1; 

   c_imtoken_myqueue read_image_to_susan(QUEUE_SIZE);
   c_imtoken_myqueue susan_to_write_image(QUEUE_SIZE);
   

   
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
