/*
E/16/388
Weerasundara WMTMPB
Project CO225

Panel class - Draw set
*/

import java.awt.*; 				/* java abstract window toolkit */ 
import javax.swing.*;			//as a GUI widget toolkit

class Panel extends JPanel 
{  	
	private static final long serialVersionUID = -3088001842L;  //to handle serializable class warnings
	private int max;											//no of iterations
	private String type;										//fractal type


	public Panel(String type) 									//if only type passed to constructor
      { 
		setPreferredSize(new Dimension(800, 800)); 				//set dimensions for framme
		this.max = 1000;										//set iterations as default 1000
		this.type = type;										//set type
      }
	
    public Panel(int max, String type)							 //if type and no of iterations passed.overloading technique
      { 
		setPreferredSize(new Dimension(800, 800)); 				//set dimensions for framme
		this.max = max;											//set iteratioms
		this.type = type;										//set type
      }
   

/*************************************************************************************************************************************************/

    public void paintComponent(Graphics g) 						//method to draw the plot
      { 
     	final int height = 800, width = 800;			
		int color_value[][] = new int[800][800];				//array to store iteration values
		
		if(type.equals("Mandelbrot"))     						//if plot is mandelbrot get values for mandelbrot plot
			color_value = Mandelbrot.getCordinates();	

		else 													//if plot is julia get values for julia plot
		{
			color_value = Julia.getCordinates();
		}
			
		
		for (int row = 0; row < width; row++) 					//start to paint from left down corner and end at right upper corner
			{													//iterate from 0 to 799 because values are reading from an array
				for (int col = height - 1; col > -1; col--) 
					{
						if(color_value[row][col] == max) 		//if belongs to the set
							{	
								g.setColor(Color.black); 		//set black color
					            g.fillRect(row,col,1,1) ;		//draw a point(square)
							}
						else 									//doesnt belong to the set
							{	
								int iteration = color_value[row][col];  //store iteration count of point
								g.setColor(Color.getHSBColor((float)iteration*8.0f/(float)max,1.0f,1.0f));		//assign a color according to iterations
					            g.fillRect(row,col,1,1) ;		//draw a point(square)
							}
					}					
			}	
			
 					
	  }
} 