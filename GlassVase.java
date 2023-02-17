import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class Guest2 implements Runnable{
	//private static final Object lock = new Object();
	private int ID;
	public static int N;
	public boolean visited = false;
	public static AtomicInteger num_fin = new AtomicInteger(0);
	public static AtomicBoolean sign = new AtomicBoolean(true);
	
	public Guest2(int ID) {
		this.ID = ID;
	}
	
	@Override
	public void run() {
		while(N != num_fin.get()) {
			int value;
			do {
				value = new Random().nextInt(50);
			}while(value != 1);
			//System.out.println("Guest " + ID + " has looked at the sign!");
			boolean status = sign.getAndSet(false);
			if(status) {
				if(!visited) {
					visited = true;
					num_fin.incrementAndGet();
				}
				//System.out.println("Guest " + ID + " has looked at the vase! " + num_fin.get() + " guests have looked at the vase.");
				sign.set(true);
			}
			else {
				System.out.println("Guest " + ID + " was turned away!");
			}
		}
	}
}
public class GlassVase {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
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
		
		
		Thread[] tList = new Thread[N];
		Guest2[] gList = new Guest2[N];
		Guest2.N = N;
		//Guest2.notify.set(new Random().nextInt(N));
		
		//there's at least 1 thread
		gList[0] = new Guest2(0);
		tList[0] = new Thread(gList[0]);
		
		//setup all the threads
		for(int i = 1; i < N; i++) {
			gList[i] = new Guest2(i);
			tList[i] = new Thread(gList[i]);
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
        
        //Guest2.queue.printQueue();
        /*for(int i = 0; i < N; i++) {
        	System.out.println(gList[i].visited);
        }*/
        s.close();
		long endTime = System.nanoTime();
        double totalTime = (endTime - startTime);
        String timeOutput = (totalTime/1000000 + " ms");
        System.out.println(timeOutput);
		
	}

}
