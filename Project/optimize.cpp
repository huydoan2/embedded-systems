
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include "snn.sh"

using namespace std;

void exchange_neurons(unsigned int neuron_1, unsigned int neuron_2)
{
	unsigned int dummy = 0xAA000000;
	
	vector<string> connectivity;
	ifstream conn;
	conn.open("connectivity_matrix_mod");
	
	ofstream myfile;
  	myfile.open("connectivity_matrix_mod_out"); 		
	string line;

	while(getline(conn, line))
	{
		string token;
		string line_rep;
		istringstream iss(line);
		while(getline(iss, token, ' '))
		{
			unsigned int tk = stol(token);
			if(tk==neuron_1) tk = neuron_2;
			else if(tk==neuron_2) tk = neuron_1;	
			myfile << tk << " ";
		}
		myfile << "\n";
	}
	myfile.close();
	

	ifstream spike;
	ofstream spike_out;
	spike.open("spike_matrix_mod");
	spike_out.open("spike_matrix_mod_out");
	vector<string> sp;
	while(getline(spike, line))
	{
		string token;
		istringstream iss(line);
		getline(iss, token, ' ');
		spike_out << token << " ";

		getline(iss, token, ' ');
		
		unsigned int tk = stol(token);
		if(tk==neuron_1) tk = neuron_2;	
		else if(tk==neuron_2) tk = neuron_1;
		spike_out << tk << "\n";
	}
	spike.close();
	spike_out.close();


}

int main()
{
	int num_neurons = 31325;
	exchange_neurons(0x00000001, 0x01000001);

	//
	
	return 0;
	
}
