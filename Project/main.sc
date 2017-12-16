// Main file. Specifies the network architecture. Default: router based NOC

#include "PE_replay.sc"

behavior Main()

{

	unsigned int i = 0;
	PE_node PE_0(i);

	int main()
	{
		PE_0.main();		
		return 0;
	}	

};

