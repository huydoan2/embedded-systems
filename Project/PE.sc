//Processing element. Contains code for a single neuron, and arrays for constants of each neuron

#include<stdio.h>
#include "snn.sh"
#include "neuron_model.sh"
#include "synapse_model.sh"


behavior PE_node
{
	unsigned int my_PE_addr;

	float I_in[NUM_PER_PE][SIM_TIME+1] = {{0}};

	//Local clock for each PE
	unsigned int time = 0;

	//Variable for all neurons
	float V_array[NUM_PER_PE] = {0};
	float I_array[NUM_PER_PE] = {0};
	unsigned int spikes[NUM_PER_PE] = {0};

	unsigned int ref_period[NUM_PER_PE] = {0};

	//For each neuron, there SYN_PER_NEURON max incoming and outgoing synapses. Within the PE, the network can be fully connencted
	//A weight of 0 indicates no connection. We store the connections only for outgoing synapses
	unsigned int post_addresses[NUM_PER_PE*SYN_PER_NEURON] = {0};
	unsigned int post_delays[NUM_PER_PE*SYN_PER_NEURON] = {0};
	
	unsigned int pre_addresses[NUM_PER_PE*SYN_PER_NEURON] = {0};
	unsigned int pre_weights[NUM_PER_PE*SYN_PER_NEURON] = {0};
	
	//Vector for storing arriving spikes. In gneral, this would have very few entries (corresponding to input spikes).
	// At most, all incoming synapses spike for Tmax = 50ms (we only store spikes upto 5ms earlier since contribution of the rest is minimal) 
	unsigned int time_arr[NUM_PER_PE*SYN_PER_NEURON*ARR_HIST] = {0};

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

	//Read input neuron map. Also set inputs if any
	void initialize()
	{
		int i,j;
		my_PE_addr = 0;
		//Initialization	
		
		for(i=0; i<NUM_PER_PE; i++)
		{
			for(j=1; j<11; j++)
			{
				I_in[4][j] = 50000;
				I_in[3][3+j] = 50000;
				I_in[2][7+j] = 50000;
			}	

			V_array[i] = EL;
			spikes[i] = 0;
		}

		post_addresses[SYN_PER_NEURON*2] = 1;
		post_addresses[SYN_PER_NEURON*2+1] = 5;
		post_addresses[SYN_PER_NEURON*3] = 1;
		post_addresses[SYN_PER_NEURON*3+1] = 5;
		post_addresses[SYN_PER_NEURON*4] = 1;
		post_addresses[SYN_PER_NEURON*4+1] = 5;
		
		post_delays[SYN_PER_NEURON*2] = 1;
		post_delays[SYN_PER_NEURON*2+1] = 8;
		post_delays[SYN_PER_NEURON*3] = 5;
		post_delays[SYN_PER_NEURON*3+1] = 5;
		post_delays[SYN_PER_NEURON*4] = 9;
		post_delays[SYN_PER_NEURON*4+1] = 1;

		pre_addresses[SYN_PER_NEURON*1] = 2;
		pre_addresses[SYN_PER_NEURON*1+1] = 3;
		pre_addresses[SYN_PER_NEURON*1+2] = 4;	
		pre_addresses[SYN_PER_NEURON*5] = 2;
		pre_addresses[SYN_PER_NEURON*5+1] = 3;
		pre_addresses[SYN_PER_NEURON*5+2] = 4;
		 
		pre_weights[SYN_PER_NEURON*1] = 3000; 
		pre_weights[SYN_PER_NEURON*1+1] = 3000;
		pre_weights[SYN_PER_NEURON*1+2] = 3000;
		pre_weights[SYN_PER_NEURON*5] = 3000;
		pre_weights[SYN_PER_NEURON*5+1] = 3000; 
		pre_weights[SYN_PER_NEURON*5+2] = 3000;
		
	}

	void register_spikes(unsigned int neuron_addr, unsigned int pre_addr, unsigned int t_delay)
	{
		int i,j,k;
		unsigned int pre;
		//Determined by NUM_PER_PE and SYN_PER_NEURON
		i = neuron_addr & 0x0000FFFF;
		pre = pre_addr & 0x0000FFFF;

		//If not enough space, the spike is lost
		for(j=0; j<SYN_PER_NEURON; j++)
		{
			char end = 0;
			if(pre_addresses[SYN_PER_NEURON*i+j]==pre)
			{
				for(k=0; k<ARR_HIST; k++)
				{
					if(time_arr[SYN_PER_NEURON*ARR_HIST*i + ARR_HIST*j + k]==0)
					{
						time_arr[SYN_PER_NEURON*ARR_HIST*i + ARR_HIST*j + k] = time + t_delay*10;
						end = 1;
						break;
					}
				}
			}
			if(end==1) break;
		}
	}

	void send_spikes_external(unsigned int neuron_addr, unsigned int post_addr, unsigned int t_delay) {}

	void send_spikes_internal(unsigned int neuron_num, unsigned int post_addr, unsigned int t_delay)
	{
		register_spikes(post_addr, neuron_num, t_delay);	
	}

	void send_spikes(unsigned int neuron_num)
	{
		int j;
		unsigned int neuron_addr, post_addr, post_PE_addr, t_delay;
		neuron_addr = (my_PE_addr << 16) + neuron_num;
		for (j=0; j<SYN_PER_NEURON; j++)
		{
			post_addr = post_addresses[SYN_PER_NEURON*neuron_addr + j];
			if(post_addr!=0)
			{
				t_delay = post_delays[SYN_PER_NEURON*neuron_addr + j];
				post_PE_addr = post_addr >> 16;
				if(post_PE_addr==my_PE_addr) send_spikes_internal(neuron_num, post_addr, t_delay);
				else send_spikes_external(neuron_addr, post_addr, t_delay);
			}
		}
	}


	void compute_current()
	{
		//We get the synaptic current by adding the contribution of all previous spikes (upto 50ms ago)
		//Note that spikes are added to time_arr array based on their weight (if the input spike's weight is equal)
		//In case of too many spikes, simply update the array of the next match. In any case, will never go out of space!

		int i,j,k;
		for(i=0; i<NUM_PER_PE; i++)
		{
			float I;
			I_array[i] = I_in[i][time];
			I =  I_array[i];		
			for(j=0; j<SYN_PER_NEURON; j++)
			{
				unsigned int w;
				w = pre_weights[SYN_PER_NEURON*i + j]; 
				//Can add condition on w
				for(k=0; k<ARR_HIST; k++)
				{
					unsigned int t_arr;
					int t_diff;
					t_arr = time_arr[SYN_PER_NEURON*ARR_HIST*i + ARR_HIST*j + k];
					t_diff = time - t_arr;

					//Beyond this, the current contribution is negligible
					if(t_arr!=0 && t_diff>=0 && t_diff<=ARR_HIST) 
						I = I + synaptic_current(w, t_diff*0.1);
					else if(t_arr!=0 && t_diff>ARR_HIST)
						time_arr[SYN_PER_NEURON*ARR_HIST*i + SYN_PER_NEURON*ARR_HIST*j + k] = 0;
				
				}
			}
			I_array[i] = I;
		}
		
	}

	void compute_voltages()
	{
		int i;
		printf("At time %f", time*0.1);

		//Iterate over all neurons 
		for(i=0; i<NUM_PER_PE; i++)
		{

			//Standard Eulers for intergration
			float V,I, V_next;
			float k1;
			
			V = V_array[i];
			I = I_array[i]; 
		
			if(ref_period[i]!=0)
			{
				V_next = EL;
				ref_period[i]--;
			}
			
			else
			{
				float k2;
				k1 = LIF_slope(V, I);
				V_next = V + k1*TIME_STEP*0.1;
				//k2 = LIF_slope(V_next, I_in[i][time+1]);
				//V_next = V + ((k1+k2)/2)*TIME_STEP*0.1;	

				if(V_next > VT)
				{
					V_next = EL;
					spikes[i]++;
					ref_period[i] = refactory_period;
					send_spikes(i);
					printf("%d Spiked", i);
					//print_times();
				}
			}

			V_array[i] = V_next;
			printf("%f ", V_array[i]);
		}
		printf("\n");
	}

	void main()
	{
		
		initialize();
		 
		//Time step
		while(time<SIM_TIME)
		{
			compute_current(); 
			compute_voltages();
		
			//Advance time step now
			time = time + TIME_STEP;
		}
		
		print_spikes();	
	}	

};
