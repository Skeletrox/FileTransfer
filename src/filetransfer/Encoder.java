package filetransfer;
import passgens.RC4Gen;
import java.io.*;
import java.util.Scanner;

public class Encoder {
	DataInputStream di;
	Scanner passScan;
	FileWriter outFile;
	String inName, outName;
	FileOutputStream fos;
	RC4Gen genPass;
	String outKey;
	
	//Getters and Setters
	
	public void setInFileName(String s)
	{
		inName = s;
		setInFile();
	}
	public void setOutFileName(String s)
	{
		outName = s;
		setOutFile();
	}
	
	//Other manufacturers, constructors and stuff
	
	public Encoder()
	{
		passScan = new Scanner(System.in);
		genPass = new RC4Gen();
		System.out.print("Enter password to encode/decode: ");
		genPass.setKey(passScan.nextLine());
		System.out.print("Enter length of output key: ");
		genPass.setOPLength(passScan.nextInt());
		genPass.scheduleKey();
		genPass.generatePseudoRandom(false);
		outKey = genPass.getOutput();		
	}

	public Encoder(String password, int length)
	{
        genPass = new RC4Gen();
		genPass.setKey(password);
		genPass.setOPLength(length);
		genPass.scheduleKey();
		genPass.generatePseudoRandom(false);
		outKey = genPass.getOutput();
	}
	
	public void finalize()
	{
		if (passScan != null)
		{
			passScan.close();
		}
	}
	
	private void setInFile()
	{
		try
		{
			di = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(inName))));
		}
		catch (FileNotFoundException f)
		{
			System.out.println("Said file does not exist. Please restart");
		}
	}
	
	private void setOutFile()
	{
		try
		{
			outFile = new FileWriter (outName);
			fos = new FileOutputStream (outName);
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
	}
	
	public void encode()
	{
		int i = 0;
		try
		{
			while (true)
			{
				byte[] buffer = new byte[8192];
				boolean shouldBreak = false;
				for (i = 0;i<8192;i++)
				{
					try
					{
						buffer[i] = di.readByte();
						buffer[i] ^= outKey.charAt(i%outKey.length());
					}
					catch (EOFException e)
					{
						System.out.println("Finished reading from file");
						shouldBreak = true;
						break;
					}
				}
				fos.write(buffer, 0, i);
				fos.flush();
				if (shouldBreak) {
                    break;
                }

			}
			System.out.println("Encoding done");
			outFile.close();
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
		
	}

}
