/*
Implementation of the RC4 algorithm in order to generate a random password for the program.
For some reason I believe this could be used to generate a random password (By using a random value), or work as a password "database",
by using a key and an output length [This helps the user create their own key, in case they want a low-security alternative to LastPass or KeePass].
By just remembering the main word and the password length, the user can retrieve their password.
*/
package passgens;
import java.util.Random;

public class RC4Gen
{
    private String key = "";
    private StringBuilder output;
    private int[] sArray;
    private int i, j, length, outLength;
    private Random randomizer;
    private byte[] nonces;

    private void noncify()
    {
        randomizer.setSeed((long) (randomizer.nextGaussian() *(1 + randomizer.nextInt(544288))));
        nonces = new byte[length];
        randomizer.nextBytes(nonces);
    }

    //Getters and setters

    public void setKey (String s)
    {
        key = s;
        length = key.length();
        return;
    }

    public void setOPLength (int x)
    {
        if (x < 256 && x >0)
        {
            outLength = x;
            return;
        }
        outLength = 256;
        return;
    }

    public String getKey() {return key;}


    public String getOutput () {return output.toString();}

    //Other Functions and Constructor

    public RC4Gen()
    {
        output = new StringBuilder();
        sArray = new int[128];
        randomizer = new Random();
        i = 0;
        j = 0;
        length = 0;
        for (;i < 128;i++)
        {
            sArray[i] = i;
        }
        return;
    }

    private void swap (int i, int j)
    {
        sArray[i] ^= sArray[j];
        sArray[j] ^= sArray[i];
        sArray[i] ^= sArray[j];
        return;
    }

    public void scheduleKey()
    {
        j = 0;
        for (i = 0;i < 128;i++)
        {
            j = (j + sArray[i] + (int) key.charAt(i % length)) % 128;
            swap (i, j);
        }
        return;
    }

    private void appendOutput (char c)
    {
        output.append(c);
    }

    public void generatePseudoRandom(boolean randBool)
    {
        char retChar = '\0';
        int count = 0, value = 0;
        i = 0;
        j = 0;
        for(;count < outLength;count++)
        {
            i = (i + 1) % 128;
            j = (j + sArray[i]) % 128;
            swap (i, j);
            value = (sArray[(sArray[i] + sArray[j]) % 128]) % 94 + 33;
            if (randBool)
            {
            	noncify();
            	value ^= nonces[i % length];
            }
            value = (value % 94);
            if (value < 0)
            {
            	value = 0 - value;
            }
            retChar = (char) (value % 94 + 33);
            appendOutput (retChar);
        }
        return;
    }

}