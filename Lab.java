import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;
import java.util.Scanner;

class Guest implements Runnable{
	private static final Object lock = new Object();
	public static AtomicBoolean cupcake = new AtomicBoolean(true);
	public static AtomicBoolean finished = new AtomicBoolean(false);
	private boolean eaten = false;
	private boolean counter;
	public int count = 0;
	
	public static int N;
	private int ID;
	public static AtomicInteger guestValue = new AtomicInteger(0);
	public static boolean[] guestList;
	
	public Guest(boolean c, int ID) {
		this.counter = c;
		this.ID = ID;
	}
	
	@Override
	public void run() {
		while(!finished.get()) {
			//keep threads in loop (waiting) unless they're up
			synchronized (lock) {
				//System.out.println(guestValue.get() + " is up! ");
				while(guestValue.get() != ID) {
					if(finished.get()) {
						return;
					}
					//System.out.println(Thread.currentThread().getName() + " is locked");
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//if(!eaten && cupcake.get()) System.out.println(Thread.currentThread().getName() + " is seeing the cupcake for the first time! ");
				//if haven't eaten cupcake and it exists eat it (thread has visited at this point)
				if(cupcake.get() && !eaten) {
					cupcake.set(false);
					//System.out.println(Thread.currentThread().getName() + " has eaten!");
					eaten = true;
					guestList[ID] = true;
				}
				//for counter, replace cupcake if it's missing and increment
				if(!cupcake.get() && counter) {
					count += 1;
					cupcake.set(true);
				}
				//if(counter) System.out.println(count);
				//if counter sees everyone has been visited
				if(count >= N) {
					finished.set(true);
				}
				
				//minotaur sets new value
				guestValue.set(new Random().nextInt(N));
				lock.notifyAll();
			}
		}
		if(finished.get()) {
			if(counter)
				System.out.println("COUNTER REPORTS EVERYONE HAS VISITED!");
			return;
		}
	}
}

public class Lab {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		Random r = new Random();
		Scanner s = new Scanner(System.in);
		int upperBound = 100;
		int N = upperBound + 1;
		
		while(N > upperBound || N <= 0) {
			System.out.println("How many guests are there: ");
			N = s.nextInt();
			if(N > upperBound)
				System.out.println("ERROR: value over upper bound of 100\n");
			else if(N <= 0)
				System.out.println("ERROR: value lower than lower bound of 0\n");
		}
		
		int startingValue = r.nextInt(N);
		//System.out.println("The Starting Value is: " + startingValue);
		Guest.guestValue.set(startingValue);
		
		Guest.N = N;
		Thread[] tList = new Thread[N];
		Guest.guestList = new boolean[N];
		
		//there's at least 1 thread
		tList[0] = new Thread(new Guest(true, 0));
		Guest.guestList[0] = false;
		
		//setup all the threads
		for(int i = 1; i < N; i++) {
			tList[i] = new Thread(new Guest(false, i));
			Guest.guestList[i] = false;
		}
		
		
		//start all threads
		for(Thread t: tList){
            t.start();
        }

        //Wait till all threads die
        try{
            for (Thread thread : tList) {
                thread.join();
            }
        }
        catch (InterruptedException e){
            System.out.println(e);
        }
        for (int i = 0; i < Guest.guestList.length; i++) {
        	System.out.println(i + ": " + Guest.guestList[i]);
        }
        
        s.close();
		long endTime = System.nanoTime();
        double totalTime = (endTime - startTime);
        String timeOutput = (totalTime/1000000 + " ms");
        System.out.println(timeOutput);
	}
}
