import processing.core.PApplet;
import processing.core.PVector;


public abstract class Terrain 
{
	PApplet parent;
	
	PVector position;
	
	Terrain(PVector _position, PApplet _parent)
	{
		position = _position;
		parent = _parent;
	}
	
	void update()
	{
		parent.pushMatrix();
		parent.translate(position.x, position.y);
		draw();
		parent.popMatrix();	
	}
	
	void draw()
	{
		// Needed for rendering...don't know why.
	}
	
}
