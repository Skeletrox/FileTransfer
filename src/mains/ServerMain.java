package mains;
import filetransfer.*;
import java.util.Scanner;

public class ServerMain {
	
	public static void main(String[] args)
	{
		TranServer ts = new TranServer();
		Scanner sc = new Scanner(System.in);
		String fileName;
		int port;
		System.out.println("Enter port number to listen on: ");
		port = sc.nextInt();
		ts.setPort(port);
		System.out.println("Enter file to save at: ");
		fileName = sc.next();
		try {
			ts.setOutFile(fileName);
			ts.makeConnection();
			ts.getData();
		}
		catch (Exception e)
		{

		}
		sc.close();
	}

}
