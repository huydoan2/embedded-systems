

import "c_packet_queue";

#define MAX_DEST 1014
#define N 5


behavior Router(int x, int y,
				i_packet_receiver buffer,
				i_packet_sender router_pe,
				i_packet_sender r0, i_packet_sender r1, i_packet_sender r2,
				i_packet_sender r3, i_packet_sender r4, i_packet_sender r5)
	{
		int random_factor = 0;

		int abs(int x){
			return (x < 0) ? -x : x;
		}
		void getCoordinate(unsigned int addr, int* x, int* y){
			*x = ((int)addr) >> 24;
			*y = (((int)addr) << 8) >> 24;
		}

		unsigned int getPEAddress(unsigned int addr){
			return ((addr >> 16) & 0xFFFF )
		}

		void routing(int s_x, int s_y, int t_x, int t_y, packet* p){
			// N= 4 => alpha = 4 + 3w
			int diff_x = t_x - s_x;
			int diff_y = t_y - s_y;
			int r_x, r_y;
			//int w1a_x, w1a_y, w2a_x, w2a_y, w3a_x, w3a_y, w4a_x, w4a_y, w5a_x, w5a_y, w6a_x, w6a_y;

			// Setup constants
			// w2 = w - 1, w3 = -1, w4 = -w, w5 = 1- w, w6 = 1
			// w1a_x = -4; w1a_y =  9;
			// w2a_x =  5; w2a_x = -9;
			// w3a_x = -5; w3a_y = -4;
			// w4a_x =  4; w4a_y = -9;
			// w5a_x = -5; w5a_y =  9;
			// w6a_x =  5; w6a_y =  4;

			int wax[6] = {5, -4, 5, -5, 4, -5};
			int way[6] = {4, 9, -9, -4, -9, 9};

			int j = -1;		// v = aw^j + bw^(j+1)
			// Sector 1 or Sector 4
			if(diff_x*diff_y >= 0){
				if (diff_x >= 0){
					r_x = diff_x;
					r_y = diff_y;
					j = 0;
				}
				else{
					r_x = abs(x);
					r_y = asb(y);
					j = 3;
				}
			}
			// Sector 2 or 3
			else if (x < 0 && y > 0){
				if(abs(y) > abs(x)){
					r_x = abs(y) - abs(x);
					r_y = abs(x);
					j = 1;
				}
				else{
					r_x = abs(x) - abs(y);
					r_y = abs(y);
					j = 2;
				}
			}
			// Sector 5 or 6
			else if( x > 0 && y < 0){
				if(abs(y) > abs(x)){
					r_x = abs(y) - abs(x);
					r_y = abs(x);
					j = 4;
				}
				else{
					r_x = abs(x) - abs(y);
					r_y = abs(y);
					j = 5;
				}
			}

			if(r_x + r_y > N){
				// need to take modulus
				r_x = diff_x - wax[j];
				r_y = diff_y - way[j];
			}


			random_factor = 1- random_factor;	// toggle this to be 0 or 1
			if(r_y == 0){
				if(r_x > 0)
					s0.send(p, 1);
				else
					s3.send(p, 1);
			}
			else if(r_x == 0){
				if(r_y > 0)
					s1.send(p,1);
				else
					s4.send(p,1);
			}
			else{
				// When we can go both horizontally and vertially
				if(random_factor == 0){
					// Decide to go horizontally
					if(r_x > 0)
						s0.send(p, 1);
					else
						s3.send(p, 1);					
				}
				else{
					if(r_y > 0)
						s1.send(p,1);
					else
						s4.send(p,1);
				}
			}

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

		void Main(void){
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

				buffer.receive(&p, 1);	

				t = getPEAddress(p.dest);				
				getCoordinate(t, &t_x, &t_y);
				
				if(t_x == x && t_y == y){
					// Reach the target PE
					// Deliver the spike and neuron address to the PE
					router_pe.send(&p, 1);
					continue;
				}

				routing(x, y, t_x, t_y, &p);
				
			}

		
	}
}