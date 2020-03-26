/*
 * Inventory.java
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
package pl.isangeles.senlin.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.isangeles.senlin.core.bonus.UnlockBonus;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Misc;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.gui.SlotContent;

/**
 * Class for character inventory, contains all player items
 *
 * @author Isangeles
 */
public final class Inventory extends LinkedList<Item> implements SaveElement {
  private static final long serialVersionUID = 1L;
  private Targetable owner;
  private Equipment equipment;
  private int gold;
  private InventoryLock lock = new InventoryLock();
  /**
   * Inventory constructor
   *
   * @param character Inventory owner
   */
  public Inventory(Targetable character) {
    equipment = new Equipment();
    owner = character;
  }
  /**
   * Inventory constructor, with lock
   *
   * @param character Inventory owner
   * @param lock Inventory lock
   */
  public Inventory(Targetable character, InventoryLock lock) {
    equipment = new Equipment();
    owner = character;
    this.lock = lock;
  }
  /** Updates inventory (for example bonuses from equipped items) */
  public void update() {
    for (Equippable item : equipment.getAll()) {
      owner.getEffects().addAllFrom(item);
    }
  }
  /**
   * Adds item to inventory
   *
   * @return True if item successfully added, false otherwise
   */
  @Override
  public boolean add(Item item) {
    if (item != null) {
      super.add(item);
      item.setOwner(owner);
      if (Character.class.isInstance(owner)) {
        ((Character) owner).getQTracker().check(item);
      }
      return true;
    } else return false;
  }
  /**
   * Adds all items from collection to inventory
   *
   * @param items Collection with items
   * @return True if all items was added successfully, false if at least one wasn't added
   */
  @Override
  public boolean addAll(Collection<? extends Item> items) {
    boolean isOk = true;
    for (Item item : items) {
      if (!add(item)) isOk = false;
    }

    return isOk;
  }
  /**
   * Adds all items from collection to inventory
   *
   * @param items Collection with items
   * @return True if all items was added successfully, false if at least one wasn't added
   */
  public boolean addAll(Item[] items) {
    boolean isOk = true;
    for (Item item : items) {
      if (!add(item)) isOk = false;
    }

    return isOk;
  }
  /**
   * Removes specified item from inventory
   *
   * @param item Game item
   * @return True if item was successfully removed, false otherwise
   */
  @Override
  public boolean remove(Object item) {
    if (super.remove(item)) {
      if (Equippable.class.isInstance(item)) {
        equipment.unequipp((Equippable) item);
      }
      return true;
    } else return false;
  }
  /**
   * Removes items with specified serial ID from inventory
   *
   * @param serialId Serial ID of item to remove
   * @return True if item was successfully removed, false otherwise
   */
  public boolean remove(String serialId) {
    for (Item item : this) {
      if (item.getSerialId().equals(serialId)) return remove(item);
    }
    return false;
  }
  /**
   * Removes specified amount of items with specified ID from inventory
   *
   * @param serialId ID of items to remove
   * @return True if specified amount of items was successfully removed, false otherwise
   */
  public boolean remove(String id, int amount) {
    List<Item> itemsToRemove = new ArrayList<>();
    for (Item item : this) {
      if (item.getId().equals(id)) itemsToRemove.add(item);

      if (itemsToRemove.size() == amount) break;
    }
    if (itemsToRemove.size() == amount) return removeAll(itemsToRemove);
    else return false;
  }

