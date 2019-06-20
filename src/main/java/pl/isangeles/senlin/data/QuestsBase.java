/*
 * QuestsBase.java
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

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.quest.Quest;
import pl.isangeles.senlin.util.DConnector;
/**
 * Static base class for game quests 
 * @author Isangeles
 *
 */
public class QuestsBase
{
    private static Map<String, Quest> questsMap;
    /**
     * Private constructor to prevent initialization
     */
    private QuestsBase(){}
    /**
     * Loads base
     * @param questsBaseName Quests base file name in data/quests 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static void load(String basePath) throws ParserConfigurationException, SAXException, IOException
    {
        questsMap = DConnector.getQuests(basePath);
    }
    /**
     * Returns quest with specified ID from base
     * @param id Desire quest ID
     * @return Quest with specified ID
     */
    public static Quest get(String id)
    {
        return questsMap.get(id);
    }
    
}
