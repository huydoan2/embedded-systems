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
		waitfor(1000);
		notify rdy;
		wait ack;
	}

	void masterRead(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d)
	{
		A = a;
		notify rdy;
		wait ack;
		waitfor(1000);
		*d = D;
	}

	void slaveWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d)
	{
		do{
			wait rdy;
		} while(a != A);
		waitfor(2000);
		D = d;
		notify ack;
	}

	void slaveRead (unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d)
	{
		do {
			wait rdy;
		} while(a != A);
		waitfor(2000);
		*d = D;
		notify ack;
	}
};
