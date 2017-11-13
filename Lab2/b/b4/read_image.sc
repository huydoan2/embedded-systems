#include "susan.sh"

import "i_receive";
import "c_osuchar7220_queue";
import "c_uchar7220_queue";
import "HWBus";

behavior ReadImage(i_receive start, in uchar image_buffer[IMAGE_SIZE], ISlaveDriver out_image)
{

    void main(void) {
        int i;
        uchar image_buffer_out[IMAGE_SIZE];

        while (true) {
            start.receive();
            for (i=0; i<IMAGE_SIZE; i++)
                image_buffer_out[i] = image_buffer[i];
            out_image.send(image_buffer_out, IMAGE_SIZE);       
        }
    }
         
}; 
