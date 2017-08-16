package pl.isangeles.senlin.core.skill;

import java.util.List;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.req.Requirement;

public class Passive extends Skill 
{
    
	public Passive(Character character, String id, String imgName, EffectType type, List<Requirement> reqs, List<String> effects) 
	{
		super(character, id, imgName, type, reqs, 0, 0, effects);
	}

	@Override
	protected String getInfo() 
	{
		return null;
	}

    @Override
    public void activate()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public CharacterOut prepare(Character user, Targetable target)
    {
        // TODO Auto-generated method stub
        return CharacterOut.UNKNOWN;
    }

}
