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
	private int castTime;
	private int range;
	
	public Attack(String id, String name, String info, String imgName, int damage, int magickaCost, int castTime, int range, GameContainer gc)
			throws SlickException, IOException, FontFormatException 
	{
		super(id, name, info, imgName, magickaCost);
		this.damage = damage;
		this.castTime = castTime;
		this.range = range;
		setTile(gc);
	}

	@Override
	public String getInfo() 
	{
		String fullInfo = name + File.separator + info;
		
		return fullInfo;
	}
	@Override
	public void activate(Character user, Character target) 
	{
		CommBase.addInformation("Range: " + Global.getRangeFromTar());
		if(super.isActive())
		{
			if(target != null)
			{
				if(Global.getRangeFromTar() <= range)
				{
					if(castTime == 0)
					{
						target.takeHealth(damage+user.getHit());
						user.takeMagicka(magickaCost);
					}
					else
					{
						user.startCast(castTime);
					}
				}
				else
					CommBase.addWarning(TConnector.getText("ui", "logNoRange"));
			}
			else
				CommBase.addWarning(TConnector.getText("ui", "logNoTar"));
		}
		else
			CommBase.addWarning(TConnector.getText("ui", "logNotRdy"));
	}

}
