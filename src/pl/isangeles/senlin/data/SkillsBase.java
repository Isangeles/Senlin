package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.pattern.AttackPattern;
import pl.isangeles.senlin.util.DConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.EffectType;
import pl.isangeles.senlin.core.item.WeaponType;
/**
 * Static class for skills
 * loaded at newGameMenu initialization
 * @author Isangeles
 *
 */
public class SkillsBase 
{
	private static GameContainer gc;
	private static Map<String, AttackPattern> attacksMap = new HashMap<>();
	/**
	 * Private constructor to prevent initialization
	 */
	private SkillsBase() 
	{}
	
	public static Attack getAutoAttack(Character character) throws SlickException, IOException, FontFormatException
	{
		//return new Attack(character, "autoA", "Attack", "Basic attack", "autoAttack.png", EffectType.NORMAL, 0, 0, 0, 2000, false, WeaponType.FIST, 40, null, gc);
		return attacksMap.get("autoA").make(character, gc);
	}
	/**
	 * Returns attack skill with specific ID from base
	 * @param character Game character
	 * @param id ID of desired skill
	 * @return Attack skill from base
	 */
	public static Attack getAttack(Character character, String id)
	{
	    try
        {
            return attacksMap.get(id).make(character, gc);
        } 
	    catch (SlickException | IOException | FontFormatException e)
        {
           Log.addSystem("skill_builder-fail msg///" + e.getMessage());
           return null;
        }
	}
	/**
	 * Loads skills base
	 * TODO implement skills base
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 */
	public static void load(GameContainer gc) throws SlickException, IOException, FontFormatException, SAXException, ParserConfigurationException
	{
		SkillsBase.gc = gc;
		attacksMap = DConnector.getAttacksMap("attacks");
	}
}
