/*
 * SkillsMenu.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package pl.isangeles.senlin.gui.tools;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.core.skill.Passive;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.Slot;
/**
 * Class for in-game skills menu
 * @author Isangeles
 *
 */
class SkillsMenu extends InterfaceObject implements UiElement, MouseListener
{
	private Character player;
	private TrueTypeFont ttf;
	private Button pageSkills;
	private Button pageMagic;
	private Button pageAbilities;
	private SlotsPages slots;
	private Set<Skill> skills = new HashSet<>();
	private Set<Skill> magic = new HashSet<>();
	private Set<Skill> passives = new HashSet<>();
	private boolean openReq;
	private boolean focus;
	/**
	 * Skills menu constructor
	 * @param gc Slick game container
	 * @param player Player character
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public SkillsMenu(GameContainer gc, Character player)
			throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("ui/background/skillsMenuBGv2.png"), "uiSMenu", false, gc);
		gc.getInput().addMouseListener(this);
		this.player = player;
		
		pageSkills = new Button(GConnector.getInput("button/bookmark.png"), "uiBookmark", false, TConnector.getText("ui", "sMenuSkills"), gc);
		pageMagic = new Button(GConnector.getInput("button/bookmark.png"), "uiBookmark", false, TConnector.getText("ui", "sMenuMagic"), gc);
		pageAbilities = new Button(GConnector.getInput("button/bookmark.png"), "uiBookmark", false, TConnector.getText("ui", "sMenuAbi"), gc);
		
		Slot[][] slots = new Slot[11][11];
        for(int i = 0; i < slots.length; i ++)
        {
            for(int j = 0; j < slots[i].length; j ++)
            {
                slots[i][j] = new SkillSlot(gc);
            }
        }
		this.slots = new SlotsPages(slots, gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(12f), true);
		
		addSkills();
        this.slots.insertContent(skills);
	}
	
	@Override
	public void draw(float x, float y)
	{
		this.draw(x, y, false);
		ttf.drawString(x+(getHeight()/2), y+getDis(10), TConnector.getText("ui", "sMenuTitle"));
		
		pageSkills.draw(x+getDis(70), y+getDis(590), false);
		pageMagic.draw(x+getDis(215), y+getDis(590), false);
		pageAbilities.draw(x+getDis(365), y+getDis(590), false);
		slots.draw(x+getDis(25), y+getDis(50), false);
	}
	/**
	 * Updates menu
	 */
	public void update()
	{
		addSkills();
		slots.update();
	}
	
	public void open()
	{
		openReq = true;
		focus = true;
	}
	/**
	 * Resets skills menu to default state
	 */
    @Override
    public void reset()
    {
        super.moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
        focus = false;
        slots.setFocus(false);
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#close()
	 */
	@Override
	public void close()
	{
		openReq = false;
		reset();
	}
	
	public boolean isOpenReq()
	{
		return openReq;
	}
    /**
     * Returns currently dragged slote
     * @return Dragged skill slot
     */
	public SkillSlot getDragged()
	{
	    if(slots.getDragged() != null)
	        return (SkillSlot)slots.getDragged();
	    
	    return null;
	}
	/**
	 * Adds all player skills to menu
	 */
	private void addSkills()
	{
		for(Skill skill : player.getSkills())
		{
			if(Attack.class.isInstance(skill) || Buff.class.isInstance(skill))
			{
				if(skill.isMagic())
				{
					magic.add(skill);
				}
				else
				{
					skills.add(skill);
				}
			}
			if(Passive.class.isInstance(skill))
			{
				passives.add(skill);
			}
		}
	}
    /* (non-Javadoc)
     * @see org.newdawn.slick.ControlledInputReciever#inputEnded()
     */
    @Override
    public void inputEnded()
    {
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
     */
    @Override
    public void inputStarted()
    {
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
     */
    @Override
    public boolean isAcceptingInput()
    {
        return focus;
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
     */
    @Override
    public void setInput(Input input)
    {
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
     */
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount)
    {
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mouseDragged(int, int, int, int)
     */
    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy)
    {
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
     */
    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mousePressed(int, int, int)
     */
    @Override
    public void mousePressed(int button, int x, int y)
    {
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
     */
    @Override
    public void mouseReleased(int button, int x, int y)
    {
        if(pageSkills.isMouseOver())
        {
            slots.clear();
            slots.insertContent(skills);
        }
        
        if(pageMagic.isMouseOver())
        {
            slots.clear();
            slots.insertContent(magic);
        }
        
        if(pageAbilities.isMouseOver())
        {
            slots.clear();
            slots.insertContent(passives);
        }
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
     */
    @Override
    public void mouseWheelMoved(int change)
    {
    }
}
