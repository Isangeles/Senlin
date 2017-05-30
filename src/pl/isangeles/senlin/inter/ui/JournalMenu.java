package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.quest.Quest;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
/**
 * Class for UI journal menu
 * @author Isangeles
 * 
 */
class JournalMenu extends InterfaceObject implements UiElement 
{
	private Character player;
	private List<QuestField> questFields;
	private List<Quest> quests = new ArrayList<>();
	private TrueTypeFont ttf;
	
	public JournalMenu(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("ui/background/journalBG.png"), "uiJournalMenuBg", false, gc);
		
		this.player = player;
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(12f), true);
		
		questFields = new ArrayList<>();
		for(int i = 0; i < 12; i ++)
		{
			questFields.add(new QuestField(gc));
		}
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		
		int qfFirstX = (int)(x + getDis(30));
		int qfFirstY = (int)(y + getDis(20));
		int column = 0;
		
		for(QuestField field : questFields)
		{
			field.draw(qfFirstX, qfFirstY + ((field.getHeight() * column)), false);
			column ++;
		}
	}

	@Override
	public void update()
	{
		for(Quest quest : player.getQuests())
		{
			if(!quests.contains(quest))
			{
				addQuest(quest);
			}
		}
	}

	@Override
	public void reset() 
	{
		super.moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
	}
	
	private void addQuest(Quest quest)
	{
		quests.add(quest);
	}

	private class QuestField extends Button
	{

		public QuestField(GameContainer gc) throws SlickException, FontFormatException, IOException 
		{
			super(GConnector.getInput("field/textBg.png"), "uiJournalMenuQField", false, "", gc, "");
		}
		
	}
}
