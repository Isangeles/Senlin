package pl.isangeles.senlin.core;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.*;
import pl.isangeles.senlin.graphic.Avatar;
/**
 * Class for game characters like players, NPCs, etc.
 * @author Isangeles
 *
 */
public class Character 
{
	private String name;
	private int level;
	private int health;
	private int magicka;
	private int[] position = {0, 0};
	private float haste;
	private Attributes atributes;
	private Image portrait;
	private boolean live;
	private Avatar avatar;
	private Random numberGenerator = new Random();
	/**
	 * Basic constructor for character creation menu, after use this constructor method levelUp() should be used to make new character playable
	 * @throws SlickException
	 * @throws IOException
	 */
	public Character() throws SlickException, IOException
	{
		name = "Name";
		level = 0;
		atributes = new Attributes(1, 1, 1, 1, 1);
		portrait = new Image("data" + File.separator + "portrait" + File.separator + "default.jpg");
		live = true;
		avatar = new Avatar();
	}
	/**
	 * This constructor provides playable character
	 * @param name Name of character in game
	 * @param level Character experience level
	 * @param atributes Set of character attributes
	 * @param portraitName Name of image file in portrait catalog
	 * @throws SlickException
	 * @throws IOException
	 */
	public Character(String name, int level, Attributes atributes, String portraitName) throws SlickException, IOException
	{
		this.name = name;
		this.atributes = atributes;
		setLevel(level);
		portrait = new Image("data" + File.separator + "portrait" + File.separator + portraitName);
		live = true;
		avatar = new Avatar();
	}
	/**
	 * Adds one level to character
	 */
	public void levelUp()
	{
		level ++;
		health = atributes.addHealth();
		magicka = atributes.addMagicka();
		haste = atributes.addHaste();
	}
	/**
	 * Adds levels to character
	 * @param value Numbers of levels
	 */
	public void setLevel(int value)
	{
		level = value;
		health = atributes.addHealth() * value;
		magicka = atributes.addMagicka() * value;
		haste = atributes.addHaste() * value;
	}
	/**
	 * Sets specific portrait from portrait catalog to character 
	 * @param img
	 */
	public void setPortrait(Image img)
	{
	    portrait = img;
	}
	/**
	 * Sets character name
	 * @param text String with name
	 */
	public void setName(String text)
	{
	    name = text;
	}
	/**
	 * Draws character avatar
	 * @param x Position on x axis
	 * @param y Position on y axis
	 */
	public void draw()
	{
		avatar.draw(position[0], position[1]);
	}
	/**
	 * Draws character portrait
	 * @param x Position on x axis
	 * @param y Position on y axis
	 */
	public void drawPortrait(float x, float y)
	{
		portrait.draw(x, y, 50f, 70f);
	}
	/**
	 * Updates character avatar animation
	 * @param delta
	 */
	public void update(int delta)
	{
		avatar.update(delta);
	}
	
	public boolean move(int x, int y)
	{
		if(position[0] == x && position[1] == y)
		{
			avatar.move(false);
			return false;
		}
		else
		{
			avatar.move(true);
			if(position[0] > x)
			{
				position[0] -= 1;
				avatar.goLeft();
			}
			if(position[0] < x)
			{
				position[0] += 1;
				avatar.goRight();
			}
			if(position[1] > y)
			{
				position[1] -= 1;
				avatar.goUp();
			}
			if(position[1] < y)
			{
				position[1] += 1;
				avatar.goDown();
			}
			return true;
		}
	}
	
	public boolean isMove()
	{
		return avatar.isMove();
	}
	
	public int getHit()
	{
		return numberGenerator.nextInt(10) + atributes.getBasicHit();
	}
	
	public int getSpell()
	{
		return numberGenerator.nextInt(20) + atributes.getBasicSpell(); 
	}
	
	public int getLevel()
	{ return level; }
	
	public int getHealth()
	{ return health; }
	
	public int getMagicka()
	{ return magicka; }
	
	public int getStr()
	{ return atributes.getStr(); }
	
	public int getCon()
	{ return atributes.getCon(); }
	
	public int getDex()
	{ return atributes.getDex(); }
	
	public int getInt()
	{ return atributes.getInt(); }
	
	public int getWis()
	{ return atributes.getWis(); }
	
	public float getHaste()
	{ return haste; }
	
	public String getName()
	{ return name; }
	
	public boolean isLive()
	{ return live; }
	
	public Image getPortrait()
	{
		return portrait;
	}
	
	public void takeHealth(int value)
	{
		health -= value;
		if(health <= 0)
			live = false;
	}
	
	public void takeMagicka(int value)
	{
		magicka -= value;
	}
	
	public void addStr()
	{ atributes.addStr(); }
	
	public void addStr(int value)
	{ atributes.addStr(value); }
	
	public void addCon()
	{ atributes.addCon(); }
	
	public void addCon(int value)
	{ atributes.addCon(value); }
	
	public void addDex()
	{ atributes.addDex(); }
	
	public void addDex(int value) 
	{ atributes.addDex(value); }
	
	public void addInt()
	{ atributes.addInt(); }
	
	public void addInt(int value) 
	{ atributes.addInt(value); }
	
	public void addWis()
	{ atributes.addWis(); }
	
	public void addWis(int value) 
	{ atributes.addWis(value); }
}
