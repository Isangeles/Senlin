package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import pl.isangeles.senlin.util.*;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.core.skill.Abilities;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.dialogue.Dialogue;
import pl.isangeles.senlin.graphic.Avatar;
import pl.isangeles.senlin.inter.Portrait;
import pl.isangeles.senlin.quest.Quest;
import pl.isangeles.senlin.quest.QuestTracker;
import pl.isangeles.senlin.states.Global;
/**
 * Class for game characters like players, NPCs, etc.
 * @author Isangeles
 *
 */
public class Character implements Targetable
{
	private String id;
	private String name;
	private Attitude attitude;
	private Guild guild;
	private int level;
	private int experience;
	private int maxExperience;
	private int health;
	private int maxHealth;
	private int magicka;
	private int maxMagicka;
	private int[] position = {0, 0};
	private int[] destPoint = {position[0], position[1]};
	private float haste;
	private Attributes attributes;
	private Portrait portrait;
	private boolean live;
	private Avatar avatar;
	private Inventory inventory;
	private Abilities abilities;
	private Targetable target;
	private List<Dialogue> dialogues;
	private Map<Character, Attitude> attitudeMem = new HashMap<>();
	private Effects effects = new Effects();
	private List<Quest> quests = new ArrayList<>();
	private Flags flags = new Flags();
	private QuestTracker qTracker;
	private Random numberGenerator = new Random();
	/**
	 * Basic constructor for character creation menu, after use this constructor method levelUp() should be called to make new character playable
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException 
	 */
	public Character(GameContainer gc) 
	        throws SlickException, IOException, FontFormatException
	{
		id = "player";
		name = "Name";
		level = 0;
		attitude = Attitude.FRIENDLY;
		guild = GuildsBase.getGuild(0);
		attributes = new Attributes(1, 1, 1, 1, 1);
		portrait = new Portrait("data" + File.separator + "portrait" + File.separator + "default.jpg", gc);
		live = true;
		avatar = new Avatar(this, gc, "cloth12221-45x55-2.png");
		inventory = new Inventory();
		abilities = new Abilities();
		abilities.add(SkillsBase.getAutoAttack(this));
		qTracker = new QuestTracker(this);
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
	public Character(String id, Attitude attitude, int guildID, String name, int level, Attributes atributes, Portrait portrait, String spritesheet, GameContainer gc) 
	        throws SlickException, IOException, FontFormatException
	{
		this.id = id;
		this.name = name;
		this.attitude = attitude;
		this.guild = GuildsBase.getGuild(guildID);
		this.attributes = atributes;
		this.portrait = portrait;
		live = true;
		avatar = new Avatar(this, gc, spritesheet);
		inventory = new Inventory();
		abilities = new Abilities();
		addLevel(level);
		abilities.add(SkillsBase.getAutoAttack(this));
		dialogues = DialoguesBase.getDialogues(this.id);
		qTracker = new QuestTracker(this);
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
	 * Sets character attitude
	 * @param attitude Attitude enumeration
	 */
	public void setAttitude(Attitude attitude)
	{
		this.attitude = attitude;
	}
	/**
	 * Sets character guild
	 * @param guild Guild object
	 */
	public void setGuild(Guild guild)
	{
		this.guild = guild;
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
		destPoint[0] = x;
		destPoint[1] = y;
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
    /**
     * Sets specified flag for character
     * @param flag Flag ID
     */
    public void setFlag(String flag)
    {
    	flags.add(flag);
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
	public void update(int delta, TiledMap map)
	{
		if(!live)
		{
			avatar.kill();
			attitude = Attitude.DEAD;
			return;
		}
	    if(position[0] == destPoint[0] && position[1] == destPoint[1])
        {
            avatar.move(false);
        }
        else
        {
            avatar.move(true);
            if(position[0] > destPoint[0])
            {
                if(isMovable(position[0]-1, position[1], map))
                {
                    position[0] -= 1;
                    avatar.goLeft();
                }
            }
            if(position[0] < destPoint[0])
            {
                if(isMovable(position[0]+1, position[1], map))
                {
                    position[0] += 1;
                    avatar.goRight();
                }
            }
            if(position[1] > destPoint[1])
            {
                if(isMovable(position[0], position[1]-1, map))
                {
                    position[1] -= 1;
                    avatar.goUp();
                }
            }
            if(position[1] < destPoint[1])
            {
                if(isMovable(position[0], position[1]+1, map))
                {
                    position[1] += 1;
                    avatar.goDown();
                }
            }
        }
	    
	    abilities.update(delta);
		avatar.update(delta);
		effects.update(delta, this);
		flags.update(quests);
	}
	/**
	 * Moves character to given position  
	 * @param x Position on X axis
	 * @param y Position on Y axis
	 */
	public void moveTo(int x, int y)
	{
		destPoint[0] = x;
		destPoint[1] = y;
	}
	/**
	 * Moves character to given target position   
	 * @param x Position on X axis
	 * @param y Position on Y axis
	 * @return False if given position is same as actual or true if else
	 */
	public void moveTo(Targetable target)
	{
		destPoint[0] = target.getPosition()[0];
		destPoint[1] = target.getPosition()[1];
	}
	/**
	 * Moves character by specified values
	 * @param moveX Horizontal move value
	 * @param moveY Vertical move value
	 */
	public void move(int moveX, int moveY)
	{
		destPoint[0] = position[0] + moveX;
		destPoint[1] = position[1] + moveY;
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
	 * TODO include offhand weapon damage
	 * @return Hit value
	 */
	public int getHit()
	{
		int hit = numberGenerator.nextInt(10) + attributes.getBasicHit();
		if(inventory.getMainWeapon() != null)
		{
			hit += (numberGenerator.nextInt(inventory.getWeaponDamage()[0])+inventory.getWeaponDamage()[1]);
		}
		return hit;
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
	/**
	 * Returns character name
	 * @return String with name
	 */
	public String getName()
	{ return name; }
	/**
	 * Returns character attitude
	 * @return Character attitude enumeration
	 */
	public Attitude getAttitude()
	{ return attitude; }
	/**
	 * Returns attitude to specified character
	 * @param character Some character
	 * @return Attitude enumeration
	 */
	public Attitude getAttitudeTo(Character character)
	{
		if(character == null)
			return attitude;
		if(!character.isLive())
		    return Attitude.DEAD;
		if(attitudeMem.containsKey(character))
		    return attitudeMem.get(character);
		if(guild == character.getGuild())
			return Attitude.FRIENDLY;
		else
			return attitude;
	}
	/**
	 * Returns guild id
	 * @return Integer with guild id
	 */
	public Guild getGuild()
	{ return guild; }
	/**
	 * Returns all character flags
	 * @return Container with flags IDs
	 */
	public Flags getFlags()
	{ return flags; }
	/**
	 * Returns current character position
	 * @return Table with x and y position
	 */
	public int[] getPosition()
	{ return position; }
	/**
	 * Returns range from specified xy point
	 * @param xyPos Table with x and y position
	 * @return Range from given xy position
	 */
	public int getRangeFrom(int[] xyPos)
	{
		return (int)Math.sqrt((position[0]-xyPos[0]) * (position[0]-xyPos[0]) +
				 (position[1]-xyPos[1]) * (position[1]-xyPos[1]));
	}
	/**
	 * Checks if character is live
	 * @return True if character is live, false otherwise
	 */
	public boolean isLive()
	{ return live; }
	/**
	 * Checks if mouse is over character avatar
	 * @return True if mouse is over character, false otherwise
	 */
	public boolean isMouseOver()
	{ return avatar.isMouseOver(); }
	
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
	 * Returns characters avatar
	 * @return Avatar of this character
	 */
	public Avatar getAvatar()
	{ return avatar; }
	/**
	 * Returns character abilities list  
	 * @return Abilities list
	 */
	public Abilities getSkills()
	{ return abilities; }
	/**
	 * Returns all effects on character
	 */
	public Effects getEffects()
	{ return effects; }
	/**
	 * Returns all character quests
	 * @return List with quests
	 */
	public List<Quest> getQuests()
	{ return quests; }
	
	public QuestTracker getQTracker()
	{
	    return qTracker;
	}
	/**
	 * Returns character dodge chance
	 * @return Dodge chance (range 0-100)
	 */
	public float getDodgeChance()
	{ return attributes.getDodge() * 100f; }
	
	public Dialogue getDialogueFor(Character character)
	{
		for(Dialogue dialogue : dialogues)
		{
			if(character.getFlags().contains(dialogue.getReqFlag()))
				return dialogue;
		}
		
		return dialogues.get(0);
	}
	/**
	 * Subtract specified value from character health value 
	 * @param value Value to subtract
	 */
	public void takeHealth(int value)
	{
		health -= value;
		Log.loseInfo(name, value, TConnector.getText("ui", "hpName"));
		if(health <= 0)
		{
			live = false;
			Log.addInformation(name + " " + TConnector.getText("ui", "logKilled"));
		}
	}
	/**
	 * Subtract specified value from character magicka value 
	 * @param value Value to subtract
	 */
	public void takeMagicka(int value)
	{
		magicka -= value;
		Log.loseInfo(name, value, TConnector.getText("ui", "manaName"));
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
		Log.loseInfo(name, value, TConnector.getText("ui", "expName"));
		if(experience < 0)
			experience = 0;
	}
	/**
	 * Handles attacks
	 */
	public void takeAttack(int attackDamage, List<Effect> attackEffects)
	{
		if(numberGenerator.nextFloat()+attributes.getDodge() >= 1f)
		{
			Log.addInformation(name + ":" + TConnector.getText("ui", "logDodge"));
		}
		else
		{
			takeHealth(attackDamage - inventory.getArmorRating());
			for(Effect effect : attackEffects)
			{
				effect.turnOn(this);
				effects.add(effect);
			}
		}
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
		Log.gainInfo(name, value, TConnector.getText("ui", "hpName"));
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
		Log.gainInfo(name, value, TConnector.getText("ui", "manaName"));
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
		Log.gainInfo(name, value, TConnector.getText("ui", "expName"));
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
	
	public boolean addSkill(Skill skill)
	{
	    if(abilities.add(skill))
	    {
	        Log.addInformation(skill.getName() + " added to container");
	        return true;
	    }
	    else
	    {
	        Log.addInformation(skill.getName() + " add fail!");
	        return false;
	    }
	}
	/**
	 * Adds quest to character quests list
	 * @param quest Game quest
	 */
	public void startQuest(Quest quest)
	{
		if(quests.add(quest))
		{
			quest.start();
			Log.addInformation(quest.getName() + " accepted");
		}
		else
			Log.addSystem("character_startQuest_fail msg//fail to add quest to list");
	}
	/**
     * Adds gold to character inventory
     * @param value Integer value to add
     */
    public void addGold(int value)
    { 
    	inventory.addGold(value);
    	Log.gainInfo(getName(), value, "gold");
    }
    
    public void modHealth(int value)
    {
    	maxHealth += value;
    	
    	Log.addInformation(name + ": " + value + " " + TConnector.getText("ui", "hpName"));
    }
    
    public void modMgicka(int value)
    {
    	maxMagicka += value;
    }
    
    public void modAttributes(Attributes attributes)
    {
    	this.attributes.addAll(attributes);
    }
    /**
     * Activates specified skill, if character know this skill
     * @param skill Some skill known by this character
     */
    public void useSkill(Skill skill)
    {
    	if(live && abilities.contains(skill) && skill.prepare(this, target))
    	{
    	    if(Attack.class.isInstance(skill))
    	        avatar.meleeAttack((Attack)skill);
    	}
    	else
    		Log.addWarning(TConnector.getText("ui", "logUnable"));
    }
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
	/**
	 * Memorises specified game character as hostile, friendly or neutral
	 * @param character Some game character
	 * @param attitude Attitude to specified character
	 */
	public void memCharAs(Character character, Attitude attitude)
	{
	    attitudeMem.put(character, attitude);
	}
	
	public void speak(String what)
	{
	    avatar.speak(what);
	}
	@Override
	public void setTarget(Targetable target)
	{
		this.target = target;
	}
	@Override
	public Targetable getTarget() 
	{
		return target;
	}
	
	private boolean isMovable(int x, int y, TiledMap map)
	{
	    if(map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 2) != 0 || 
	       map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 3) != 0)
            return false;
        
        return true;
	}
}
