#include <c_typed_queue.sh>	

DEFINE_I_TYPED_TRANCEIVER(imtoken, bit[8*sizeof(unsigned char)*7220])

DEFINE_I_TYPED_SENDER(imtoken, bit[8*sizeof(unsigned char)*7220])

DEFINE_I_TYPED_RECEIVER(imtoken, bit[8*sizeof(unsigned char)*7220])

DEFINE_C_TYPED_QUEUE(imtoken, bit[8*sizeof(unsigned char)*7220])

interface i_imtoken_myreceiver
{
	void receive(unsigned char *im);
};
interface i_imtoken_mysender
{
	void send(unsigned char *im);
};

channel c_imtoken_myqueue(in const unsigned long size) implements i_imtoken_mysender, i_imtoken_myreceiver
{
	c_imtoken_queue chnl(size);
	bit[8*sizeof(unsigned char)*7220] im_bit;
	
	void send(unsigned char *im)
	{
		int i,k;
		for(k=0; k<7220; k++) 
		{
			bit[8*sizeof(unsigned char)] temp;
			temp = im[k];
			for(i=0; i<(8*sizeof(unsigned char)); i++)
				im_bit[8*sizeof(unsigned char)*k+i] = temp[i]; 	 
		}
		chnl.send(im_bit);
	}
	
	void receive(unsigned char* im)
	{
		int i,k;
		chnl.receive(&im_bit);

		for(k=0; k<7220; k++) 
		{
			bit[8*sizeof(unsigned char)] temp;
			for(i=0; i<(8*sizeof(unsigned char)); i++)
				temp[i] = im_bit[8*sizeof(unsigned char)*k+i]; 	 
			im[k] = temp;
		}
	}
};

// EOF c_char_queue.sc
