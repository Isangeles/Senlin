/*
 * RequirementsParser.java
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
package pl.isangeles.senlin.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.req.GoldRequirement;
import pl.isangeles.senlin.core.req.PointsRequirement;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.StatsRequirement;

/**
 * Class for parsing requirements nodes
 * @author Isangeles
 *
 */
public final class RequirementsParser 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private RequirementsParser() {}
	/**
	 * Parses specified node to list with requirements
	 * @param reqsNode XML document node
	 * @return List with requirements objects
	 */
	public static List<Requirement> getReqFromNode(Node reqsNode)
	{
		List<Requirement> reqs = new ArrayList<>();
		if(reqsNode == null)
			return reqs;
		
		NodeList reqsList = reqsNode.getChildNodes();
		for(int i = 0; i < reqsList.getLength(); i ++)
		{
			Node reqNode = reqsList.item(i);
			if(reqNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					Element reqE = (Element)reqNode;
					switch(reqE.getNodeName())
					{
					case "goldReq":
						int gAmount = Integer.parseInt(reqE.getTextContent());
						reqs.add(new GoldRequirement(gAmount));
						break;
					case "statsReq":
						Attributes stats = new Attributes(reqE.getTextContent());
						reqs.add(new StatsRequirement(stats));
						break;
					case "pointsReq":
						int pAmount = Integer.parseInt(reqE.getTextContent());
						reqs.add(new PointsRequirement(pAmount));
						break;
					}
				}
				catch(NumberFormatException e)
				{
					Log.addSystem("req_builder_fail_msg///some base node corrupted");
					continue;
				}
			}
		}
		
		return reqs;
	}
}
