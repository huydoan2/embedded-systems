
#include <stdio.h>
#include <math.h>

#define X_SIZE  76
#define Y_SIZE  95
#define max_no_edges  2650

import "c_bptoken_queue.sc";
import "c_imtoken_queue.sc";
import "c_rtoken_queue.sc";


behavior FirstLoop(in int id, in int BLK_SIZE, in unsigned char img[7220], in unsigned char bp[516], out int r[7220]){

      int img_index = 0; 
      int bp_index = 258;

  void main(void){

	  int cp, p, n, i, j, start, end;
    if(id == 0)
      start = 3;
    else
      start = id*BLK_SIZE;

    if(id == 4)
       end = (id + 1) * BLK_SIZE - 3;
    else
       end = (id + 1) * BLK_SIZE;

      for(i = start; i < end; ++i)
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
};


behavior SecondLoop(in int id, in int BLK_SIZE, in unsigned char img[7220], in unsigned char bp[516], int r[7220], out unsigned char mid[7220]){

  void main(void){
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

  int start, end;

  if(id == 0)
    start = 4;
  else
    start = id*BLK_SIZE;

  if(id == 4)
     end = (id + 1) * BLK_SIZE - 4;
  else
     end = (id + 1) * BLK_SIZE;

  for (i = start; i < end; i++) {
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
  }
};

behavior susan_edges(i_imtoken_myreceiver port_img_in, i_bptoken_myreceiver port_bp_in, i_imtoken_mysender port_img_out, i_imtoken_mysender port_mid_out, i_rtoken_mysender port_r_out) 
{

    const unsigned long IMG_SIZE = 7220;
    const unsigned long BP_SIZE = 516;

    unsigned char img[IMG_SIZE];
    unsigned char bp[BP_SIZE];
    int r[IMG_SIZE] = {0};
    unsigned char mid[IMG_SIZE];

    int BLK_SIZE = 19;
    FirstLoop fl0(0, BLK_SIZE, img, bp, r);
    FirstLoop fl1(1, BLK_SIZE, img, bp, r);
    FirstLoop fl2(2, BLK_SIZE, img, bp, r);
    FirstLoop fl3(3, BLK_SIZE, img, bp, r);
    FirstLoop fl4(4, BLK_SIZE, img, bp, r);

    SecondLoop sl0(0, BLK_SIZE, img, bp, r, mid);
    SecondLoop sl1(1, BLK_SIZE, img, bp, r, mid);
    SecondLoop sl2(2, BLK_SIZE, img, bp, r, mid);
    SecondLoop sl3(3, BLK_SIZE, img, bp, r, mid);
    SecondLoop sl4(4, BLK_SIZE, img, bp, r, mid);

   void main(void)
   {
      //Local variables created in susan_edges to be forwarded
      unsigned int hj;

      //Local variables created in susan_edges NOT to be forwarded
      //float z;
      //int   do_symmetry, i, j, m, n, a, b, x, y, w;
      //unsigned char c;

      // These are converted to indices. Note that cp indexes in, and p indexes bp.
      //int cp;
      //int p;

      // Additional variables for indices
      //int img_index = 0; 
      //int bp_index = 258;

      for(hj=0; hj<IMG_SIZE; hj++)
      	mid[hj] = 100;
    
      //Read in arrays and other variables
      port_bp_in.receive(bp);
      port_img_in.receive(img);

       par{
          fl0.main();
          fl1.main();
          fl2.main();
          fl3.main();
          fl4.main();      
       }

       par{
          sl0.main();
          sl1.main();
          sl2.main();
          sl3.main();
          sl4.main();
       }       

      //Write into the output ports
      port_img_out.send(img);
      port_mid_out.send(mid);
      port_r_out.send(r);
   
   }
};
