package mains;
import filetransfer.*;

import java.io.*;
import java.util.Scanner;

public class ClientMain {
	
	public static void main (String[] args)
	{
		TranClient tc = new TranClient();
		String serverIP, fileName;
		int port;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter file to send");
		fileName = sc.next();
		System.out.println("Enter Server IP");
		serverIP = sc.next();
		System.out.println("Enter Server port");
		port = sc.nextInt();
		try
		{
			tc.setFile(fileName);
		}
		catch (FileNotFoundException f)
		{
			System.out.println("File does not exist");
			sc.close();
			return;
		}
		tc.setServerIP(serverIP);
		tc.setServerPort(port);	
		try
		{
			tc.makeConnection();
			tc.sendData();
		}
		catch (IOException io)
		{
			System.out.println("Unable to send data");
			sc.close();
			return;
		}
		sc.close();
	}
}
