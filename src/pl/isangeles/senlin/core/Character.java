package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.*;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.CommBase;
import pl.isangeles.senlin.graphic.Avatar;
import pl.isangeles.senlin.inter.Portrait;
/**
 * Class for game characters like players, NPCs, etc.
 * @author Isangeles
 *
 */
public class Character 
{
	private String id;
	private String name;
	private Attitude attitude;
	private int level;
	private int experience;
	private int maxExperience;
	private int health;
	private int maxHealth;
	private int magicka;
	private int maxMagicka;
	private int minDamage; //UNUSED damage calculate dynamically by getDamage method
	private int maxDamage; //UNUSED damage calculate dynamically by getDamage method 
	private int[] position = {0, 0};
	private float haste;
	private Attributes attributes;
	private Portrait portrait;
	private boolean live;
	private boolean isCasting;
	private Avatar avatar;
	private Inventory inventory;
	private Random numberGenerator = new Random();
	/**
	 * Basic constructor for character creation menu, after use this constructor method levelUp() should be called to make new character playable
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException 
	 */
	public Character(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		id = "player";
		name = "Name";
		level = 0;
		attitude = Attitude.FRIENDLY;
		attributes = new Attributes(1, 1, 1, 1, 1);
		portrait = new Portrait("data" + File.separator + "portrait" + File.separator + "default.jpg", gc);
		live = true;
		avatar = new Avatar(this, gc);
		inventory = new Inventory();
	}
	/**
	 * This constructor provides playable character
	 * @param name Name of character in game
	 * @param level Character experience level
	 * @param atributes Set of character attributes
	 * @param portraitName Name of image file in portrait catalog
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException 
	 */
	public Character(String id, Attitude attitude, String name, int level, Attributes atributes, String portraitName, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		this.id = id;
		this.name = name;
		this.attitude = attitude;
		this.attributes = atributes;
		addLevel(level);
		portrait = new Portrait(GConnector.getPortrait(portraitName), gc);
		live = true;
		avatar = new Avatar(this, gc);
		inventory = new Inventory();
	}
	/**
	 * Adds one level to character
	 */
	public void levelUp()
	{
		level ++;
		maxHealth = attributes.addHealth();
		health = maxHealth;
		maxMagicka = attributes.addMagicka();
		magicka = maxMagicka;
		haste = attributes.addHaste();
		maxExperience = 1000 * level;
	}
	/**
	 * Adds levels to character
	 * @param value Numbers of levels
	 */
	public void addLevel(int value)
	{
		for(int i = 0; i < value; i ++)
		{
			levelUp();
		}
	}
	/**
	 * Sets specific portrait from portrait catalog to character 
	 * @param img
	 */
	public void setPortrait(Portrait img)
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
	 * Sets character attributes
	 * @param attributes Attributes for character
	 */
	public void setAttributes(Attributes attributes)
	{
		this.attributes = attributes;
	}
	/**
	 * Instantly sets character position
	 * @param x Position on x-axis
	 * @param y Position on y-axis
	 */
	public void setPosition(int x, int y)
	{
		position[0] = x;
		position[1] = y;
	}
	/**
	 * Sets item as one of character weapon
	 * @param weapon Any item that can be casted to weapon
	 * @return True if item was successful inserted, false otherwise
	 */
	public boolean setWeapon(Item weapon)
    {
		return inventory.setWeapon(weapon);
    }
    /**
     * Sets item as one of character armor parts
     * @param armorPart Item that can be casted to armor
     * @return True if item was successful inserted, false otherwise
     */
    public boolean setArmor(Item armorPart)
    {
    	return inventory.setArmor(armorPart);
    }
    /**
     * Sets item as one of character trinkets
     * @param trinket Item that can be casted to trinket
     * @return True if item was successful inserted, false otherwise
     */
    public boolean setTrinket(Item trinket)
    {
    	return inventory.setTrinket(trinket);
    }
    
    public void startCast(int castTime)
    {
    	isCasting = true;
    	
    }
	/**
	 * Removes specific item from equippment
	 * @param item Equipped character item
	 */
	public void unequipp(Item item)
    { inventory.unequipp(item); }
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
	/**
	 * Moves character to given position if thats position is different then actual   
	 * @param x Position on X axis
	 * @param y Position on Y axis
	 * @return False if given position is same as actual or true if else
	 */
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
	/**
	 * Checks if character avatar is in move
	 * @return True if avatar is in move false if else
	 */
	public boolean isMove()
	{
		return avatar.isMove();
	}
	/**
	 * Get character hit
	 * TODO include current weapon damage
	 * @return Hit value
	 */
	public int getHit()
	{
		return numberGenerator.nextInt(10) + attributes.getBasicHit();
	}
	/**
	 * Returns character damage
	 * @return Table with minimal[0] and maximal[1] damage values
	 */
	public int[] getDamage()
	{
		int max = attributes.getBasicHit() + inventory.getWeaponDamage()[1] + 10;
		int min = attributes.getBasicHit() + inventory.getWeaponDamage()[0];
		return new int[]{min, max};
	}
	/**
	 * Returns character armor rating
	 * @return Value of armor rating
	 */
	public int getArmorRating()
	{
		return inventory.getArmorRating();
	}
	
