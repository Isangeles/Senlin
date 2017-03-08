package pl.isangeles.senlin.core;

public class Atributes 
{
	private Atribute strenght;
	private Atribute constitution;
	private Atribute dexterity;
	private Atribute intelligence;
	private Atribute wisdom;
	
	public Atributes(int str, int con, int dex, int inte, int wis)
	{
		strenght = new Atribute(str);
		constitution = new Atribute(con);
		dexterity = new Atribute(dex);
		intelligence = new Atribute(inte);
		wisdom = new Atribute(wis);
	}
	public int addHealth()
	{
		return constitution.value * 10 + strenght.value;
	}
	public int addMagicka()
	{
		return intelligence.value * 10 + wisdom.value;
	}
	public float addHaste()
	{
		return 4 - ((constitution.value/2) + dexterity.value)/2;
	}
	
	public int getBasicHit()
	{
		return strenght.value *2 + constitution.value;
	}
	
	public int getBasicSpell()
	{
		return intelligence.value *2 + wisdom.value;
	}
	
	public int getStr()
	{ return strenght.value; }
	
	public int getCon()
	{ return constitution.value; }
	
	public int getDex()
	{ return dexterity.value; }
	
	public int getInt()
	{ return intelligence.value; }
	
	public int getWis()
	{ return wisdom.value; }
	
	public void addStr()
	{ strenght.value ++; }
	
	public void addStr(int value)
	{ strenght.value += value; }
	
	public void addCon()
	{ constitution.value ++; }
	
	public void addCon(int value)
	{ constitution.value += value; }
	
	public void addDex()
	{ dexterity.value ++; }
	
	public void addDex(int value) 
	{ dexterity.value += value; }
	
	public void addInt()
	{ intelligence.value ++; }
	
	public void addInt(int value) 
	{ intelligence.value += value; }
	
	public void addWis()
	{ wisdom.value ++; }
	
	public void addWis(int value) 
	{ wisdom.value += value; }
}
