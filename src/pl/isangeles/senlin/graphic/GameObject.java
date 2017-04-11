package pl.isangeles.senlin.graphic;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.Settings;
/**
 * Class for game object like avatars etc.
 * @author Isangeles
 *
 */
public abstract class GameObject extends Image
{
    private float scale;
    protected float x;
    protected float y;
    
    public GameObject(InputStream is, String ref, boolean flipped) throws SlickException
    {
    	super(is, ref, flipped);
    	setScale();
    }
    
    public GameObject(Image img)
    {
    	super(img);
    	setScale();
    }
    /**
     * Draws object on specified position on screen
     * @param x Position on x-axis
     * @param y Position on y-axis
     * @param scaledPos True if position should be scale to current resolution, false otherwise(Irrespective to this, object size is still scaled)
     */
    public void draw(float x, float y, boolean scaledPos)
    {
    	this.x = x;
    	this.y = y;
        if(scaledPos)
        {
        	this.x *= scale;
            this.y *= scale;
        }
        super.draw(this.x, this.y, scale);
    }
    
    public float getScale()
    {
    	return scale;
    }
    /**
     * Returns distance corrected by scale
     * @param rawDistance Distance on 1920x1080
     * @return Distance scaled to current resolution
     */
    protected int getDis(int rawDistance)
    {
    	return Math.round(rawDistance * scale);
    }
    /**
     * Sets proportion for object based on current resolution, called by constructor
     * @throws FileNotFoundException
     */
    private void setScale()
    {
        float defResX = 1920;
        float defResY = 1080;
        float resX = Settings.getResolution()[0];
        float resY = Settings.getResolution()[1];
        float proportionX = resX / defResX;
        float proportionY = resY / defResY;
        scale = Math.round(Math.min(proportionX, proportionY) * 10f) / 10f;
    }
}
