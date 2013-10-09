package mips;

import java.util.TreeMap;

public class IInstruction extends Instruction 
{
	private Register rs;
	private Register rt;
	private int immediate;
	
	public static TreeMap<Integer, String> omMap = null;
	static
	{
		omMap = new TreeMap<Integer, String>();
		omMap.put(0x8, "addi");
		omMap.put(0x9, "addiu");
		omMap.put(0xc, "andi");
		omMap.put(0x4, "beq");
		omMap.put(0x5, "bne");
		omMap.put(0x24, "lbu");
		omMap.put(0x25, "lhu");
		omMap.put(0x30, "ll");
		omMap.put(0xf, "lui");
		omMap.put(0x23, "lw");
		omMap.put(0xd, "ori");
		omMap.put(0xa, "slti");
		omMap.put(0xb, "sltiu");
		omMap.put(0x28, "sb");
		omMap.put(0x38, "sc");
		omMap.put(0x29, "sh");
		omMap.put(0x2b, "sw");
	}
	
	
	public IInstruction(int pcAddr, int code) 
	{
		super(pcAddr, code);
		
		rs = Register.registerMap.get((machineCode>>21) & 0x1f);
		rt = Register.registerMap.get((machineCode>>16) & 0x1f);
		immediate = machineCode & 0xffff;
		mnemonic = omMap.get(opcode);
	}
	
	public String toString()
	{
		switch(opcode)
		{
		case 0x8:
		case 0xa: return mnemonic+" "+rt.name+", "+rs.name+", "+getSignExt(immediate);
		case 0xc: return mnemonic+" "+rt.name+", "+rs.name+", "+getSignExt(immediate);
		case 0x4:
		case 0x5: return mnemonic+" "+rs.name+", "+rt.name+", "+"0x"+
					Integer.toHexString((pcAddr + 0x4 + getBranchAddr(immediate)));
		case 0x24:
		case 0x25:
		case 0x30:
		case 0x23: 
		case 0x28:
		case 0x38:
		case 0x29:
		case 0x2b: return mnemonic+" "+rt.name+", "+getSignExt(immediate)+"("+rs.name+")";
		case 0xf: return mnemonic+" "+rt.name+", "+getSignExt(immediate);
		case 0x9: 
		case 0xb: 
		case 0xd: return mnemonic+" "+rt.name+", "+rs.name+", "+getZeroExt(immediate);
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
	
	public int getImmediate()
	{
		return immediate;
	}
}
