#include "susan.sh"

import "c_uchar7220_queue";
import "c_int7220_queue";
import "setup_brightness_lut";
import "susan_edges";
import "i_os_api";

behavior DetectEdges(i_uchar7220_receiver in_image,  i_int7220_sender out_r, i_uchar7220_sender out_mid, i_uchar7220_sender out_image, OSAPI OS_i)
{

    uchar bp[516];
        
    SetupBrightnessLut setup_brightness_lut(bp, OS_i);
    SusanEdges susan_edges(in_image, out_r, out_mid, bp, out_image, OS_i);
    
    void main(void) {
        setup_brightness_lut.main(); 
        susan_edges.main();
    }

};

behavior Edges(i_uchar7220_receiver in_image,  i_int7220_sender out_r, i_uchar7220_sender out_mid, i_uchar7220_sender out_image, OSAPI OS_i)
{

    DetectEdges detect_edges(in_image,  out_r, out_mid, out_image, OS_i);
    
    void main(void) {
        fsm{
            detect_edges: {goto detect_edges;}
        }
    }
};

