/**
 * 
 */
package primes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author Jimmy Messmer
 *
 */
public class PrimeGenerator implements Runnable
{
	final static private int CORES = Runtime.getRuntime().availableProcessors();
	private static ArrayList<Integer> finalPrimes = new ArrayList<Integer>();
	private static BufferedReader in;
	private static int low;
	private static int high;
	private static int startIndex = 0;
	private static int endIndex = 0;
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Lower bound? ");
		low = Integer.parseInt(in.readLine());
		System.out.print("Upper bound? ");
		high = Integer.parseInt(in.readLine());
		
		//System.out.println("Low: "  + low + " | " + "High: " + high);
		
		for(int i = 0; i < CORES + 1; i++)
		{
			startIndex = (((high - low) / (CORES + 1)) * i);
			endIndex = (((high - low) / (CORES + 1)) * (i + 1));
			PrimeGenerator gen = new PrimeGenerator();
			Thread t = new Thread(gen);
			t.start();
		}
		while(Thread.activeCount() > 1)
		{
			Thread.currentThread().sleep(1);
		}
		
		System.out.println(finalPrimes.toString());
		
		//System.out.println(generatePrimes(0, 100000).toString());
		
	}

	@Override
	public void run() 
	{
		ArrayList<Integer> sub = generatePrimes(startIndex, endIndex);
		//System.out.println(sub.toString());
		finalPrimes.addAll(sub);
	}
	
	private static ArrayList<Integer> generatePrimes(int start, int end)
	{
		ArrayList<Integer> primes = new ArrayList<Integer>();
		
		for(int i = start; i <= end; i++)
		{
			if(isPrime(i) == true)
			{
				primes.add(i);
				//System.out.println(i);
			}
		}
		return primes;
	}
	
	private static boolean isPrime(int num)
	{
		boolean prime = true;
		
		for(int i = 2; i <= Math.sqrt((double)num); i++)
		{
			//System.out.println(i);
			if(num % i == 0)
				prime = false;
		}
		return prime;		
	}
}

