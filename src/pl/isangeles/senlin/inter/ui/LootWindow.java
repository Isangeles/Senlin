package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.item.Item;
/**
 * Class for loot window
 * @author Isangeles
 *
 */
class LootWindow extends InterfaceObject implements UiElement, MouseListener, KeyListener
{
	private Character lootingChar;
	private Character lootedChar;
	private GameContainer gc;
	private TrueTypeFont ttf;
	private Button next;
	private Button back;
	private Button takeAll;
	private Button takeGold;
	private List<SlotsBlock> slotsPages = new ArrayList<>();
	private int pageIndex;
	private boolean openReq;
	/**
	 * Loot window constructor
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public LootWindow(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
	{
		super(GConnector.getInput("ui/background/lootBG.png"), "uiLoot", false, gc);
		gc.getInput().addMouseListener(this);
		gc.getInput().addKeyListener(this);
		this.gc = gc;
		lootingChar = player;
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(12f), true);
		
		next = new Button(GConnector.getInput("button/buttonBack.png"), "uiLootBN", false, "",  gc);
		back = new Button(GConnector.getInput("button/buttonNext.png"), "uiLootBB", false, "",  gc);
		takeAll = new Button(GConnector.getInput("button/buttonS.png"), "uiLootB1S", false, "Take all",  gc);
		takeGold = new Button(GConnector.getInput("button/buttonS.png"), "uiLootB2S", false, "Take gold",  gc);
		
		next.setActive(false);
		back.setActive(false);
		
		slotsPages.add(newPage());
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		//Text
		String windowTitle = lootedChar.getName() + " " + "loot";
		String aoGold = "Gold: " + lootedChar.getInventory().getGold();
		ttf.drawString(x+((getScaledWidth()/2)-ttf.getWidth(windowTitle)), y, windowTitle);
		ttf.drawString(x+((getScaledWidth()/2)-ttf.getWidth(aoGold)), y+getDis(30), aoGold);
		//Slots
		slotsPages.get(pageIndex).draw(x+getDis(15), y+getDis(70));
		//Buttons
		next.draw(x+getDis(265), y+getDis(350), false);
		back.draw(x+getDis(0), y+getDis(350), false);
		takeAll.draw(x+getDis(159), y+getDis(350), false);
		takeGold.draw(x+getDis(70), y+getDis(350), false);
	}
	
	@Override
	public void update() 
	{
		if(slotsPages.size() > 1)
			next.setActive(true);
		if(pageIndex > 0)
			back.setActive(true);
	}
	/**
	 * Resets loot window to default state
	 */
	@Override
	public void reset() 
	{
		super.moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
		lootedChar = null;
		pageIndex = 0;
		for(SlotsBlock page : slotsPages)
		{
			page.clear();
		}
	}
	/**
	 * Opens loot window
	 * @param characterToLoot Character with loot to display 
	 * @throws SlickException
	 * @throws IOException
	 */
	public void open(Character characterToLoot) throws SlickException, IOException
	{
		lootedChar = characterToLoot;
		if(openReq == false)
		{
			if(lootingChar.getRangeFrom(lootedChar.getPosition()) < 40)
			{
				lootedChar = characterToLoot;
				loadLoot();
				openReq = true;
			}
			else
				lootingChar.moveTo(lootedChar.getPosition()[0], lootedChar.getPosition()[1]);
		}
		else
			close();
	}
	/**
	 * Closes and resets loot window
	 */
	public void close()
	{
		openReq = false;
		reset();
	}
	
	public boolean isOpenReq()
	{
		return openReq;
	}

	@Override
	public void inputEnded() 
	{
		
	}

	@Override
	public void inputStarted() 
	{
	}

	@Override
	public boolean isAcceptingInput()
	{
		return true;
	}

	@Override
	public void setInput(Input input)
	{
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
	}

	@Override
	public void mousePressed(int button, int x, int y) 
	{
	}

	@Override
	public void mouseReleased(int button, int x, int y) 
	{
		if(button == Input.MOUSE_LEFT_BUTTON)
		{
			if(takeAll.isMouseOver())
			{
				for(Item lootedItem : lootedChar.getItems())
				{
					lootingChar.addItem(lootedChar.getInventory().takeItem(lootedItem));
				}
				lootingChar.addGold(lootedChar.getInventory().takeGold(lootedChar.getInventory().getGold()));
				close();
			}
			if(takeGold.isMouseOver())
			{
				lootingChar.addGold(lootedChar.getInventory().takeGold(lootedChar.getInventory().getGold()));
				close();
			}
			
			if(next.isMouseOver() && next.isActive())
			{
				if(pageIndex < slotsPages.size())
					pageIndex ++;
			}
			if(back.isMouseOver() && back.isActive())
			{
				if(pageIndex > 0)
					pageIndex --;
			}
		}
	}

	@Override
	public void mouseWheelMoved(int change) 
	{
	}

	@Override
	public void keyPressed(int key, char c) 
	{
		if(key == Input.KEY_ESCAPE)
			close();
	}

	@Override
	public void keyReleased(int key, char c) 
	{
	}
	/**
	 * Generates and returns new slots block
	 * @return New slots block
	 * @throws SlickException
	 * @throws IOException
	 */
	private SlotsBlock newPage() throws SlickException, IOException
	{
		ItemSlot[][] isTab = new ItemSlot[8][6]; 
		for(int i = 0; i < isTab.length; i ++)
		{
			for(int j = 0; j < isTab[i].length; j ++)
			{
				isTab[i][j] = new ItemSlot(gc);
			}
		}
		return new SlotsBlock(isTab);
	}
	/**
	 * Loads all items from looted character to window
	 * @throws SlickException
	 * @throws IOException
	 */
	private void loadLoot() throws SlickException, IOException
	{
		for(Item item : lootedChar.getItems())
		{
			if(slotsPages.get(pageIndex).insertContent(item) == false)
			{
				slotsPages.add(newPage());
				pageIndex ++;
				slotsPages.get(pageIndex).insertContent(item);
			}
		}
		pageIndex = 0;
	}
}
