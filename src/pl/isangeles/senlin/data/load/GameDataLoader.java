/*
 * GameDataLoader.java
 * 
 * Copyright 2018 Dariusz Sikora <darek@pc-solus>
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
package pl.isangeles.senlin.data.load;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.RecipesBase;
import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.data.SkillsBase;

/**
 * @author Isangeles
 *
 */
public class GameDataLoader extends Thread 
{
	private final GameContainer container;
	
	public GameDataLoader(GameContainer gc)
	{
		this.container = gc;
	}
	public void run()
	{
        try 
        {
			EffectsBase.load(Module.getSkillsPath(), container);
            SkillsBase.load(Module.getSkillsPath(), container);
            ItemsBase.load(Module.getItemsPath(), container);
			RecipesBase.load(Module.getItemsPath());
            NpcBase.load(Module.getNpcsPath(), container);
	        DialoguesBase.load(Module.getDBasePath());
	        GuildsBase.load(Module.getGuildPath());
            QuestsBase.load(Module.getQuestsPath());
            ObjectsBase.load(Module.getChapterObjectsPath(), container);
            ObjectsBase.load(Module.getModuleObjectsPath(), container);
            ScenariosBase.load(Module.getAreaPath(), container);
		} 
        catch (ParserConfigurationException | SAXException | IOException | SlickException | FontFormatException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
