/*
 * ItemsRequirement.java
 *
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
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
package pl.isangeles.senlin.core.req;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for items requirements
 *
 * @author Isangeles
 */
public class ItemsRequirement extends Requirement {
  private final Map<String, Integer> reqItems;
  private final List<Item> itemsToRemove = new ArrayList<>();
  /**
   * Items requirement constructor (for one type of items)
   *
   * @param reqItems Map with required items IDs as keys and amount of thats items as values
   */
  public ItemsRequirement(String id, int amount) {
    super(RequirementType.ITEMS, "");
    this.reqItems = new HashMap<>();
    reqItems.put(id, amount);

    info = TConnector.getText("ui", "reqItems") + ": ";
    for (String itemId : reqItems.keySet()) {
      String itemName = TConnector.getInfoFromModule("items", itemId)[0];
      String itemAmount = "" + reqItems.get(itemId);
      info += System.lineSeparator() + itemName + " x" + itemAmount;
    }
  }
  /**
   * Items requirement constructor (for multiple types of items)
   *
   * @param reqItems Map with required items IDs as keys and amount of thats items as values
   */
  public ItemsRequirement(Map<String, Integer> reqItems) {
    super(RequirementType.ITEMS, "");
    this.reqItems = reqItems;

    info = TConnector.getText("ui", "reqItems") + ": ";
    for (String itemId : reqItems.keySet()) {
      String itemName = TConnector.getInfoFromModule("items", itemId)[0];
      String itemAmount = "" + reqItems.get(itemId);
      info += System.lineSeparator() + itemName + " x" + itemAmount;
    }
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.req.Requirement#isMeet(pl.isangeles.senlin.core.Character)
   */
  @Override
  public boolean isMetBy(Character character) {
    Map<String, Integer> countMap = new HashMap<>();
    for (String reqItId : reqItems.keySet()) {
      countMap.put(reqItId, 0);
    }

    for (Item item : character.getInventory()) {
      String iId = item.getId();
      if (countMap.containsKey(iId) && countMap.get(iId) < reqItems.get(iId)) {
        int amount = countMap.get(item.getId());
        countMap.put(item.getId(), amount + 1);
        itemsToRemove.add(item);
      }
    }

    if (countMap.equals(reqItems)) {
      met = true;
      // character.getInventory().removeAll(itemsToRemove); //separate charge() method to remove
      // required items
      return true;
    } else {
      /*
      for(String i : countMap.keySet())
      {
      	if(reqItems.containsKey(i))
      	{
      		if(countMap.get(i) < (reqItems.get(i)))
      			return false;
      	}
      	else
      		return false;
      }
      met = true;
      return true;
      */
      return false;
    }
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.req.Requirement#charge(pl.isangeles.senlin.core.Character)
   */
  @Override
  public void charge(Character character) {
    if (met) {
      character.getInventory().removeAll(itemsToRemove);
      itemsToRemove.clear();
      met = false;
    }
  }
  /**
   * Returns map with all required items IDs as keys and required amount as value
   *
   * @return Map with items IDs as keys and required amount as value
   */
  public Map<String, Integer> getReqItems() {
    Map<String, Integer> items = new HashMap<>();
    items.putAll(reqItems);
    return items;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.data.SaveElement#getSave(org.w3c.dom.Document)
   */
  @Override
  public Element getSave(Document doc) {
    Element itemReq = doc.createElement("itemReq");
    for (String id : reqItems.keySet()) {
      for (int i = 0; i < reqItems.get(id); i++) {
        Element itemE = doc.createElement("item");
        itemE.setTextContent(id);
        itemReq.appendChild(itemE);
      }
    }
    return itemReq;
  }
}
