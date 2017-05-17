package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.data.ItemBase;
import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Attitude;
/**
 * Class for NPC patterns used to create specific NPC by NpcBase class
 * @author Isangeles
 *
 */
public class NpcPattern 
{
	private final String npcId;
	private final Attitude npcAttitude;
	private final int guildID;
	private final String constructorLine;
	private final String headItem;
	private final String chestItem;
	private final String handsItem;
	private final String mainHandItem;
	private final String offHandItem;
	private final String feetItem;
	private final String neckItem;
	private final String fingerAItem;
	private final String fingerBItem;
	private final String artifact;
	private final String spritesheet;
	private final int gold;
	private final List<ItemPattern> invItems;
	/**
	 * NPC pattern constructor
	 * @param npcId NPC id
	 * @param constructorLine String with data for character constructor in form:
	 * [name][level][strength][constitution][dexterity][intelligence][wisdom][portrait(file name in data/portrait dir)]
	 * @param headItem Character head item
	 * @param chestItem Character chest item
	 * @param handsItem Character hands item
	 * @param mainHandItem Character main hand item
	 * @param offHandItem Character off hand item
	 * @param feetItem Character feet item
	 * @param neckItem Character neck item
	 * @param fingerAItem Character finger item
	 * @param fingerBItem Character finger item
	 * @param artifact Character artifact
	 * @param gold Character amount of gold
	 * @param invItems List of all items in character inventory
	 */
	public NpcPattern(String npcId, String attitude, int guildID, String constructorLine, String headItem, String chestItem,
					  String handsItem, String mainHandItem, String offHandItem, String feetItem,
					  String neckItem, String fingerAItem, String fingerBItem, String artifact, String spritesheet,
					  int gold, List<ItemPattern> invItems) 
	{
		this.npcId = npcId;
		switch(attitude)
		{
		case "hostile":
			npcAttitude = Attitude.HOSTILE;
			break;
		case "neutral":
			npcAttitude = Attitude.NEUTRAL;
			break;
		case "friendly":
			npcAttitude = Attitude.FRIENDLY;
			break;
		default:
			npcAttitude = Attitude.NEUTRAL;
		}
		this.guildID = guildID;
		this.constructorLine = constructorLine;
		this.headItem = headItem;
		this.chestItem = chestItem;
		this.handsItem = handsItem;
		this.mainHandItem = mainHandItem;
		this.offHandItem = offHandItem;
		this.feetItem = feetItem;
		this.neckItem = neckItem;
		this.fingerAItem = fingerAItem;
		this.fingerBItem = fingerBItem;
		this.artifact = artifact;
		this.spritesheet = spritesheet;
		this.gold = gold;
		this.invItems = invItems;
		
	}
	/**
	 * Creates new character from all pattern fields
	 * @param gc Slick game container
	 * @return New character object based on this pattern
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws SlickException
	 */
	public Character make(GameContainer gc) throws IOException, FontFormatException, SlickException
	{
		Scanner scann = new Scanner(constructorLine);
		scann.useDelimiter(";");
		Character npc = new Character(npcId, npcAttitude, guildID, scann.next(), scann.nextInt(),
									  new Attributes(scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt()), 
									  scann.next(), spritesheet, gc);
		scann.close();
		Item helmet = ItemBase.getItem(headItem);
		Item chest = ItemBase.getItem(chestItem);
		Item gloves = ItemBase.getItem(handsItem);
		Item mainWeap = ItemBase.getItem(mainHandItem);
		Item offHand = ItemBase.getItem(offHandItem);
		Item boots = ItemBase.getItem(feetItem);
		Item amulet = ItemBase.getItem(neckItem);
		Item ringA = ItemBase.getItem(fingerAItem);
		Item ringB = ItemBase.getItem(fingerBItem);
		Item artifact = ItemBase.getItem(this.artifact);
		
		npc.addItem(helmet);
		npc.addItem(chest);
		npc.addItem(gloves);
		npc.addItem(mainWeap);
		npc.addItem(offHand);
		npc.addItem(boots);
		npc.addItem(amulet);
		npc.addItem(ringA);
		npc.addItem(ringB);
		npc.addItem(artifact);
		
		npc.setArmor(helmet);
		npc.setArmor(chest);
		npc.setArmor(gloves);
		npc.setWeapon(mainWeap);
		npc.setWeapon(offHand);
		npc.setArmor(boots);
		npc.setTrinket(amulet);
		npc.setTrinket(ringA);
		npc.setTrinket(ringB);
		npc.setTrinket(artifact);
		
		npc.addGold(gold);
		for(ItemPattern ip : invItems)
		{
			Item it = ip.make();
			if(it != null)
				npc.addItem(it);
		}
		
		return npc;
	}
}
