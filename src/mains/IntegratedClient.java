package mains;
import filetransfer.*;
import java.util.Scanner;
import java.io.*;

public class IntegratedClient {
	
	public static void main (String[] args)
	{
		Encoder enc = new Encoder();
		TranClient tc = new TranClient();
		String serverIP, fileName, outFile = "OUTFILE";
		int port;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter IP of Server");
		serverIP = sc.next();
		System.out.println("Enter port to send data");
		port = sc.nextInt();
		System.out.println("Enter file name to encode");
		fileName = sc.next();
		enc.setInFileName(fileName);
		enc.setOutFileName(outFile);
		enc.encode();
		try
		{
			Compressor comp = new Compressor();
			comp.compress(new File(outFile));
			String zippedFile = outFile.concat(".zip");
			tc.setFile(zippedFile);
			tc.setServerIP(serverIP);
			tc.setServerPort(port);
			tc.makeConnection();
			tc.sendData();
			new File(outFile).delete();
			new File(outFile.concat(".zip")).delete();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		sc.close();
	}

}
