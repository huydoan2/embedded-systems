
#include <stdio.h>
#include "snn.sh"


interface comm_interface
{
	send_spike_packets(unsigned int neuron_addr, unsigned int dest_addr, unsigned int local_time, unsigned int t_delay);
	receiver_spike_packets(unsigned int nuron_addr, unsigned int dest_addr, unsigned int local_time, unsigned int t_delay);
}

channel comm_layer implements comm_interface
{
	
}
