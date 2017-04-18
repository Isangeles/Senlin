package pl.isangeles.senlin.states;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pl.isangeles.senlin.inter.*;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Character;
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
	
	boolean mainMenuReq;
	boolean gameWorldReq;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		try 
		{
			player = new Character(container);
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
			
			
		} catch (IOException | FontFormatException e) 
		{
			System.err.println(e.getMessage());
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
		porList.get(imgId).draw(200, 400, 100f, 120f);
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
		    game.addState(new GameWorld(player));
            game.getState(2).init(container, game);
		    game.enterState(2);
		}
		
		if(fieldName.getText() != null && ptsAtributes.getValue() == 0)
		    buttNext.setActive(true);
		else 
		    buttNext.setActive(false);
	}

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
