package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.TextBlock;
import pl.isangeles.senlin.inter.TextBox;
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
	private Character interlocutorA;
	private Character interlocutorB;
	
	private TextBox textBox;

	private List<TextBlock> dialogueBoxContent = new ArrayList<>();
	private List<String> dialogueBoxTextA = new ArrayList<>();
	private List<String> dialogueBoxTextB = new ArrayList<>();
	private List<Answer> dialogueAnswers;
	private List<DialogueOption> options = new ArrayList<>();
	
	private TrueTypeFont ttf;
	
	private boolean openReq;
	
	private GameContainer gc;
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

		textBox = new TextBox(gc);
		
		for(int i = 0; i < 5; i ++)
		{
			options.add(new DialogueOption(gc));
		}
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		
		interlocutorA.getPortrait().draw(x+getDis(17), y+getDis(16), 85f, 120f, false);
		interlocutorB.getPortrait().draw(x+getDis(350), y+getDis(16), 85f, 120f, false);
		ttf.drawString(x+getDis(115), y+getDis(20), interlocutorA.getName());
		ttf.drawString(x+getDis(265), y+getDis(20), interlocutorB.getName());
		
		for(int i = 0; i < dialogueBoxContent.size(); i ++)
		{
			if(dialogueBoxTextB.contains(dialogueBoxContent.get(i).getText()))
				dialogueBoxContent.get((dialogueBoxContent.size()-1)-i).draw(x+getDis(260), (y+getDis(300)) - (dialogueBoxContent.get(i).getTextHeight()*i));
			if(dialogueBoxTextA.contains(dialogueBoxContent.get(i).getText()))
				dialogueBoxContent.get((dialogueBoxContent.size()-1)-i).draw(x+getDis(15), (y+getDis(300)) - (dialogueBoxContent.get(i).getTextHeight()*i));
		}
		
		//textBox.draw(x+getDis(15), y+getDis(160), 400f, 150f, true);
		
		for(int i = 0; i < options.size(); i ++)
		{
			options.get(i).draw(x+getDis(15), (y+getDis(360)) + (i*options.get(i).getHeight()), false);
		}
		
	}
	
	@Override
	public void update() 
	{
	}

	@Override
	public void reset() 
	{
		super.moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
		interlocutorA = null;
		interlocutorB = null;
		dialogueBoxContent.clear();
		dialogueBoxTextA.clear();
		dialogueBoxTextB.clear();
		textBox.clear();
		clearAnswersBox();
	}
	/**
	 * Opens dialogue box if 
	 * @param interlocutorA 
	 * @param interlocutorB
	 */
	public void open(Character interlocutorA, Character interlocutorB)
	{
		if(interlocutorA.getRangeFrom(interlocutorB.getPosition()) > 40)
		{
			interlocutorA.moveTo(interlocutorB);
		}
		else
		{
			this.interlocutorA = interlocutorA;
			this.interlocutorB = interlocutorB;

			dialogueBoxTextB.add(interlocutorB.getDialog().getText());
			dialogueBoxContent.add(new TextBlock(interlocutorB.getDialog().getText(), 20, ttf));
			textBox.add(new TextBlock(interlocutorB.getDialog().getText(), 20, ttf));
			dialogueAnswers = interlocutorB.getDialog().getAnswers();
			addOptions(dialogueAnswers);
			openReq = true;
		}
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
        interlocutorB.getDialog().answerOn(dialogueOption);
        clearAnswersBox();
        dialogueBoxTextB.add(interlocutorB.getDialog().getText());
		dialogueBoxContent.add(new TextBlock(interlocutorB.getDialog().getText(), 20, ttf));
        textBox.add(new TextBlock(interlocutorB.getDialog().getText(), 20, ttf));
        dialogueAnswers = interlocutorB.getDialog().getAnswers();
        addOptions(dialogueAnswers);
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
	 * Class for dialogue box text options 
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
					interlocutorB.getDialog().reset();
					close();
				}
				else if(option != null && !option.isEnd())
				{
					dialogueBoxTextA.add(option.getText());
					dialogueBoxContent.add(new TextBlock(option.getText(), 20, ttf));
		            textBox.add(new TextBlock(option.getText(), 20, ttf));
				    nextDialogueStage(option);
				}
			}
		}
		/**
		 * Puts specified answer to option slot
		 * @param option Some answer connected to current dialogue stage
		 */
		public void putOption(Answer option)
		{
			this.option = option;
		}
		/**
		 * Clears dialogue option slot
		 */
		public void clear()
		{
			option = null;
		}
	}
}
