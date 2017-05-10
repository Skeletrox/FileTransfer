/**
 * This uses a Salsa20 encryption algorithm in order to generate a secret key.
 * The resources [variables] required are:
 * 	1. 16 words [8 of which are the user key, 2 are the nonce, 2 are stream position,
 *     4 are fixed] 
 * 	2. one value to do left rotate [by default 7, 9, 13, 18]
 * 
 * The basic methodology goes this way:
 * 	1. The 16 words are arranged in a 4 x 4 format and the operations are performed,
 * 	   basically x[i] XOR= (x[j] + x[k])%(2^32) <<< (7/9/13/18)
 * 	2. This is done 20 times, with odd numbered iterations using the rows and even 
 * 	   numbered iterations using the columns
 * 	3. This literally takes the pesudocode from the Wikipedia page for Salsa20
 */

package passgens;
import java.util.Random;

public class SalsaGen {
	
	private String userKey;
	private String output;
	private int length, i, j;
	private Random randomizer;
	private byte[] nonces;
	private byte[][] matrix;
	private final byte const1 = (byte) 173, const2 = (byte) 212, const3 = (byte) 190, const4 = (byte) 61;
	
	//Constructor
	
	public SalsaGen()
	{
		nonces = new byte[4];
		matrix = new byte[4][4];
		randomizer = new Random();
	}
	
	//Getters and setters
	
	public String getOutput() { return output;}
	
	public void setUserKey(String key) { userKey = key; }
	
	public void setLength (int l) {length = l;}
	
	//The generator functions, most are private for added security

	private void pseudoNoncify()
	{
		for (int i = 0; i< 4; i++)
		{
			nonces[i] = (byte) userKey.charAt(i%4);
		}
	}
	private void noncify()
    {
        randomizer.setSeed((long) (randomizer.nextGaussian() *(1 + randomizer.nextInt(44362))));
        randomizer.nextBytes(nonces);
    }
	
	private void makeHashed()
	{
		RC4Gen tempgen = new RC4Gen();
		tempgen.setKey(userKey);
		tempgen.setOPLength(8);
		tempgen.hashCode();
		tempgen.generatePseudoRandom(true);
		userKey = tempgen.getOutput();
	}
	
	private void  matrixify()
	{
		for (i = 0; i< 8;i++)
		{
				matrix[i / 4][i % 4] = (byte) userKey.charAt(i);
		}
		matrix[2][0] = const1;
		matrix[2][1] = const2;
		matrix[2][2] = const3;
		matrix[2][3] = const4;
		for (j = 0;j<4;j++)
		{
			matrix[3][j] = nonces[j];
		}
	}
	
	private byte rotateLeft (byte b, short amount)
	{
		/*int[] boolArray = new int[amount];
		int[] bitMask =  {0x0080, 0x0040, 0x0020, 0x0010, 
						  0x0008, 0x0004, 0x0002, 0x0001};
		short l;
	
		for (l = 0;l < amount;l++)
		{
			boolArray[l] = b & bitMask[l];
		}
		b %= Math.pow(2) , amount);
		b *= Math.pow(2, 8-amount);
		return b;
		Too complicated, and memory wasteful*/
		short val = (short) b, rem, l;
		rem = (short) (val % Math.pow(2,amount));
		val = (short) (val / (int)Math.pow(2,  amount));
		rem = (short) (rem * (int) Math.pow(2,  8-amount));
		for (l = 0;l < 8-amount;l++)
		{
			if (val % (int) Math.pow(2,  l+1) - val % (int) Math.pow(2,  l) == 1)
			{
				rem += Math.pow(2, l);
			}
		}
		b = (byte) rem;
		return b;
	}
	
	private void manufacture()
	{
		short[][] set = {{4, 14, 8, 2, 12, 6, 0, 10},
					   {9, 3, 13, 7, 1, 11, 5, 15},
					   {1, 11, 2, 8, 3, 9, 0, 10},
					   {6, 12, 7, 13, 4, 14, 5, 15}};
		short[] rotateOffset = {7, 9, 13, 18};
		int k;
		
		for (i = 0;i < 20;i++)
		{
			for (j = 0;j<4;j++)
			{
				for (k=0;k<8;k++)
				{
					if (i % 2 == 0)
					{
						matrix[set[j][k] / 4][set[j][k] % 4] ^= 
						rotateLeft((byte) (matrix[set[j][(k+2) % 8] / 4][set[j][(k+2) % 8] % 4] + matrix[set[j][(k + 4) % 4] / 4][set[j][(k+4) % 8] % 4]), rotateOffset[j]);
					}
					else
					{
						matrix[set[j][k] % 4][set[j][k] / 4] ^= 
						rotateLeft((byte) (matrix[set[j][(k + 2) % 8] % 4][set[j][(k + 2) % 8] / 4] + matrix[set[j][(k + 4) % 8] % 4][set[j][(k + 4) % 8] / 4]), rotateOffset[j]);
					}
				}
			}
		}
	}
	
	private void reHash()
	{
		RC4Gen regen = new RC4Gen();
		StringBuilder tempOut = new StringBuilder();
		for (i = 0;i < 4;i++)
		{
			for (j = 0;j < 4;j++)
			{
				char c = (char) matrix[i][j];
				tempOut.append(c);
			}			
		}
		regen.setOPLength(length);
		regen.setKey(tempOut.toString());
		regen.hashCode();
		regen.generatePseudoRandom(false);
		output = regen.getOutput();
	}
	
	public void generateSalsa()
	{
		pseudoNoncify();
		makeHashed();
		matrixify();
		manufacture();
		reHash();
	}

}
