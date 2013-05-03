import processing.core.PApplet;
import processing.core.PVector;
import java.util.LinkedList;
import java.util.ListIterator;

public class Screen 
{
	PApplet parent;
	Grid grid;
	int cellCount = 100;
	int frame;
	
	LinkedList<Sprite> sList; 
	LinkedList<Terrain> tList;
	
	Screen(PApplet p)
	{
		parent = p;
		frame = 0;
		
		sList = new LinkedList<Sprite>();
		tList = new LinkedList<Terrain>();
		grid = new Grid((parent.width / cellCount), (parent.height / cellCount), cellCount, cellCount, parent);
	}
	
	void setup()
	{
		sList.add(new Roomba(parent, new PVector(100, 200), 20, grid));	
		
		tList.add(new Rectangle(parent, new PVector(parent.width/2, parent.height/2), 25, 25));
	}
	
	void draw()
	{
		grid.draw();
				
		if(!sList.isEmpty())
		{
			ListIterator<Sprite> itr = sList.listIterator();			
			while(itr.hasNext())
			{
				Sprite s = itr.next();
				s.draw();
			}
		}

		if(!tList.isEmpty())
		{
			ListIterator<Terrain> itr = tList.listIterator();			
			while(itr.hasNext())
			{
				Terrain t = itr.next();
				t.draw();
			}
		}
	}
	
	void update()
	{
		grid.update();

		if(!sList.isEmpty())
		{
			ListIterator<Sprite> itr = sList.listIterator();			
			while(itr.hasNext())
			{
				Sprite s = itr.next();
				s.update();
			}
		}
		
		if(!tList.isEmpty())
		{
			ListIterator<Terrain> itr = tList.listIterator();
			while(itr.hasNext())
			{
				Terrain t = itr.next();
				t.update();
			}
		}
	}
	
	void mouseClicked()
	{
		grid.mouseClicked();
		
		if(!tList.isEmpty())
		{
			ListIterator<Terrain> itr = tList.listIterator();			
			while(itr.hasNext())
			{
				Terrain t = itr.next();
				t.mouseClicked();
			}
		}		
	}
	
	void mousePressed()
	{
		if(!tList.isEmpty())
		{
			ListIterator<Terrain> itr = tList.listIterator();			
			while(itr.hasNext())
			{
				Terrain t = itr.next();
				t.mousePressed();
			}
		}		
	}
}
