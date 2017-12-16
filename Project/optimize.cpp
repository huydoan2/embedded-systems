
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
/*
struct pair
{
	unsigned int a;
	unsigned int b;
};
*/
int main()
{
	int num_neurons = 31325;
/*
	vector<pair> repeat;
	
	vector<vector <unsigned int>> traffic  = (37, vector<unsigned int>(37));

	for(int i=0; i<37; i++)
	{
		ifstream comm;
		string filname = "PE" + to_string((long long) i);
		string line;
		comm.open(filename);
		int j=0;
		while(getline(comm, line))
		{
			traffic[i][j] = stol(line);
			j++;
		}
		comm.close(); 	
	}		

	vector<unsigned int> aggr_traffic;
	for(int i=0; i<37; i++)
		for(int j=0; j<47; j++)
			aggr_traffic[i] = addr_traffic[i]+traffic[i][j];
	
	int max = 0;
	int max_i = 0;
	for(int i=0; i<37; i++)
	{
		if(max_1 < aggr_traffic[i]) 
		{
			max_1 = aggr_traffic;	
			max_i = i;
		}
	}
	aggr_traffic[max_i] = 0;

	int max2 = 0;
	int max_2i = 0;
	for(int i=0; i<37; i++)
	{
		if(max_2 < aggr_traffic[i]) 
		{
			max_2 = aggr_traffic;	
			max_2i = 2i;
		}
	}

	srand(time(NULL));
	int n1 = 0;
	n2 = 0;

	while((match(reapeat, n1, n2);
	
	int n1 = rand%(num_neurons/37)
	int n2 = rand%(num_neurons/37)


	pair p;
	p.a = n1;
	p.b = n2;
	repeat.push_back(pair)	
*/



	exchange_neurons(0x00000001, 0x01000001);

	//
	
	return 0;
	
}
