//Processing element. Contains code for a single neuron, and arrays for constants of each neuron

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include "snn.sh"
#include "neuron_model.sh"
#include "synapse_model.sh"
#include "c_packet_queue.sc"

//Global to prevent running out of stack space!!
unsigned int arr_spikes[NUM_PER_PE] = {0};
unsigned int ref_period[NUM_PER_PE] = {0};
//For each neuron, there SYN_PER_NEURON max incoming and outgoing synapses. Within the PE, the network can be fully connencted
unsigned int post_addresses[NUM_PER_PE*SYN_PER_NEURON] = {-1};
unsigned int time_arr[NUM_PER_PE] = {0};

unsigned int spikes[1000000];
unsigned int t_spikes[1000000];

unsigned int comm[NUM_PE] = {0};

behavior PE_node(unsigned int id)
{
	unsigned int my_PE_addr;

	//Local clock for each PE
	unsigned int time = 0;

	//Variable for all neurons
	unsigned int max_spikes =0;

	unsigned int spikes_i = 0;
	

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
		int first = 0, valid = 0;
		float value_f;	
		unsigned int value_i;
		int synapse = 0, neuron=1, i=0;
		input_file = fopen("connectivity_matrix_mod" ,"r");
		
        	while (EOF!=(ch=fgetc(input_file)))
		{
            		if(ch=='\n')
			{
				i=0;
				synapse=0;
				first=0;
				valid=0;
			}
			else if(ch ==' ')
			{
				unsigned int adr = 0;
                                val_string[i]='\0';
				value_i = atol(val_string);
				adr = value_i & 0xFFFF0000;
				
				if(first!=0 && valid==1)
				{
					post_addresses[SYN_PER_NEURON*neuron+synapse] = value_i;
					synapse++;
				}
				else
				{
					if(adr==PE_ADDR[my_PE_addr])
					{
						neuron = value_i & 0x0000FFFF;
						first++;
						valid = 1;
					}
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
		input_file = fopen("spike_matrix_mod", "r");
		i = 0;
		while (EOF!=(ch=fgetc(input_file)))
                {
                        if(ch=='\n')
                        {
				unsigned int adr = 0;
                                val_string[i]='\0';
                                value_i = atol(val_string);	
				adr = value_i & 0xFFFF0000;
				if(adr==PE_ADDR[my_PE_addr]) 
				{
					spikes[max_spikes] = value_i & 0x0000FFFF;
					max_spikes++;
				}
                                i=0;
					
                        }
                        else if(ch ==' ')
                        {
                                val_string[i]='\0';
				value_f = atof(val_string);
				t_spikes[max_spikes] = value_f*10; 
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

	void accumulate_stats()
	{
		FILE *out_file;
		char fname[10] = "PE";
		char result[10];
		int i;
		sprintf(result, "%u", my_PE_addr);
		if(my_PE_addr<10){
			fname[3] = result[0];
			fname[4] = '\0';
		}
		else{
			fname[3] = result[0];
			fname[4] = result[1];
			fname[5] = '\0';
		} 
		out_file = fopen(fname, "w");
	
		for(i=0; i<NUM_PE; i++)
		{
			fprintf(out_file, "%u\n", comm[i]); 
		}
		fclose(out_file);	
		
	}

	//Read input neuron map. Also set inputs if any
	void initialize()
	{
		int i,j;
		my_PE_addr = id;
		//Initialization
		for(i=0; i<NUM_PER_PE; i++)	
			for (j=0; j<SYN_PER_NEURON; j++)
				post_addresses[SYN_PER_NEURON*i + j] = 0xAAAA0000;
		
		read_mapping();
	}

	void register_spikes(unsigned int input_neuron_addr, unsigned int dest_neuron_addr, unsigned int t_local)
	{

		//DELAY 0 FOR NOW
		unsigned int t_delay = 0;

		// Register when the spike is due to arrive
		int i;	
		i = dest_neuron_addr & 0x0000FFFF;

		if(time > (t_local+t_delay)) printf("Sync error\n");

		//Model current calulation time accurately?
		//Calculate average time at which spike occured
		arr_spikes[i]++;
		time_arr[i] = ((time_arr[i]*(arr_spikes[i]-1)) + t_local)/arr_spikes[i];
	
		//WAITFOR update synapse weight here.
		waitfor(500);
	}

	void read_spikes_external()
	{
		packet p;
		int i;
		//receiver.read(p);
		register_spikes(p.sender, p.target, p.time);

		//Accumulate stats for communication
		for(i=0; i<NUM_PE; i++)
		if(PE_ADDR[i]==(p.sender & 0xFFFF0000)) {
			comm[i]++;
			break;
		}	
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
		neuron_addr = PE_ADDR[my_PE_addr] + neuron_num;
		for (j=0; j<SYN_PER_NEURON; j++)
		{
			post_addr = post_addresses[SYN_PER_NEURON*neuron_num + j];
			if(post_addr!=0xAAAA0000)
			{
				post_PE_addr = post_addr & 0xFFFF0000;
				if(post_PE_addr==PE_ADDR[my_PE_addr]) send_spikes_internal(neuron_addr, post_addr, time);
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

			if(t_arr!=0 && t_diff>=0 && t_diff<=ARR_HIST){
				waitfor(500);
			}
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
				waitfor(500);
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

			read_spikes_external();		
			compute_currents();
			compute_voltages();	
			//Advance time step now
			time = time + TIME_STEP;
		}
		
		accumulate_stats();
		print_spikes();	
	}	

};
