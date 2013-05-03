import processing.core.PApplet;
import processing.core.PVector;


public abstract class Terrain extends Component
{	
	enum Corner {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTER, NONE};

	Corner grabbed;

	PApplet parent;
	
	PVector position;
	PVector oldMousePosition;

	Terrain(PVector _position, PApplet _parent)
	{
		position = _position;
		parent = _parent;
		grabbed = Corner.NONE;
		oldMousePosition = new PVector(-1, -1);
	}
	
	void update()
	{
		// Reset if we let go of mouse
		if(!parent.mousePressed)
			grabbed = Corner.NONE;
		else if(grabbed == Corner.CENTER && oldMousePosition.x > -1 && oldMousePosition.y > -1)
		{
			PVector deltaP = changeInPosition();
			position.x += deltaP.x;
			position.y += deltaP.y;
		}
				
		oldMousePosition.x = parent.mouseX;
		oldMousePosition.y = parent.mouseY;	
	}
		
	PVector changeInPosition()
	{
		float deltaX = parent.mouseX - oldMousePosition.x;
		float deltaY = parent.mouseY - oldMousePosition.y;
		return new PVector(deltaX, deltaY);
	}
}