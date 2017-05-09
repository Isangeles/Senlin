package pl.isangeles.senlin.core.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import pl.isangeles.senlin.core.Attitude;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for artificial intelligence controlling game characters
 * @author Isangeles
 *
 */
public class CharacterAi 
{
	private List<Character> aiNpcs = new ArrayList<>();
	private Random rng = new Random();
	private int moveTimer;
	
	private GameWorld gw;
	
	public CharacterAi(GameWorld gw) 
	{	
		this.gw = gw;
	}
	/**
	 * Updates all NPCs controlled by AI
	 * @param delta
	 */
	public void update(int delta)
	{
		List<Character> deadNpcs = new ArrayList<>();
		
		moveTimer += delta;
		
		for(Character npc : aiNpcs)
		{
			if(moveTimer > 2000)
			{
				moveAround(npc);
                saySomething(npc, "idle", true);
				moveTimer = 0;
			}
			
			for(Character nearbyChar : gw.getNearbyCharacters(npc))
			{
				if(npc.getAttitudeTo(nearbyChar) == Attitude.HOSTILE || nearbyChar.getAttitudeTo(npc) == Attitude.HOSTILE)
				{
					attack(npc, nearbyChar);
				}
			}
			npc.update(delta);
			
			//Removing NPCs dynamically causes ConcurrentModificationException   
			if(!npc.isLive())
				deadNpcs.add(npc);
		}
		
		aiNpcs.removeAll(deadNpcs);
	}
	/**
	 * Puts specified NPCs under AI control
	 * @param npcs List with game characters
	 */
	public void addNpcs(List<Character> npcs)
	{
		aiNpcs.addAll(npcs);
	}
	/**
	 * Moves NPC around 
	 * @param npc Character controlled by AI
	 */
	private void moveAround(Character npc)
	{
		if(npc.getTarget() == null)
		{
			switch(rng.nextInt(8))
			{
			case 0:
				if(npc.getAvatar().getDirection() != Coords.UP)
					npc.move(0, -4);
				break;
			case 1:
				if(npc.getAvatar().getDirection() != Coords.RIGHT)
					npc.move(4, 0);
				break;
			case 2:
				if(npc.getAvatar().getDirection() != Coords.DOWN)
					npc.move(0, 4);
				break;
			case 3:
				if(npc.getAvatar().getDirection() != Coords.LEFT)
					npc.move(-4, 0);
				break;
			default:
				return;
			}
		}
	}
	/**
	 * Attacks target of AI NPC
	 * @param agressor Character controlled by AI
	 * @param target Target of aggressor
	 */
	private void attack(Character aggressor, Targetable target)
	{
		if(aggressor.getTarget() == null && target.isLive())
		{
            aggressor.setTarget(target);
            saySomething(aggressor, "aggressive", false);
		}
		else if(aggressor.getTarget() != null && !target.isLive())
            aggressor.setTarget(null);
		
		aggressor.useSkill(aggressor.getSkills().get("autoA"));
	}
	/**
	 * Urges NPC to say something
	 * @param who Character controled by AI
	 * @param what String with one of these categories: aggressive, friendly, idle
	 * @param random Determines whether speech should be random or not 
	 */
	private void saySomething(Character who, String what, boolean random)
	{
	    if(random)
	    {
	        if(rng.nextInt(10) == 1)
	            who.speak(TConnector.getRanomText(what));
	    }
	    else
	        who.speak(TConnector.getRanomText(what));
	}
}