package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.CommBase;
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
	 * @param range Required range
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Attack(Character character, String id, String name, String info, String imgName, int damage, int magickaCost, int castTime, int range, GameContainer gc)
			throws SlickException, IOException, FontFormatException 
	{
		super(character, id, name, info, imgName, magickaCost, castTime);
		this.damage = damage;
		this.range = range;
		setTile(gc);
	}

	@Override
	public String getInfo() 
	{
		String fullInfo = name + System.lineSeparator() + "Damage:" + damage + System.lineSeparator() + "Range:" + range + System.lineSeparator() +
						  "Cast time:" + getCastSpeed() + System.lineSeparator() + info;
		
		return fullInfo;
	}
	@Override
	public boolean prepare(Character user, Character target) 
	{
		if(super.isActive())
		{
			if(target != null)
			{
				CommBase.addInformation("Range: " + owner.getRangeFrom(target.getPosition())); //TEST LINE
				if(owner.getRangeFrom(target.getPosition()) <= range)
				{
				    this.user = user;
				    this.target = target;
				    ready = true;
				    active = false;
				    return true;
				}
				else
				{
	                user.move(target.getPosition()[0]-(range-15), target.getPosition()[1]-(range-15));
					CommBase.addWarning(TConnector.getText("ui", "logNoRange"));
				}
			}
			else
				CommBase.addWarning(TConnector.getText("ui", "logNoTar"));
		}
		else
			CommBase.addWarning(TConnector.getText("ui", "logNotRdy"));
		
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
	        user.takeMagicka(magickaCost);
	        target.takeHealth(user.getHit()+damage);
	        ready = false;
	        active = true;
	    }
	}
}
