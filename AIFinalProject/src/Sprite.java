import processing.core.PApplet;
import processing.core.PVector;

public abstract class Sprite extends Component
{
	PApplet parent;
	
	PVector position;
	PVector direction;
	  
	PVector dimension;
	float velocity;
	float max_velocity;
	float turn_speed;
	PVector oldMousePosition;
	
	boolean grabbed;
	
	Sprite(PApplet p, PVector pv)
	{
		parent = p;
		position = pv;
		dimension = new PVector(10,10);
		direction = new PVector(1,0);
		velocity = 0;
		turn_speed = 1;
		max_velocity = 5;
		oldMousePosition = new PVector(-1, -1);
		grabbed = false;
	}
	
	void update()
	{
		// Reset if we let go of mouse
		if(!parent.mousePressed)
			grabbed = false;
		//Using explicit corner cases for clarity. Could simplify but might make this condition confusing
		else if(grabbed && oldMousePosition.x > -1 && oldMousePosition.y > -1)
		{
			PVector deltaPos = changeInPosition();
			position.x += deltaPos.x;
			position.y += deltaPos.y;
		}
		
		oldMousePosition.x = parent.mouseX;
		oldMousePosition.y = parent.mouseY;
	}
		
	void draw()
	{
		if (position.x+dimension.x > parent.width || position.x-dimension.x < 0)
			direction.x = -direction.x;
		if (position.y+dimension.y > parent.height || position.y-dimension.y < 0)
			direction.y = -direction.y;
	}
	
	boolean mousePressed(){return false;}
	
	PVector changeInPosition()
	{
		float deltaX = parent.mouseX - oldMousePosition.x;
		float deltaY = parent.mouseY - oldMousePosition.y;
		
		return new PVector(deltaX, deltaY);		
	}
}
