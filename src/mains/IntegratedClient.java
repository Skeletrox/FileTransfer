package mains;
import filetransfer.*;
import java.util.Scanner;
import java.io.*;

public class IntegratedClient {
	
	public static void main (String[] args)
	{
		Encoder enc = new Encoder(args[1], Integer.parseInt(args[2]));
		TranClient tc = new TranClient();
		String serverIP, fileName, outFile = "OUTFILE";
		int port;
		fileName = args[0];
		serverIP = args[3];
		port = Integer.parseInt(args[4]);
		System.out.println("Enter IP of Server");
		System.out.println("Enter port to send data");
		System.out.println("Enter file name to encode");
		enc.setInFileName(fileName);
		enc.setOutFileName(outFile);
		enc.encode();
		try
		{
			Compressor comp = new Compressor();
			comp.compress(new File(outFile));
			String zippedFile = "OUT.zip";
			tc.setFile(zippedFile);
			tc.setServerIP(serverIP);
			tc.setServerPort(port);
			tc.makeConnection();
			tc.sendData();
			new File(outFile).delete();
			new File(zippedFile).delete();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println ("Backend transmission done at IntegratedClient");
		return;
	}

}
