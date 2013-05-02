import processing.core.*;

public class main extends PApplet 
{	
	Screen screen;
	
	public void setup() 
	{
		size(800, 800);
		screen = new Screen(this);
		screen.setup();
	}
	
	public void draw()
	{		
		background(255); //white
		screen.draw();
	}
	
	public void mouseClicked()
	{
		screen.update();
	}
}
