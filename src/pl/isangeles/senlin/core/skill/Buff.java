package pl.isangeles.senlin.core.skill;

import java.util.List;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.bonus.Bonuses;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.req.Requirement;

public class Buff extends Skill 
{
	private BuffType type;
    private Bonuses statsBuff;
    private int hpBuff;
    private int range;
    private int time;
    private int duration;
    
	public Buff(Character character, String id, String imgName, EffectType effectType, BuffType type, List<Requirement> reqs, int castTime, int range, int cooldown, int duration, List<Effect> effects) 
	{
		super(character, id, imgName, effectType, reqs, castTime, cooldown, effects);
		this.type = type;
		this.duration = duration;
	}
	
	public void update(int delta)
	{
	    if(active)
	    {
	        time += delta;
	        if(time >= duration)
	            active = false;
	    }
	}

	@Override
	public String getInfo() 
	{
		return null;
	}

    @Override
    public void activate()
    {
    	if(ready)
    	{
    		
    	}
    }

    @Override
    public CharacterOut prepare(Character user, Targetable target)
    {
        if(isActive() && useReqs.isMetBy(user))
        {
        	if(type == BuffType.ONTARGET && target == null)
        		return CharacterOut.NOTARGET;
        	
        	if(type.useTarget() && target != null)
        	{
        		if(user.getRangeFrom(target) <= range)
        		{
        			this.target = target;
        			ready = true;
        			active = false;
        			playSoundEffect();
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
    			ready = true;
    			active = false;
    			playSoundEffect();
    			return CharacterOut.SUCCESS;
        	}
        	else
        		return CharacterOut.UNKNOWN;
        }
        else
        	return CharacterOut.NOTREADY;
    }

}
