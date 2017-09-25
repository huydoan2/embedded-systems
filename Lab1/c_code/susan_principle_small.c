#include "susan_principle_small.h"

void susan_principle_small(uchar in[76 * 95],
   int r[76 * 95],
   uchar bp[516],
   int max_no,
   int x_size,
   int y_size) {

   int   i, j, n;
   uchar *p, *cp;

   memset(r, 0, x_size * y_size * sizeof(int));

   max_no = 730; /* ho hum ;) */

   for (i = 1; i<y_size - 1; i++)
      for (j = 1; j<x_size - 1; j++) {
         n = 100;
         p = in + (i - 1)*x_size + j - 1;
         cp = bp + in[i*x_size + j];

         n += *(cp - *p++);
         n += *(cp - *p++);
         n += *(cp - *p);
         p += x_size - 2;

         n += *(cp - *p);
         p += 2;
         n += *(cp - *p);
         p += x_size - 2;

         n += *(cp - *p++);
         n += *(cp - *p++);
         n += *(cp - *p);

         if (n <= max_no)
            r[i*x_size + j] = max_no - n;
      }
}