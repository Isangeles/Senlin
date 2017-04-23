package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Character;

public class Attack extends Skill 
{
	private int damage;
	private int magickaCost;
	private int castTime;
	
	public Attack(String id, String name, String info, String imgName, int damage, int magickaCost, int castTime, GameContainer gc)
			throws SlickException, IOException, FontFormatException 
	{
		super(id, name, info, imgName);
		this.damage = damage;
		this.magickaCost = magickaCost;
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
		if(castTime == 0)
		{
			
		}
		else
		{
			user.startCast(castTime);
		}
	}

}
