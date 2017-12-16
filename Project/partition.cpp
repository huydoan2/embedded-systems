
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include "snn.sh"

using namespace std;

void create_initial_partition(unsigned int num_neurons)
{
	unsigned int bin_size = (num_neurons/NUM_PE) + 1;
	unsigned int neuron_number = 1;
	unsigned int mapping[num_neurons];
	unsigned int offset;
	ifstream conn;
	conn.open("connectivity_matrix");
	for(int j=0; j<1; j++)
	{
		ofstream myfile;
		string fname = "connectivity_matrix_mod"; 		
  		myfile.open(fname);
		string line;
		while(getline(conn, line))
		{
			string token;
			istringstream iss(line);
			while(getline(iss, token, ' '))
			{
				unsigned int tk = stol(token);
				unsigned int PE_addr = PE_ADDR[(tk/bin_size)];
				unsigned int addr = tk%bin_size;
				addr = PE_addr + addr;	
				myfile << addr << " ";
			}
			myfile << "\n";
		}

		myfile.close();
	}
	conn.close();
	
	ifstream spike;
	spike.open("spike_matrix");
	for(int j=0; j<1; j++)
	{
		ofstream myfile;
		string fname = "spike_matrix_mod"; 		
  		myfile.open(fname);
		string line;
		while(getline(spike, line))
		{
			string token;
			istringstream iss(line);	
			
			getline(iss, token, ' ');
			myfile << token << " ";
			getline(iss, token, ' ');
		
			unsigned int tk = stol(token);
			unsigned int PE_addr = PE_ADDR[(tk/bin_size)];
			unsigned int addr = tk%bin_size;
			addr = PE_addr + addr;	
			myfile << addr;	
			myfile << "\n";
		}
		

		myfile.close();
	}
	spike.close();
}

int main()
{
	int num_neurons = 31325;
	create_initial_partition(num_neurons);

	//
	
	return 0;
	
}
