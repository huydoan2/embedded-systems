//Processing element. Contains code for a single neuron, and arrays for constants of each neuron

#include<stdio.h>
#include<stdlib.h>
#include "snn.sh"
#include "neuron_model.sh"
#include "synapse_model.sh"


behavior PE_node
{
	unsigned int my_PE_addr;

	//Local clock for each PE
	unsigned int time = 0;

	//Variable for all neurons
	unsigned int spikes[100000];
	unsigned int t_spikes[100000];
	unsigned int max_spikes =0;

	unsigned int spikes_i = 0;

	unsigned int arr_spikes[NUM_PER_PE] = {0};
	unsigned int ref_period[NUM_PER_PE] = {0};

	//For each neuron, there SYN_PER_NEURON max incoming and outgoing synapses. Within the PE, the network can be fully connencted
	unsigned int post_addresses[NUM_PER_PE*SYN_PER_NEURON] = {0};
	
	unsigned int time_arr[NUM_PER_PE] = {0};

	void print_spikes()
	{	
		int i;	
		for(i=0; i<NUM_PER_PE; i++)
		{
			printf("Spikes for neuron %d: %d\n", i, spikes[i]);
		}
	}
	
	void print_times()
	{
		int i;
		for(i=0; i<NUM_PER_PE; i++)
		{
			printf("Spike for neuron %d at %fms\n", i, time*0.1);
		}
	}

	void read_mapping()
	{
		FILE *input_file;
		char val_string[8000];
		char ch;
		int first = 0;
		float value;
		int synapse = 0, neuron=1, i=0;
		input_file = fopen("PE0_mapping" ,"r");
		
        	while (EOF!=(ch=fgetc(input_file)))
		{
            		if(ch=='\n')
			{
				i=0;
				synapse=0;
				first=0;
			}
			else if(ch ==' ')
			{
            			val_string[i]='\0';
				value = atoi(val_string);
				if(first!=0)
				{
					post_addresses[SYN_PER_NEURON*neuron+synapse] = value;
					synapse++;
				}
				else
				{
					neuron = value;
					first++;
				}
 
				i=0;	
			}
			else
			{
				val_string[i]=ch;
				i++;
			}		
        	}

		fclose(input_file);

		neuron = 0;
		input_file = fopen("PE0_spikes", "r");
		while (EOF!=(ch=fgetc(input_file)))
                {
                        if(ch=='\n')
                        {
                                i=0;
                                first=0;
				max_spikes++;
                        }
                        else if(ch ==' ')
                        {
                                val_string[i]='\0';
                                value = atof(val_string);
				if(first==0) 
				{
					t_spikes[max_spikes] = value*10; 
					first++;
				}
				else spikes[max_spikes] = value;
	
                                i=0;
                        }
                        else
                        {
                                val_string[i]=ch;
                                i++;
                        }
                }
                fclose(input_file);
    	}


	//Read input neuron map. Also set inputs if any
	void initialize()
	{
		int i,j;
		my_PE_addr = 0;
		//Initialization	
		
		read_mapping();
	}

	void register_spikes(unsigned int neuron_addr, unsigned int post_addr, unsigned int t_local)
	{

		//DELAY 0 FOR NOW
		unsigned int t_delay = 0;

		// Register when the spike is due to arrive
		int i;	
		i = post_addr & 0x0000FFFF;

		if(time > (t_local+t_delay)) printf("Sync error\n");

		//Model current calulation time accurately?
		//Calculate average time at which spike occured
		arr_spikes[i]++;
		time_arr[i] = ((time_arr[i]*(arr_spikes[i]-1)) + t_local)/arr_spikes[i];


	}

	void send_spikes_external(unsigned int neuron_addr, unsigned int post_addr, unsigned int t_local) {}

	void send_spikes_internal(unsigned int neuron_addr, unsigned int post_addr, unsigned int t_local)
	{
		register_spikes(neuron_addr, post_addr, t_local);	
	}

	void send_spikes(unsigned int neuron_num)
	{
		int j;
		unsigned int neuron_addr, post_addr, post_PE_addr;
		neuron_addr = (my_PE_addr << 16) + neuron_num;
		for (j=0; j<SYN_PER_NEURON; j++)
		{
			post_addr = post_addresses[SYN_PER_NEURON*neuron_addr + j];
			if(post_addr!=0)
			{
				post_PE_addr = post_addr >> 16;
				if(post_PE_addr==my_PE_addr) send_spikes_internal(neuron_num, post_addr, time);
				else send_spikes_external(neuron_addr, post_addr, time);
			}
		}
	}


	void compute_currents()
	{
		//We get the synaptic current by adding the contribution of all previous spikes (upto 50ms ago)
		//Note that spikes are added to time_arr array based on their weight (if the input spike's weight is equal)
		//In case of too many spikes, simply update the array of the next match. In any case, will never go out of space!

		int i;
		for(i=0; i<NUM_PER_PE; i++)
		{	
			unsigned int t_arr, t_diff;
			t_arr  = time_arr[i];
			t_diff = time - t_arr;

			if(t_arr!=0 && t_diff>=0 && t_diff<=ARR_HIST){}
				//Add delay based on number in arr_spikes[i]
				//I = I + synaptic_current(w, t_diff*0.1);
			else if(t_arr!=0 && t_diff>ARR_HIST)
				arr_spikes[i] = 0;
				
		}
		
	}

	void compute_voltages()
	{
		int i;
		unsigned int n;
		printf("At time %f", time*0.1);

		//Iterate over all neurons 
		for(i=0; i<NUM_PER_PE; i++)
		{

			if(ref_period[i]!=0)
			{
				ref_period[i]--;
			}
			
			else
			{
				//Computation delay added here
				//Replay spikes
				if(t_spikes[spikes_i]==time)
				{
					n = spikes[spikes_i];
					if(ref_period[n]!=0) ref_period[n]--;
					else{
					
						ref_period[n] = 200;
						send_spikes(n);
						spikes_i++;
						printf(" %d Spiked ", n);
						//print_times();
					}
				}
			}

		}
		printf("\n");
	}
	void main()
	{
		
		initialize();
		 
		//Time step
		while(time<SIM_TIME)
		{
		
			compute_currents();
			compute_voltages();	
			//Advance time step now
			time = time + TIME_STEP;
		}
		
		print_spikes();	
	}	

};
