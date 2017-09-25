

main(argc, argv)
int   argc;
char  *argv[];
{
   /* {{{ vars */

   FILE   *ofp;
   char   filename[80],
      *tcp;
   float  dt = 4.0;
   int    *r,
      argindex = 3,
      bt = 20,
      principle = 0,
      thin_post_proc = 1,
      three_by_three = 0,
      drawing_mode = 0,
      max_no_edges = 2650,
      mode = 0, i,
      x_size, y_size;

   /* }}} */

   if (argc<3)
      usage();

   uchar in[X_SIZE * Y_SIZE];
   uchar mid[X_SIZE * Y_SIZE];
   uchar bp[516];
   int   r[X_SIZE*Y_SIZE];

   get_image(argv[1], &in, &x_size, &y_size);

   /* {{{ look at options */

   while (argindex < argc) {
      tcp = argv[argindex];
      if (*tcp == '-')
         switch (*++tcp) {
         case 'p': /* principle */
            principle = 1;
            break;
         case 'n': /* thinning post processing */
            thin_post_proc = 0;
            break;
         case 'b': /* simple drawing mode */
            drawing_mode = 1;
            break;
         case '3': /* 3x3 flat mask */
            three_by_three = 1;
            break;
         case 't': /* brightness threshold */
            if (++argindex >= argc) {
               printf("No argument following -t\n");
               exit(0);
            }
            bt = atoi(argv[argindex]);
            break;
         }
      else
         usage();
      argindex++;
   }

   /* }}} */
   /* {{{ main processing */


   //r = (int *)malloc(x_size * y_size * sizeof(int));
   setup_brightness_lut(&bp, bt, 6);

   if (principle) {
      if (three_by_three)
         susan_principle_small(in, r, bp, max_no_edges, x_size, y_size);
      else
         susan_principle(in, r, bp, max_no_edges, x_size, y_size);
      int_to_uchar(r, in, x_size*y_size);
   }
   else {
      //mid = (uchar *)malloc(x_size*y_size);
      memset(mid, 100, x_size * y_size); /* note not set to zero */

      if (three_by_three)
         susan_edges_small(in, r, mid, bp, max_no_edges, x_size, y_size);
      else
         susan_edges(in, r, mid, bp, max_no_edges, x_size, y_size);
      if (thin_post_proc)
         susan_thin(r, mid, x_size, y_size);
      edge_draw(in, mid, x_size, y_size, drawing_mode);
   }

   /* }}} */

   put_image(argv[2], in, x_size, y_size);
}