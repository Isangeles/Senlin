/*
 * SaveMaker.java
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
package pl.isangeles.senlin.data;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.Character;
/**
 * @author Isangeles
 *
 */
public class SaveMaker 
{
	public static final String SAVES_PATH = "savegames" + File.separator;
	
	public static void saveGame(Character player, String saveName) throws ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element saveE = doc.createElement("save");
		doc.appendChild(saveE);
		saveE.appendChild(player.getSave(doc));
		
		TransformerFactory trf = TransformerFactory.newInstance();
		Transformer tr = trf.newTransformer();
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(new File(SAVES_PATH + saveName + ".ssg"));
		
		tr.transform(doms, sr);
		
		Log.addSystem("Game saved");
	}
}
