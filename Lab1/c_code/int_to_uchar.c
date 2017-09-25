#include "int_to_uchar.h"

int_to_uchar(uchar r[76*95], uchar in[76*95], int size)
uchar *in;
int   *r, size;
{
   int i,
      max_r = r[0],
      min_r = r[0];

   for (i = 0; i<size; i++) {
      if (r[i] > max_r)
         max_r = r[i];
      if (r[i] < min_r)
         min_r = r[i];
   }

   /*printf("min=%d max=%d\n",min_r,max_r);*/

   max_r -= min_r;

   for (i = 0; i<size; i++)
      in[i] = (uchar)((int)((int)(r[i] - min_r) * 255) / max_r);
}
