#define ADDR_WIDTH      16u
#define DATA_WIDTH      32u

#if DATA_WIDTH == 32u
# define DATA_BYTES 4u
#elif DATA_WIDTH == 16u
# define DATA_BYTES 2u
#elif DATA_WIDTH == 8u
# define DATA_BYTES 1u
#else
# error "Invalid data width"
#endif


// Protocol primitives
interface IMasterHardwareBusProtocol
{ 
  void masterWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d);
  void masterRead (unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d);
};

interface ISlaveHardwareBusProtocol
{
  void slaveWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d);
  void slaveRead (unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d);
};

channel HWBusTLM()
	implements IMasterHardwareBusProtocol, ISlaveHardwareBusProtocol
{
	int A, D;
	event rdy, ack;

	void masterWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d)
	{
		A = a;
		D = d;
		notify rdy;
		wait ack;
		waitfor(15000);
	}

	void masterRead(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d)
	{
		A = a;
		notify rdy;
		wait ack;
		*d = D;
		waitfor(20000);
	}

	void slaveWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d)
	{
		do{
			wait rdy;
		} while(a != A);
		D = d;
		notify ack;
		waitfor(20000);
	}

	void slaveRead (unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d)
	{
		do {
			wait rdy;
		} while(a != A);
		*d = D;
		notify ack;
		waitfor(20000);
	}
};
