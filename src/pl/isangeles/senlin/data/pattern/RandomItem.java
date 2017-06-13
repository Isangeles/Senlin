package pl.isangeles.senlin.data.pattern;

import java.util.Random;

import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.Log;
/**
 * Class for random items(for NPCs loot)
 * @author Isangeles
 *
 */
public class RandomItem
{
	private String id;
	private boolean random;
	private Random gen = new Random();
	/**
	 * Random item constructor
	 * @param id Item id
	 * @param random True if item spawn should be random, false otherwise
	 */
	public RandomItem(String id, boolean random)
	{
		this.id = id;
		this.random = random;
	}
	/**
	 * Creates new item, if pattern is random can return null
	 * @return Item or null(if pattern is random)
	 */
	public Item make()
	{
		if(random)
		{
			if(gen.nextBoolean())
				return ItemsBase.getItem(id);
			else
				return null;
		}
		else
			return ItemsBase.getItem(id);
	}
}
