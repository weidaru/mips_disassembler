package mips;

import java.util.TreeMap;

public class Register
{
	public final String name;
	public final int number;
	private int value;
	
	public static TreeMap<Integer, Register> registerMap = null;
	static
	{
		registerMap = new TreeMap<Integer, Register>();
		registerMap.put(0, new Register("$zero", 0));
		registerMap.put(1, new Register("$at", 1));
		registerMap.put(2, new Register("$v0", 2));
		registerMap.put(3, new Register("$v1", 3));
		registerMap.put(4, new Register("$a0", 4));
		registerMap.put(5, new Register("$a1", 5));
		registerMap.put(6, new Register("$a2", 6));
		registerMap.put(7, new Register("$a3", 7));
		for(int i=8; i<=15; i++)
			registerMap.put(i, new Register("$t" + (i-8), i));
		for(int i=16; i<=23; i++)
			registerMap.put(i, new Register("$s" + (i-16), i));
		registerMap.put(24, new Register("$t8", 24));
		registerMap.put(25, new Register("$t9", 25));
		registerMap.put(26, new Register("$k0", 26));
		registerMap.put(27, new Register("$k1", 27));
		registerMap.put(28, new Register("$gp", 28));
		registerMap.put(29, new Register("$sp", 29));
		registerMap.put(30, new Register("$fp", 30));
		registerMap.put(31, new Register("$ra", 31));
	}
	
	Register(String name, int number)
	{
		this.name = name;
		this.number =  number;
	}
	
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public boolean equals(Object obj)
	{
		Register objRegister = (Register)obj;
		
		if(this.name == objRegister.name && this.number == objRegister.number)
			return true;
		else
			return false;
	}
}
