package mips;

import java.util.TreeMap;

public class RInstruction extends Instruction 
{
	private Register rs;
	private Register rt;
	private Register rd;
	private int shamt;
	private int funct;
	
	public static TreeMap<Integer, String> fmMap = null;
	static 
	{
		fmMap = new TreeMap<Integer, String>();
		fmMap.put(0x20, "add");
		fmMap.put(0x21, "addu");
		fmMap.put(0x24, "and");
		fmMap.put(0x8, "jr");
		fmMap.put(0x27, "nor");
		fmMap.put(0x25, "or");
		fmMap.put(0x2a, "slt");
		fmMap.put(0x2b, "sltu");
		fmMap.put(0x0, "sll");
		fmMap.put(0x2, "srt");
		fmMap.put(0x22, "sub");
		fmMap.put(0x23, "subu");
	}
	
	public RInstruction(int pcAddr, int code) 
	{
		super(pcAddr, code);
		
		rs = Register.registerMap.get((machineCode>>21) & 0x1f);
		rt = Register.registerMap.get((machineCode>>16) & 0x1f);
		rd = Register.registerMap.get((machineCode>>11) & 0x1f);
		shamt = (machineCode>>6) & 0x1f;
		funct = machineCode & 0x3f;
		mnemonic = fmMap.get(funct);
	}

	public String toString()
	{
		switch(funct)
		{
		case 0x20:
		case 0x21:
		case 0x24:
		case 0x27:
		case 0x25:
		case 0x2a:
		case 0x2b:
		case 0x22:
		case 0x23: return mnemonic+" "+rd.name+", "+rs.name+", "+rt.name;
		case 0x08: return mnemonic+" "+rs.name;
		case 0x00:
		case 0x02: return  mnemonic+" "+rd.name+", "+rt.name+", "+Integer.toHexString(shamt);
		default: return null;
		}
	}
	
	
	
	public Register getRs()
	{
		return rs;
	}
	
	public Register getRt()
	{
		return rt;
	}
	
	public Register getRd()
	{
		return rd;
	}
	
	public int getShamt()
	{
		return shamt;
	}
	
	public int getFunct()
	{
		return funct;
	}
}
