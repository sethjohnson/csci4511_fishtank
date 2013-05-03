import processing.core.PApplet;
import processing.core.PVector;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

public class Screen 
{
	PApplet parent;
	Grid grid;
	int cellCount = 100;
	int frame;
	
	LinkedList<Component> cList;
	
	Screen(PApplet p)
	{
		parent = p;
		frame = 0;
		
		cList = new LinkedList<Component>();
		grid = new Grid((parent.width / cellCount), (parent.height / cellCount), cellCount, cellCount, parent);
	}
	
	void setup()
	{	
		cList.add(new Roomba(parent, new PVector(100, 200), 20, grid));
		cList.add(new Rectangle(parent, new PVector(100, 400), 25, 25));
		cList.add(new Rectangle(parent, new PVector(300, 500), 25, 25));
		cList.add(new Rectangle(parent, new PVector(400, 300), 25, 25));
		cList.add(new Rectangle(parent, new PVector(400, 600), 25, 25));
	}
	
	void draw()
	{
		grid.draw();
		
		if(!cList.isEmpty())
		{
			ListIterator<Component> itr = cList.listIterator();
			while(itr.hasNext())
			{
				Component c = itr.next();
				c.draw();
			}
		}
	}
	
	void update()
	{
		grid.update();

		if(!cList.isEmpty())
		{
			ListIterator<Component> itr = cList.listIterator();
			while(itr.hasNext())
			{
				Component c = itr.next();
				c.update();
			}
		}
	}
	
	void mouseClicked()
	{
		grid.mouseClicked();
		
		if(!cList.isEmpty())
		{
			ListIterator<Component> itr = cList.listIterator();
			while(itr.hasNext())
			{
				Component c = itr.next();
				c.mouseClicked();
			}
		}
	}
	
	void mousePressed()
	{
		if(!cList.isEmpty())
		{
			ListIterator<Component> itr = cList.listIterator();
			while(itr.hasNext())
			{
				Component c = itr.next();
				if(c.mousePressed())
				{
					//Moves the currently pressed terrain to the bottom of the list
					//This subsequently makes it so it is drawn last, and therefore on top
					Collections.rotate(cList.subList(itr.previousIndex(), cList.size()), -1);
					
					break;
				}
			}
		}
	}
}
