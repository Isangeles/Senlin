package pl.isangeles.senlin.states;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pl.isangeles.senlin.inter.*;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Atribute;

public class NewGameMenu extends BasicGameState 
{
	private Switch strSwitch;
	private Switch conSwitch;
	private Switch dexSwitch;
	private Switch intSwitch;
	private Switch wisSwitch;
	private PointsField fieldAtributesPts;
	private Atribute ptsAtributes;
	
	private TextInput fieldName;
	
	private List<Image> imgPorList;
	int imgId;
	private Image imgPortrait;
	private Button buttNextPor;
	private Button buttPrevPor;
	
	private Character player;
	
	private Button buttNext;
	private Button buttBack;
	
	boolean mainMenuReq;
	boolean gameWorldReq;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		try 
		{
			player = new Character();
			ptsAtributes = new Atribute(5);
			
			strSwitch = new Switch(container, "Strenght", player.getStr(), ptsAtributes);
			conSwitch = new Switch(container, "Constitution", player.getCon(), ptsAtributes);
			dexSwitch = new Switch(container, "Dexterity", player.getDex(), ptsAtributes);
			intSwitch = new Switch(container, "Intelect", player.getInt(), ptsAtributes);
			wisSwitch = new Switch(container, "Wisdom", player.getWis(), ptsAtributes);
			fieldAtributesPts = new PointsField(GConnector.getInput("field/ptsFieldBG.png"), "fieldAP", false, ptsAtributes, "Points");
			
			imgPorList = GConnector.getPortraits();
			imgId = 0;
			//imgPortrait = player.getPortrait();
			buttNextPor = new Button(GConnector.getInput("button/buttonNext.png"), "buttNP", false, "", container);
			buttPrevPor = new Button(GConnector.getInput("button/buttonBack.png"), "buttBP", false, "", container);
			
			fieldName = new TextInput(GConnector.getInput("field/textFieldBG.png"), "fieldName", false, container);
			

			buttNext = new Button(GConnector.getInput("button/buttonNext.png"), "buttN", false, "", container);
			buttBack = new Button(GConnector.getInput("button/buttonBack.png"), "buttB", false, "", container);
			
			buttNext.setActive(false);
			
		} catch (IOException | FontFormatException e) 
		{
			e.printStackTrace();
		}
	}

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
		imgPorList.get(imgId).draw(200, 400, 100f, 120f);
		buttNextPor.draw(300, 480);
		buttPrevPor.draw(160, 480);
		
		buttNext.draw(1800, 1000);
		buttBack.draw(10, 1000);
	}

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
		    game.enterState(2);
		}
		
		if(fieldName.getText() != null && ptsAtributes.getValue() == 0)
		    buttNext.setActive(true);
	}

	@Override
	public int getID() 
	{
		return 1;
	}
	
	@Override
	public void mouseReleased(int button, int x, int y)
	{
		if(buttNextPor.isMouseOver() && imgId < imgPorList.size()-1)
			imgId ++;
		else if(buttPrevPor.isMouseOver() && imgId > 0)
			imgId --;
		
		if(buttNext.isMouseOver() && buttNext.isActive())
		{
		    player.setName(fieldName.getText());
		    player.setPortrait(imgPorList.get(imgId));
		    player.levelUp();
		    gameWorldReq = true;
		}
		
		if(buttBack.isMouseOver())
		    mainMenuReq = true;
	}

}
