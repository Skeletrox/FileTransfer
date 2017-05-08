package mains;
import java.util.Scanner;

public class IntegratedMain {
	
	public static void main (String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Type \"send\" to send and \"receive\" to receive:");
		String str = sc.next();
		if (str.equals("receive"))
		{
			IntegratedServer.main(args);
		}
		else if (str.equals("send"))
		{
			IntegratedClient.main(args);
		}
		else
		{
			System.out.println("VRANG");
		}
	}

}
