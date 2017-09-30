
#include <stdio.h>

#define X_SIZE  76
#define Y_SIZE  95
#define IMG_SIZE 7220 

import "c_queue";

behavior susan_thin(i_receiver port_mid_in, i_receiver port_r_in, i_sender port_mid_out) 
{

   void main(void)
   {
      //Local variables created in susan_thin to be forwarded 
      int r[X_SIZE * Y_SIZE] = {0};
      unsigned char mid[X_SIZE * Y_SIZE] = {0};

      //Local variables created in susan_edges NOT to be forwarded
      int   l[9], centre, nlinks, npieces,
         b01, b12, b21, b10,
         p1, p2, p3, p4,
         b00, b02, b20, b22,
         m, n, a, b, x, y, i, j;
       
      // These are converted to indices. Note mp indexes mid.
      int mp = 0;

      // Additional variables for indices
      int mid_index = 0; 

      //Read input variables
      port_mid_in.receive(mid, X_SIZE * Y_SIZE);
      port_r_in.receive(r, X_SIZE * Y_SIZE);

       for (i = 4; i < Y_SIZE - 4; i++)
          for (j = 4; j < X_SIZE - 4; j++)
             if (mid[i*X_SIZE + j] < 8) {
                centre = r[i*X_SIZE + j];
                /* {{{ count number of neighbours */

                mp = mid_index + (i - 1)*X_SIZE + j - 1;

                n = (mid[mp] < 8) +
                   (mid[mp + 1] < 8) +
                   (mid[mp + 2] < 8) +
                   (mid[mp + X_SIZE] < 8) +
                   (mid[mp + X_SIZE + 2] < 8) +
                   (mid[mp + X_SIZE + X_SIZE] < 8) +
                   (mid[mp + X_SIZE + X_SIZE + 1] < 8) +
                   (mid[mp + X_SIZE + X_SIZE + 2] < 8);

                /* }}} */
                /* {{{ n==0 no neighbours - remove point */

                if (n == 0)
                   mid[i*X_SIZE + j] = 100;

                /* }}} */
                /* {{{ n==1 - extend line if I can */

                /* extension is only allowed a few times - the value of mid is used to control this */

                if ((n == 1) && (mid[i*X_SIZE + j] < 6)) {
                   /* find maximum neighbour weighted in direction opposite the
                   neighbour already present. e.g.
                   have: O O O  weight r by 0 2 3
                   X X O              0 0 4
                   O O O              0 2 3     */

                   l[0] = r[(i - 1)*X_SIZE + j - 1]; l[1] = r[(i - 1)*X_SIZE + j]; l[2] = r[(i - 1)*X_SIZE + j + 1];
                   l[3] = r[(i)*X_SIZE + j - 1]; l[4] = 0;                 l[5] = r[(i)*X_SIZE + j + 1];
                   l[6] = r[(i + 1)*X_SIZE + j - 1]; l[7] = r[(i + 1)*X_SIZE + j]; l[8] = r[(i + 1)*X_SIZE + j + 1];

                   if (mid[(i - 1)*X_SIZE + j - 1] < 8) {
                      l[0] = 0; l[1] = 0; l[3] = 0; l[2] *= 2;
                      l[6] *= 2; l[5] *= 3; l[7] *= 3; l[8] *= 4;
                   }
                   else {
                      if (mid[(i - 1)*X_SIZE + j] < 8) {
                         l[1] = 0; l[0] = 0; l[2] = 0; l[3] *= 2;
                         l[5] *= 2; l[6] *= 3; l[8] *= 3; l[7] *= 4;
                      }
                      else {
                         if (mid[(i - 1)*X_SIZE + j + 1] < 8) {
                            l[2] = 0; l[1] = 0; l[5] = 0; l[0] *= 2;
                            l[8] *= 2; l[3] *= 3; l[7] *= 3; l[6] *= 4;
                         }
                         else {
                            if (mid[(i)*X_SIZE + j - 1] < 8) {
                               l[3] = 0; l[0] = 0; l[6] = 0; l[1] *= 2;
                               l[7] *= 2; l[2] *= 3; l[8] *= 3; l[5] *= 4;
                            }
                            else {
                               if (mid[(i)*X_SIZE + j + 1] < 8) {
                                  l[5] = 0; l[2] = 0; l[8] = 0; l[1] *= 2;
                                  l[7] *= 2; l[0] *= 3; l[6] *= 3; l[3] *= 4;
                               }
                               else {
                                  if (mid[(i + 1)*X_SIZE + j - 1] < 8) {
                                     l[6] = 0; l[3] = 0; l[7] = 0; l[0] *= 2;
                                     l[8] *= 2; l[1] *= 3; l[5] *= 3; l[2] *= 4;
                                  }
                                  else {
                                     if (mid[(i + 1)*X_SIZE + j] < 8) {
                                        l[7] = 0; l[6] = 0; l[8] = 0; l[3] *= 2;
                                        l[5] *= 2; l[0] *= 3; l[2] *= 3; l[1] *= 4;
                                     }
                                     else {
                                        if (mid[(i + 1)*X_SIZE + j + 1] < 8) {
                                           l[8] = 0; l[5] = 0; l[7] = 0; l[6] *= 2;
                                           l[2] *= 2; l[1] *= 3; l[3] *= 3; l[0] *= 4;
                                        }
                                     }
                                  }
                               }
                            }
                         }
                      }
                   }

                   m = 0;     /* find the highest point */
                   for (y = 0; y < 3; y++)
                      for (x = 0; x < 3; x++)
                         if (l[y + y + y + x] > m) { m = l[y + y + y + x]; a = y; b = x; }

                   if (m > 0) {
                      if (mid[i*X_SIZE + j] < 4)
                         mid[(i + a - 1)*X_SIZE + j + b - 1] = 4;
                      else
                         mid[(i + a - 1)*X_SIZE + j + b - 1] = mid[i*X_SIZE + j] + 1;
                      if ((a + a + b) < 3) /* need to jump back in image */
                      {
                         i += a - 1;
                         j += b - 2;
                         if (i < 4) i = 4;
                         if (j < 4) j = 4;
                      }
                   }
                }

                /* }}} */
                /* {{{ n==2 */

                if (n == 2) {
                   /* put in a bit here to straighten edges */
                   b00 = mid[(i - 1)*X_SIZE + j - 1] < 8; /* corners of 3x3 */
                   b02 = mid[(i - 1)*X_SIZE + j + 1] < 8;
                   b20 = mid[(i + 1)*X_SIZE + j - 1] < 8;
                   b22 = mid[(i + 1)*X_SIZE + j + 1] < 8;
                   if (((b00 + b02 + b20 + b22) == 2) && ((b00 | b22)&(b02 | b20))) {  /* case: move a point back into line.
                                                                                       e.g. X O X  CAN  become X X X
                                                                                       O X O              O O O
                                                                                       O O O              O O O    */
                      if (b00) {
                         if (b02) { x = 0; y = -1; }
                         else { x = -1; y = 0; }
                      }
                      else {
                         if (b02) { x = 1; y = 0; }
                         else { x = 0; y = 1; }
                      }
                      if (((float)r[(i + y)*X_SIZE + j + x] / (float)centre) > 0.7) {
                         if (((x == 0) && (mid[(i + (2 * y))*X_SIZE + j] > 7) && (mid[(i + (2 * y))*X_SIZE + j - 1] > 7) && (mid[(i + (2 * y))*X_SIZE + j + 1] > 7)) ||
                            ((y == 0) && (mid[(i)*X_SIZE + j + (2 * x)] > 7) && (mid[(i + 1)*X_SIZE + j + (2 * x)] > 7) && (mid[(i - 1)*X_SIZE + j + (2 * x)] > 7))) {
                            mid[(i)*X_SIZE + j] = 100;
                            mid[(i + y)*X_SIZE + j + x] = 3;  /* no jumping needed */
                         }
                      }
                   }
                   else {
                      b01 = mid[(i - 1)*X_SIZE + j] < 8;
                      b12 = mid[(i)*X_SIZE + j + 1] < 8;
                      b21 = mid[(i + 1)*X_SIZE + j] < 8;
                      b10 = mid[(i)*X_SIZE + j - 1] < 8;
                      /* {{{ right angle ends - not currently used */
					    


                      /* }}} */
                      if (((b01 + b12 + b21 + b10) == 2) && ((b10 | b12)&(b01 | b21)) &&
                         ((b01&((mid[(i - 2)*X_SIZE + j - 1] < 8) | (mid[(i - 2)*X_SIZE + j + 1] < 8))) | (b10&((mid[(i - 1)*X_SIZE + j - 2] < 8) | (mid[(i + 1)*X_SIZE + j - 2] < 8))) |
                         (b12&((mid[(i - 1)*X_SIZE + j + 2] < 8) | (mid[(i + 1)*X_SIZE + j + 2] < 8))) | (b21&((mid[(i + 2)*X_SIZE + j - 1] < 8) | (mid[(i + 2)*X_SIZE + j + 1] < 8))))) { /* case; clears odd right angles.
                                                                                                                                                                                   e.g.; O O O  becomes O O O
                                                                                                                                                                                   X X O          X O O
                                                                                                                                                                                   O X O          O X O     */
                         mid[(i)*X_SIZE + j] = 100;
                         i--;               /* jump back */
                         j -= 2;
                         if (i < 4) i = 4;
                         if (j < 4) j = 4;
                      }
                   }
                }

                /* }}} */
                /* {{{ n>2 the thinning is done here without breaking connectivity */

                if (n > 2) {
                   b01 = mid[(i - 1)*X_SIZE + j] < 8;
                   b12 = mid[(i)*X_SIZE + j + 1] < 8;
                   b21 = mid[(i + 1)*X_SIZE + j] < 8;
                   b10 = mid[(i)*X_SIZE + j - 1] < 8;
                   if ((b01 + b12 + b21 + b10) > 1) {
                      b00 = mid[(i - 1)*X_SIZE + j - 1] < 8;
                      b02 = mid[(i - 1)*X_SIZE + j + 1] < 8;
                      b20 = mid[(i + 1)*X_SIZE + j - 1] < 8;
                      b22 = mid[(i + 1)*X_SIZE + j + 1] < 8;
                      p1 = b00 | b01;
                      p2 = b02 | b12;
                      p3 = b22 | b21;
                      p4 = b20 | b10;

                      if (((p1 + p2 + p3 + p4) - ((b01 & p2) + (b12 & p3) + (b21 & p4) + (b10 & p1))) < 2) {
                         mid[(i)*X_SIZE + j] = 100;
                         i--;
                         j -= 2;
                         if (i < 4) i = 4;
                         if (j < 4) j = 4;
                      }
                   }
                }

                /* }}} */
                   }
      
      //Write output variables to be forwarded
      port_mid_out.send(mid, IMG_SIZE);
   
   }

};
