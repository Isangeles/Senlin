package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.Effect;
import pl.isangeles.senlin.data.pattern.EffectPattern;
import pl.isangeles.senlin.util.DConnector;
/**
 * Static class for all game effects
 * Loaded on newGameMenu initialization
 * @author Isangeles
 *
 */
public class EffectsBase 
{
	private static GameContainer gc;
	private static Map<String, EffectPattern> effectsMap = new HashMap<>();
	
	private EffectsBase() 
	{
	}
	
	public static Effect getEffect(String id)
	{
	    if(effectsMap.get(id) != null)
	    {
	        try 
	        {
				return effectsMap.get(id).make(gc);
			} 
	        catch (SlickException | IOException | FontFormatException e) {
	        	Log.addSystem("effects_base_get-fail msg///effect building from pattern fail: " + id);
	            return null;
			}
	    }
	    else
	    {
	        Log.addSystem("effects_base_get-fail msg///no such effect: " + id);
            return null;
	    }
	}

	public static void load(GameContainer gc) throws SAXException, IOException, ParserConfigurationException
	{
		EffectsBase.gc = gc;
		effectsMap = DConnector.getEffectsMap("effects");
	}
}
