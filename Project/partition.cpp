
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <string>
#include "snn.sh"

using namespace std;

void create_initial_partition(unsigned int num_neurons)
{
	unsigned int bin_size = (num_neurons/NUM_PE) + 1;
	unsigned int neuron_number = 1;
	unsigned int mapping[num_neurons];
	ifstream conn;
	conn.open("connectivity_matrix");
	for(int j=0; j<NUM_PE; j++)
	{
		ofstream myfile;
		string fname = "PE" + to_string((long long)j) + "_mapping"; 		
  		myfile.open(fname);
		for(int i=0; i<bin_size; i++)
		{
			string line;
			if(getline(conn, line))
			{
				myfile << line << "\n";
			}
			else break;
		}
		myfile.close();
	}
	conn.close();
	/*		
	for(int j=0; j<NUM_PE; j++)
	{
		ofstream myfile;
		string fname = "PE" + to_string((long long)j) + "_mapping"; 		
  		myfile.open(fname);
		for(int i=0; i<bin_size; i++)
		{
			if(neuron_number>num_neurons) break;
			else myfile << neuron_number << "\n";
			neuron_number++;
		}
	}	
	*/	
}

int main()
{
	int num_neurons = 31325;
	create_initial_partition(num_neurons);
	
	return 0;
	
}
