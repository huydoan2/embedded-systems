#include "setup_brightness_lut.h"

void setup_brightness_lut(uchar bp[516], int thresh, int form)
{
   int   k;
   float temp;

   //*bp=(unsigned char *)malloc(516);
   *bp = *bp + 258;

   for (k = -256; k<257; k++) {
      temp = ((float)k) / ((float)thresh);
      temp = temp*temp;
      if (form == 6)
         temp = temp*temp*temp;
      temp = 100.0*exp(-temp);
      *(*bp + k) = (uchar)temp;
   }

}