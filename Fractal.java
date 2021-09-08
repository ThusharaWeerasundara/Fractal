/*
E/16/388
Weerasundara WMTMPB
Project CO225

Fractal class - handle inputs and uses other classes
*/


import javax.swing.*; //as a GUI widget toolkit

public class  Fractal  extends JFrame 		//fractral class inherit panel
{	
	private static final long serialVersionUID = -3088001842L;		//to handle serializable class warnings
	private final int WIDTH = 800;		//fixed WIDTH 800 pixels	
	
	/*	Below variables are used by both Julia and Mandelbrot classes*/


	protected static int colorValue[][] = new int[800][800];		//array to store iterations of all points		
	protected int start, end, runTime;			//to store panel interval
	protected int max;							//to store iteration limit	
	
	public static void main(String args[]) 		//main method
	{ 	

		double x1 = -1;			//boundary of default region of interest.
		double x2 = 1;			// x values are real i values are imaginary
		double i1 = -1;
		double i2 = 1;
		
		//	x1 + (i1)i <= region <= x2 + (i2)i

		int iteration = 1000;
		
		int length = args.length - 1;		//get number of operands inputed
		String type_1 = "Mandelbrot";		//store fractal types
	    String type_2 = "Julia";

	    Fractal  fractal = new  Fractal();	//make ab object to continue

	    if(type_1.equals(args[0]))			//if Mandelbrot set is inputed
				{	
					if(length == 4)			//only 4 operands inputed those 4 should be region
						{
							 x1 = Double.parseDouble(args[1]);		//store commandline inputs as double as region
							 x2 = Double.parseDouble(args[2]);
							 i1 = Double.parseDouble(args[3]);
							 i2 = Double.parseDouble(args[4]);

							 if(x1 > x2)							//if user enterd region falsely.swap 1st and 2nd numbers
							 	{
								 	System.out.println("*****Region boundaries corrected.*****");
								 	double swap1 = x1;
								 	double swap2 = i1;
								 	x1 = x2;
								 	x2 = swap1;
								 	i1 = i2;
								 	i2 = swap2;
								}

							 /*
								Mandelbrot class contructor takes start and end points for iteration, thread number as arguments here.
								To support larger iteration count 4 threads are used. 
							*/

							Thread t1 = new Thread(new Mandelbrot(x1,i1,x2,i2,0,(fractal.WIDTH/4),0));		
							Thread t2 = new Thread(new Mandelbrot(x1,i1,x2,i2,(fractal.WIDTH/4),(fractal.WIDTH/2),1));
							Thread t3 = new Thread(new Mandelbrot(x1,i1,x2,i2,(fractal.WIDTH/2),(fractal.WIDTH/4)*3,2));
							Thread t4 = new Thread(new Mandelbrot(x1,i1,x2,i2,(fractal.WIDTH/4)*3,fractal.WIDTH,3));
							
							fractal.threadRun(t1,t2,t3,t4);											//run 4 threads
							fractal.threadJoin(t1,t2,t3,t4);										//free threads
							fractal.drawMandelbrot();												//call method to draw set 
						}

					else if(length == 5)	//if 5 operands were inputed they are region and no of iterations
						{
							 x1 = Double.parseDouble(args[1]);			//store commandline inputs as double for 4 region values
							 x2 = Double.parseDouble(args[2]);
							 i1 = Double.parseDouble(args[3]);
							 i2 = Double.parseDouble(args[4]);
							 iteration = Integer.parseInt(args[5]);		//store commandline inputs as int for no of iterations
							

							 if(iteration < 0)			//for negative iteration values exit
							 {
							 	System.out.println("*****Iterations must be positive.*****");
							 	System.exit(0);
							 }

							 if(x1 > x2)							//if user enterd region falsely.swap 1st and 2nd numbers
							 	{
								 	System.out.println("*****Region boundaries corrected.*****");
								 	double swap1 = x1;
								 	double swap2 = i1;
								 	x1 = x2;
								 	x2 = swap1;
								 	i1 = i2;
								 	i2 = swap2;
								}

							 /*
								Mandelbrot class contructor takes 4 region of interest , start and end points for iteration, thread number as arguments here.
								To support larger iteration count 4 threads are used. 
							*/
							Thread t1 = new Thread(new Mandelbrot(x1,i1,x2,i2,iteration,0,(fractal.WIDTH/4),0));	
							Thread t2 = new Thread(new Mandelbrot(x1,i1,x2,i2,iteration,(fractal.WIDTH/4),(fractal.WIDTH/2),1));
							Thread t3 = new Thread(new Mandelbrot(x1,i1,x2,i2,iteration,(fractal.WIDTH/2),(fractal.WIDTH/4)*3,2));
							Thread t4 = new Thread(new Mandelbrot(x1,i1,x2,i2,iteration,(fractal.WIDTH/4)*3,fractal.WIDTH,3));
							
							fractal.threadRun(t1,t2,t3,t4);			//run 4 threads
							fractal.threadJoin(t1,t2,t3,t4);		//free threads
							fractal.drawMandelbrot(iteration);		//call method to draw set with 4 threads
						}

					else if(length == 0) 	//if no operands were given use default conditions
						{	
							/*
								Mandelbrot class contructor takes start and end points for iteration, thread number as arguments here.
								To support larger iteration count 4 threads are used. 
							*/

							Thread t1 = new Thread(new Mandelbrot(0,(fractal.WIDTH/4),0));		//use 4 threads to calculate mandelbrot set
							Thread t2 = new Thread(new Mandelbrot((fractal.WIDTH/4),(fractal.WIDTH/2),1));
							Thread t3 = new Thread(new Mandelbrot((fractal.WIDTH/2),(fractal.WIDTH/4)*3,2));
							Thread t4 = new Thread(new Mandelbrot((fractal.WIDTH/4)*3,fractal.WIDTH,3));
							
							fractal.threadRun(t1,t2,t3,t4);					//run 4 threads
							fractal.threadJoin(t1,t2,t3,t4);				//free threads
							fractal.drawMandelbrot();						//call method to draw set
						}	
							
					else 		//for wrong no of operands
						{	
							System.out.println("No of arguments are invalid.");
							System.exit(0);
						}
					}

		
			else if(type_2.equals(args[0]))			//for Julia inputs	
				{

					if(length == 0) 			//0 operands were given use default values.use default values for C constant in Julia set
						{
							/*
								Julia class constructor takes start and end points for iteration, 
								thread number as arguments here.To support larger iteration count 
								4 threads are used.
							 */

							Thread t1 = new Thread(new Julia(0, (fractal.WIDTH/4), 0));			
							Thread t2 = new Thread(new Julia((fractal.WIDTH/4), (fractal.WIDTH)/2, 1));
							Thread t3 = new Thread(new Julia((fractal.WIDTH)/2, 3*(fractal.WIDTH)/4, 2));
							Thread t4 = new Thread(new Julia(3*(fractal.WIDTH)/4, (fractal.WIDTH), 3));
							
							fractal.threadRun(t1,t2,t3,t4);		//run 4 threads
							fractal.threadJoin(t1,t2,t3,t4);	//free threads
							fractal.drawJulia(1000);			//call method to draw set 

						}

					else if(length == 2)		//2 operands were given they are the constant complex number
						{
							 double c_re = Double.parseDouble(args[1]);			//store commandline inputs as double values for constant C
							 double c_im = Double.parseDouble(args[2]);

							 /*
								Julia class constructor takes input constant C values real and imaginary parts, 
								start and end points for iteration, thread number as arguments here.To support larger iteration count 
								4 threads are used.
							 */

							Thread t1 = new Thread(new Julia(c_re, c_im, 0, (fractal.WIDTH/4), 0));			
							Thread t2 = new Thread(new Julia(c_re, c_im, (fractal.WIDTH/4), (fractal.WIDTH)/2, 1));
							Thread t3 = new Thread(new Julia(c_re, c_im, (fractal.WIDTH)/2, 3*(fractal.WIDTH)/4, 2));
							Thread t4 = new Thread(new Julia(c_re, c_im, 3*(fractal.WIDTH)/4, (fractal.WIDTH), 3));
							
							fractal.threadRun(t1,t2,t3,t4);		//run 4 threads
							fractal.threadJoin(t1,t2,t3,t4);	//free threads
							fractal.drawJulia(1000);			//call method to draw set 

						}

					else if(length == 3)
						{
							double c_re = Double.parseDouble(args[1]);			//store commandline inputs as double for C constant in Julia set
							double c_im = Double.parseDouble(args[2]);
							int iterations = Integer.parseInt(args[3]);			//store no of iterations as an int
							 
							 if(iterations < 0)			//for negative iteration values exit
							 {
							 	System.out.println("*****Iterations must be positive.*****");
							 	System.exit(0);
							 }


							 /*
								Julia class constructor takes input constant C values real and imaginary parts, iteration count,
								start and end points for iteration, thread number as arguments here.To support larger iteration count 
								4 threads are used.
							 */

							Thread t1 = new Thread(new Julia(c_re, c_im, iterations, 0, (fractal.WIDTH/4), 0));			
							Thread t2 = new Thread(new Julia(c_re, c_im, iterations, (fractal.WIDTH/4), (fractal.WIDTH)/2, 1));							
							Thread t3 = new Thread(new Julia(c_re, c_im, iterations, (fractal.WIDTH)/2, 3*(fractal.WIDTH)/4, 2));						
							Thread t4 = new Thread(new Julia(c_re, c_im, iterations, 3*(fractal.WIDTH)/4, (fractal.WIDTH), 3));
							
							fractal.threadRun(t1,t2,t3,t4);		//run 4 threads
							fractal.threadJoin(t1,t2,t3,t4);	//free threads
							fractal.drawJulia(iterations);		//call method to draw set 
						}	

					else 		//for wrong no of operands
						{
							System.out.println("No of arguments are invalid.");
							System.exit(0);
						}	
				}

			else
				{
					System.out.println("Fractral must be Mandelbrot or Julia.");
				}
	}


/****************************************************************************************************************************************************************/

public void drawJulia(int max)					//method to draw Julia set using only iteration value as input
	{
	    JFrame frame = new JFrame("Julia Set"); 
        															
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		//to close when click exit
																	// set the content of the frame as one of this panel
																	//constructor for Panel takes Fractral type as input here
		frame.setContentPane(new Panel(max,"Julia")); 

		frame.pack(); 
																	//to centre the output on screen
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
	}


/***************************************************************************************************************************************************************/


public void drawMandelbrot()											//method to draw Mandelbrot set using default values
	{	
			JFrame frame = new JFrame("Mandelbrot Set"); 				// create a frame
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		//to close when click exit
																		//constructor for Panel takes Fractral type as input here
			frame.setContentPane(new Panel("Mandelbrot")); 				// set the content of the frame as one of this panel	

			frame.pack(); 
			frame.setLocationRelativeTo(null); 							//to centre the output on screen
			frame.setVisible(true); 
	}

/******************************************************************************************************************************************************************/

public void drawMandelbrot(int max)											//take 4 no of iterations.using overloading technique here.
	{																				
			
			JFrame frame = new JFrame("Mandelbrot Set"); 					// create a frame
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 			//to close when click exit
																			//constructor for Panel takes Fractral type and no of iterations as inputs here
			frame.setContentPane(new Panel(max,"Mandelbrot")); 				// set the content of the frame as one of this panel and passes no of iterations

			frame.pack(); 
			frame.setLocationRelativeTo(null); 								//to centre the output on screen
			frame.setVisible(true); 
		
	}


/**************************************************************************************************************************************************************************/

public boolean absoluteValue(double real, double imaginary)		//method to see whether complex value is in the mandelbrot set or not.
	{															//both mandelbrot and julia classes use this
		if(real*real + imaginary*imaginary < 4)					//if in the set return true
			return true;
		
		else
			return false;										//else false
	}
/**************************************************************************************************************************************************************************/

public void threadRun(Thread t1, Thread t2, Thread t3, Thread t4)	//Takes 4 threads and run them
	{
		t1.start();		t2.start();		t3.start();		t4.start();		//start run method in threads
		System.out.println("4 threads are running!!!");
	}

/*********************************************************************************************************************************************/

public void threadJoin(Thread t1, Thread t2, Thread t3, Thread t4)	//Takes 4 threads and wait till they finish
	{
		while(true)
			{
				if(java.lang.Thread.activeCount() == 1)				//break from loop if running threads == 1
					break;
			}
			System.out.println("4 threads are done!!!");
	}
/**********************************************************************************************************************************************/
}