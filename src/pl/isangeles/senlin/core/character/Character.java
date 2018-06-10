/*
 * Character.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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
import pl.isangeles.senlin.core.Defense;
import pl.isangeles.senlin.core.Experience;
import pl.isangeles.senlin.core.Health;
import pl.isangeles.senlin.core.Inventory;
import pl.isangeles.senlin.core.Magicka;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.bonus.Modifier;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.bonus.DamageBonus;
import pl.isangeles.senlin.core.bonus.DualwieldBonus;
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.ProfessionType;
import pl.isangeles.senlin.core.dialogue.Answer;
import pl.isangeles.senlin.core.dialogue.Dialogue;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectSource;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.effect.Effects;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.core.item.WeaponType;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.quest.Journal;
import pl.isangeles.senlin.core.quest.ObjectiveTarget;
import pl.isangeles.senlin.core.quest.Quest;
import pl.isangeles.senlin.core.quest.QuestTracker;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
import pl.isangeles.senlin.core.signal.CharacterSignal;
import pl.isangeles.senlin.core.signal.Signals;
import pl.isangeles.senlin.core.skill.Abilities;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.core.skill.Passive;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.core.train.ProfessionTraining;
import pl.isangeles.senlin.core.train.RecipeTraining;
import pl.isangeles.senlin.core.train.SkillTraining;
import pl.isangeles.senlin.core.train.Training;
import pl.isangeles.senlin.data.ABase;
import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.data.area.Area;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.graphic.DynamicAvatar;
import pl.isangeles.senlin.graphic.CharacterAvatar;
import pl.isangeles.senlin.graphic.Effective;
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
    private int serial = 0;
	private String id;
	private String serialId;
	private String name;
	private Gender sex;
	private Race race;
	private Attitude attitude;
	private Guild guild;
	private int level;
	private Experience exp = new Experience();
	private Health hp = new Health();
	private Magicka mana = new Magicka(); 
	private int learnPoints;
	private int[] position = {-404, -404};
	private int[] destPoint = {position[0], position[1]};
	private Attributes attributes;
	private Defense defense = new Defense(this);
	private Portrait portrait;
	private boolean live;
	private boolean agony;
	private boolean trade;
	private boolean train;
	private Signals signals = new Signals();
	private CharacterAvatar avatar;
	private Inventory inventory;
	private Abilities abilities;
	private Targetable target;
	private Set<Dialogue> dialogues = new HashSet<>();
	private EnumMap<ProfessionType, Profession> crafting = new EnumMap<>(ProfessionType.class);
	private Map<String, Attitude> attitudeMem = new HashMap<>();
	private Effects effects = new Effects(this);
	private Modifiers modifiers = new Modifiers();
	private Journal quests = new Journal(this);
	private Flags flags = new Flags();
	private List<Training> trainings = new ArrayList<>();
	private QuestTracker qTracker;
	private SkillCaster sCaster;
	private Area currentArea;
	private Random rng = new Random();
	/**
	 * Creates playable character
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
	public Character(String id, Gender sex, Race race, Attitude attitude, String guildID, String name, int level, Attributes atributes, 
					 Portrait portrait, String spritesheet, boolean staticAvatar, GameContainer gc) 
	        throws SlickException, IOException, FontFormatException
	{
		this.id = id;
		this.sex = sex;
		this.race = race;
		this.name = name;
		this.attitude = attitude;
		this.guild = GuildsBase.getGuild(guildID);
		this.attributes = atributes;
		this.portrait = portrait;
	    File fileForName = new File(portrait.getResourceReference());
	    this.portrait.setName(fileForName.getName());
		live = true;
		if(staticAvatar)
			avatar = new StaticAvatar(this, gc, spritesheet);
		else
			avatar = new DynamicAvatar(this, gc, spritesheet);

		addLevel(level);
		
		inventory = new Inventory(this);
		abilities = new Abilities(this);
		abilities.add(SkillsBase.getAutoAttack(this));
		abilities.add(SkillsBase.getShot(this));
		dialogues.add(DialoguesBase.getDefaultDialogue());
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
	 * Creates playable character with specified serial number
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
    public Character(String id, int serial, Gender sex, Race race, Attitude attitude, String guildID, String name, int level, Attributes atributes, Portrait portrait, 
    				 String spritesheet, boolean staticAvatar, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        this(id, sex, race, attitude, guildID, name, level, atributes, portrait, spritesheet, staticAvatar, gc);
        
        reservedIDs.remove(serialId);
        this.serial = serial;
        serialId = id + "_" + serial;
        reservedIDs.add(serialId);
    }
	/**
	 * Character constructor with default spritesheet
	 * @param id Character ID
	 * @param attitude Character attitude
	 * @param guildID Character guild ID
	 * @param name Character name
	 * @param level Character level
	 * @param atributes Character attributes
	 * @param portrait Character portrait
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Character(String id, Gender sex, Race race, Attitude attitude, String guildID, String name, int level, Attributes atributes, 
					 Portrait portrait, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
		this(id, sex, race, attitude, guildID, name, level, atributes, portrait, race.getDefaultSpritesheet(sex), false, gc);
    }
	/**
	 * Character constructor with custom serial number and default spritesheet
	 * @param id Character ID
	 * @param serial Character serial number
	 * @param attitude Character attitude
	 * @param guildID Character guild ID
	 * @param name Character name
	 * @param level Character level
	 * @param atributes Character attributes
	 * @param portrait Character portrait
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
    public Character(String id, int serial, Gender sex, Race race, Attitude attitude, String guildID, String name, int level, Attributes atributes, 
    				 Portrait portrait, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
    	this(id, serial, sex, race, attitude, guildID, name, level, atributes, portrait, race.getDefaultSpritesheet(sex), false, gc);
    }
	/**
	 * Promotes character to next level
	 */
	public void levelUp()
	{
		level ++;
		learnPoints ++;
		int maxHealth = attributes.getHealth() + 10;
		hp = new Health(maxHealth, maxHealth);
		int maxMagicka = attributes.getMagicka() + 5;
		mana = new Magicka(maxMagicka, maxMagicka);
		exp.setMax(1000 * level);
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
	 * Sets specified value as current health value
	 * @param value Value to set
	 */
	public void setHealth(int value)
	{
	    hp.setValue(value);
	}
	/**
	 * Sets specified value as current magicka value
	 * @param value Value to set
	 */
	public void setMagicka(int value)
	{
	    mana.setValue(value);
	}
	/**
	 * Sets specified value as current experience value
	 * @param value Value to set
	 */
	public void setExperience(int value)
	{
	    exp.setValue(value);
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
	/**
	 * Sets character gender
	 * @param sex Gender to set
	 */
	public void setGender(Gender sex)
	{
		this.sex = sex;
	}
	/**
     * Instantly sets character position at specified tile position
     * @param tilePos Tile position(number of row and column)
     */
    public boolean setPosition(TilePosition tilePos)
    {
    	Position pos = tilePos.asPosition();
        if(currentArea == null || pos.isIn(new Position(0, 0), new Position(currentArea.getMapSize().width, currentArea.getMapSize().height)))
        {
        	position[0] = (int)(pos.x);
    		position[1] = (int)(pos.y);
    		destPoint[0] = (int)(pos.x);
    		destPoint[1] = (int)(pos.y);
    		return true;
        }
        else
        {
            Log.addSystem(serialId + "-fail to set position: " + pos.x + ";" + pos.y);
            return false;
        }
    }
	/**
     * Instantly sets character position
     * @param pos XY position
     */
    public boolean setPosition(Position pos)
    {   
        if(currentArea == null || pos.isIn(new Position(0, 0), new Position(currentArea.getMapSize().width, currentArea.getMapSize().height)))
        {
        	position[0] = (int)(pos.x);
    		position[1] = (int)(pos.y);
    		destPoint[0] = (int)(pos.x);
    		destPoint[1] = (int)(pos.y);
    		return true;
        }
        else
        {
            Log.addSystem(serialId + "-fail to set position: " + pos.x + ";" + pos.y);
            return false;
        }
    }
    /**
     * Sets specified area as current area of this character
     * @param area Game world area
     */
    public void setArea(Area area)
    {
    	//area.addNpc(this);
    	qTracker.check(area);
        currentArea = area;
    }
    /**
     * Equips specified item, if item is in character inventory and its equippable
     * @param item Equippable item in character inventory
     * @return True if item was successfully equipped, false otherwise
     */
    public boolean equipItem(Equippable item)
    {
    	if(item != null && item.getEquipReqs().isMetBy(this))
        	return inventory.equip(item);
    	else
    		return false;
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
    		if(!dialogue.hasReqs())
    		{
    			hasDefD = true;
    			break;
    		}
    	}
    	if(!hasDefD)
    		dialogues.add(DialoguesBase.getDefaultDialogue());
    	
    	for(Dialogue dialogue : dialogues)
    	{
    		if(!dialogue.hasReqs())
    			dialogue.addOption(new Answer("tradeReq01", "", false , true, true, new Requirements()));
    	}
    	trade = true;
    }
    /**
     * Returns character signals
     * @return Signals container 
     */
    public Signals getSignals()
    {
    	return signals;
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
    		if(!dialogue.hasReqs())
    		{
    			hasDefD = true;
    			break;
    		}
    	}
    	if(!hasDefD)
    		dialogues.add(DialoguesBase.getDefaultDialogue());
    	
    	for(Dialogue dialogue : dialogues)
    	{
    		if(!dialogue.hasReqs())
    			dialogue.addOption(new Answer("trainReq01", "", true, false, true, new Requirements()));
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
	public void unequipp(Equippable item)
    { inventory.unequipp(item); }
	/**
	 * Draws character avatar
	 * @param row Position on x axis
	 * @param column Position on y axis
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
	 * Updates character
	 * @param delta Time between updates
	 * @return Character out message
	 */
	public CharacterOut update(int delta)
	{
	    CharacterOut out = CharacterOut.SUCCESS;
	    
	    effects.update(delta);
        abilities.update(delta);
        inventory.update();
        avatar.update(delta);
        quests.update();
        sCaster.update(delta);
        
        if(agony && hp.getValue() >= 10)
        	agony = false;
        else if(hp.getValue() < 10 && hp.getValue() > 0)
        	agony = true;
        else if(hp.getValue() < 0) 
        {
	    	live = false;
    	}
	    
	    if(agony)
	    {
	    	avatar.kneel();
	    	modHealth(1); //slow HP recover in agony state
	    }
	    else
	    	avatar.resetStance();
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
        else if(!agony)
        {
            sCaster.reset(); //move interrupts cast
        	for(int i = 0; i < 2; i ++) //Movement speed is determined by numbers of loops
        	{
            	if(signals.containsKey(CharacterSignal.FOLLOWING))
            	{
            		Targetable target = (Targetable)signals.get(CharacterSignal.FOLLOWING);
            		moveTo(target, 10);
            	}
                avatar.move(true);
                if(position[0] > destPoint[0])
                {
                    if(isMovable(position[0]-1, position[1], currentArea.getMap()))
                    {
                        position[0] -= 1;
                        avatar.goLeft();
                    }
                    else
                    	destPoint[0] = position[0];
                }
                if(position[0] < destPoint[0])
                {
                    if(isMovable(position[0]+1, position[1], currentArea.getMap()))
                    {
                        position[0] += 1;
                        avatar.goRight();
                    }
                    else
                    	destPoint[0] = position[0];
                }
                if(position[1] > destPoint[1])
                {
                    if(isMovable(position[0], position[1]-1, currentArea.getMap()))
                    {
                        position[1] -= 1;
                        avatar.goUp();
                    }
                    else
                    	destPoint[1] = position[1];
                }
                if(position[1] < destPoint[1])
                {
                    if(isMovable(position[0], position[1]+1, currentArea.getMap()))
                    {
                        position[1] += 1;
                        avatar.goDown();
                    }
                    else
                    	destPoint[1] = position[1];
                }
        	}
        }
		if(signals.containsKey(CharacterSignal.FIGHTING))
		{
			Targetable target = (Targetable)signals.get(CharacterSignal.FIGHTING);
			if(target.isLive())
			    out = useSkillOn(target, abilities.get("autoA"));
			else
				signals.remove(CharacterSignal.FIGHTING);
		}
		
		return out;
	}
	/**
	 * Moves character to specified position  
	 * @param x Position on X axis
	 * @param y Position on Y axis
	 */
	public void moveTo(int x, int y)
	{
		signals.remove(CharacterSignal.FOLLOWING);
		signals.remove(CharacterSignal.FIGHTING);
		destPoint[0] = x;
		destPoint[1] = y;
	}
	/**
	 * Moves character to specified target position   
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
		{
			destPoint[0] = pos.x;
			destPoint[1] = pos.y;
			return;
		}
		
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
	public void moveBy(int moveX, int moveY)
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
	 * Returns character hit value
	 * @return Hit value
	 */
	public int getHit()
	{	
		Weapon mainWeapon = inventory.getMainWeapon();
		Weapon offWeapon = inventory.getOffHand();
		
		int mainHit = 0;
		if(rng.nextDouble() + attributes.getMainHitChance() >= 1.0f)
		{
			mainHit += rng.nextInt(10) + attributes.getBasicHit();
			
			if(mainWeapon != null) 
			{	
				mainHit += rng.nextInt(mainWeapon.getDamage()[0]) + mainWeapon.getDamage()[1];
				mainHit += modifiers.getDmgBonusFor(WeaponType.fromOrdinal(mainWeapon.getType()));
			}
			else
				mainHit += modifiers.getDmgBonusFor(WeaponType.FIST);
		}
		else
			mainHit = -1; //means miss
		
		if(mainWeapon == null && offWeapon != null)
			mainHit = 0;
		
		int offHit = 0;
		if(inventory.isDualwield() || offWeapon != null || (mainWeapon == null && offWeapon == null))
		{	
			if(rng.nextDouble() + attributes.getOffHitChance() >= 1.0f)
			{
				offHit += attributes.getBasicHit();
				if(offWeapon != null)
				{
					offHit += (rng.nextInt(offWeapon.getDamage()[0]) + offWeapon.getDamage()[1]);
					offHit += modifiers.getDmgBonusFor(WeaponType.fromOrdinal(offWeapon.getType()));
				}
				else
					offHit += modifiers.getDmgBonusFor(WeaponType.FIST);
			}
			else
				offHit = -1; //means miss
		}
		
		int fullHit = mainHit + offHit;

		if(fullHit < 0) 
		{
			Log.addInformation(name + ":" + TConnector.getText("ui", "logMiss"));
			return -1;
		}
		
		if(inventory.isDualwield())
		{
			float dwPenalty = attributes.getDualwieldPenalty() - modifiers.getDualwieldBonus();
			fullHit /= dwPenalty;
		}
		
		return fullHit;
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
		return rng.nextInt(20) + attributes.getBasicSpell(); 
	}
	
	public int getLevel()
	{ return level; }
	
	public int getExperience()
	{ return exp.getValue(); }
	
	public int getMaxExperience()
	{ return exp.getMax(); }
	/**
     * Returns current amount of health points
     */
	public int getHealth()
	{ return hp.getValue(); }
	
	public int getMaxHealth()
	{ return hp.getMax(); }
	/**
	 * Returns current amount of magicka points
	 */
	public int getMagicka()
	{ return mana.getValue(); }
	
	public int getMaxMagicka()
	{ return mana.getMax(); }
	
	public Attributes getAttributes()
	{
	    return attributes;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#getDefense()
	 */
	@Override
	public Defense getDefense() 
	{
		return defense;
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
	 * Returns character race
	 * @return Race enum
	 */
	public Race getRace()
	{ return race; }
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
		else if(!isLive())
		    return Attitude.DEAD;
		else if(attitudeMem.containsKey(character.getSerialId()))
		    return attitudeMem.get(character.getSerialId());
		else if(!guild.getId().equals("none") && guild == character.getGuild())
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
	 * Returns character invisibility level
	 * @return Invisibility level
	 */
	public int getInvisibilityLevel()
	{
		return modifiers.getStealthLevel();
	}
	/**
	 * Returns current area of this character
	 * @return Game world area
	 */
	public Area getCurrentArea()
	{
		return currentArea;
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
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#getGEffectsTarget()
	 */
	@Override
	public Effective getGEffectsTarget() 
	{
		return avatar;
	}
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
	 * @param who Aggressor
	 * @param value Value to subtract
	 */
	public void takeHealth(Targetable who, int value)
	{
		hp.modValue(-value);
		Log.loseInfo(name, value, TConnector.getText("ui", "hpName"));
		if(live && hp.getValue() <= 0)
		{
			Log.addInformation(name + " " + TConnector.getText("ui", "logKilled"));
			if(Character.class.isInstance(who))
			{
				Character ch = (Character)who;
				ch.modExperience(level * 100);
				ch.getQTracker().check(this);
			}
		}
	}
	public void takeLearnPoints(int value)
	{
		learnPoints -= value;
	}
	
	public void addLearnPoints(int value)
	{  learnPoints += value; }
	/**
	 * Adds specified value to character health points
	 * @param value Value to add
	 */
	public void modHealth(int value)
	{
		hp.modValue(value);
		Log.gainInfo(name, value, TConnector.getText("ui", "hpName"));
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#incMaxHealth(int)
	 */
	public void modMaxHealth(int value)
	{
		hp.modValue(value);
	}
	/**
	 * Adds specified value to character magicka points
	 * @param value Value to add
	 */
	@Override
	public void modMagicka(int value)
	{
		mana.modValue(value);
		Log.gainInfo(name, value, TConnector.getText("ui", "manaName"));
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#incMaxMagicka(int)
	 */
	@Override
	public void modMaxMagicka(int value)
	{
		mana.modValue(value);
	}
	/**
	 * Adds specified value to character experience points
	 * @param value Value to add
	 */
	@Override
	public void modExperience(int value)
	{
		exp.modValue(value);
		Log.gainInfo(name, value, TConnector.getText("ui", "expName"));
		if(exp.isMax())
			levelUp();
	}
	/**
	 * Adds specified item to character inventory
	 * @param item Game item
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
		if(skill != null)
		    return abilities.add(skill);
		else
			return false;
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#addModifier(pl.isangeles.senlin.core.bonus.Modifier)
     */
	public boolean addModifier(Modifier modifier)
	{
		if(modifiers.add(modifier))
		{
			modifier.applyOn(this);
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
	 * Adds specified dialogue to character dialogues
	 * @param dialogue Dialogue
	 * @return True if specified dialogue was successfully added, false otherwise
	 */
	public boolean addDialogue(Dialogue dialogue)
	{
		return dialogues.add(dialogue);
	}
	/**
	 * Adds quest to character quests list
	 * @param quest Game quest
	 */
	public void startQuest(Quest quest)
	{
		if(quest.start(this) && quests.add(quest))
		{
			Log.addInformation(quest.getName() + " accepted");
		}
		else
			Log.addSystem("char_startQuest_fail-msg//fail to add quest to list");
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#removeModifier(pl.isangeles.senlin.core.bonus.Modifier)
     */
    public boolean removeModifier(Modifier modifier)
    {
    	if(modifiers.remove(modifier))
    	{
    		modifier.removeFrom(this);
    		return true;
    	}
    	else
    		return false;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#hasModifier(pl.isangeles.senlin.core.bonus.Modifier)
     */
    public boolean hasModifier(Modifier modifier)
    {
    	return modifiers.contains(modifier);
    }
    
    public EffectSource getEffectSource(String sourceId)
    {
    	return abilities.get(sourceId);
    }
    
    public void modAttributes(Attributes attributes)
    {
    	this.attributes.mod(attributes);
    }
    /**
     * Activates specified skill(only if character know this skill)
     * @param skill Some skill known by this character
     * @return Out with result
     */
    public CharacterOut useSkill(Skill skill)
    {
        if(live && !agony && skill != null && abilities.contains(skill))
        {
            CharacterOut out = skill.prepare(this, target);
            if(out.isSuccess())
            {
                sCaster.cast(skill);
                return CharacterOut.SUCCESS;
            }
            else
                return CharacterOut.UNABLE;
        }
        else
            return CharacterOut.UNABLE;
    }
    /**
     * Activates specified skill(only if character know this skill)
     * @param skill Some skill known by this character
     * @param target Skill target
     * @return Out with result
     */
    public CharacterOut useSkillOn(Targetable target, Skill skill)
    {
        if(live && skill != null && abilities.contains(skill))
        {
            CharacterOut out = skill.prepare(this, target);
            if(out.isSuccess())
            {
                sCaster.cast(skill);
                return CharacterOut.SUCCESS;
            }
            else
                return CharacterOut.UNABLE;
        }
        else
            return CharacterOut.UNABLE;
    }
	/**
	 * 'Memorises' specified game character as hostile, friendly or neutral
	 * @param character Some game character
	 * @param attitude Attitude to specified character
	 */
	public void memCharAs(Character character, Attitude attitude)
	{
	    attitudeMem.put(character.getSerialId(), attitude);
	}
    /**
     * 'Memorises' specified game character as hostile, friendly or neutral
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
	    if(this.isNearby(Global.getPlayer()))
	        Log.addSpeech(name, what);
	}
	/**
	 * Starts dialogue with specified game character
	 * @param dialogueTarget
	 * @return Started dialogue
	 */
	public Dialogue startDialogueWith(Character dialogueTarget)
	{
		signals.startTalking(dialogueTarget);
		dialogueTarget.getSignals().startTalking(this);
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
     * Parses character to XML document element for game save file
     * @param doc Document for game save
     * @return XML document element 
     */
    public Element getSave(Document doc)
    {   
        Element charE = doc.createElement("character");
        charE.setAttribute("id", id);
        charE.setAttribute("serial", serial+"");
        charE.setAttribute("attitude", attitude.toString());
        charE.setAttribute("race", race.toString());
        charE.setAttribute("gender", sex.toString());
        charE.setAttribute("guild", guild.getId());
        charE.setAttribute("level", level+"");
        charE.setAttribute("trade", trade+"");
        charE.setAttribute("train", train+"");
        
        Element statsE = doc.createElement("stats");
        statsE.setTextContent(attributes.toString());
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
        
        Element dialoguesE = doc.createElement("dialogues");
        for(Dialogue dialogue : dialogues)
        {
        	dialoguesE.appendChild(dialogue.getSave(doc));
        }
        charE.appendChild(dialoguesE);
        
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
        
        charE.appendChild(flags.getSave(doc));
        charE.appendChild(effects.getSave(doc));
        
        Element pointsE = doc.createElement("points");
        Element hpE = doc.createElement("hp");
        Element manaE = doc.createElement("mana");
        Element expE = doc.createElement("exp");
        Element lpE = doc.createElement("lp");
        hpE.setTextContent(hp.getValue()+"");
        manaE.setTextContent(mana.getValue()+"");
        expE.setTextContent(exp.getValue()+"");
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
        if(currentArea != null)
        	positionE.setAttribute("area", currentArea.getId());
        else
        	positionE.setAttribute("area", "none");
        positionE.setTextContent(new TilePosition(position[0]/32, position[1]/32).toString());
        charE.appendChild(positionE);
        
        return charE;
    }
    /**
     * Compares this character to another game character
     * @param character Game character to compare
     * @return True if specified character have same serial ID as this character, false otherwise
     */
    public boolean equals(Character character)
    {
    	return serialId.equals(character.getSerialId());
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
			if(dialogue.hasReqs())
			{
				if(dialogue.getReqs().isMetBy(character)) 
				{
					return dialogue;
				}
			}
		}
		
		for(Dialogue dialogue : dialogues)
		{
			if(!dialogue.hasReqs() && !dialogue.getId().equals("default"))
				return dialogue;
		}
		
		for(Dialogue dialogue : dialogues)
		{
			if(!dialogue.hasReqs())
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
	    if(map != null)
	    {
	    	try
	    	{
		        if(map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 2) != 0 || //blockground layer 
		           map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 3) != 0 || //water layer
		           map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 4) != 0 || //trees layer
		           map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 5) != 0 || //buildings layer
		           map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 6) != 0)   //objects layer
		                 return false;
		        else
		            return true;
	    	}
	    	catch(ArrayIndexOutOfBoundsException e)
	    	{
	    		System.err.println(serialId + ":moveable_check_fail");
	    		return false;//throw new ArrayIndexOutOfBoundsException();
	    	}
	    }
	    else
	        return true;
	}
}
