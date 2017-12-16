#include <c_typed_queue.sh>	/* make the template available */


typedef struct
{
	unsigned int sender;
	unsigned int time;
	unsigned int target;	
} packet;

//typedef packet_t * packet;

DEFINE_I_TYPED_TRANCEIVER(packet, packet)
DEFINE_I_TYPED_SENDER(packet, packet)
DEFINE_I_TYPED_RECEIVER(packet, packet)
DEFINE_C_TYPED_QUEUE(packet, packet)
