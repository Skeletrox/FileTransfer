package mains;
import filetransfer.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	//	ts.getClientIP();
		Decompressor dc = new Decompressor();
		try {
            dc.decompress(new File(fileName));
        }
        catch (IOException io) {
            System.out.println ("Error! Insufficient permissions?");
        }
		Encoder enc = new Encoder();
		enc.setInFileName("OUTPUT");
		enc.setOutFileName(outFile);
		new File(fileName).delete();
		new File("OUTPUT").delete();
		enc.encode();
		sc.close();
	}

}
