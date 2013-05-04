import processing.core.*;

public class main extends PApplet 
{	
	/**
	 * Version 0.1
	 */
	private static final long serialVersionUID = 1L;

	Screen screen;
	int val;
	public void setup() 
	{
		val = 42;
		size(700, 700);
		screen = new Screen(this);
		screen.setup();
	}
	
	public void draw()
	{		
		screen.draw();
		screen.update();
	}
		
	public void mouseClicked()
	{
		screen.mouseClicked();
	}
	
	public void mousePressed()
	{
		screen.mousePressed();
	}
}
