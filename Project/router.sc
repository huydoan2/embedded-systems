

import "c_packet_queue";

#define MAX_DEST 1014
#define N 4


behavior Router(in int id_x, in int id_y,
				i_packet_receiver buffer,
				i_packet_sender router_pe,
				i_packet_sender s0, i_packet_sender s1, i_packet_sender s2,
				i_packet_sender s3, i_packet_sender s4, i_packet_sender s5)
	{
		int random_factor = 0;
		// Setup constants
		// w2 = w - 1, w3 = -1, w4 = -w, w5 = 1- w, w6 = 1
		int wax[6] = {4,-3,-7,-4, 3, 7};
		int way[6] = {3, 7, 4,-3,-7,-4};

		// wax[0] =  4; way[0] =  3;
		// wax[1] =  7; way[1] = -3;
		// wax[2] = -7; way[2] =  2;
		// wax[3] = -4; way[3] = -3;
		// wax[4] = -7; way[4] =  3;
		// wax[5] =  7; way[5] = -2;

		int abs(int a){
			return (a < 0) ? -a : a;
		}
		void getCoordinate(unsigned int addr, int* x, int* y){
			*x = ((int)addr) >> 24;
			*y = (((int)addr) << 8) >> 24;
		}

		unsigned int getPEAddress(unsigned int addr){
			return ((addr >> 16) & 0xFFFF );
		}

		void modulo_alpha(int x, int y, int* r_x, int* r_y, int* j){
			//int w1a_x, w1a_y, w2a_x, w2a_y, w3a_x, w3a_y, w4a_x, w4a_y, w5a_x, w5a_y, w6a_x, w6a_y;

			
			// w1a_x = -4; w1a_y =  9;
			// w2a_x =  5; w2a_x = -9;
			// w3a_x = -5; w3a_y = -4;
			// w4a_x =  4; w4a_y = -9;
			// w5a_x = -5; w5a_y =  9;
			// w6a_x =  5; w6a_y =  4;


			
			// int wax[6] = {4, -4, 5, -5, 4, -5};
			// int way[6] = {3, 9, -9, -4, -9, 9};

			*j = -1;		// v = aw^j + bw^(j+1)
			// Sector 1 or Sector 4
			if(x*y >= 0){
				if (x >= 0){
					*r_x = x;
					*r_y = y;
					*j = 0;
				}
				else{
					*r_x = abs(x);
					*r_y = abs(y);
					*j = 3;
				}
			}
			// Sector 2 or 3
			else if (x < 0 && y > 0){
				if(abs(y) > abs(x)){
					*r_x = abs(y) - abs(x);
					*r_y = abs(x);
					*j = 1;
				}
				else{
					*r_x = abs(y);
					*r_y = abs(x) - abs(y);
					*j = 2;
				}
			}
			// Sector 5 or 6
			else if( x > 0 && y < 0){
				if(abs(y) > abs(x)){
					*r_x = abs(y) - abs(x);
					*r_y = abs(x);
					*j = 4;
				}
				else{
					*r_x = abs(y);
					*r_y = abs(x) - abs(y);
					*j = 5;
				}
			}		
		}


		void send(int x, int y, i_packet_sender d1, i_packet_sender d2, packet* p){
			if(x == 0)
				d2.send(*p);
			else if(y == 0)
				d1.send(*p);
			else{
				random_factor = 1- random_factor;	// toggle this to be 0 or 1
				if(random_factor == 0)
					d1.send(*p);
				else
					d2.send(*p);
			}
		}

		void routing(int s_x, int s_y, int t_x, int t_y, packet p){
			// N= 4 => alpha = 4 + 3w
			int diff_x, diff_y;
			int r_x, r_y;
			int j;

			diff_x = t_x - s_x;
			diff_y = t_y - s_y;
			
			j = -1;

			modulo_alpha(diff_x, diff_y, &r_x, &r_y, &j);
			if(r_x + r_y >= N){
				// need to take modulus
				r_x = diff_x - wax[j];
				r_y = diff_y - way[j];
				modulo_alpha(r_x, r_y, &r_x, &r_y, &j);
			}

			
			switch(j){

				case 0:
				send(r_x, r_y, s0, s1, &p);
				break;

				case 1:
				send(r_x, r_y, s1, s2, &p);
				break;

				case 2:
				send(r_x, r_y, s2, s3, &p);
				break;

				case 3:
				send(r_x, r_y, s3, s4, &p);
				break;

				case 4:
				send(r_x, r_y, s4, s5, &p);
				break;

				case 5:
				send(r_x, r_y, s5, s0, &p);
				break;
			}

			// if(r_y == 0){
			// 	if(r_x > 0)
			// 		s0.send(p);
			// 	else
			// 		s3.send(p);
			// }
			// else if(r_x == 0){
			// 	if(r_y > 0)
			// 		s1.send(p);
			// 	else
			// 		s4.send(p);
			// }
			// else{
			// 	// When we can go both horizontally and vertially
			// 	if(r_x > 0 && r_y > 0){
			// 		if(random_factor)
			// 			s0.send(p);
			// 		else
			// 			s1.send(p);
			// 	}
			// 	else if(r_x > 0 && r_y < 0){
			// 		if(random_factor)
			// 			s0.send(p);
			// 		else
			// 			s5.send(p);
			// 	}
			// 	else if(r_x < 0 && r_y > 0){
			// 		if(random_factor)
			// 			s3.send(p);
			// 		else
			// 			s2.send(p);
			// 	}
			// 	else if(r_x < 0 && r_y < 0){
			// 		if(random_factor)
			// 			s3.send(p);
			// 		else
			// 			s4.send(p);
			// 	}
			// }

			// Sector 1 or Sector 4
			// if(diff_x*diff_y >= 0){
			// 	if(diff_y == 0){
			// 		if(diff_x >= 0)
			// 			s0.send(p, 1);
			// 		else
			// 			s3.send(p, 1);
			// 	}
			// 	else if(diff_x == 0){
			// 		if(diff_y > 0)
			// 			s1.send(p,1);
			// 		else
			// 			s4.send(p,1)
			// 	}
			// 	else{
			// 		// randomly pick one direction to go
			// 		if(random_factor == 0){
			// 			// go horizontally first
			// 			if(diff_x > 0)
			// 				s0.send(p,1);
			// 			else
			// 				s3.send(p,1);
			// 		}
			// 		else{
			// 			// go vertically first
			// 			if(diff_y > 0)
			// 				s1.send(p,1);
			// 			else
			// 				s4.send(p,1);
			// 		}

			// 	}
			// }
			// // Sector 2 or Sector 3
			// else if (x < 0 && y > 0){
			// 	if(abs(y) > abs(x)){
			// 		if(random_factor == 0){
			// 			s1.send()
			// 		}
			// 	}
			// 	else{

			// 	}
			// }
			// else if( x > 0 && y < 0){

			// }

		}

		void main(void){
			// protocol
			// struct packet{
			// 	unsigned int sender,
			// 	unsigned int time,
			// 	unsigned int dest
			// }
			while(true){
				packet p;
				unsigned int s, time, len;
				unsigned int t;
				int s_x, s_y;
				int t_x, t_y;

				buffer.receive(&p);	

				//t = getPEAddress(p.target);				
				getCoordinate(p.target, &t_x, &t_y);
				
				if(t_x == id_x && t_y == id_y){
					// Reach the target PE
					// Deliver the spike and neuron address to the PE
					router_pe.send(p);
					continue;
				}

				routing(id_x, id_y, t_x, t_y, p);
				
			}

		
	}
};
