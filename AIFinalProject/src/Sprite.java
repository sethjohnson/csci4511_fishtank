import processing.core.PApplet;
import processing.core.PVector;

public class Sprite 
{
	PApplet parent;
	
	PVector position;
	PVector direction;
	  
	PVector dimension;
	float velocity;
	float max_velocity;
	float turn_speed;
	
	Sprite(PApplet p, PVector pv)
	{
		parent = p;
		position = pv;
		dimension = new PVector(10,10);
		direction = new PVector(1,0);
		velocity = 0;
		turn_speed = 1;
		max_velocity = 5;
	}
		  	
	void update()
	{
		if(position.x < 0)
			position.x = 1.0f;
		if(position.x > parent.width)
			position.x = parent.width - 1.0f;
		if(position.y < 0)
			position.y = 1.0f;
		if(position.y > parent.height)
			position.y = parent.height - 1.0f;
		
		parent.pushMatrix();
		parent.translate(position.x, position.y);
		parent.rotate(direction.heading());
		draw();
		parent.popMatrix();		
	}
	
	void draw()
	{
		// Needed for rendering...don't know why.
	}
}