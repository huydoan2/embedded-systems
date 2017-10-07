
import "c_double_handshake";

#define IMG_SIZE 7220

behavior ReadImage(i_receiver start, in unsigned char img[IMG_SIZE], i_sender img_out){
	void main(void){
		unsigned char start_sig = 0;
		do{
			start.receive(&start_sig, sizeof(start_sig));
			img_out.send(img, IMG_SIZE);	
		}
		while(start_sig);
	}
};