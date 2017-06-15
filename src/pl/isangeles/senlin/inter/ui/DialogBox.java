/*
 * DialogBox.java
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
import pl.isangeles.senlin.quest.Quest;
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
	
	private MessageBox textBox;

	private List<Answer> dialogueAnswers;
	private List<DialogueOption> options = new ArrayList<>();
	
	private TrueTypeFont ttf;
	
	private boolean openReq;
	private boolean tradeReq;
	private boolean trainReq;
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

		textBox = new MessageBox(gc);
		
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
		
		textBox.draw(x+getDis(15), y+getDis(160), 410f, 160f, true);
		
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

			textBox.addRight(new TextBlock(interlocutorB.getDialogueFor(interlocutorA).getText(), 20, ttf));
			dialogueAnswers = interlocutorB.getDialogueFor(interlocutorA).getAnswers();
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
	
	public void tradeReq(boolean tradeReq)
	{
		this.tradeReq = tradeReq;
	}
	
	public void trainReq(boolean train)
	{
		trainReq = train;
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
	 * Checks if trade is requested
	 * @return
	 */
	public boolean isTradeReq()
	{
		return tradeReq;
	}
	/**
	 * Checks if training is requested
	 * @return
	 */
	public boolean isTrainReq()
	{
		return trainReq;
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
        interlocutorB.getDialogueFor(interlocutorA).answerOn(dialogueOption);
        clearAnswersBox();
        textBox.addRight(new TextBlock(interlocutorB.getDialogueFor(interlocutorA).getText(), 20, ttf));
        dialogueAnswers = interlocutorB.getDialogueFor(interlocutorA).getAnswers();
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
				if(option != null)
				{
					Quest answerQ = option.getQuest();
					if(answerQ != null)
						interlocutorA.startQuest(answerQ);
					
					interlocutorA.getQTracker().check(option);
					
					if(option.getId().equals("tradeReq"))
						tradeReq = true;
					if(option.getId().equals("trainReq"))
						trainReq = true;
					
					if(option.isEnd())
					{
						interlocutorB.getDialogueFor(interlocutorA).reset();
						close();
					}
					else if(!option.isEnd())
					{
			            textBox.addLeft(new TextBlock(option.getText(), 20, ttf));
					    nextDialogueStage(option);
					}
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
	/**
	 * Class for 'messaging style' text box
	 * @author Isangeles
	 *
	 */
	private class MessageBox extends TextBox
	{
		private List<TextBlock> textsRight = new ArrayList<>();
		private List<TextBlock> textsLeft = new ArrayList<>();
		
		public MessageBox(GameContainer gc) throws SlickException, IOException, FontFormatException
		{
			super(gc);
		}
		
		@Override
		public void draw(float x, float y, float width, float height, boolean scaledPos)
		{
			super.drawWithoutText(x, y, width, height, scaledPos);
		       
		    for(int i = 0; i < visibleTexts.size(); i ++)
		    {
	           TextBlock text = visibleTexts.get(i);
	           if(i == 0)
	           {
	               if(textsLeft.contains(text))
	            	   text.draw(super.x, (super.y + super.getScaledHeight() - getDis(25)) - text.getTextHeight());
	               if(textsRight.contains(text))
	            	   text.draw(super.getTR().x - text.getTextWidth(), (super.y + super.getScaledHeight() - getDis(25)) - text.getTextHeight());
	           }
	           else if(i > 0)
	           {
	               TextBlock prevText = visibleTexts.get(i-1);
	               if(textsLeft.contains(text))
	            	   text.draw(super.x, (prevText.getPosition().y - getDis(10)) - text.getTextHeight());
	               if(textsRight.contains(text))
	            	   text.draw(super.getTR().x - text.getTextWidth(), (prevText.getPosition().y - getDis(10)) - text.getTextHeight());
	           }
	       }
			
		}
		/**
		 * Adds text on right side of box
		 * @param text Block of text
		 */
		public void addRight(TextBlock text)
		{
			super.add(text);
			textsRight.add(text);
		}
		/**
		 * Adds text on left side of box
		 * @param text Block of text
		 */
		public void addLeft(TextBlock text)
		{
			super.add(text);
			textsLeft.add(text);
		}
		/**
		 * Removes all text from box
		 */
		@Override
		public void clear()
		{
			super.clear();
			textsRight.clear();
			textsLeft.clear();
		}
	}
}
