package pl.isangeles.senlin.core;

import java.io.File;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.*;

public class Character 
{
	private String name;
	private int level;
	private int health;
	private int magicka;
	private float haste;
	private Atributes atributes;
	private Image portrait;
	private boolean live;
	private Random numberGenerator = new Random();
	
	public Character() throws SlickException
	{
		name = "Name";
		level = 0;
		atributes = new Atributes(1, 1, 1, 1, 1);
		portrait = new Image("data" + File.separator + "portrait" + File.separator + "default.jpg");
		live = true;
	}
	
	public Character(String name, int level, Atributes atributes, String portraitName) throws SlickException
	{
		this.name = name;
		this.atributes = atributes;
		setLevel(level);
		portrait = new Image("data" + File.separator + "portrait" + File.separator + portraitName);
		live = true;
	}
	
	public void levelUp()
	{
		level ++;
		health = atributes.addHealth();
		magicka = atributes.addMagicka();
		haste = atributes.addHaste();
	}
	
	public void setLevel(int value)
	{
		level = value;
		health = atributes.addHealth() * value;
		magicka = atributes.addMagicka() * value;
		haste = atributes.addHaste() * value;
	}
	
	public void setPortrait(Image img)
	{
	    portrait = img;
	}
	
	public void setName(String text)
	{
	    name = text;
	}
	
	public void drawPortrait(float x, float y)
	{
		portrait.draw(x, y, 50f, 70f);
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
