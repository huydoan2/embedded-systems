#define x_size = 76
#define y_size = 95
#define bp_size = 516
#define max_no_edges = 2650

void susan_edges(i_receiver port_in_in, i_receiver port_bp_in, i_sender port_mid_out, i_sender port_r_out) {

   void main(void)
   {
      //Local variables created in susan_edges to be forwarded
      int r[x_size * y_size] = {0};
      uchar mid[x_size * y_size] = {100};
      uchar in[x_size * y_size];
      uchar bp[bp_size];

      //Local variables created in susan_edges NOT to be forwarded
      float z;
      int   do_symmetry, i, j, m, n, a, b, x, y, w;
      uchar c;

      // These are converted to indices. Note that cp indexes in, and p indexes bp.
      int cp;
      int p;

      // Additional variables for indices
      int in_index = 0; 
      int bp_index = 258;

      //Read in arrays and other variables
      port_in_in.receive(in, x_size * y_size);
      port_bp_in.receive(bp, bp_size);


       for (i = 3; i < y_size - 3; i++)
          for (j = 3; j < x_size - 3; j++) {
             n = 100;
             p = in_index + (i - 3)*x_size + j - 1;
             cp = bp_index + in[i*x_size + j];

             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p]];
             p += x_size - 3;

             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p]];
             p += x_size - 5;

             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p]];
             p += x_size - 6;

             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p]];
             p += 2;
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p]];
             p += x_size - 6;

             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p]];
             p += x_size - 5;

             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p]];
             p += x_size - 3;

             n += bp[cp - in[p++]];
             n += bp[cp - in[p++]];
             n += bp[cp - in[p]];

             if (n <= max_no)
                r[i*x_size + j] = max_no - n;
          }

       for (i = 4; i < y_size - 4; i++)
          for (j = 4; j < x_size - 4; j++) {
             if (r[i*x_size + j] > 0) {
                m = r[i*x_size + j];
                n = max_no - m;
                cp = bp_index + in[i*x_size + j];

                if (n > 600) {
                   p = in_index + (i - 3)*x_size + j - 1;
                   x = 0; y = 0;

                   c = bp[cp - in[p++]]; x -= c; y -= 3 * c;
                   c = bp[cp - in[p++]]; y -= 3 * c;
                   c = bp[cp - in[p]]; x += c; y -= 3 * c;
                   p += x_size - 3;

                   c = bp[cp - in[p++]]; x -= 2 * c; y -= 2 * c;
                   c = bp[cp - in[p++]]; x -= c; y -= 2 * c;
                   c = bp[cp - in[p++]]; y -= 2 * c;
                   c = bp[cp - in[p++]]; x += c; y -= 2 * c;
                   c = bp[cp - in[p]]; x += 2 * c; y -= 2 * c;
                   p += x_size - 5;

                   c = bp[cp - in[p++]]; x -= 3 * c; y -= c;
                   c = bp[cp - in[p++]]; x -= 2 * c; y -= c;
                   c = bp[cp - in[p++]]; x -= c; y -= c;
                   c = bp[cp - in[p++]]; y -= c;
                   c = bp[cp - in[p++]]; x += c; y -= c;
                   c = bp[cp - in[p++]]; x += 2 * c; y -= c;
                   c = bp[cp - in[p]]; x += 3 * c; y -= c;
                   p += x_size - 6;

                   c = bp[cp - in[p++]]; x -= 3 * c;
                   c = bp[cp - in[p++]]; x -= 2 * c;
                   c = bp[cp - in[p]]; x -= c;
                   p += 2;
                   c = bp[cp - in[p++]]; x += c;
                   c = bp[cp - in[p++]]; x += 2 * c;
                   c = bp[cp - in[p]]; x += 3 * c;
                   p += x_size - 6;

                   c = bp[cp - in[p++]]; x -= 3 * c; y += c;
                   c = bp[cp - in[p++]]; x -= 2 * c; y += c;
                   c = bp[cp - in[p++]]; x -= c; y += c;
                   c = bp[cp - in[p++]]; y += c;
                   c = bp[cp - in[p++]]; x += c; y += c;
                   c = bp[cp - in[p++]]; x += 2 * c; y += c;
                   c = bp[cp - in[p]]; x += 3 * c; y += c;
                   p += x_size - 5;

                   c = bp[cp - in[p++]]; x -= 2 * c; y += 2 * c;
                   c = bp[cp - in[p++]]; x -= c; y += 2 * c;
                   c = bp[cp - in[p++]]; y += 2 * c;
                   c = bp[cp - in[p++]]; x += c; y += 2 * c;
                   c = bp[cp - in[p]]; x += 2 * c; y += 2 * c;
                   p += x_size - 3;

                   c = bp[cp - in[p++]]; x -= c; y += 3 * c;
                   c = bp[cp - in[p++]]; y += 3 * c;
                   c = bp[cp - in[p]]; x += c; y += 3 * c;

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
                      if ((m > r[(i + a)*x_size + j + b]) && (m >= r[(i - a)*x_size + j - b]) &&
                         (m > r[(i + (2 * a))*x_size + j + (2 * b)]) && (m >= r[(i - (2 * a))*x_size + j - (2 * b)]))
                         mid[i*x_size + j] = 1;
                   }
                   else
                      do_symmetry = 1;
                }
                else
                   do_symmetry = 1;

                if (do_symmetry == 1) {
                   p = in_index + (i - 3)*x_size + j - 1;
                   x = 0; y = 0; w = 0;

                   /*   |      \
                   y  -x-  w
                   |        \   */

                   c = bp[cp - in[p++]]; x += c; y += 9 * c; w += 3 * c;
                   c = bp[cp - in[p++]]; y += 9 * c;
                   c = bp[cp - in[p]]; x += c; y += 9 * c; w -= 3 * c;
                   p += x_size - 3;

                   c = bp[cp - in[p++]]; x += 4 * c; y += 4 * c; w += 4 * c;
                   c = bp[cp - in[p++]]; x += c; y += 4 * c; w += 2 * c;
                   c = bp[cp - in[p++]]; y += 4 * c;
                   c = bp[cp - in[p++]]; x += c; y += 4 * c; w -= 2 * c;
                   c = bp[cp - in[p]]; x += 4 * c; y += 4 * c; w -= 4 * c;
                   p += x_size - 5;

                   c = bp[cp - in[p++]]; x += 9 * c; y += c; w += 3 * c;
                   c = bp[cp - in[p++]]; x += 4 * c; y += c; w += 2 * c;
                   c = bp[cp - in[p++]]; x += c; y += c; w += c;
                   c = bp[cp - in[p++]]; y += c;
                   c = bp[cp - in[p++]]; x += c; y += c; w -= c;
                   c = bp[cp - in[p++]]; x += 4 * c; y += c; w -= 2 * c;
                   c = bp[cp - in[p]]; x += 9 * c; y += c; w -= 3 * c;
                   p += x_size - 6;

                   c = bp[cp - in[p++]]; x += 9 * c;
                   c = bp[cp - in[p++]]; x += 4 * c;
                   c = bp[cp - in[p]]; x += c;
                   p += 2;
                   c = bp[cp - in[p++]]; x += c;
                   c = bp[cp - in[p++]]; x += 4 * c;
                   c = bp[cp - in[p]]; x += 9 * c;
                   p += x_size - 6;

                   c = bp[cp - in[p++]]; x += 9 * c; y += c; w -= 3 * c;
                   c = bp[cp - in[p++]]; x += 4 * c; y += c; w -= 2 * c;
                   c = bp[cp - in[p++]]; x += c; y += c; w -= c;
                   c = bp[cp - in[p++]]; y += c;
                   c = bp[cp - in[p++]]; x += c; y += c; w += c;
                   c = bp[cp - in[p++]]; x += 4 * c; y += c; w += 2 * c;
                   c = bp[cp - in[p]]; x += 9 * c; y += c; w += 3 * c;
                   p += x_size - 5;

                   c = bp[cp - in[p++]]; x += 4 * c; y += 4 * c; w -= 4 * c;
                   c = bp[cp - in[p++]]; x += c; y += 4 * c; w -= 2 * c;
                   c = bp[cp - in[p++]]; y += 4 * c;
                   c = bp[cp - in[p++]]; x += c; y += 4 * c; w += 2 * c;
                   c = bp[cp - in[p]]; x += 4 * c; y += 4 * c; w += 4 * c;
                   p += x_size - 3;

                   c = bp[cp - in[p++]]; x += c; y += 9 * c; w -= 3 * c;
                   c = bp[cp - in[p++]]; y += 9 * c;
                   c = bp[cp - in[p]]; x += c; y += 9 * c; w += 3 * c;

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
                   if ((m > r[(i + a)*x_size + j + b]) && (m >= r[(i - a)*x_size + j - b]) &&
                      (m > r[(i + (2 * a))*x_size + j + (2 * b)]) && (m >= r[(i - (2 * a))*x_size + j - (2 * b)]))
                      mid[i*x_size + j] = 2;
                }
             }
          }
   
      //Write into the output ports
      port_mid.out.write(mid, x_size*y_size);
      port_r_out.write(r, x_size*y_size);
   
   }
}
