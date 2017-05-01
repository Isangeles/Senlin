package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.core.skill.Passive;
import pl.isangeles.senlin.core.skill.Skill;
/**
 * Class for in-game skills menu
 * @author Isangeles
 *
 */
class SkillsMenu extends InterfaceObject 
{
	private Character player;
	private TrueTypeFont ttf;
	private SkillSlots normals;
	private SkillSlots magic;
	private SkillSlots passives;
	private List<Skill> skillsIn = new ArrayList<>();

	public SkillsMenu(GameContainer gc, Character player)
			throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("ui/background/skillsMenuBG.png"), "uiSMenu", false, gc);
		this.player = player;
		
		normals = new SkillSlots(gc);
		magic = new SkillSlots(gc);
		passives = new SkillSlots(gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(12f), true);
	}
	
	@Override
	public void draw(float x, float y)
	{
		this.draw(x, y, false);
		ttf.drawString(x+(getHeight()/2), y+getDis(10), TConnector.getText("ui", "sMenuTitle"));
		
		ttf.drawString(x+getDis(13), y+getDis(65), TConnector.getText("ui", "sMenuSkills"));
		ttf.drawString(x+getDis(13), y+getDis(245), TConnector.getText("ui", "sMenuMagic"));
		ttf.drawString(x+getDis(13), y+getDis(415), TConnector.getText("ui", "sMenuAbi"));
		normals.draw(x+getDis(13), y+getDis(84));
		magic.draw(x+getDis(13), y+getDis(259));
		passives.draw(x+getDis(13), y+getDis(434));
	}
	
	public void update()
	{
		addSkills();
	}
	
	private void addSkills()
	{
		for(Skill skill : player.getSkills())
		{
			if(!skillsIn.contains(skill))
			{
				if(Attack.class.isInstance(skill) || Buff.class.isInstance(skill))
				{
					if(skill.isMagic())
					{
						if(magic.insertSkill(skill))
							skillsIn.add(skill);
					}
					else
					{
						if(normals.insertSkill(skill))
							skillsIn.add(skill);
					}
				}
				if(Passive.class.isInstance(skill))
				{
					if(passives.insertSkill(skill))
						skillsIn.add(skill);
				}
			}
		}
	}
	/**
	 * Inner class for skill slots
	 * @author Isangeles
	 *
	 */
	private class SkillSlots
	{
		private SkillSlot[][] slots;
		
		public SkillSlots(GameContainer gc) throws SlickException, IOException
		{
			slots = new SkillSlot[3][17];
			
	        for(int i = 0; i < 3; i ++)
			{
				for(int j = 0; j < 17; j ++)
				{
					slots[i][j] = new SkillSlot(gc);
				}
			}
		}
		/**
		 * Draws all skill slots
		 * @param x Position on x-axis
		 * @param y	Position on y-axis
		 */
		public void draw(float x, float y)
		{
			for(int i = 0; i < 3; i ++)
			{
				for(int j = 0; j < 17; j ++)
				{
					slots[i][j].draw(x + (j*getDis(45)) + getDis(3), y + (i*getDis(35)) + getDis(3), false);
				}
			}
		}
		/**
		 * Moves skill from slotA to slotB
		 * @param slotA Current skill slot
		 * @param slotB New slot for skill
		 */
		public void moveSkill(SkillSlot slotA, SkillSlot slotB)
		{
			slotA.dragged(false);
			slotB.insertSkill(slotA.getSkill());
			slotA.removeSkill();
		}
		/**
		 * Inserts skill to first empty slot
		 * @param skill Some skill
		 */
		public boolean insertSkill(Skill skill)
		{
			for(int i = 0; i < slots.length; i ++)
			{
				for(int j = 0; j < slots[i].length; j ++)
				{
					if(slots[i][j].isNull())
					{
						slots[i][j].insertSkill(skill);
						return true;
					}
				}
			}
			return false;
		}
		/**
		 * Returns dragged skill slot
		 * @return Current dragged slot, if no slot from table dragged returns null
		 */
		public SkillSlot getDragged()
		{
			for(SkillSlot slotsLine[] : slots)
			{
				for(SkillSlot ss : slotsLine)
				{
					if(ss.isSkillDragged())
						return ss;
				}
			}
			return null;
		}
	}
}