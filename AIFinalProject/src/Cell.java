import processing.core.PApplet;

public class Cell
{
	// A cell object knows about its location in the grid as well as its size with the variables x,y,w,h.
	float x,y;   // x,y location
	float w,h;   // width and height
	int color;
	float influence;
	PApplet parent; // The parent PApplet that we will render ourselves onto
	
	// Cell Constructor
	Cell(float tempX, float tempY, float tempW, float tempH, PApplet p) 
	{
	    x = tempX;
	    y = tempY;
	    w = tempW;
	    h = tempH;
	    color = 127;
	    influence = 0.0f;
	    parent = p;
	}
			  	
	void display()
	{
		parent.stroke(100);
		parent.fill(parent.color(0, 255*influence*influence,  0));
		parent.rect(x,y,w,h);
	}
}
