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

import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.character.Attitude;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.character.Gender;
import pl.isangeles.senlin.core.character.Race;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.GuildsBase;
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
	private Switch strS;
	private Switch conS;
	private Switch dexS;
	private Switch intS;
	private Switch wisS;
	private TextSwitch genS;
	private PointsField attrPointsF;
	private Attribute attrPoints;
	
	private TextInput nameI;
	
	private List<Portrait> portraits;
	private int imgId;
	private Button nextPortraitB;
	private Button prevPortraitB;
	
	//private Character player;
	
	private Button nextB;
	private Button backB;
	
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
			Module.setDir(Settings.getModuleName());
	        EffectsBase.load(Module.getSkillsPath(), container);
        	SkillsBase.load(Module.getSkillsPath(), container);
            
			//player = new Character(container);
			//Global.setPlayer(player);
			
			attrPoints = new Attribute(5);
			
			strS = new Switch(container, "Strenght", 1, attrPoints, TConnector.getText("ui", "strInfo"));
			conS = new Switch(container, "Constitution", 1, attrPoints, TConnector.getText("ui", "conInfo"));
			dexS = new Switch(container, "Dexterity", 1, attrPoints, TConnector.getText("ui", "dexInfo"));
			intS = new Switch(container, "Intelect", 1, attrPoints, TConnector.getText("ui", "intInfo"));
			wisS = new Switch(container, "Wisdom", 1, attrPoints, TConnector.getText("ui", "wisInfo"));
			genS = new TextSwitch(container, TConnector.getText("ui", "genName"), new String[] {TConnector.getText("ui", "genMale"), TConnector.getText("ui", "genFemale")});
			attrPointsF = new PointsField(GConnector.getInput("field/ptsFieldBG.png"), "fieldAP", false, attrPoints, "Points", container, TConnector.getText("ui", "attPtsInfo"));
			
			portraits = new ArrayList<>();
			List<Image> imgPorList = GConnector.getPortraits();
			for(Image img : imgPorList.toArray(new Image[imgPorList.size()]))
			{
				portraits.add(new Portrait(img, container));
			}
			
			nextPortraitB = new Button(GConnector.getInput("button/buttonNext.png"), "buttNP", false, "", container);
			prevPortraitB = new Button(GConnector.getInput("button/buttonBack.png"), "buttBP", false, "", container);
			
			nameI = new TextInput(GConnector.getInput("field/textFieldBG.png"), "fieldName", false, container);
			
			nextB = new Button(GConnector.getInput("button/buttonNext.png"), "buttN", false, "", container);
			backB = new Button(GConnector.getInput("button/buttonBack.png"), "buttB", false, "", container);
			
			nextB.setActive(false);
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
		strS.draw(200, 200, true);
		conS.draw(500, 200, true);
		dexS.draw(800, 200, true);
		intS.draw(1100, 200, true);
		wisS.draw(1400, 200, true);
		attrPointsF.draw(1400, 200, false);
		
		nameI.draw(800, 100, true);
		nameI.render(g);
		genS.draw(200, 300, true);
		portraits.get(imgId).draw(200, 400, Coords.getSize(100f), Coords.getSize(120f));
		nextPortraitB.draw(300, 480, true);
		prevPortraitB.draw(160, 480, true);
		
		nextB.draw(1800, 1000, true);
		backB.draw(10, 1000, true);
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
		    try 
		    {
				game.addState(new LoadingScreen(createPlayer(container)));
			} 
		    catch (IOException | FontFormatException e) 
		    {
				e.printStackTrace();
				System.exit(1);
			}
		    game.getState(4).init(container, game);
		    game.enterState(4);
		}
		
		if(nameI.getText() != null && attrPoints.getValue() == 0)
		    nextB.setActive(true);
		else 
		    nextB.setActive(false);
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
		if(nextPortraitB.isMouseOver() && imgId < portraits.size()-1)
			imgId ++;
		else if(prevPortraitB.isMouseOver() && imgId > 0)
			imgId --;
		
		if(nextB.isMouseOver() && nextB.isActive())
		{
			/*
		    player.setName(nameI.getText());
		    switch(genS.getValueId())
		    {
		    case 0:
		    	player.setGender(Gender.MALE);
		    	break;
		    case 1:
		    	player.setGender(Gender.FEMALE);
		    	break;
		    default:
		    	player.setGender(Gender.MALE);
		    }
		    player.setPortrait(portraits.get(imgId));
		    player.setAttributes(new Attributes(strS.getValue(), conS.getValue(), dexS.getValue(), intS.getValue(), wisS.getValue()));
		    player.levelUp();
		    */
		    gameWorldReq = true;
		}
		
		if(backB.isMouseOver())
		    mainMenuReq = true;
	}
	/**
	 * Creates player character
	 * @param gc
	 * @return
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	private Character createPlayer(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		String name = nameI.getText();
		Gender gender;
		switch(genS.getValueId())
		{
		case 0:
			gender = Gender.MALE;
			break;
		case 1:
			gender = Gender.FEMALE;
			break;
		default:
			gender = Gender.MALE;
		}
		Portrait portrait = portraits.get(imgId);
		Attributes attributes = new Attributes(strS.getValue(), conS.getValue(), dexS.getValue(), intS.getValue(), wisS.getValue());
		return new Character("player", gender, Race.HUMAN, Attitude.FRIENDLY, "", name, 1, attributes, portrait, gender.getSSName("cloth-1222211-80x90.png"), false, gc);
	}
}
