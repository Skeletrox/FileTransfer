package filetransfer;
import java.io.*;
import java.net.*;

public class TranClient {
	FileReader inFile;
	File inFileF;
	Socket socket;
	String serverIP;
	int serverPort;
	FileInputStream in;
	OutputStream out;
	DataInputStream di;
	float percentDone = 0.0f;
	
	//getters and setters
	
	public void setServerIP(String s)
	{
		serverIP = s;
	}
	
	public void setServerPort(int i)
	{
		serverPort = i;
	}
	
	public void setFile(String s) throws FileNotFoundException
	{
		inFile = new FileReader (s);
		inFileF = new File(s);
		
	}
	
	public void makeConnection() throws IOException
	{
		socket = new Socket(serverIP, serverPort);
		System.out.println("Establishing connection to server...");
	}

	public float getBytesSent()
	{
		return percentDone;
	}
	public void sendData() throws IOException
	{
		out = socket.getOutputStream();
		
		di = new DataInputStream(new BufferedInputStream(new FileInputStream(inFileF)));
		int i = 0, totSent = 0;
		while (true)
		{
			byte[] buffer = new byte[8192];
			boolean shouldBreak = false;
			for (i = 0;i<8192;i++)
			{
				try
				{
					buffer[i] = di.readByte();
				}
				catch (EOFException e)
				{
					System.out.println("Finished reading from file");
					shouldBreak = true;
					break;
				}
			}
			out.write(buffer, 0, i);
			out.flush();
			totSent += i;
			percentDone = (float) totSent / (float) inFileF.length();
			if (shouldBreak)
			{
				break;
			}
		}
		/*
		 * -----------------------------
		 * Code for text file IO
		 * -----------------------------
		 * 
		int length;
		char[] charBuffer = new char[8192];
		while ((length = inFile.read(charBuffer)) > 0)
		{
			byte[] buffer = new byte[length*2];
			System.out.println("Length of char buffer = " + length);
			for (int i = 0, j = 0;i<length;i++)
			{
				//Split char into two halves in order to force it to retain data
				 
				buffer[j++] = (byte) (charBuffer[i] / 256);
				buffer[j++] = (byte) (charBuffer[i] % 256);
			}
			System.out.println("Length of byte buffer = " + buffer.length);
			out.write(buffer, 0, buffer.length);
		}
		*
		*/
		socket.close();
	}
	
	public void finalize()
	{
		try
		{
			di.close();
			out.close();
			inFile.close();
			socket.close();
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}
	
}
