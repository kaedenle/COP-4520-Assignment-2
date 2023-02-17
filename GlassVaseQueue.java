import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
class Queue{
	int head = 0, tail = 0; 
	
	Guest2[] items; 
	public Queue(int cap) {
		items = new Guest2[cap];
	}
	
	public synchronized void enq(Guest2 x){
		if(tail-head == items.length) 
			return;
		items[tail % items.length] = x; tail++;
	}
	
	public synchronized Guest2 deq(){
		if (tail == head)
			return null;
		Guest2 item = items[head % items.length]; head++;
		return item;
	}
	
	public int getSize() {
		return tail-head;
	}
	
	public void printQueue() {
		System.out.println("---------------------");
		int start = head % items.length;
		int end = tail % items.length;
		
		for(int i = start; i < end; i++) {
			System.out.println(i + " QUEUE " + items[i % items.length].ID);
		}
		System.out.println("---------------------");
	}
}

class Guest2 implements Runnable{
	public int ID;
	public static Queue queue;
	public static AtomicInteger inRoom = new AtomicInteger(-1);
	
	public boolean visited = false;
	public static int N;
	public static AtomicInteger num_fin = new AtomicInteger(0);
	public static AtomicInteger move = new AtomicInteger(-1);
	
	public Guest2(int ID) {
		this.ID = ID;
	}
	
	@Override
	public void run() {
        synchronized(this){
            if(move.get() == -1)
                move.set(ID);
        }
        
		while(N > num_fin.get()) {
			
			//randomly pick
            if(move.get() != ID){
                try {
                    Thread.sleep(new Random().nextInt(10));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
            }
			
			queue.enq(this);
			
			//this is the equivalent to being in the room
			
			synchronized(this) {
				//if room is vacant go in
				if(move.get() == -1) {
					move.set(ID);
					System.out.println(ID + " took vacancy");
					queue.deq();
				}
				//if not your ID and room wasn't vacant 
				if(move.get() != ID)
					continue;
				else
					System.out.println(ID + " entered the room");
				if(!visited) {
					visited = true;
					num_fin.incrementAndGet();
				}
				Guest2 next = queue.deq();
				if(next != null) {
					System.out.println(ID + " poked " + next.ID + " " + num_fin.get());
					move.set(next.ID);
				}
				else {
					System.out.println(ID + " left the room empty " + num_fin.get());
					move.set(-1);
				}
			}
		}
	}
}
public class GlassVaseQueue {

	public static void main(String[] args) throws InterruptedException {
		long startTime = System.nanoTime();
		Scanner s = new Scanner(System.in);
		int upperBound = 100;
		int N = upperBound + 1;
		
		while(N > upperBound || N <= 0) {
			System.out.println("How many guests are there: ");
			N = s.nextInt();
			if(N > upperBound)
				System.out.println("ERROR: value over upper bound of " + upperBound +"\n");
			else if(N <= 0)
				System.out.println("ERROR: value lower than lower bound of 0\n");
		}
		
		Thread[] tList = new Thread[N];
		Guest2[] gList = new Guest2[N];
		Guest2.queue = new Queue(N);
		Guest2.N = N;
		
		//setup all the threads
		for(int i = 0; i < N; i++) {
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
        for(int i = 0; i < N; i++) {
        	String buffer = "";
        	if(i < 10)
        		buffer = " ";
        	System.out.print("Guest " + i + " " + gList[i].visited + buffer +" | ");
        	if(i % 5 == 4)
        		System.out.println();
        }
        s.close();
		long endTime = System.nanoTime();
        double totalTime = (endTime - startTime);
        String timeOutput = (totalTime/1000000000 + " s");
        System.out.println(timeOutput);
		
	}

}
