
/*
E/16/388
Weerasundara WMTMPB
Project CO225

Julia class - calculate julia set
*/

class Julia extends Fractal implements Runnable 			//class for calculate Julia set.implements Runnable for threads
	{
		/*
			Julia class basically takes, where to start and end in the panel, which thread is using the class. 
			According the constructor it uses default values or user values for iterations and constant complex number
		*/

		/*
			Below variables are unique to Julia class.Other variables and methods are inherited
		*/
					
	    private final double R2 =1,R1 = -1, Z2 = 1, Z1 = -1;		//for Julia set region of interest is constant for always
	    private double cReal, cImaginary;							//for constanc C in Julia set
		private static final long serialVersionUID = -3088001842L;		//to handle serializable class warnings
		

		Julia(int start, int end, int runTime)						//when using default C.No inputs for Julia set
			{
				this.cReal = -0.4;									//storeing default values 
				this.cImaginary = 0.6;
				this.max = 1000;
				
				this.start = start;									//storing values required to use thread correctly
				this.end = end;
				this. runTime = runTime;
			}	

		Julia(double cReal, double cImaginary, int start, int end, int runTime)		//when C value is inputed.Overloading process happens.
			{
				this.cReal = cReal;						//storeing user values 
				this.cImaginary = cImaginary;

				this.max = 1000;						//storeing default value

				this.start = start;						//storing values required to use thread correctly
				this.end = end;
				this. runTime = runTime;

			}

		Julia(double cReal, double cImaginary, int max , int start, int end, int runTime)	//when C value and iteration count is inputed.Overloading process happens.
			{
				this.cReal = cReal;	this.cImaginary = cImaginary;	//storeing user values				
				this.max = max;
				
				this.start = start;	this.end = end;					//storing values required to use thread correctly
				this. runTime = runTime;				
			}	

/*******************************************************************************************************************************************************/

		public static int[][] getCordinates()		//method to return iterations for all points after calculation
			{		
				return colorValue;
			}	
		
/*******************************************************************************************************************************************************/

		public void run()										//run method will execute when a thread is orderd to run.then calculate iterations for required interval
			{	
				Julia julia = new Julia(start, end, runTime);	//to use inherited methods
				final int height = 800, width = 800;			//panel sizes  are constants

				double low_re =R1 ,low_im = Z1;					//begin from left down corner

				double dr = (R2 -R1)/width;						//geting step size for real(dr) and complex(dz) axis
				double dz = (Z2 - Z1)/height;
				
				low_re = low_re + dr*200*runTime;				//calculating startpoint in real axis for current thread

				for (int row = start; row < end; row++ )		 //go left to right
					{	
						low_im = Z1;

						for (int col = height - 1; col > -1; col--)  	//start from left down and end at top
							{
								double x = low_re, y = low_im;			//Z0 for Julia set
								int iteration = 0;						//to store iteration count

								while(iteration < max)					//go 1000 iterations for julia set.atleast runs 1 iteration
								{
									iteration++;
									double x_new = x*x - y*y + cReal;	//geting next real value
									y = 2*x*y + cImaginary;				//getting next complex value
									x = x_new;

									if(!julia.absoluteValue(x , y))		//condition to check whether in thr Julia set or not
									{
										break;							//break if not in the set
									}					           
								}
								
								low_im = low_im + dz;					//increase imaginary axis value
					            colorValue[row][col] = iteration;		//store iterations for current point(pixel)
								}
							low_re = low_re + dr;						//increase real axis value
					}	
			}
}