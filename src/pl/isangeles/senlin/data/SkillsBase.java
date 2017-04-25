package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Skill;
/**
 * Static class for skills
 * loaded at newGameMenu initialization
 * @author Isangeles
 *
 */
public class SkillsBase 
{
	private static GameContainer gc;
	private static Attack autoAttack;
	
	private SkillsBase() 
	{}
	
	public static Attack getAutoAttack() throws SlickException, IOException, FontFormatException
	{
		return new Attack("autoA", "Attack", "Basic attack", "autoAttack.png", 0, 0, 0, gc);
	}
	/**
	 * Loads skills base
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public static void load(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		SkillsBase.gc = gc;
	}
}
