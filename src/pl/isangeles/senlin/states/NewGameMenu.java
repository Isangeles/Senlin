/*
 * NewGameMenu.java
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
package pl.isangeles.senlin.states;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.gui.*;
import pl.isangeles.senlin.core.Attribute;
import pl.isangeles.senlin.core.Attributes;
/**
 * Class for new game menu used to create new character for player
 * @author Isangeles
 *
 */
public class NewGameMenu extends BasicGameState 
{
	private Switch strSwitch;
	private Switch conSwitch;
	private Switch dexSwitch;
	private Switch intSwitch;
	private Switch wisSwitch;
	private PointsField fieldAtributesPts;
	private Attribute ptsAtributes;
	
	private TextInput fieldName;
	
	private List<Portrait> porList;
	private int imgId;
	private Button buttNextPor;
	private Button buttPrevPor;
	
	private Character player;
	
	private Button buttNext;
	private Button buttBack;
	
	private boolean mainMenuReq;
	private boolean gameWorldReq;
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		try 
		{
	        EffectsBase.load(container);
        	SkillsBase.load(container);
        	
			player = new Character(container);
			Global.setPlayer(player);
			
			ptsAtributes = new Attribute(5);
			
			strSwitch = new Switch(container, "Strenght", player.getStr(), ptsAtributes, TConnector.getText("ui", "strInfo"));
			conSwitch = new Switch(container, "Constitution", player.getCon(), ptsAtributes, TConnector.getText("ui", "conInfo"));
			dexSwitch = new Switch(container, "Dexterity", player.getDex(), ptsAtributes, TConnector.getText("ui", "dexInfo"));
			intSwitch = new Switch(container, "Intelect", player.getInt(), ptsAtributes, TConnector.getText("ui", "intInfo"));
			wisSwitch = new Switch(container, "Wisdom", player.getWis(), ptsAtributes, TConnector.getText("ui", "wisInfo"));
			fieldAtributesPts = new PointsField(GConnector.getInput("field/ptsFieldBG.png"), "fieldAP", false, ptsAtributes, "Points", container, TConnector.getText("ui", "attPtsInfo"));
			
			porList = new ArrayList<>();
			List<Image> imgPorList = GConnector.getPortraits();
			for(Image img : imgPorList.toArray(new Image[imgPorList.size()]))
			{
				porList.add(new Portrait(img, container));
			}
			
			buttNextPor = new Button(GConnector.getInput("button/buttonNext.png"), "buttNP", false, "", container);
			buttPrevPor = new Button(GConnector.getInput("button/buttonBack.png"), "buttBP", false, "", container);
			
			fieldName = new TextInput(GConnector.getInput("field/textFieldBG.png"), "fieldName", false, container);
			
			buttNext = new Button(GConnector.getInput("button/buttonNext.png"), "buttN", false, "", container);
			buttBack = new Button(GConnector.getInput("button/buttonBack.png"), "buttB", false, "", container);
			
			buttNext.setActive(false);
		} 
		catch (IOException | FontFormatException | SAXException | ParserConfigurationException e) 
		{
			System.err.println(e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		strSwitch.draw(200, 200);
		conSwitch.draw(500, 200);
		dexSwitch.draw(800, 200);
		intSwitch.draw(1100, 200);
		wisSwitch.draw(1400, 200);
		fieldAtributesPts.draw(1400, 300);
		
		fieldName.draw(800, 100);
		fieldName.render(g);
		porList.get(imgId).draw(200, 400, 100f, 120f);
		buttNextPor.draw(300, 480);
		buttPrevPor.draw(160, 480);
		
		buttNext.draw(1800, 1000);
		buttBack.draw(10, 1000);
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		if(mainMenuReq)
		{
		    mainMenuReq = false;
			game.enterState(0);
		}
		if(gameWorldReq)
		{
		    gameWorldReq = false;
		    game.addState(new LoadingScreen(player));
		    game.getState(4).init(container, game);
		    game.enterState(4);
		}
		
		if(fieldName.getText() != null && ptsAtributes.getValue() == 0)
		    buttNext.setActive(true);
		else 
		    buttNext.setActive(false);
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() 
	{
		return 1;
	}
	
	@Override
	public void mouseReleased(int button, int x, int y)
	{
		if(buttNextPor.isMouseOver() && imgId < porList.size()-1)
			imgId ++;
		else if(buttPrevPor.isMouseOver() && imgId > 0)
			imgId --;
		
		if(buttNext.isMouseOver() && buttNext.isActive())
		{
		    player.setName(fieldName.getText());
		    player.setPortrait(porList.get(imgId));
		    player.setAttributes(new Attributes(strSwitch.getValue(), conSwitch.getValue(), dexSwitch.getValue(), intSwitch.getValue(), wisSwitch.getValue()));
		    player.levelUp();
		    gameWorldReq = true;
		}
		
		if(buttBack.isMouseOver())
		    mainMenuReq = true;
	}

}
