//Synaptic model

#include <math.h>

#define Io 1
#define tau 15
#define refactory_period 20

float synaptic_current(unsigned int w, float t_diff)
{
	float I;
	I = Io*w*(exp((-t_diff)/tau) - exp(((-t_diff)*4.0)/tau));
	return I;
}
