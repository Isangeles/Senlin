package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.core.bonus.Bonuses;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.effect.Effects;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.gui.tools.EffectTile;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for buffs
 * @author Isangeles
 *
 */
public class Buff extends Skill
{
	private BuffType type;
	private List<Bonus> bonuses;
    private int range;
    /**
     * Buff constructor
     * @param character Game character (skill owner)
     * @param id Skill ID
     * @param imgName Skill icon image
     * @param effectType Effect type
     * @param type Buff type
     * @param reqs Requirements to use
     * @param castTime Casting time
     * @param range Maximal range form target
     * @param cooldown Skill cooldown time in milliseconds
     * @param duration Buff duration time in milliseconds
     * @param bonuses Buff bonuses
     * @param effects Skill effects
     * @param gc Slick game container
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
	public Buff(Character character, String id, String imgName, EffectType effectType, BuffType type, List<Requirement> reqs, int castTime, int range, int cooldown, 
	            List<Bonus> bonuses, List<String> effects, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(character, id, imgName, effectType, reqs, castTime, cooldown, effects);
		this.bonuses = bonuses;
		this.type = type;
		setTile(gc);
		setSoundEffect();
	}
	
	@Override
	public void update(int delta)
	{
		super.update(delta);
	}
	/**
	 * Returns list with buff bonuses
	 * @return List with bonus objects
	 */
	public List<Bonus> getBonuses()
	{
		return bonuses;
	}
	/**
	 * Returns list with buff effects
	 * @return List with effect objects
	 */
	public List<Effect> getEffects()
	{
		List<Effect> effectsToPass = new ArrayList<>();
		for(String effectId : effects)
		{
			Effect effect = EffectsBase.getEffect(effectId);
			effectsToPass.add(effect);
		}
		return effectsToPass;
	}

	@Override
	public String getInfo() 
	{
	    String fullInfo = name + System.lineSeparator() + TConnector.getText("ui", "eleTInfo") + ":" + getTypeString() + System.lineSeparator() + 
                TConnector.getText("ui", "rangeName") + ":" + range + System.lineSeparator() +
                TConnector.getText("ui", "castName") + ":" + getCastTime() + System.lineSeparator() + 
                TConnector.getText("ui", "cdName") + ":" + cooldown/1000 + " sec"  + System.lineSeparator();
	    
	    for(Bonus bon : bonuses)
	    {
	        fullInfo += System.lineSeparator() + bon.getInfo();
	    }
	    
	    fullInfo += System.lineSeparator() + info;

	    return fullInfo;
	}
	/**
	 * Check if buff is active
	 * @return
	 */
	public boolean isActive()
	{
		return active;
	}
    @Override
    public void activate()
    {
    	if(active)
    	{
    		target.takeBuff(owner, this);
    		deactivate();
    	}
    }

    @Override
    public CharacterOut prepare(Character user, Targetable target)
    {
        if(isReady() && useReqs.isMetBy(user))
        {
        	if(type == BuffType.ONTARGET && target == null)
        		return CharacterOut.NOTARGET;
        	
        	if(type.useTarget() && target != null)
        	{
        		if(user.getRangeFrom(target) <= range)
        		{
        			this.target = target;
        			active = true;
        			ready = false;
        			return CharacterOut.SUCCESS;
        		}
            	else
            	{
            		user.moveTo(target, range);
            		return CharacterOut.NORANGE;
            	}
        	}
        	else if(type.useUser())
        	{
        		this.target = user;
    			active = true;
    			ready = false;
    			playSoundEffect();
    			return CharacterOut.SUCCESS;
        	}
        	else
        		return CharacterOut.UNKNOWN;
        }
        else
        	return CharacterOut.NOTREADY;
    }
    /**
     * Deactivates buff
     */
    private void deactivate()
    {
    	target = null;
        active = false;
    }
}
