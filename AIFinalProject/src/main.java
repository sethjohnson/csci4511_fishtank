import processing.core.*;

public class main extends PApplet 
{	
	/**
	 * Version 0.1
	 */
	private static final long serialVersionUID = 1L;

	Screen screen;
	
	public void setup() 
	{
		size(700, 700);
		screen = new Screen(this);
		screen.setup();
	}
	
	public void draw()
	{		
		screen.draw();
	}
	
	public void mouseClicked()
	{
		screen.update();
	}
}
