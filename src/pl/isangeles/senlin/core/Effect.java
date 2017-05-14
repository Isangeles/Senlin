package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.ui.EffectTile;
import pl.isangeles.senlin.util.GConnector;

/**
 * Class for effects used by items, skills, etc.
 * @author Isangeles
 *
 */
public class Effect 
{
    private String id;
    private String name;
    private String info;
    private String imgName;
	private EffectType type;
	private int hpMod;
	private int manaMod;
	private Attributes attMod;
	private float hasteMod;
	private float dodgeMod;
	private int dmgMod;
	private int dot;
	private int duration;
	private int time;
	private int dotTimer;
	private boolean dotActive;
	private boolean on;
	private EffectTile tile;
	/**
	 * Effect constructor
	 * @param id Effect ID
	 * @param name Effect name
	 * @param info Basic informations about effect
	 * @param hpMod Affect on health points
	 * @param manaMod Affect on magicka points
	 * @param attMod Affect on attributions 
	 * @param hasteMod Affect on haste
	 * @param dodgeMod Affect on dodge rating
	 * @param dmgMod Affect on damage
	 * @param duration Effect duration
	 * @param type Effect type
	 * @throws FontFormatException 
	 * @throws IOException 
	 * @throws SlickException 
	 */
	public Effect(String id, String name, String info, String imgName, int hpMod, int manaMod, Attributes attMod, float hasteMod, float dodgeMod, 
				  int dmgMod, int dot, int duration, EffectType type, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
	    this.id = id;
	    this.name = name;
	    this.info = info;
	    this.imgName = imgName;
		this.type = type;
		this.hpMod = hpMod;
		this.manaMod = manaMod;
		this.attMod = attMod;
		this.hasteMod = hasteMod;
		this.dodgeMod = dodgeMod;
		this.dmgMod = dmgMod;
		this.dot = dot;
		this.duration = duration;
		buildTile(gc);
	}
	/**
	 * Starts affect on specified character
	 * @param character Some game character
	 */
	public void affect(Character character)
	{
		if(dotActive)
		{
			character.addHealth(dot);
			dotActive = false;
		}
	}
	/**
	 * Removes affect from specified character
	 * @param character Some game character
	 */
	public void removeFrom(Character character)
	{
		character.modHealth(-hpMod);
		character.modMgicka(-manaMod);
		character.modAttributes(attMod.nagative());
	}
	
	public int getHpMod()
	{
		return hpMod;
	}
	
	public int getManaMod()
	{
		return manaMod;
	}
	
	public Attributes getAttributesMod()
	{
		return attMod;
	}
	
	public float getHasteMod()
	{
		return hasteMod;
	}
	
	public float getDodgeMod()
	{
		return dodgeMod;
	}
	
	public int getDmgMod()
	{
		return dmgMod;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public boolean isOn()
	{
		return on;
	}
	/**
	 * Updates effect current time
	 * @param delta Time from last game loop update
	 */
	public void updateTime(int delta)
	{
		time += delta;
		dotTimer += delta;
		if(time >= duration)
			on = false;
		if(dotTimer > 1000 && !dotActive)
		{
			dotActive = true;
			dotTimer = 0;
		}
	}
	/**
	 * Starts effect timer
	 */
	public void turnOn(Character character)
	{
		on = true;
		character.modHealth(hpMod);
		character.modMgicka(manaMod);
		character.modAttributes(attMod);
	}
	/**
	 * Returns effect UI icon
	 * @return EffectTile object
	 */
	public EffectTile getTile()
	{
		return tile;
	}
	
	public String getId()
	{
		return id;
	}
	/**
	 * Builds effect UI icon
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	private void buildTile(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		tile = new EffectTile(GConnector.getInput("icon/effect/"+imgName), "uiEff"+id, false, gc, info);
	}

}
