import processing.core.PVector;

public abstract class Component 
{
	void draw(){}
	void update(){}
	boolean mouseClicked(){return false;}
	boolean mousePressed(){return false;}
	boolean containsPoint(PVector p){return false;}
	boolean intersectsLine(PVector origin, PVector terminal){return false;}
}
