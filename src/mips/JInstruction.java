package mips;

public class JInstruction extends Instruction 
{
	private int address;
	
	public JInstruction(int pcAddr, int code) 
	{
		super(pcAddr, code);
		address = machineCode & 0x3ffffff;
		if(opcode == 0x2)
			mnemonic = "j";
		else
		{
			if(opcode == 0x3)
				mnemonic = "jal";
			else
				mnemonic = null;
		}
	}

	public String toString()
	{
		if(mnemonic != null)
			return mnemonic+" "+"0x"+Integer.toHexString(getJumpAddr(address));
		else
			return null;
	}
	
	public int getAddress()
	{
		return address;
	}
}
