
// Specification for the neuron model. This includes the constants for calculation of the slope
// We use the base eqaution C.dV(t)/dt = -gL.(V(t)-EL) + I(t)
// We use gradient descent to step over voltage values to perform integration
// I is in pA
// V is in mV

#define C 300
#define gL 30
#define EL -70
#define VT 20

float LIF_slope(float V, float I)
{
	float ret_val, t1, t2;
	t1 = V-EL;
	t2 = I - gL*t1; 
	ret_val = t2/C;
	return ret_val;
}



