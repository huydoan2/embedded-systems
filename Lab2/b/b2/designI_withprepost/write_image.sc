#include "susan.sh"

import "i_sender";
import "c_uchar7220_queue";
import "HWBus";

behavior WriteImage(ISlaveDriver in_image, i_sender out_image)
{

    void main(void) {
        
        uchar image_buffer[IMAGE_SIZE];
        
        while (true) {
            in_image.receive(&image_buffer, IMAGE_SIZE);
            out_image.send(image_buffer, sizeof(image_buffer));       
        }
    }
         
}; 
