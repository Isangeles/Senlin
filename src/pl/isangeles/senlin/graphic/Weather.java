package pl.isangeles.senlin.graphic;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.GConnector;
/**
 * Class for game world weather
 * @author Isangeles
 *
 */
public class Weather 
{
	private Animation rainAnim;
	private boolean rain;
	private int timer;
	private int rainTime;
	
	public Weather() throws SlickException, IOException 
	{
		Image[] rainFrames = new Image[4];
		rainFrames[0] = new Image(GConnector.getInput("day/rain/rain01.png"), "rain01", false);
		rainFrames[1] = new Image(GConnector.getInput("day/rain/rain02.png"), "rain02", false);
		rainFrames[2] = new Image(GConnector.getInput("day/rain/rain03.png"), "rain03", false);
		rainFrames[3] = new Image(GConnector.getInput("day/rain/rain04.png"), "rain04", false);
		int[] duration = {300, 300, 300, 300};
		
		rainAnim = new Animation(rainFrames, duration);
	}
	
	public void draw(float x, float y)
	{
		if(rain)
			rainAnim.draw(x, y);
	}
	
	public void update(int delta)
	{
		if(rain)
		{
			rainAnim.update(delta);
			timer += delta;
		}
		if(timer >= rainTime)
			rain = false;
	}
	
	public void startRaining(boolean rain, int howLong)
	{
		this.rain = rain;
		rainTime = howLong;
		timer = 0;
	}
	
	public boolean isRaining()
	{
		return rain;
	}

}
