package mips;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class MIPSDisassember
{
	private File inputFile;
	private File outputFile;
	private FileInputStream in;
	private PrintWriter out;
	private int pcAddr;
	
	public MIPSDisassember(File inputFile, File outputFile)
	{
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}
	
	public void reset(File inputFile, File outputFile)
	{
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}
	
	
	public void disassemble() throws IOException
	{

		in = new FileInputStream(inputFile);
		out = new PrintWriter(outputFile);

		int code;
		byte[] pool = new byte[4];
		Instruction temp;
		pcAddr = 0;
		
		while(in.read(pool) != -1)
		{
			code = 0;
			
			code = ((int)pool[0]&0xff)<<24;
			code = ((int)pool[1]&0xff)<<16 | code;
			code = ((int)pool[2]&0xff)<<8 | code;
			code = ((int)pool[3]&0xff) | code;
			
			temp = Instruction.getInstruction(pcAddr, code);
			pcAddr += 4;
			out.printf("%#010x: %s", temp.getPCAddress(), temp.toString());
			out.println();
		}
		in.close();
		out.close();
	}
	
	public static File generateDefaultInputFile()
	{
		File file = null;
		try {
			file = new File("Default.bin");
			FileOutputStream out = new FileOutputStream(file);
			
			for(int i=3; i>=0; i--)
				out.write(0x2aa80015>>>(i*8));
			for(int i=3; i>=0; i--)
				out.write(0x0240c808>>>(i*8));
			for(int i=3; i>=0; i--)
				out.write(0x0c01000d>>>(i*8));
			for(int i=3; i>=0; i--)
				out.write(0x21080003>>>(i*8));
			for(int i=3; i>=0; i--)
				out.write(0x00884824>>>(i*8));
			for(int i=3; i>=0; i--)
				out.write(0x1131fffe>>>(i*8));
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	public File getInputFile() { return inputFile; }
	public File getOutputFile() { return outputFile; }
	
	
	
	public static void main(String[] args)
	{
		File inputFile = null;
		File outputFile = null;
		JFileChooser fileChooser = new JFileChooser();
		
		int choice = JOptionPane.showConfirmDialog(null, 
				"Do you want to choose the input file? If not, Default.bin will be generated and used.",
				null, 
				JOptionPane.YES_NO_OPTION);
		if(choice == JOptionPane.NO_OPTION)
			inputFile = generateDefaultInputFile();
		else
		{
			fileChooser.setFileFilter(new FileFilter() 
			{
				@Override
				public String getDescription() 
				{
					return ".bin";
				}
				
				@Override
				public boolean accept(File pathname) 
				{
					String temp = pathname.getName().toLowerCase();
					if(temp.endsWith(".bin"))
						return true;
					else
						return false;
				}
			});
			
			fileChooser.setDialogTitle("Choose inputfile");
			choice = fileChooser.showOpenDialog(null);
			if(choice == JFileChooser.APPROVE_OPTION)
				inputFile = fileChooser.getSelectedFile();
			else
			{
				if(choice == JFileChooser.CANCEL_OPTION)
					return;
				else
				{
					JOptionPane.showMessageDialog(null, "Error");
					return;
				}
			}
		}


		fileChooser.setFileFilter(new FileFilter() 
		{
			@Override
			public String getDescription() 
			{
				return ".txt";
			}
			
			@Override
			public boolean accept(File pathname) 
			{
				String temp = pathname.getName().toLowerCase();
				if(temp.endsWith(".txt"))
					return true;
				else
					return false;
			}
		});
		fileChooser.setDialogTitle("Choose outputfile");
		choice = fileChooser.showSaveDialog(null);
		if(choice == JFileChooser.APPROVE_OPTION)
			outputFile = fileChooser.getSelectedFile();
		else
		{
			if(choice == JFileChooser.CANCEL_OPTION)
				return;
			else
			{
				JOptionPane.showMessageDialog(null, "Error");
				return;
			}
		}

		
		MIPSDisassember disassembler = new MIPSDisassember(inputFile, outputFile);
		try 
		{
			disassembler.disassemble();
		} catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "Error during disassembling");
		}
	}
}