  @Override
  public boolean removeAll(Collection<?> items) {
    boolean ok = true;
    for (Object item : items) {
      if (this.contains(item)) {
        if (!this.remove(item)) ok = false;
      }
    }
    return ok;
  }
  /**
   * Adds gold to inventory DEPRECATED now currency is represented as in-game items
   *
   * @param value Integer value to add
   * @deprecated
   */
  private void addGold(int value) {
    gold += value;
  }
  /**
   * Returns all gold in inventory DEPRECATED now currency is represented as in-game items
   *
   * @return Amount of gold in integer
   * @deprecated
   */
  private int getGold() {
    return gold;
  }
  /**
   * Returns value of all coins in this inventory
   *
   * @return Value of coins in inventory
   */
  public int getCashValue() {
    int value = 0;
    for (Item item : this) {
      if (Misc.class.isInstance(item)) {
        Misc misc = (Misc) item;
        if (misc.isCurrency()) value += item.getValue();
      }
    }
    return value;
  }
  /**
   * Removes specific item from equippment
   *
   * @param item Equipped character item
   */
  public void unequipp(Equippable item) {
    equipment.unequipp(item);
    owner.getEffects().removeAllFrom(item);
  }
  /**
   * Equips specified item, if item is in character inventory and its equippable
   *
   * @param item Equippable item in character inventory
   * @return True if item was successfully equipped, false otherwise
   */
  public boolean equip(Item item) {
    if (this.contains(item) && Equippable.class.isInstance(item)) {
      Equippable eqItem = (Equippable) item;
      return equipment.equip(eqItem);
    } else return false;
  }
  /**
   * Checks if specified item is equipped
   *
   * @param item Game item
   * @return True if specified item is equipped, false otherwise
   */
  public boolean isEquipped(Item item) {
    if (Equippable.class.isInstance(item)) return equipment.isEquipped((Equippable) item);
    else return false;
  }
  /**
   * Returns weapon damage of equipment items
   *
   * @return Table with minimal[0] and maximal[1] damage
   */
  public int[] getWeaponDamage() {
    int[] damage = {0, 0};
    if (equipment.getMainWeapon() != null) {
      Weapon mainWeapon = equipment.getMainWeapon();
      damage[0] += mainWeapon.getDamage()[0];
      damage[1] += mainWeapon.getDamage()[1];
    }
    if (equipment.getOffHand() != null) {
      Weapon secWeapon = equipment.getOffHand();
      damage[0] += secWeapon.getDamage()[0];
      damage[1] += secWeapon.getDamage()[1];
    }
    return damage;
  }
  /**
   * Returns armor rating of equipped items
   *
   * @return Value of armor rating
   */
  public int getArmorRating() {
    return equipment.getArmorRat();
  }
  /**
   * Return character helmet
   *
   * @return Equipped armor item, type helmet OR null if not equipped
   */
  public Armor getHelmet() {
    return equipment.getHelmet();
  }
  /**
   * Return character chest
   *
   * @return Equipped armor item, type chest OR null if not equipped
   */
  public Armor getChest() {
    return equipment.getChest();
  }
  /**
   * Return character main weapon
   *
   * @return Equipped main weapon or null if no item equipped
   */
  public Weapon getMainWeapon() {
    return equipment.getMainWeapon();
  }
  /**
   * Returns character off hand
   *
   * @return Equipped secondary weapon or null if no item equipped
   */
  public Weapon getOffHand() {
    return equipment.getOffHand();
  }
  /**
   * Returns item which matches to specific id
   *
   * @param itemId Id of requested item
   * @return Item with specific id or error item if item was not fund
   */
  public Item getItem(String itemId) {
    for (int i = 0; i < super.size(); i++) {
      if (super.get(i).getId().equals(itemId)) return super.get(i);
    }
    return null; // ItemsBase.getErrorItem(itemId);
  }
  /**
   * Returns specified amount of items with specified ID
   *
   * @param id Items ID
   * @param amount Amount of items to return
   * @return Collection with specified amount of items with specified ID or empty collection if
   *     specified amount is too big
   */
  public Collection<Item> getItems(String id, int amount) {
    List<Item> items = new ArrayList<>();
    int counter = 0;
    for (Item item : this) {
      if (item.getId().equals(id)) {
        counter++;
        items.add(item);
        if (counter == amount) break;
      }
    }
    if (counter != amount) items.clear();
    return items;
  }
  /**
   * Returns all items with specified ID from inventory
   *
   * @param id String with items ID
   * @return Collection with all items with specified ID from inventory
   */
  public Collection<Item> getItems(String id) {
    List<Item> items = new ArrayList<>();
    for (Item item : this) {
      if (item.getId().equals(id)) items.add(item);
    }
    return items;
  }
  /**
   * Returns all coins in inventory
   *
   * @return Collections with items
   */
  public Collection<Item> getCoins() {
    List<Item> coins = new ArrayList<>();
    for (Item item : this) {
      if (Misc.class.isInstance(item)) {
        Misc misc = (Misc) item;
        if (misc.isCurrency()) coins.add(misc);
      }
    }
    return coins;
  }
  /**
   * Returns item with specific index in inventory container
   *
   * @param index Index in inventory container
   * @return Item from inventory container
   */
  @Override
  public Item get(int index) {
    return super.get(index);
  }
  /**
   * Returns item with specified serial ID
   *
   * @param serialId String with serial ID
   * @return Item with specified serial ID or null if item was not found
   */
  public Item getItemBySerial(String serialId) {
    for (int i = 0; i < super.size(); i++) {
      if (super.get(i).getSerialId().equals(serialId)) return super.get(i);
    }
    return null;
  }
  /**
   * Removes specified item from inventory and returns it
   *
   * @param item Item in inventory to remove
   * @return Removed item or null if inventory does not contain that item
   */
  public Item takeItem(Item item) {
    if (this.remove(item)) return item;
    else return null;
  }
  /**
   * Removes item with specified ID from inventory and returns it
   *
   * @param item Item in inventory to remove
   * @return Removed item or null if inventory does not contain item with such ID
   */
  public Item takeItem(String itemId) {
    Item itemToTake = null;
    for (Item item : this) {
      if (item.getId().equals(itemId)) {
        itemToTake = item;
        break;
      }
    }
    this.remove(itemToTake);
    return itemToTake;
  }
  /**
   * Removes specified amount of gold from inventory DEPRECATED now currency is represented by
   * in-game items
   *
   * @param value Amount of gold to remove
   * @return Removed value or 0 if value is to big to remove
   * @deprecated
   */
  private int takeGold(int value) {
    if (lock.isOpen()) {
      gold -= value;
      if (gold >= 0) return value;
      else return 0;
    } else return 0;
  }
  /**
   * Takes amount of coins with specified value from inventory
   *
   * @param value Value to 'pay' with coins from inventory
   * @return True if value was successfully 'paid', false if there was no enough coins in inventory
   */
  public boolean takeCash(int value) {
    // TODO gold, silver and copper coins be can't retrieved by ID, because e.q. there can be more
    // then one type of gold coin
    Collection<Item> coins = getCoins();
    List<Item> coinsToTake = new ArrayList<>();

    for (Item coin : coins) {
      if (value - coin.getValue() >= 0) {
        value -= coin.getValue();
        coinsToTake.add(coin);
      }
    }

    /*
      	Collection<Item> gold = getItems("gold01");
      	Collection<Item> silver = getItems("sliver01");
      	Collection<Item> copper = getItems("copper01");
      	for(Item coin : gold)
    {
    	if(value - coin.getValue() >= 0)
    	{
    		value -= coin.getValue();
    		coinsToTake.add(coin);
    	}
    }
    for(Item coin : silver)
    {
    	if(value - coin.getValue() >= 0)
    	{
    		value -= coin.getValue();
    		coinsToTake.add(coin);
    	}
    }
    for(Item coin : copper)
    {
    	if(value - coin.getValue() >= 0)
    	{
    		value -= coin.getValue();
    		coinsToTake.add(coin);
    	}
    }
    */
    if (value > 0) return false;
    else return removeAll(coinsToTake);
  }
  /**
   * Returns all items except equipped items
   *
   * @return List with items
   */
  public List<Item> getWithoutEq() {
    List<Item> invWithoutEq = new ArrayList<>();
    invWithoutEq.addAll(this);
    invWithoutEq.removeAll(equipment.getAll());
    return invWithoutEq;
  }
  /**
   * Returns all inventory as list with slot content
   *
   * @return List with slot content
   */
  public List<SlotContent> asSlotsContent() {
    List<SlotContent> slotsContent = new ArrayList<>();
    slotsContent.addAll(this);
    return slotsContent;
  }
  /**
   * Checks if both main and off weapon are equipped
   *
   * @return True if main and off weapon are equipped
   */
  public boolean isDualwield() {
    return equipment.isDualwield();
  }
  /**
   * Checks if inventory is locked
   *
   * @return True if inventory is locked, false otherwise
   */
  public boolean isLocked() {
    return !lock.isOpen();
  }
  /**
   * Opens lock
   *
   * @param character Game character opening lock
   * @return True if lock was successfully opened, false otherwise
   */
  public boolean unlock(Character character) {
    return lock.open(character);
  }
  /**
   * Opens lock with specified skill
   *
   * @param skill Skill
   * @return True if lock was successfully opened, false otherwise
   */
  public boolean unlock(UnlockBonus skill) {
    return lock.open(skill);
  }
  /**
   * Lock inventory with specified lock
   *
   * @param lock Inventory lock
   */
  public void lock(InventoryLock lock) {
    if (lock != null) this.lock = lock;
  }
  /**
   * Parses inventory to XML document element
   *
   * @param doc Document for save game file
   * @return XML document element
   */
  public Element getSave(Document doc) {
    Element eq = equipment.getSave(doc);
    Element in = doc.createElement("in");
    in.setAttribute("gold", gold + "");
    Element itemsE = doc.createElement("items");
    for (Item item : this) {
      Element itemE = doc.createElement("item");
      itemE.setAttribute("serial", item.getNumber() + "");
      itemE.setTextContent(item.getId());
      itemsE.appendChild(itemE);
    }
    in.appendChild(itemsE);
    if (!lock.isOpen()) in.appendChild(lock.getSave(doc));
    eq.appendChild(in);
    return eq;
  }
  /**
   * Parses inventory without equipment to XML document element
   *
   * @param doc Document for save game file
   * @return XML document element
   */
  public Element getSaveWithoutEq(Document doc) {
    Element eq = doc.createElement("eq");
    Element in = doc.createElement("in");
    in.setAttribute("gold", gold + "");
    Element itemsE = doc.createElement("items");
    for (Item item : this) {
      Element itemE = doc.createElement("item");
      itemE.setAttribute("serial", item.getNumber() + "");
      itemE.setTextContent(item.getId());
      itemsE.appendChild(itemE);
    }
    in.appendChild(itemsE);
    if (!lock.isOpen()) in.appendChild(lock.getSave(doc));
    eq.appendChild(in);

    return eq;
  }
}
