
import "i_receive";
import "i_sender";

#define IMG_SIZE 7220

behavior ReadImage(i_receive start, in unsigned char img[IMG_SIZE], i_sender img_out){
	void main(void){
		unsigned char start_sig = 0;
		do{
			start.receive();
			img_out.send(img, IMG_SIZE);	
		}
		while(true);
	}
};
