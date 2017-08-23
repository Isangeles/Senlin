/*
 * Character.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package pl.isangeles.senlin.core.character;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.util.*;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Flags;
import pl.isangeles.senlin.core.Inventory;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.Training;
import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.core.bonus.Bonuses;
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.ProfessionTraining;
import pl.isangeles.senlin.core.craft.ProfessionType;
import pl.isangeles.senlin.core.craft.RecipeTraining;
import pl.isangeles.senlin.core.dialogue.Answer;
import pl.isangeles.senlin.core.dialogue.Dialogue;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.Effects;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.quest.Journal;
import pl.isangeles.senlin.core.quest.ObjectiveTarget;
import pl.isangeles.senlin.core.quest.Quest;
import pl.isangeles.senlin.core.quest.QuestTracker;
import pl.isangeles.senlin.core.signal.CharacterSignal;
import pl.isangeles.senlin.core.skill.Abilities;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.core.skill.SkillTraining;
import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.graphic.Avatar;
import pl.isangeles.senlin.graphic.CharacterAvatar;
import pl.isangeles.senlin.graphic.StaticAvatar;
import pl.isangeles.senlin.gui.Portrait;
import pl.isangeles.senlin.states.Global;
/**
 * Class for game characters like players, NPCs, etc.
 * @author Isangeles
 *
 */
