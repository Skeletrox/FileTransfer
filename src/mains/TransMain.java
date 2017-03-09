package mains;
import filetransfer.*;
import java.util.Scanner;

public class TransMain 
{
	
	
	public static void main (String[] args)
	{
		Encoder enc = new Encoder();
		String inName, outName;
		Scanner sc = new Scanner(System.in);
		enc = new Encoder();
		System.out.println("Enter name of file to read");
		inName = sc.next();
		System.out.println("Enter name of file to write to");
		outName = sc.next();
		enc.setInFileName(inName);
		enc.setOutFileName(outName);
		enc.transmit();
		sc.close();
	}
}
