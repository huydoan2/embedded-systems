#include "susan_edge_small.h"

void susan_edges(uchar in[76 * 95], int r[76 * 95], uchar mid[76 * 95], uchar bp[516], int max_no, int x_size, int y_size) {
   float z;
   int   do_symmetry, i, j, m, n, a, b, x, y, w;
   uchar c, *p, *cp;

   memset(r, 0, x_size * y_size * sizeof(int));

   max_no = 730; /* ho hum ;) */

   for (i = 1; i < y_size - 1; i++)
      for (j = 1; j < x_size - 1; j++) {
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

   for (i = 2; i < y_size - 2; i++)
      for (j = 2; j < x_size - 2; j++) {
         if (r[i*x_size + j] > 0) {
            m = r[i*x_size + j];
            n = max_no - m;
            cp = bp + in[i*x_size + j];

            if (n > 250) {
               p = in + (i - 1)*x_size + j - 1;
               x = 0; y = 0;

               c = *(cp - *p++); x -= c; y -= c;
               c = *(cp - *p++); y -= c;
               c = *(cp - *p); x += c; y -= c;
               p += x_size - 2;

               c = *(cp - *p); x -= c;
               p += 2;
               c = *(cp - *p); x += c;
               p += x_size - 2;

               c = *(cp - *p++); x -= c; y += c;
               c = *(cp - *p++); y += c;
               c = *(cp - *p); x += c; y += c;

               z = sqrt((float)((x*x) + (y*y)));
               if (z > (0.4*(float)n)) /* 0.6 */
               {
                  do_symmetry = 0;
                  if (x == 0)
                     z = 1000000.0;
                  else
                     z = ((float)y) / ((float)x);
                  if (z < 0) { z = -z; w = -1; }
                  else w = 1;
                  if (z < 0.5) { /* vert_edge */ a = 0; b = 1; }
                  else {
                     if (z > 2.0) { /* hor_edge */ a = 1; b = 0; }
                     else { /* diag_edge */ if (w > 0) { a = 1; b = 1; }
                     else { a = -1; b = 1; }
                     }
                  }
                  if ((m > r[(i + a)*x_size + j + b]) && (m >= r[(i - a)*x_size + j - b]))
                     mid[i*x_size + j] = 1;
               }
               else
                  do_symmetry = 1;
            }
            else
               do_symmetry = 1;

            if (do_symmetry == 1) {
               p = in + (i - 1)*x_size + j - 1;
               x = 0; y = 0; w = 0;

               /*   |      \
               y  -x-  w
               |        \   */

               c = *(cp - *p++); x += c; y += c; w += c;
               c = *(cp - *p++); y += c;
               c = *(cp - *p); x += c; y += c; w -= c;
               p += x_size - 2;

               c = *(cp - *p); x += c;
               p += 2;
               c = *(cp - *p); x += c;
               p += x_size - 2;

               c = *(cp - *p++); x += c; y += c; w -= c;
               c = *(cp - *p++); y += c;
               c = *(cp - *p); x += c; y += c; w += c;

               if (y == 0)
                  z = 1000000.0;
               else
                  z = ((float)x) / ((float)y);
               if (z < 0.5) { /* vertical */ a = 0; b = 1; }
               else {
                  if (z > 2.0) { /* horizontal */ a = 1; b = 0; }
                  else { /* diagonal */ if (w > 0) { a = -1; b = 1; }
                  else { a = 1; b = 1; }
                  }
               }
               if ((m > r[(i + a)*x_size + j + b]) && (m >= r[(i - a)*x_size + j - b]))
                  mid[i*x_size + j] = 2;
            }
         }
      }
}