public class Character implements Targetable, ObjectiveTarget, SaveElement
{
    private static int charCounter;
    private static List<String> reservedIDs = new ArrayList<>();
    private int serial = charCounter;
	private String id;
	private String serialId;
	private String name;
	private Gender sex;
	private Attitude attitude;
	private Guild guild;
	private int level;
	private int experience;
	private int maxExperience;
	private int health;
	private int maxHealth;
	private int magicka;
	private int maxMagicka;
	private int learnPoints;
	private int[] position = {0, 0};
	private int[] destPoint = {position[0], position[1]};
	private Attributes attributes;
	private Portrait portrait;
	private boolean live;
	private boolean trade;
	private boolean train;
	private EnumMap<CharacterSignal, Object>signals = new EnumMap<>(CharacterSignal.class);
	private CharacterAvatar avatar;
	private Inventory inventory;
	private Abilities abilities;
	private Targetable target;
	private List<Dialogue> dialogues;
	private EnumMap<ProfessionType, Profession> crafting = new EnumMap<>(ProfessionType.class);
	private Map<String, Attitude> attitudeMem = new HashMap<>();
	private List<Buff> buffs = new ArrayList<>();
	private Effects effects = new Effects();
	private Bonuses bonuses = new Bonuses();
	private Journal quests = new Journal();
	private Flags flags = new Flags();
	private List<Training> trainings = new ArrayList<>();
	private QuestTracker qTracker;
	private SkillCaster sCaster;
	private Random numberGenerator = new Random();
	/**
	 * Basic constructor for character creation menu, after use this constructor method levelUp() should be called to make new character playable
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException 
	 */
	public Character(GameContainer gc) 
	        throws SlickException, IOException, FontFormatException
	{
		id = "player";
		sex = Gender.MALE;
		name = "Name";
		level = 0;
		attitude = Attitude.FRIENDLY;
		guild = GuildsBase.getGuild("none");
		attributes = new Attributes(1, 1, 1, 1, 1);
		portrait = new Portrait("data" + File.separator + "portrait" + File.separator + "default.jpg", gc);
		live = true;
		avatar = new Avatar(this, gc, "m-cloth-1222211-80x90.png");
		inventory = new Inventory();
		abilities = new Abilities();
		abilities.add(SkillsBase.getAutoAttack(this));
		abilities.add(SkillsBase.getShot(this));
		qTracker = new QuestTracker(this);
		sCaster = new SkillCaster(this);

        serialId = id + "_" + serial;
		while(reservedIDs.contains(serialId))
        {
		    serial ++;
            charCounter ++;
            serialId = id + "_" + serial;
        }
		reservedIDs.add(serialId);
	}
	/**
	 * This constructor provides playable character
	 * @param id Character ID
	 * @param attitude Character attitude
	 * @param guildID Character guild ID
	 * @param name Character name
	 * @param level Character level
	 * @param atributes Character attributes
	 * @param portrait Character portrait
	 * @param spritesheet Character sprite sheet
	 * @param staticAvatar If avatar sprite sheet should be static 
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Character(String id, Gender sex, Attitude attitude, String guildID, String name, int level, Attributes atributes, 
					 Portrait portrait, String spritesheet, boolean staticAvatar, GameContainer gc) 
	        throws SlickException, IOException, FontFormatException
	{
		this.id = id;
		this.sex = sex;
		this.name = name;
		this.attitude = attitude;
		this.guild = GuildsBase.getGuild(guildID);
		this.attributes = atributes;
		this.portrait = portrait;
		live = true;
		if(staticAvatar)
			avatar = new StaticAvatar(this, gc, spritesheet);
		else
			avatar = new Avatar(this, gc, spritesheet);
		inventory = new Inventory();
		abilities = new Abilities();
		addLevel(level);
		abilities.add(SkillsBase.getAutoAttack(this));
		//abilities.add(SkillsBase.getShot(this));
		dialogues = DialoguesBase.getDialogues(this.id);
		qTracker = new QuestTracker(this);
		sCaster = new SkillCaster(this);

		serialId = id + "_" + serial;
        while(reservedIDs.contains(serialId))
        {
            serial ++;
            charCounter ++;
            serialId = id + "_" + serial;
        }
        reservedIDs.add(serialId);
	}
	/**
	 * This constructor provides playable character with specified serial number
	 * @param id Character ID
	 * @param serial Character serial number
	 * @param attitude Character attitude
	 * @param guildID Character guild ID
	 * @param name Character name
	 * @param level Character level
	 * @param atributes Character attributes
	 * @param portrait Character portrait
	 * @param spritesheet Character sprite sheet
	 * @param staticAvatar True if avatar sprite sheet should be static 
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
    public Character(String id, int serial, Gender sex, Attitude attitude, String guildID, String name, int level, Attributes atributes, Portrait portrait, 
    				 String spritesheet, boolean staticAvatar, GameContainer gc) 
            throws SlickException, IOException, FontFormatException
    {
        this(id, sex, attitude, guildID, name, level, atributes, portrait, spritesheet, staticAvatar, gc);
        
        reservedIDs.remove(serialId);
        this.serial = serial;
        serialId = id + "_" + serial;
        reservedIDs.add(serialId);
    }
	/**
	 * Promotes character to next level
	 */
	public void levelUp()
	{
		level ++;
		learnPoints ++;
		maxHealth = attributes.addHealth();
		health = maxHealth;
		maxMagicka = attributes.addMagicka();
		magicka = maxMagicka;
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
	
	public void setHealth(int value)
	{
	    health = value;
	}
	
	public void setMagicka(int value)
	{
	    magicka = value;
	}
	
	public void setExperience(int value)
	{
	    experience = value;
	}
	/**
	 * Sets specific portrait from portrait catalog to character 
	 * @param img
	 */
	public void setPortrait(Portrait img)
	{
	    portrait = img;
	    File fileForName = new File(portrait.getResourceReference());
	    portrait.setName(fileForName.getName());
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
	
	public void setGender(Gender sex)
	{
		this.sex = sex;
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
     * Instantly sets character position
     * @param pos XY position
     */
    public void setPosition(Position pos)
    {
        position[0] = pos.x;
        position[1] = pos.y;
        destPoint[0] = pos.x;
        destPoint[1] = pos.y;
    }
    /**
     * Equips specified item, if item is in character inventory and its equippable
     * @param item Equippable item in chracter inventory
     * @return True if item was successfully equipped, false otherwise
     */
    public boolean equipItem(Item item)
    {
    	return inventory.equip(item);
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
     * Allows or disallows trade with this character
     * @param trade True to allow trade, false to disallow
     */
    public void setTrade()
    {
    	boolean hasDefD = false;
    	for(Dialogue dialogue : dialogues)
    	{
    		if(!dialogue.isReqFlag())
    		{
    			hasDefD = true;
    			break;
    		}
    	}
    	if(!hasDefD)
    		dialogues.add(DialoguesBase.getDefaultDialogue());
    	
    	for(Dialogue dialogue : dialogues)
    	{
    		if(!dialogue.isReqFlag())
    			dialogue.addOption(new Answer("tradeReq", "", true));
    	}
    	trade = true;
    }
    /**
     * 
     * @param talking
     */
    public void startTalking(Targetable target)
    {
    	signals.put(CharacterSignal.TALKING, target);
    }    
    
    public void stopTalking()
    {
    	signals.remove(CharacterSignal.TALKING);
    }
    
    public void startLooting(Targetable target)
    {
        signals.put(CharacterSignal.LOOTING, target);
    }	
    
    public void stopLooting()
    {
    	signals.remove(CharacterSignal.LOOTING);
    }
    
	public void startFollowing(Targetable target)
	{
		signals.put(CharacterSignal.FOLLOWING, target);
	}
	
	public void stopFollowing()
	{
		signals.remove(CharacterSignal.FOLLOWING);
	}
    
    public void enterCombat(Targetable target)
    {
    	signals.put(CharacterSignal.FIGHTING, target);
    }
    
    public void stopCombat()
    {
    	signals.remove(CharacterSignal.FIGHTING);
    }
    
    public void startReading(String textId)
    {
    	signals.put(CharacterSignal.READING, textId);
    }
    
    public void stopReading()
    {
    	signals.remove(CharacterSignal.READING);
    }
    /**
     * Marks this character as trainer
     * @param train 
     */
    public void setTrain(List<Training> trainings)
    {
    	boolean hasDefD = false;
    	for(Dialogue dialogue : dialogues)
    	{
    		if(!dialogue.isReqFlag())
    		{
    			hasDefD = true;
    			break;
    		}
    	}
    	if(!hasDefD)
    		dialogues.add(DialoguesBase.getDefaultDialogue());
    	
    	for(Dialogue dialogue : dialogues)
    	{
    		if(!dialogue.isReqFlag())
    			dialogue.addOption(new Answer("trainReq", "", true));
    	}
    	train = true;
    	this.trainings = trainings;
    }
    
    public Collection<Training> getTrainings()
    {
    	return trainings;
    }
	/**
	 * Removes specific item from equipment
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
	 * @throws GameLogErr 
	 */
	public CharacterOut update(int delta, TiledMap map)
	{
	    CharacterOut out = CharacterOut.SUCCESS;
		if(!live)
		{
			avatar.lie();
			attitude = Attitude.DEAD;
			return CharacterOut.SUCCESS;
		}
	    if(position[0] == destPoint[0] && position[1] == destPoint[1])
        {
            avatar.move(false);
        }
        else
        {
        	if(signals.containsKey(CharacterSignal.FOLLOWING))
        	{
        		Targetable target = (Targetable)signals.get(CharacterSignal.FOLLOWING);
        		moveTo(target, 10);
        	}
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
		if(signals.containsKey(CharacterSignal.FIGHTING))
		{
			Targetable target = (Targetable)signals.get(CharacterSignal.FIGHTING);
		    out = useSkillOn(target, abilities.get("autoA"));
		}
		
	    //if(target == null && looting)
	    //    looting = false;
	    
	    abilities.update(delta);
		avatar.update(delta);
		for(Buff buff : buffs)
		{
			buff.update(delta);
		}
		effects.update(delta, this);
		flags.update(quests);
		quests.update();
		if(isCasting())
			sCaster.update(delta);
		
		return out;
	}
	/**
	 * Moves character to given position  
	 * @param x Position on X axis
	 * @param y Position on Y axis
	 */
	public void moveTo(int x, int y)
	{
		signals.remove(CharacterSignal.FOLLOWING);
		//signals.remove(CharacterSignal.FIGHTING);
		destPoint[0] = x;
		destPoint[1] = y;
	}
	/**
	 * Moves character to given target position   
	 * @param target Some targetable character
	 */
	public void moveTo(Targetable target)
	{
		signals.put(CharacterSignal.FOLLOWING, target);
		destPoint[0] = target.getPosition()[0];
		destPoint[1] = target.getPosition()[1];
	}
	/**
	 * Moves character on maximal range from specified target
	 * @param target Some targetable object
	 * @param maxRange Maximal range from target
	 */
	public void moveTo(Targetable target, int maxRange)
	{
		signals.put(CharacterSignal.FOLLOWING, target);
		Position pos = new Position(position);
		Position hitBoxStart = new Position(target.getPosition()[0] - maxRange, target.getPosition()[1] - maxRange);
		Position hitBoxEnd = new Position(target.getPosition()[0] + maxRange, target.getPosition()[1] + maxRange);
		
		if(pos.isIn(hitBoxStart, hitBoxEnd))
			return;
		
		if(target.getPosition()[0] > position[0])
			destPoint[0] = target.getPosition()[0];// - Coords.getDis(maxRange);
		if(target.getPosition()[0] < position[0])
			destPoint[0] = target.getPosition()[0];// + Coords.getDis(maxRange);
		
		if(target.getPosition()[1] > position[1])
			destPoint[1] = target.getPosition()[1];// - Coords.getDis(maxRange);
		if(target.getPosition()[1] < position[1])
			destPoint[1] = target.getPosition()[1];// + Coords.getDis(maxRange);
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
		if(position[0] != destPoint[0] || position[1] != destPoint[1])
			return true;
		else
			return false;
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
	/**
     * Returns current amount of health points
     */
	public int getHealth()
	{ return health; }
	
	public int getMaxHealth()
	{ return maxHealth; }
	/**
	 * Returns current amount of magicka points
	 */
	public int getMagicka()
	{ return magicka; }
	
	public int getMaxMagicka()
	{ return maxMagicka; }
	
	public Attributes getAttributes()
	{
	    return attributes;
	}
	
	public int getLearnPoints()
	{ return learnPoints; }
	/**
	 * Returns character ID
	 * @return String with character ID
	 */
	public String getId()
	{
		return id;
	}
    /**
     * Returns serial ID
     * @return String with serial ID
     */
    public String getSerialId()
    {
        return serialId;
    }
	/**
	 * Returns character name
	 * @return String with name
	 */
	public String getName()
	{ return name; }
	/**
	 * Returns character gender
	 * @return Gender enum
	 */
	public Gender getGender()
	{ return sex; }
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
		if(attitudeMem.containsKey(character.getSerialId()))
		    return attitudeMem.get(character.getSerialId());
		if(!guild.getId().equals("none") && guild == character.getGuild())
			return Attitude.FRIENDLY;
		else
			return attitude;
	}
	/**
	 * Returns guild 
	 * @return Guild
	 */
	public Guild getGuild()
	{ 
	  if(guild == null) 
	    return GuildsBase.getGuild("none");
	  else
	    return guild;
	}
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
	
	public Position getDestPoint()
	{
		return new Position(destPoint);
	}
	/**
	 * Returns range from specified xy point
	 * @param xyPos Table with x and y position
	 * @return Range from given xy position
	 */
	public int getRangeFrom(int[] xyPos)
	{
		return (int)Math.hypot(xyPos[0]-position[0], xyPos[1]-position[1]);
	}

	/**
	 * Returns range from specified object
	 * @param object Some targetable game object
	 * @return Range from specified object
	 */
	public int getRangeFrom(Targetable object)
	{
		return getRangeFrom(object.getPosition());
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
	
	public boolean isNearby(Targetable object)
	{
	    if(this.getRangeFrom(object.getPosition()) <= 400)
	        return true;
	    else
	        return false;
	}
	
	public boolean isNearby(int[] pos)
	{
	    if(getRangeFrom(pos) <= 400)
	        return true;
	    else
	        return false;
	}
	/* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#looting()
     */
    @Override
	public Targetable looting()
	{
	    if(signals.get(CharacterSignal.LOOTING) != null)
	    	return (Targetable)signals.get(CharacterSignal.LOOTING);
	    else
	    	return null;
	}
	/**
	 * Checks if character talking with someone
	 * @return
	 */
	public boolean isTalking()
	{
		if(signals.get(CharacterSignal.TALKING) != null)
			return true;
		else
			return false;
	}
	
	public boolean isFighting()
	{
		if(signals.get(CharacterSignal.FIGHTING) != null)
			return true;
		else
			return false;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#reading()
	 */
	@Override
	public String reading()
	{
		if(signals.get(CharacterSignal.READING) != null)
			return (String)signals.get(CharacterSignal.READING);
		else
			return null;
	}
	
	public boolean isCasting()
	{
		return sCaster.isCasting();
	}
	
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
	 * Returns character inventory
	 * @return Inventory
	 */
	public Inventory getInventory()
	{ return inventory; }
	/**
	 * Returns characters avatar
	 * @return Avatar of this character
	 */
	public CharacterAvatar getAvatar()
	{ return avatar; }
	/**
	 * Returns character abilities list  
	 * @return Abilities list
	 */
	public Abilities getSkills()
	{ return abilities; }
	/**
	 * Returns specified character profession
	 * @param proType Type of desired profession
	 * @return Desired character profession or null if character don't know such profession
	 */
	public Profession getProfession(ProfessionType proType)
	{
		return crafting.get(proType);
	}
	/**
	 * Returns all character professions
	 * @return Collection with all character professions
	 */
	public Collection<Profession> getProfessions()
	{
		return crafting.values();
	}
	/**
	 * Returns all effects on character
	 */
	public Effects getEffects()
	{ return effects; }
	/**
	 * Returns all character quests
	 * @return List with quests
	 */
	public Journal getQuests()
	{ return quests; }
	
	public QuestTracker getQTracker()
	{
	    return qTracker;
	}
	
	public SkillCaster getSkillCaster()
	{
		return sCaster;
	}
	/**
	 * Returns character dodge chance
	 * @return Dodge chance (range 0-100)
	 */
	public float getDodgeChance()
	{ return attributes.getDodge() * 100f; }
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
	 * Subtract specified value from character health value 
	 * @param who Aggressor
	 * @param value Value to subtract
	 */
	public void takeHealth(Targetable who, int value)
	{
		health -= value;
		Log.loseInfo(name, value, TConnector.getText("ui", "hpName"));
		if(health <= 0)
		{
			live = false;
			Log.addInformation(name + " " + TConnector.getText("ui", "logKilled"));
			if(Character.class.isInstance(who))
			{
				Character ch = (Character)who;
				ch.addExperience(level * 100);
				ch.getQTracker().check(this);
			}
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
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#decMaxHealth(int)
	 */
	public void decMaxHealth(int value)
	{
		maxHealth -= value;
		if(health > maxHealth)
			health = maxHealth;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#decMaxMagicka(int)
	 */
	public void decMaxMagicka(int value)
	{
		maxMagicka -= value;
		if(magicka > maxMagicka)
			magicka = maxMagicka;
	}
	
	public void takeLearnPoints(int value)
	{
		learnPoints -= value;
	}
	/**
	 * Handles attacks
	 */
	public void takeAttack(Targetable aggressor, Attack attack)
	{
		if(Character.class.isInstance(aggressor))
		{
			Character aggressorChar = (Character)aggressor;
			memCharAs(aggressorChar.getSerialId(), Attitude.HOSTILE);
		}
		
		if(numberGenerator.nextFloat()+attributes.getDodge() >= 1f)
		{
			Log.addInformation(name + ":" + TConnector.getText("ui", "logDodge"));
		}
		else
		{
			takeHealth(aggressor, attack.getDamage() - inventory.getArmorRating());
			for(Effect effect : attack.getEffects())
			{
				effect.turnOn(this);
			}
		}
	}
	
	public void takeBuff(Targetable buffer, Buff buff)
	{
    	buffs.add(buff);
    	effects.addAll(buff.getEffects());
    	bonuses.addAll(buff.getBonuses());
    	bonuses.applyAllOn(this);
	}
	
	public void addLearnPoints(int value)
	{  learnPoints += value; }
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
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#incMaxHealth(int)
	 */
	public void incMaxHealth(int value)
	{
		maxHealth += value;
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
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#incMaxMagicka(int)
	 */
	public void incMaxMagicka(int value)
	{
		maxMagicka += value;
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
	/**
	 * Adds skill to player abilities set
	 * @param skill Game skill
	 * @return True if skill was successfully added, false otherwise
	 */
	public boolean addSkill(Skill skill)
	{
	    if(abilities.add(skill))
	        return true;
	    else
	        return false;
	}
	/**
	 * Adds(and applies) specified bonus to character bonuses
	 * @param bonus Bonus to add 
	 * @return True if bonus was successfully added, false otherwise
	 */
	public boolean addBonus(Bonus bonus)
	{
		if(bonuses.add(bonus))
		{
			bonus.applyOn(this);
			return true;
		}
		else
			return false;
	}
	/**
	 * Adds profession to character professions set
	 * @param profession Game profession
	 * @return True if profession was successfully added
	 */
	public boolean addProfession(Profession profession)
	{
		if(crafting.get(profession.getType()) == null)
		{
			crafting.put(profession.getType(), profession);
			return true;
		}
		else
			return false;
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
    /**
     * Removes specified bonus from character
     * @param bonus Bonus to remove
     * @return True if bonus was successfully removed, false otherwise
     */
    public boolean removeBonus(Bonus bonus)
    {
    	if(bonuses.remove(bonus))
    	{
    		bonus.removeFrom(this);
    		return true;
    	}
    	else
    		return false;
    }
    /**
     * Checks if character has specified bonus 
     * @param bonus Bonus to check
     * @return True if character bonuses container contains specified bonus object, false otherwise
     */
    public boolean hasBonus(Bonus bonus)
    {
    	return bonuses.contains(bonus);
    }
    
    public void modHealth(int value)
    {
    	maxHealth += value;
    	
    	Log.addInformation(name + ": " + value + " " + TConnector.getText("ui", "hpName"));
    }
    
    public void modMagicka(int value)
    {
    	maxMagicka += value;
    }
    
    public void modAttributes(Attributes attributes)
    {
    	this.attributes.increaseBy(attributes);
    }
    /**
     * Activates specified skill, if character know this skill
     * @param skill Some skill known by this character
     */
    public CharacterOut useSkill(Skill skill)
    {
		if(live && abilities.contains(skill) && skill.prepare(this, target).isSuccess())
    	{
    	    if(Attack.class.isInstance(skill))
    	    {
    	    	if(inventory.getMainWeapon() != null && inventory.getMainWeapon().getType() == Weapon.BOW)
    	    		avatar.rangeAnim();
    	    	else
        	        avatar.meleeAnim();
    	    	
    	    	skill.activate();
    	    	return CharacterOut.SUCCESS;
    	    }
    	    else if(Buff.class.isInstance(skill))
    	    {
    	    	Buff buff = (Buff)skill;
    	    	avatar.castAnim();
    	    	sCaster.cast(buff);
    	    	return CharacterOut.SUCCESS;
    	    }
    	    else
    	    {
                return CharacterOut.UNKNOWN;
    	    }
    	}
    	else
    		return CharacterOut.UNABLE;
    }
    /**
     * Activates specified skill, if character know this skill
     * @param skill Some skill known by this character
     * @param target Skill target
     */
    public CharacterOut useSkillOn(Targetable target, Skill skill)
    {
    	if(live && abilities.contains(skill) && skill.prepare(this, target).isSuccess())
    	{
    	    if(Attack.class.isInstance(skill))
    	    {
    	    	if(inventory.getMainWeapon() != null && inventory.getMainWeapon().getType() == Weapon.BOW)
    	    		avatar.rangeAnim();
    	    	else
        	        avatar.meleeAnim();

    	    	sCaster.cast(skill);
                return CharacterOut.SUCCESS;
    	    }
    	    else if(Buff.class.isInstance(skill))
    	    {
    	    	Buff buff = (Buff)skill;
    	    	avatar.castAnim();
    	    	sCaster.cast(buff);
    	    	return CharacterOut.SUCCESS;
    	    }
    	    else
    	    {
                return CharacterOut.UNKNOWN;
    	    }
    	}
    	else
            return CharacterOut.UNABLE;
    }
	/**
	 * Memorises specified game character as hostile, friendly or neutral
	 * @param character Some game character
	 * @param attitude Attitude to specified character
	 */
	public void memCharAs(Character character, Attitude attitude)
	{
	    attitudeMem.put(character.getSerialId(), attitude);
	}
    /**
     * Memorises specified game character as hostile, friendly or neutral
     * @param characterSerialID Character serial ID
     * @param attitude Attitude to specified character
     */
    public void memCharAs(String characterSerialID, Attitude attitude)
    {
        attitudeMem.put(characterSerialID, attitude);
    }
	/**
	 * Makes the character speaks specified text
	 * @param what String with text to say
	 */
	public void speak(String what)
	{
	    avatar.speak(what);
        Log.addSpeech(name, what);
	}
	/**
	 * Starts dialogue with specified game character
	 * @param dialogueTarget
	 * @return Started dialogue
	 */
	public Dialogue startDialogueWith(Character dialogueTarget)
	{
		startTalking(dialogueTarget);
		dialogueTarget.startTalking(this);
		Dialogue dialogue = getDialogueFor(dialogueTarget);
		dialogue.startFor(dialogueTarget);
		return dialogue;
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
    /**
     * Informs character that is targeted or not
     * @param isTargeted True if is targeted, false otherwise
     */
    public void targeted(boolean isTargeted)
    {
        avatar.targeted(isTargeted);
    }
    /**
     * Parses character to XML document element for game save file
     * @param doc Document for game save
     * @return XML document element 
     */
    public Element getSave(Document doc)
    {   
        Element charE = doc.createElement("character");
        charE.setAttribute("id", this.id);
        charE.setAttribute("serial", serial+"");
        charE.setAttribute("attitude", this.attitude.toString());
        charE.setAttribute("guild", this.guild.getId());
        charE.setAttribute("level", this.level+"");
        charE.setAttribute("trade", this.trade+"");
        charE.setAttribute("train", this.train+"");
        
        Element statsE = doc.createElement("stats");
        statsE.setTextContent(attributes.toLine());
        charE.appendChild(statsE);
        
        Element portraitE = doc.createElement("portrait");
        portraitE.setTextContent(portrait.getName());
        charE.appendChild(portraitE);
        
        Element spritesheetE = doc.createElement("spritesheet");
        spritesheetE.setAttribute("static", avatar.isStatic()+"");
        spritesheetE.setTextContent(avatar.getDefTorso().getName());
        charE.appendChild(spritesheetE);
        
        charE.appendChild(inventory.getSave(doc));
        charE.appendChild(abilities.getSave(doc));
        charE.appendChild(effects.getSave(doc));
        
        Element craftingE = doc.createElement("crafting");
        for(Profession profession : crafting.values())
        {
        	craftingE.appendChild(profession.getSave(doc));
        }
        charE.appendChild(craftingE);
        
        Element trainingE = doc.createElement("training");
        Element professionsE = doc.createElement("professions");
        Element recipesE = doc.createElement("recipes");
        Element skillsE = doc.createElement("skills");
        for(Training training : trainings)
        {
        	if(ProfessionTraining.class.isInstance(training))
        	{
        		professionsE.appendChild(training.getSave(doc));
        	}
        	if(RecipeTraining.class.isInstance(training))
        	{
        		recipesE.appendChild(training.getSave(doc));
        	}
        	if(SkillTraining.class.isInstance(training))
        	{
        		skillsE.appendChild(training.getSave(doc));
        	}
        }
        trainingE.appendChild(professionsE);
        trainingE.appendChild(recipesE);
        trainingE.appendChild(skillsE);
        charE.appendChild(trainingE);
        
        charE.appendChild(quests.getSave(doc));
        
        Element attMem = doc.createElement("attMemory");
        for(String object : attitudeMem.keySet())
        {
            Element objectE = doc.createElement("object");
            objectE.setTextContent(object);
            objectE.setAttribute("attitude", attitudeMem.get(object).toString());
            attMem.appendChild(objectE);
        }
        charE.appendChild(attMem);
        
        Element flagsE = doc.createElement("flags");
        for(String flag : flags)
        {
            Element flagE = doc.createElement("flag");
            flagE.setTextContent(flag);
            flagsE.appendChild(flagE);
        }
        charE.appendChild(flagsE);
        
        Element pointsE = doc.createElement("points");
        Element hpE = doc.createElement("hp");
        Element manaE = doc.createElement("mana");
        Element expE = doc.createElement("exp");
        Element lpE = doc.createElement("lp");
        hpE.setTextContent(health+"");
        manaE.setTextContent(magicka+"");
        expE.setTextContent(experience+"");
        lpE.setTextContent(learnPoints+"");
        pointsE.appendChild(hpE);
        pointsE.appendChild(manaE);
        pointsE.appendChild(expE);
        pointsE.appendChild(lpE);
        charE.appendChild(pointsE);
        
        Element nameE = doc.createElement("name");
        nameE.setTextContent(name);
        charE.appendChild(nameE);
        
        Element positionE = doc.createElement("position");
        positionE.setTextContent(new Position(position).toString());
        charE.appendChild(positionE);
        
        return charE;
    }
	/**
	 * Returns dialogue for specified character
	 * @param character Game character
	 * @return Dialogue object for specified character or default dialogue from base if no proper dialogue was found
	 */
	private Dialogue getDialogueFor(Character character)
	{
		for(Dialogue dialogue : dialogues)
		{
			if(character.getFlags().contains(dialogue.getReqFlag()))
				return dialogue;
		}
		
		for(Dialogue dialogue : dialogues)
		{
			if(!dialogue.isReqFlag())
				return dialogue;
		}
		
		return DialoguesBase.getDefaultDialogue();
	}
	/**
	 * Checks if specified position is 'moveable' on map 
	 * @param x Position on x axis
	 * @param y Position on y axis
	 * @param map Tiled map on which player is located
	 * @return
	 */
	private boolean isMovable(int x, int y, TiledMap map)
	{
	    if(map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 2) != 0 || 
	       map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 3) != 0)
            return false;
        
        return true;
	}
}
