#include "susan.sh"

import "c_osuchar7220_queue";
import "c_osint7220_queue";
import "c_uchar7220_queue";
import "setup_brightness_lut";
import "susan_edges";
import "i_os_api";
import "HWBus";

behavior DetectEdges(IMasterDriver in_image,  i_osint7220_sender out_r, i_osuchar7220_sender out_mid, i_osuchar7220_sender out_image, OSAPI OS_i)
{

    uchar bp[516];
        
    SetupBrightnessLut setup_brightness_lut(bp, OS_i);
    SusanEdges susan_edges(in_image, out_r, out_mid, bp, out_image, OS_i);
    
    void main(void) {
        setup_brightness_lut.main(); 
        susan_edges.main();	
    }

};

behavior Edges(IMasterDriver in_image,  i_osint7220_sender out_r, i_osuchar7220_sender out_mid, i_osuchar7220_sender out_image, OSAPI OS_i)
{

    DetectEdges detect_edges(in_image,  out_r, out_mid, out_image, OS_i);
    
    void main(void) {
	
	Task se;
	se = OS_i.task_create("se", 1);
	OS_i.task_start(se);
        
	fsm{
            detect_edges: {goto detect_edges;}
        }
	OS_i.task_terminate();
    }
};

