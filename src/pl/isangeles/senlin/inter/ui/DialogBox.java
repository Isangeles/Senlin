package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.dialogue.Answer;
/**
 * Class for UI dialogue box
 * @author Isangeles
 *
 */
class DialogBox extends InterfaceObject implements UiElement 
{
	private Character interluctorA;
	private Character interluctorB;
	
	private List<String> dialogueBoxContent = new ArrayList<>();
	private List<Answer> dialogueAnswers;
	private List<DialogueOption> options = new ArrayList<>();
	
	private TrueTypeFont ttf;
	
	private boolean openReq;
	/**
	 * Dialogue box constructor
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public DialogBox(GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("ui/background/dialogBoxBG.png"), "uiDialogBoxBg", false, gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(13f), true);

		
		for(int i = 0; i < 5; i ++)
		{
			options.add(new DialogueOption(gc));
		}
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		
		interluctorA.getPortrait().draw(x+getDis(17), y+getDis(16), 85f, 120f, false);
		interluctorB.getPortrait().draw(x+getDis(350), y+getDis(16), 85f, 120f, false);
		
		for(int i = 0; i < dialogueBoxContent.size(); i ++)
		{
			ttf.drawString(x+getDis(260), (y+getDis(300)) - (ttf.getHeight(dialogueBoxContent.get(i))*i), dialogueBoxContent.get(i));
		}
		
		for(int i = 0; i < options.size(); i ++)
		{
			options.get(i).draw(x+getDis(15), (y+getDis(350)) + (i*options.get(i).getHeight()), false);
		}
		
	}
	
	@Override
	public void update() 
	{
		if(interluctorA != null && interluctorB != null)
		{
			addOptions(dialogueAnswers);
		}
	}

	@Override
	public void reset() 
	{
		super.moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
		interluctorA = null;
		interluctorB = null;
		dialogueBoxContent.clear();
		dialogueAnswers.clear();
		clearAnswersBox();
	}
	/**
	 * Opens dialog box
	 * @param interluctorA 
	 * @param interluctorB
	 */
	public void open(Character interluctorA, Character interluctorB)
	{
		this.interluctorA = interluctorA;
		this.interluctorB = interluctorB;

		dialogueBoxContent.add(interluctorB.getDialog().getText());
		dialogueAnswers = interluctorB.getDialog().getAnswers();
		openReq = true;
	}
	/**
	 * Closes dialog box
	 */
	public void close()
	{
		openReq = false;
		reset();
	}
	/**
	 * Checks if dialog box is open
	 * @return True if dialog box is open, false otherwise
	 */
	public boolean isOpenReq()
	{
		return openReq;
	}
	/**
	 * Adds all current dialogue answers to box
	 * @param answers Current dialogue answers
	 */
	private void addOptions(List<Answer> answers)
	{
		for(int i = 0; i < answers.size(); i ++)
		{
			options.get(i).putOption(answers.get(i));
		}
	}
	/**
	 * Sets dialogue stage to specified answer-corresponding stage
	 * @param dialogueOption Answer on previous stage
	 */
	private void nextDialogueStage(Answer dialogueOption)
	{
        interluctorB.getDialog().answerOn(dialogueOption);
        clearAnswersBox();
        dialogueBoxContent.add(interluctorB.getDialog().getText());
        dialogueAnswers = interluctorB.getDialog().getAnswers();
	}
	/**
	 * Clears answer buttons
	 */
	private void clearAnswersBox()
	{
        for(DialogueOption dop : options)
        {
            dop.clear();
        }
	}
	/**
	 * Class for dialogue box options 
	 * @author Isangeles
	 *
	 */
	private class DialogueOption extends Button 
	{
		private Answer option;
		public DialogueOption(GameContainer gc) throws SlickException, FontFormatException, IOException 
		{
			super(GConnector.getInput("ui/background/dialogueOptionBG.png"), "uiDialogueBoxOptionBg", false, "", gc);
		}

		@Override
		public void draw(float x, float y, boolean scaledPos)
		{
			super.draw(x, y, scaledPos);
			if(option != null)
				super.drawString(option.getText(), ttf);
		}
		
		@Override
		public void mouseReleased(int button, int x, int y)
		{
			if(isMouseOver() && button == Input.MOUSE_LEFT_BUTTON)
			{
				if(option != null && option.isEnd())
				{
					interluctorB.getDialog().reset();
					close();
				}
				else if(option != null && !option.isEnd())
				{
				    nextDialogueStage(option);
				}
			}
		}
		
		public void putOption(Answer option)
		{
			this.option = option;
		}
		
		public void clear()
		{
			option = null;
		}
	}
}
