package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.CommBase;
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
	
	public Attack(String id, String name, String info, String imgName, int damage, int magickaCost, int castTime, GameContainer gc)
			throws SlickException, IOException, FontFormatException 
	{
		super(id, name, info, imgName, magickaCost);
		this.damage = damage;
		this.castTime = castTime;
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
		if(super.isActive())
		{
			if(target != null)
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
				CommBase.addInformation(TConnector.getText("ui", "logNoTar"));
		}
		else
			CommBase.addInformation(TConnector.getText("ui", "logNotRdy"));
	}

}
