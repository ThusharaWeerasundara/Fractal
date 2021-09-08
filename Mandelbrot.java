
/*
E/16/388
Weerasundara WMTMPB
Project CO225

Mandelbrot class - calculate Mandelbrot set
*/

class Mandelbrot extends Fractal implements Runnable
	{
		/*
			Mandelbrot class basically takes, where to start and end in the panel, which thread is using the class. 
			According the constructor it uses default values or user values for iterations and region of interest
		*/

		/*
			Below variables(region) are unique to Mandelbrot class.Other variables and methods are inherited
		*/
		private static final long serialVersionUID = -3088001842L;		//to handle serializable class warnings		
	    private double r2 ,r1 , z2 , z1;
		
		Mandelbrot(int start ,int end, int runTime) 		//user gives no arguments with Mandelbrot.Assign defaukt values to region and iterations
			{
				this.r1 = -1; this.r2 = 1; 					//storing default values
				this.z1 = -1; this.z2 = 1;
				this.max = 1000;
				
				this.start = start; this.end = end;			//storing values required to use thread correctly
				this.runTime = runTime;
			}

		Mandelbrot(double r1 ,double z1 ,double r2 ,double z2 ,int start ,int end, int runTime) 	//user gives region.Overloading happens
			{
				this.r1 = r1; this.r2 = r2; 				//store user given region and default iteration limit
				this.z1 = z1; this.z2 = z2;
				this.max = 1000;
				
				this.start = start; this.end = end;			//storing values required to use thread correctly
				this.runTime = runTime;
			}
		
		Mandelbrot(double r1 ,double z1 ,double r2 ,double z2 ,int max ,int start ,int end, int runTime) //user gives region and iterations.Overloading happens
			{
				this.r1 = r1; this.r2 = r2; 				//store user given values to region and iteration limit
				this.z1 = z1; this.z2 = z2;
				this.max = max;
				
				this.start = start; this.end = end;			//storing values required to use thread correctly
				this.runTime = runTime;
			}

			public static int[][] getCordinates()			//method to return no of iterations for each point
				{		
					return colorValue;
				}

		public void run()			//run method will execute when a thread is orderd to run.then calculate iterations for required interval
			{	
				Mandelbrot mandelbrot = new Mandelbrot(start, end, runTime);	//to use inherited methods
				final int height = 800, width = 800;							//panel sizes  are constants

				double c_re = r1 ,c_im = z1;									//begin from left down corner
				
				if(z1 > z2)														//for z1 > z2 case swap values
					{
						double swap = z1;
						z1 = z2;
						z2 = swap;
					}



				double dr = (r2 - r1)/width;									//geting step size for real(dr) and complex(dz) axis
				double dz = (z2 - z1)/height;
				
				c_re = c_re + dr*200*runTime;									//calculating startpoint in real axis for current thread

				for (int row = start; row < end; row++) 						//go left to right
					{		
							c_im = z1;
							
						for (int col = height - 1; col > -1; col--) 			//start from left down and end at top
							{	

								int iterations = 0;								//to store iteration count
								double x = 0, y = 0;							//Z0 for Mandelbrot set

							while (mandelbrot.absoluteValue(x , y)  && iterations < max) 	//condition to check whether in thr Julia set or not
				                {
				                    double x_new = x*x-y*y+c_re;				//geting next real value
				                    y = 2*x*y+c_im;								//getting next complex value
				                    x = x_new;
				                    iterations++;
				                } 		

				                colorValue[row][col] = iterations;				//store iterations for current point(pixel)	
				                c_im = c_im + dz;								//increase imaginary axis value
							}
							c_re = c_re + dr;									//increase real axis value
					}
				}
/**************************************************************************************************************************************************************************/	
	}