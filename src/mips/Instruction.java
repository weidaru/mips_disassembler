package mips;

public abstract class Instruction
{
	protected int machineCode;
	protected int pcAddr;
	protected int opcode;
	protected String mnemonic;
	
	public Instruction(int pcAddr, int code)
	{
		machineCode = code;
		this.pcAddr = pcAddr;
		opcode = (machineCode>>26) & 0x3f;
	}
	
	public static Instruction getInstruction(int pcAddr, int code)
	{
		int opcode = (code>>26) & 0x3f;
		
		if(opcode == 0x0)
			return new RInstruction(pcAddr, code);
		else
		{
			if(opcode==0x2 || opcode==0x3)
				return new JInstruction(pcAddr, code);
			else
				return new IInstruction(pcAddr, code);
		}
	}
	
	static int getZeroExt(int imm)
	{
		return imm & 0xffff;
	}
	
	static int getSignExt(int imm)
	{
		int temp = imm;
		for(int i=1; i<=16; i++)
		{
			imm = ((temp & 0x8000)<<i) | imm;
		}
		return imm;
	}
	
	static int getBranchAddr(int imm)
	{
		int temp = imm;
		imm <<= 2;
		for(int i=1; i<=14; i++)
			imm = ((temp & 0x8000)<<(i+2)) | imm;
		return imm;
	}
	
	int getJumpAddr(int address)
	{
		address <<= 2;
		return address | (pcAddr & 0xf0000000);
	}
	
	public int getMachineCode()
	{
		return machineCode;
	}
	
	public int getOpcode()
	{
		return opcode;
	}
	
	public String getMnemonic()
	{
		return mnemonic;
	}
	
	public int getPCAddress()
	{
		return pcAddr;
	}
	
	public static void main(String[] args)
	{
		Instruction[] array = new Instruction[6];
		array[0] = getInstruction(0x040034, 0x2aa80015);
		array[1] = getInstruction(0x040038, 0x0240c808);
		array[2] = getInstruction(0x04003c, 0x0c01000d);
		array[3] = getInstruction(0x040040, 0x21080003);
		array[4] = getInstruction(0x040044, 0x00884824);
		array[5] = getInstruction(0, 4333602);
		
		for(Instruction element: array)
		{
			System.out.println(element.getPCAddress()+": "+element);
//			System.out.println(element.getMachineCode());
		}
	}
}
