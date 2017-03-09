package mains;
import filetransfer.*;
import java.util.Scanner;

public class IntegratedServer {
	
	public static void main (String[] args)
	{
		String fileName = "INFILE", outFile;
		int port;
		Scanner sc = new Scanner(System.in);
		TranServer ts = new TranServer();
		System.out.println("Enter the port to activate server on!");
		port = sc.nextInt();
		ts.setPort(port);
		System.out.println("Enter the file name to be stored");
		outFile = sc.next();
		ts.setOutFile(fileName);
		ts.makeConnection();
		ts.getData();
		ts.getClientIP();
		Encoder enc = new Encoder();
		enc.setInFileName(fileName);
		enc.setOutFileName(outFile);
		enc.transmit();
		sc.close();
	}

}
