package pl.isangeles.senlin.data.pattern;

import java.util.Random;

import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.data.ItemBase;
import pl.isangeles.senlin.data.Log;
/**
 * Class for items patterns
 * @author Isangeles
 *
 */
public class ItemPattern
{
	private String id;
	private boolean random;
	private Random gen = new Random();
	/**
	 * Item pattern constructor
	 * @param id Item id
	 * @param random True if item spawn should be random, false otherwise
	 */
	public ItemPattern(String id, boolean random)
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
				return ItemBase.getItem(id);
			else
				return null;
		}
		else
			return ItemBase.getItem(id);
	}
}
