
#include <c_typed_queue.sh>	/* make the template available */

DEFINE_I_TYPED_TRANCEIVER(rtoken, bit[8*sizeof(int)*7220])

DEFINE_I_TYPED_SENDER(rtoken, bit[8*sizeof(int)*7220])

DEFINE_I_TYPED_RECEIVER(rtoken, bit[8*sizeof(int)*7220])

DEFINE_C_TYPED_QUEUE(rtoken, bit[8*sizeof(int)*7220])

interface i_rtoken_myreceiver
{
	void receive(int *r);
};
interface i_rtoken_mysender
{
	void send(int *r);
};

channel c_rtoken_myqueue(in const unsigned long size) implements i_rtoken_mysender, i_rtoken_myreceiver
{
	c_rtoken_queue chnl(size);
	bit[8*sizeof(int)*7220] r_bit;
	
	void send(int *r)
	{
		int i,k;
		for(k=0; k<7220; k++) 
		{
			bit[8*sizeof(int)] temp;
			temp = r[k];
			for(i=0; i<(8*sizeof(int)); i++)
				r_bit[8*sizeof(int)*k+i] = temp[i]; 	 
		}
		chnl.send(r_bit);
	}
	
	void receive(int *r)
	{
		int i,k;
		chnl.receive(&r_bit);

		for(k=0; k<7220; k++) 
		{
			bit[8*sizeof(int)] temp;
			for(i=0; i<(8*sizeof(int)); i++)
				temp[i] = r_bit[8*sizeof(int)*k+i]; 	 
			r[k] = temp;
		}
	}
};
// EOF c_int_queue.sc
