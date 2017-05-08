/**
 * 
 */


package filetransfer;
import java.io.*;
import java.net.*;

public class TranServer {
	
	FileWriter outFile;
	FileOutputStream fos;
	ServerSocket socket;
	InputStreamReader IPStream;
	BufferedReader reader;
	String clientIP;
	int port;
	
	//getters and setters
	
	public void setPort(int x)
	{
		port = x;
	}
	
	public void setClientIP (String IP)
	{
		clientIP = IP;
	}
	
	public void setOutFile (String s)
	{
		try
		{
			outFile = new FileWriter(s);
			fos = new FileOutputStream(new File(s));
		}
		catch (IOException e)
		{
			System.out.println("An exception occurred");
			e.printStackTrace();
		}
	}
	
	public void makeConnection()
	{
		try
		{
			socket = new ServerSocket (port);
			System.out.println("Listening...");
		}
		catch (Exception e)
		{
			System.out.println("An exception was thrown");
			e.printStackTrace();
		}
	}
	
	public void getClientIP()
	{
		InetAddress i = socket.getInetAddress();
		System.out.println(i);
	}
	
	public void getData()
	{
		Socket sock;
		byte[] buffer = new byte[8192], IPData;	//8 kB buffer
		int length;
		try
		{
			sock = socket.accept();
			System.out.println("Accepted connection");
			while ((length = sock.getInputStream().read(buffer)) > 0)
			{
				
				IPData = new byte[length];
				System.arraycopy(buffer, 0, IPData, 0, length);
				System.out.println("Length of accepted data in bytes = " + IPData.length);
				/*
				 * 
				StringBuilder inStringProt = new StringBuilder();
				char c;
				String inString;
				for (int i = 0;i<IPData.length;i++)
				{
					c = (char) (IPData[i++] * 256);
					if (IPData[i] < 0)
					{
						c += (256 + IPData[i]);
					}
					else
					{
						c += IPData[i]; 
					}
					inStringProt.append(c);
				}
				inString = inStringProt.toString();
				outFile.write(inString);*/
				fos.write(IPData);
				//fos.flush();
			}
			System.out.println("Received data");
		}
		catch (IOException io)
		{
			System.out.println("OMG");
			io.printStackTrace();
		}
				
	}
	
	public void finalize()
	{
		try
		{
			outFile.close();
			fos.close();
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}
}
