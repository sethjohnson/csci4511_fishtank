import processing.core.PApplet;
import processing.core.PVector;

public abstract class Sprite 
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
		if (position.x > parent.width || position.x < 0)
			direction.x = -direction.x;
		if (position.y > parent.height || position.y < 0)
			direction.y = -direction.y;

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
