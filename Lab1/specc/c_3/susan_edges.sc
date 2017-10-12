
#include <stdio.h>
#include <math.h>

#define X_SIZE  76
#define Y_SIZE  95
#define IMG_SIZE 7220 
#define BP_SIZE  516
#define max_no_edges  2650

import "c_bptoken_queue.sc";
import "c_imtoken_queue.sc";
import "c_rtoken_queue.sc";


behavior susan_edges(i_imtoken_myreceiver port_img_in, i_bptoken_myreceiver port_bp_in, i_imtoken_mysender port_img_out, i_imtoken_mysender port_mid_out, i_rtoken_mysender port_r_out) 
{


   void main(void)
   {
      //Local variables created in susan_edges to be forwarded
      int r[IMG_SIZE] = {0};
      unsigned char mid[IMG_SIZE];
      int hj;
      

      unsigned char img[IMG_SIZE];
      unsigned char bp[BP_SIZE];
      
      //Local variables created in susan_edges NOT to be forwarded
      float z;
      int   do_symmetry, i, j, m, n, a, b, x, y, w;
      unsigned char c;

      // These are converted to indices. Note that cp indexes in, and p indexes bp.
      int cp;
      int p;

      // Additional variables for indices
      int img_index = 0; 
      int bp_index = 258;

      for(hj=0; hj<IMG_SIZE; hj++)
      	mid[hj] = 100;
    
      //Read in arrays and other variables
      port_img_in.receive(img);
      port_bp_in.receive(bp);


       for (i = 3; i < Y_SIZE - 3; i++) {
          for (j = 3; j < X_SIZE - 3; j++) {
             n = 100;
             p = img_index + (i - 3)*X_SIZE + j - 1;
             cp = bp_index + img[i*X_SIZE + j];

             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p]];
             p += X_SIZE - 3;

             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p]];
             p += X_SIZE - 5;

             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p]];
             p += X_SIZE - 6;

             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p]];
             p += 2;
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p]];
             p += X_SIZE - 6;

             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p]];
             p += X_SIZE - 5;

             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p]];
             p += X_SIZE - 3;

             n += bp[cp - img[p++]];
             n += bp[cp - img[p++]];
             n += bp[cp - img[p]];

             if (n <= max_no_edges)
                r[i*X_SIZE + j] = max_no_edges - n;
          }
       }

       for (i = 4; i < Y_SIZE - 4; i++) {
          for (j = 4; j < X_SIZE - 4; j++) {
             if (r[i*X_SIZE + j] > 0) {
                m = r[i*X_SIZE + j];
                n = max_no_edges - m;
                cp = bp_index + img[i*X_SIZE + j];

                if (n > 600) {
                   p = img_index + (i - 3)*X_SIZE + j - 1;
                   x = 0; y = 0;

                   c = bp[cp - img[p++]]; x -= c; y -= 3 * c;
                   c = bp[cp - img[p++]]; y -= 3 * c;
                   c = bp[cp - img[p]]; x += c; y -= 3 * c;
                   p += X_SIZE - 3;

                   c = bp[cp - img[p++]]; x -= 2 * c; y -= 2 * c;
                   c = bp[cp - img[p++]]; x -= c; y -= 2 * c;
                   c = bp[cp - img[p++]]; y -= 2 * c;
                   c = bp[cp - img[p++]]; x += c; y -= 2 * c;
                   c = bp[cp - img[p]]; x += 2 * c; y -= 2 * c;
                   p += X_SIZE - 5;

                   c = bp[cp - img[p++]]; x -= 3 * c; y -= c;
                   c = bp[cp - img[p++]]; x -= 2 * c; y -= c;
                   c = bp[cp - img[p++]]; x -= c; y -= c;
                   c = bp[cp - img[p++]]; y -= c;
                   c = bp[cp - img[p++]]; x += c; y -= c;
                   c = bp[cp - img[p++]]; x += 2 * c; y -= c;
                   c = bp[cp - img[p]]; x += 3 * c; y -= c;
                   p += X_SIZE - 6;

                   c = bp[cp - img[p++]]; x -= 3 * c;
                   c = bp[cp - img[p++]]; x -= 2 * c;
                   c = bp[cp - img[p]]; x -= c;
                   p += 2;
                   c = bp[cp - img[p++]]; x += c;
                   c = bp[cp - img[p++]]; x += 2 * c;
                   c = bp[cp - img[p]]; x += 3 * c;
                   p += X_SIZE - 6;

                   c = bp[cp - img[p++]]; x -= 3 * c; y += c;
                   c = bp[cp - img[p++]]; x -= 2 * c; y += c;
                   c = bp[cp - img[p++]]; x -= c; y += c;
                   c = bp[cp - img[p++]]; y += c;
                   c = bp[cp - img[p++]]; x += c; y += c;
                   c = bp[cp - img[p++]]; x += 2 * c; y += c;
                   c = bp[cp - img[p]]; x += 3 * c; y += c;
                   p += X_SIZE - 5;

                   c = bp[cp - img[p++]]; x -= 2 * c; y += 2 * c;
                   c = bp[cp - img[p++]]; x -= c; y += 2 * c;
                   c = bp[cp - img[p++]]; y += 2 * c;
                   c = bp[cp - img[p++]]; x += c; y += 2 * c;
                   c = bp[cp - img[p]]; x += 2 * c; y += 2 * c;
                   p += X_SIZE - 3;

                   c = bp[cp - img[p++]]; x -= c; y += 3 * c;
                   c = bp[cp - img[p++]]; y += 3 * c;
                   c = bp[cp - img[p]]; x += c; y += 3 * c;

                   z = sqrt((float)((x*x) + (y*y)));
                   if (z > (0.9*(float)n)) /* 0.5 */
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
                      if ((m > r[(i + a)*X_SIZE + j + b]) && (m >= r[(i - a)*X_SIZE + j - b]) &&
                         (m > r[(i + (2 * a))*X_SIZE + j + (2 * b)]) && (m >= r[(i - (2 * a))*X_SIZE + j - (2 * b)]))
                         mid[i*X_SIZE + j] = 1;
                   }
                   else
                      do_symmetry = 1;
                }
                else
                   do_symmetry = 1;

                if (do_symmetry == 1) {
                   p = img_index + (i - 3)*X_SIZE + j - 1;
                   x = 0; y = 0; w = 0;

                   /*   |      \
                   y  -x-  w
                   |        \   */

                   c = bp[cp - img[p++]]; x += c; y += 9 * c; w += 3 * c;
                   c = bp[cp - img[p++]]; y += 9 * c;
                   c = bp[cp - img[p]]; x += c; y += 9 * c; w -= 3 * c;
                   p += X_SIZE - 3;

                   c = bp[cp - img[p++]]; x += 4 * c; y += 4 * c; w += 4 * c;
                   c = bp[cp - img[p++]]; x += c; y += 4 * c; w += 2 * c;
                   c = bp[cp - img[p++]]; y += 4 * c;
                   c = bp[cp - img[p++]]; x += c; y += 4 * c; w -= 2 * c;
                   c = bp[cp - img[p]]; x += 4 * c; y += 4 * c; w -= 4 * c;
                   p += X_SIZE - 5;

                   c = bp[cp - img[p++]]; x += 9 * c; y += c; w += 3 * c;
                   c = bp[cp - img[p++]]; x += 4 * c; y += c; w += 2 * c;
                   c = bp[cp - img[p++]]; x += c; y += c; w += c;
                   c = bp[cp - img[p++]]; y += c;
                   c = bp[cp - img[p++]]; x += c; y += c; w -= c;
                   c = bp[cp - img[p++]]; x += 4 * c; y += c; w -= 2 * c;
                   c = bp[cp - img[p]]; x += 9 * c; y += c; w -= 3 * c;
                   p += X_SIZE - 6;

                   c = bp[cp - img[p++]]; x += 9 * c;
                   c = bp[cp - img[p++]]; x += 4 * c;
                   c = bp[cp - img[p]]; x += c;
                   p += 2;
                   c = bp[cp - img[p++]]; x += c;
                   c = bp[cp - img[p++]]; x += 4 * c;
                   c = bp[cp - img[p]]; x += 9 * c;
                   p += X_SIZE - 6;

                   c = bp[cp - img[p++]]; x += 9 * c; y += c; w -= 3 * c;
                   c = bp[cp - img[p++]]; x += 4 * c; y += c; w -= 2 * c;
                   c = bp[cp - img[p++]]; x += c; y += c; w -= c;
                   c = bp[cp - img[p++]]; y += c;
                   c = bp[cp - img[p++]]; x += c; y += c; w += c;
                   c = bp[cp - img[p++]]; x += 4 * c; y += c; w += 2 * c;
                   c = bp[cp - img[p]]; x += 9 * c; y += c; w += 3 * c;
                   p += X_SIZE - 5;

                   c = bp[cp - img[p++]]; x += 4 * c; y += 4 * c; w -= 4 * c;
                   c = bp[cp - img[p++]]; x += c; y += 4 * c; w -= 2 * c;
                   c = bp[cp - img[p++]]; y += 4 * c;
                   c = bp[cp - img[p++]]; x += c; y += 4 * c; w += 2 * c;
                   c = bp[cp - img[p]]; x += 4 * c; y += 4 * c; w += 4 * c;
                   p += X_SIZE - 3;

                   c = bp[cp - img[p++]]; x += c; y += 9 * c; w -= 3 * c;
                   c = bp[cp - img[p++]]; y += 9 * c;
                   c = bp[cp - img[p]]; x += c; y += 9 * c; w += 3 * c;

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
                   if ((m > r[(i + a)*X_SIZE + j + b]) && (m >= r[(i - a)*X_SIZE + j - b]) &&
                      (m > r[(i + (2 * a))*X_SIZE + j + (2 * b)]) && (m >= r[(i - (2 * a))*X_SIZE + j - (2 * b)]))
                      mid[i*X_SIZE + j] = 2;
                }
             }
          }
    
      }

      //Write into the output ports
      port_img_out.send(img);
      port_mid_out.send(mid);
      port_r_out.send(r);
   
   }
};
