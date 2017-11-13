#include "susan.sh"

import "detect_edges";
import "susan_thin";
import "edge_draw";
import "read_image";
import "write_image";
import "c_osuchar7220_queue";
import "c_osint7220_queue";
import "c_uchar7220_queue";
import "i_os_api";
import "speccos";
import "HWBus";

behavior TASK_PE1(IMasterDriver image,
                  OSAPI OS_i){

    c_osint7220_queue r(1ul, OS_i);
    c_osuchar7220_queue mid(1ul, OS_i);
    c_osuchar7220_queue mid_edge_draw(1ul, OS_i);
    c_osuchar7220_queue image_edge_draw(1ul, OS_i);

    Edges edges(image, r, mid, image_edge_draw, OS_i);
    Thin thin(r, mid, mid_edge_draw, OS_i);
    Draw draw(image_edge_draw, mid_edge_draw, image, OS_i);

    void main(void){
	
	int id;
	Task t;
	t = OS_i.task_create("pe1", 1);
	OS_i.task_start(t);
	id = OS_i.par_start();
	par {
		edges.main();
		thin.main();
		draw.main();
	     }
	OS_i.par_end(id);	
	OS_i.task_terminate();
	}
};

behavior PE1(IMasterDriver image, OSAPI OS_i)
{
	TASK_PE1 task_pe1(image, OS_i);

	void main(void)
	{
		task_pe1.main();
	}	
};

behavior INPUT(i_receive start,
                in uchar image_buffer[IMAGE_SIZE],
                ISlaveDriver in_image){

    ReadImage read_image(start, image_buffer, in_image);

    void main(void){
        read_image.main();
    }
};

behavior OUTPUT(ISlaveDriver out_image,
                i_sender out_image_susan){

    WriteImage write_image(out_image, out_image_susan);

    void main(void){
        write_image.main();
    }
};

behavior Design(i_receive start, in uchar image_buffer[IMAGE_SIZE], i_sender out_image_susan){
    OS OS_i;
    signal unsigned bit[1] receive_intr_flag = 0;
    signal unsigned bit[1] send_intr_flag = 0;
    HardwareBus hwBus(OS_i);
    MasterDriver master(hwBus, receive_intr_flag, send_intr_flag);
    SlaveDriver slave(hwBus);

    ReceiveIntrController receive_intr_ctrl(hwBus, receive_intr_flag);
    SendIntrController send_intr_ctrl(hwBus, send_intr_flag);

    c_osuchar7220_queue in_image(1ul, OS_i);
    c_uchar7220_queue out_image(1ul);

    PE1 pe1(master, OS_i);
    INPUT input(start, image_buffer, slave);
    OUTPUT output(slave, out_image_susan);


    void main(void){
      OS_i.init();
      par{     
	        receive_intr_ctrl.main();
  	        send_intr_ctrl.main();
            input.main();
            pe1.main();
            output.main();
    }
}
};

