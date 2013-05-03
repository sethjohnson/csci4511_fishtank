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
	
	LinkedList<Roomba> sList = new LinkedList<Roomba>();
	
	Screen(PApplet p)
	{
		parent = p;
		frame = 0;
	}
	
	void setup()
	{
		grid = new Grid((parent.width / cellCount), (parent.height / cellCount), cellCount, cellCount, parent);
		
		sList.add(new Roomba(parent, new PVector(100, 200), 20, grid));
	}
	
	void draw()
	{
		grid.draw();
		if(!sList.isEmpty())
		{
			ListIterator<Roomba> itr = sList.listIterator();
			
			int snapshot = parent.frameCount;
			while(itr.hasNext())
			{
				Roomba r = itr.next();
				r.update(snapshot - frame);
			}
			frame = parent.frameCount;
		}
	}
	
	void update()
	{
		grid.update();
	}
}