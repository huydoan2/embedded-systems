
#include <c_typed_queue.sh>	/* make the template available */

DEFINE_I_TYPED_TRANCEIVER(tmtoken, bit[8*sizeof(unsigned long long int)])

DEFINE_I_TYPED_SENDER(tmtoken, bit[8*sizeof(unsigned long long int)])

DEFINE_I_TYPED_RECEIVER(tmtoken, bit[8*sizeof(unsigned long long int)])

DEFINE_C_TYPED_QUEUE(tmtoken, bit[8*sizeof(unsigned long long int)])


// EOF c_int_queue.sc
