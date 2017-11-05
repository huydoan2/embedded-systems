#include "susan.sh"

import "detect_edges";
import "susan_thin";
import "edge_draw";
import "read_image";
import "write_image";
import "c_uchar7220_queue";

behavior PE1(i_uchar7220_receiver in_image, 
            i_uchar7220_sender out_image ){

    c_int7220_queue r(1ul);
    c_uchar7220_queue mid(1ul);
    c_uchar7220_queue mid_edge_draw(1ul);
    c_uchar7220_queue image_edge_draw(1ul);

    Edges edges(in_image, r, mid, image_edge_draw);
    Thin thin(r, mid, mid_edge_draw);
    Draw draw(image_edge_draw, mid_edge_draw, out_image);

    void main(void){
	fsm {
		 edges: goto thin;
            thin: goto draw;
            draw: goto edges;
	 }
	}
};

behavior INPUT(i_receive start,
                in uchar image_buffer[IMAGE_SIZE],
                i_uchar7220_sender in_image){

    ReadImage read_image(start, image_buffer, in_image);

    void main(void){
        read_image.main();
    }
};

behavior OUTPUT(i_uchar7220_receiver out_image,
                i_sender out_image_susan){

    WriteImage write_image(out_image, out_image_susan);

    void main(void){
        write_image.main();
    }
};

behavior Design(i_receive start, in uchar image_buffer[IMAGE_SIZE], i_sender out_image_susan){
    c_uchar7220_queue in_image(1ul);
    c_uchar7220_queue out_image(1ul);

    PE1 pe1(in_image, out_image);
    INPUT input(start, image_buffer, in_image);
    OUTPUT output(out_image, out_image_susan);

    void main(void){
      par{      input.main();
            pe1.main();
            output.main();
    }
}
};

// behavior Design(i_receive start, in uchar image_buffer[IMAGE_SIZE], i_sender out_image_susan)
// {

//     c_uchar7220_queue in_image(1ul);
//     c_uchar7220_queue out_image(1ul);
    
//     ReadImage read_image(start, image_buffer, in_image);
//     Susan susan(in_image, out_image);
//     WriteImage write_image(out_image, out_image_susan);

//     void main(void) {
//        par {
//             read_image.main();
//             susan.main();
//             write_image.main();
//         }
//     }
    
// };
