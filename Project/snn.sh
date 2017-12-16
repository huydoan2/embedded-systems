
#define NUM_PE 37

#define NUM_PER_PE 1024
#define SYN_PER_NEURON 1024
#define ARR_HIST 500


//Simulation stats
#define TIME_STEP 1    //in units of 0.1ms
#define SIM_TIME 500  //in units of 0.1ms

const unsigned int PE_ADDR[NUM_PE] = {	
					0xFD030000,
					0xFE030000,
					0xFF030000,
					0x00030000,

					0xFD020000,
					0xFE020000,
					0xFF020000,
					0x00020000,
					0x01020000,
					
					0xFD010000,
					0xFE010000,
					0xFF010000,
					0x00010000,
					0x01010000,
					0x02010000,
					
					0xFD000000,
					0xFE000000,
					0xFF000000,
					0x00000000,
					0x01000000,
					0x02000000,
					0x03000000,
					
					0xFEFF0000,
					0xFFFF0000,
					0x00FF0000,
					0x01FF0000,
					0x02FF0000,
					0x03FF0000,
					
					0xFFFE0000,
					0x00FE0000,
					0x01FE0000,
					0x02FE0000,
					0x03FE0000,
	
					0x00FD0000,
					0x01FD0000,
					0x02FD0000,
					0x03FD0000
				};

