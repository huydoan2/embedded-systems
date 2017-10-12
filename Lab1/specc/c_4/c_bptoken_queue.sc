
#include <c_typed_queue.sh>	/* make the template available */

DEFINE_I_TYPED_TRANCEIVER(bptoken, bit[8*sizeof(unsigned char)*516])

DEFINE_I_TYPED_SENDER(bptoken, bit[8*sizeof(unsigned char)*516])

DEFINE_I_TYPED_RECEIVER(bptoken, bit[8*sizeof(unsigned char)*516])

DEFINE_C_TYPED_QUEUE(bptoken, bit[8*sizeof(unsigned char)*516])

interface i_bptoken_myreceiver
{
	void receive(unsigned char *bp);
};
interface i_bptoken_mysender
{
	void send(unsigned char *bp);
};

channel c_bptoken_myqueue(in const unsigned long size) implements i_bptoken_mysender, i_bptoken_myreceiver
{
	c_bptoken_queue chnl(size);
	//unsigned char bp[516];
	bit[8*sizeof(unsigned char)*516] bp_bit;
	
	void send(unsigned char *bp)
	{
		int i,k;
		for(k=0; k<516; k++) 
		{
			bit[8*sizeof(unsigned char)] temp;
			temp = bp[k];
			for(i=0; i<(8*sizeof(unsigned char)); i++)
				bp_bit[8*sizeof(unsigned char)*k+i] = temp[i]; 	 
		}
		chnl.send(bp_bit);
	}
	
	void receive(unsigned char* bp)
	{
		int i,k;
		chnl.receive(&bp_bit);

		for(k=0; k<516; k++) 
		{
			bit[8*sizeof(unsigned char)] temp;
			for(i=0; i<(8*sizeof(unsigned char)); i++)
				temp[i] = bp_bit[8*sizeof(unsigned char)*k+i]; 	 
			bp[k] = temp;
		}
	}
};

// EOF c_char_queue.sc
