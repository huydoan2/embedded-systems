behavior Main(){
   /* {{{ vars */
	char filename[80],
		*tcp;

   FILE   *ofp;

   float  dt = 4.0;
   int    *r,
		argindex = 3,
		bt = 20,
		principle = 0,
		thin_post_proc = 1,
		three_by_three = 0,
		drawing_mode = 0,
		max_no_edges = 2650,
		i,
		x_size, y_size;

   /* }}} */

   if (argc<3)
      usage();

   uchar in[X_SIZE * Y_SIZE];
   uchar mid[X_SIZE * Y_SIZE];
   uchar bp[516];
   int   r[X_SIZE*Y_SIZE];
}