	public int getSpell()
	{
		return numberGenerator.nextInt(20) + attributes.getBasicSpell(); 
	}
	
	public int getLevel()
	{ return level; }
	
	public int getExperience()
	{ return experience; }
	
	public int getMaxExperience()
	{ return maxExperience; }
	
	public int getHealth()
	{ return health; }
	
	public int getMaxHealth()
	{ return maxHealth; }
	
	public int getMagicka()
	{ return magicka; }
	
	public int getMaxMagicka()
	{ return maxMagicka; }
	
	public int getStr()
	{ return attributes.getStr(); }
	
	public int getCon()
	{ return attributes.getCon(); }
	
	public int getDex()
	{ return attributes.getDex(); }
	
	public int getInt()
	{ return attributes.getInt(); }
	
	public int getWis()
	{ return attributes.getWis(); }
	
	public float getHaste()
	{ return haste; }
	
	public String getName()
	{ return name; }
	
	public Attitude getAttitude()
	{ return attitude; }
	
	public int[] getPosition()
	{ return position; }
	
	public boolean isLive()
	{ return live; }
	
	public Portrait getPortrait()
	{
		return portrait;
	}
	/**
	 * Returns specified itemId from inventory
	 * @param itemId Item ID
	 * @return Item with specified id
	 */
	public Item getItem(String itemId)
	{ return inventory.getItem(itemId); }
	/**
	 * Returns item with specified index in inventory container
	 * @param index Item index in container 
	 * @return Item from inventory container
	 */
	public Item getItem(int index)
	{ return inventory.get(index); }
	/**
	 * Returns table with all character items in inventory
	 * @return Table with items
	 */
	public Item[] getItems()
	{ return inventory.getItems(); }
	/**
	 * Returns character inventory
	 * @return Inventory
	 */
	public Inventory getInventory()
	{ return inventory; }
	/**
	 * Subtract specified value from character health value 
	 * @param value Value to subtract
	 */
	public void takeHealth(int value)
	{
		health -= value;
		CommBase.loseInfo(value, TConnector.getText("ui", "hpName"));
		if(health <= 0)
			live = false;
	}
	/**
	 * Subtract specified value from character magicka value 
	 * @param value Value to subtract
	 */
	public void takeMagicka(int value)
	{
		magicka -= value;
		CommBase.loseInfo(value, TConnector.getText("ui", "manaName"));
		if(magicka < 0)
			magicka = 0;
	}
	/**
	 * Subtract specified value from character experience value
	 * @param value Value to subtract
	 */
	public void takeExperience(int value)
	{
		experience -= value;
		CommBase.loseInfo(value, TConnector.getText("ui", "expName"));
		if(experience < 0)
			experience = 0;
	}
	public void addStr()
	{ attributes.addStr(); }
	
	public void addStr(int value)
	{ attributes.addStr(value); }
	
	public void addCon()
	{ attributes.addCon(); }
	
	public void addCon(int value)
	{ attributes.addCon(value); }
	
	public void addDex()
	{ attributes.addDex(); }
	
	public void addDex(int value) 
	{ attributes.addDex(value); }
	
	public void addInt()
	{ attributes.addInt(); }
	
	public void addInt(int value) 
	{ attributes.addInt(value); }
	
	public void addWis()
	{ attributes.addWis(); }
	
	public void addWis(int value) 
	{ attributes.addWis(value); }
	/**
	 * Adds specified value to character health points
	 * @param value Value to add
	 */
	public void addHealth(int value)
	{
		health += value;
		CommBase.gainInfo(value, TConnector.getText("ui", "hpName"));
		if(health > maxHealth)
			health = maxHealth;
	}
	/**
	 * Adds specified value to character magicka points
	 * @param value Value to add
	 */
	public void addMagicka(int value)
	{
		magicka += value;
		CommBase.gainInfo(value, TConnector.getText("ui", "manaName"));
		if(magicka > maxMagicka)
			magicka = maxMagicka;
	}
	/**
	 * Adds specified value to character experience points
	 * @param value Value to add
	 */
	public void addExperience(int value)
	{
		experience += value;
		CommBase.gainInfo(value, TConnector.getText("ui", "expName"));
		if(experience >= maxExperience)
			levelUp();
	}
	/**
	 * Adds item to character inventory
	 * @param itemId Item ID in base
	 * @param gc Game container for item tile
	 * @throws FontFormatException 
	 * @throws IOException 
	 * @throws SlickException 
	 */
	public boolean addItem(Item item)
	{ return inventory.add(item); }
	/**
     * Adds gold to character inventory
     * @param value Integer value to add
     */
    public void addGold(int value)
    { inventory.addGold(value); }
	/**
	 * Draws all character items (called by inventory menu)
	 * @param x Position in X axis
	 * @param y Position in Y axis
	 */
	@Deprecated
	public void drawItems(float x, float y)
	{
		inventory.drawItems(x, y);
	}
}
