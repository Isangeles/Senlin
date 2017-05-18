package pl.isangeles.senlin.graphic;

import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.SlickException;
/**
 * Class for manage game day values like weather, day phase, etc.  
 * @author Isangeles
 *
 */
public class Day 
{
	private Weather conditions;
	private DayPhase phase;
	private int time;
	private Random rng = new Random();

	public Day() throws SlickException, IOException 
	{
		conditions = new Weather();
		phase = new DayPhase();
	}

	public void draw()
	{
		conditions.draw(0, 0);
		phase.draw(0, 0);
	}
	
	public void update(int delta)
	{
		time += delta;
		if(time > 60000)
			phase.changePhase(DayPhase.MORNING);
		if(time > 120000)
			phase.changePhase(DayPhase.MIDDAY);
		if(time > 180000)
			phase.changePhase(DayPhase.AFTERNON);
		if(time > 220000)
			phase.changePhase(DayPhase.NIGHT);
		if(time > 240000)
			time = 0;
		
		if(!conditions.isRaining() && rng.nextInt(100) == 1)
			conditions.startRaining(true, 10000+(rng.nextInt(50000)));
		
		conditions.update(delta);
	}
}
