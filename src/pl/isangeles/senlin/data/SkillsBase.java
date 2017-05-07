package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.core.Character;
/**
 * Static class for skills
 * loaded at newGameMenu initialization
 * @author Isangeles
 *
 */
public class SkillsBase 
{
	private static GameContainer gc;
	
	private SkillsBase() 
	{}
	
	public static Attack getAutoAttack(Character character) throws SlickException, IOException, FontFormatException
	{
		return new Attack(character, "autoA", "Attack", "Basic attack", "autoAttack.png", 0, 0, 0, 2000, 40, gc);
	}
	/**
	 * Loads skills base
	 * TODO implement skills base
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
