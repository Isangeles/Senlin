package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Effect;
import pl.isangeles.senlin.core.EffectType;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for offensive skills
 * @author Isangeles
 *
 */
public class Attack extends Skill 
{
	private int damage;
	private int range;
	/**
	 * Attack constructor
	 * @param character Skill owner
	 * @param id Skill ID
	 * @param name Skill Name
	 * @param info Skill description
	 * @param imgName Skill icon file
	 * @param damage Damage dealt on target
	 * @param magickaCost Magicka cost of use, determines whether skill is magic or not
	 * @param castTime Cast time
	 * @param cooldown Time that must be waited after skill use
	 * @param range Required range
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Attack(Character character, String id, String name, String info, String imgName, EffectType type, int damage, int magickaCost,
				  int castTime, int cooldown, boolean useWeapon, int range, List<Effect> effects, GameContainer gc)
			throws SlickException, IOException, FontFormatException 
	{
		super(character, id, name, info, imgName, type, magickaCost, castTime, cooldown, useWeapon, effects);
		this.damage = damage;
		this.range = range;
		setTile(gc);
		setSoundEffect();
	}

	@Override
	public String getInfo() 
	{
		String fullInfo = name + System.lineSeparator() + TConnector.getText("ui", "eleTInfo") + ":" + getTypeString() + System.lineSeparator() +  
						  TConnector.getText("ui", "dmgName") + ":" + damage + System.lineSeparator() + 
						  TConnector.getText("ui", "rangeName") + ":" + range + System.lineSeparator() +
						  TConnector.getText("ui", "castName") + ":" + getCastSpeed() + System.lineSeparator() + 
						  TConnector.getText("ui", "cdName") + ":" + cooldown/1000 + " sec"  + System.lineSeparator() + 
						  info;
		
		return fullInfo;
	}
	@Override
	public boolean prepare(Character user, Targetable target) 
	{
		if(super.isActive())
		{
			if(target != null)
			{
				Log.addInformation("Range: " + owner.getRangeFrom(target.getPosition())); //TEST LINE
				if(owner.getRangeFrom(target.getPosition()) <= range)
				{
				    this.target = target;
				    ready = true;
				    active = false;
		            playSoundEffect();
				    return true;
				}
				else
				{
	                user.moveTo(target);
					Log.addWarning(TConnector.getText("ui", "logNoRange"));
				}
			}
			else
				Log.addWarning(TConnector.getText("ui", "logNoTar"));
		}
		else
			Log.addWarning(TConnector.getText("ui", "logNotRdy"));
		
		return false;
	}
    /**
     * Activates attack skill
     */
	@Override
	public void activate()
	{
	    if(ready)
	    {
	        owner.takeMagicka(magickaCost);
	        
	        List<Effect> effectsToPass = new ArrayList<>();
	        if(effects != null)
	        {
		        for(Effect effect : effects)
		        {
		        	effectsToPass.add(EffectsBase.getEffect(effect.getId()));
		        }
	        }
	        target.takeAttack(owner.getHit()+damage, effectsToPass);
	        ready = false;
	    }
	}
}
