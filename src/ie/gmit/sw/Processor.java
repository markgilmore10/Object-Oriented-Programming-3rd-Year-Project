package ie.gmit.sw;

import java.util.concurrent.*;

/**
 * 
 * @author Mark Gilmore
 * @author G00214777
 * 
 * processor class creates threads and passes parameters to them
 * 
 */

public class Processor {
	
	/**
	 * 
	 * @param doc1 users first document for comparison
	 * @param doc2 users second document for comparison
	 * @throws InterruptedException
	 */
	
	public void Process(String doc1, String doc2) throws InterruptedException {
		BlockingQueue<Word>q = new ArrayBlockingQueue<>(100);
		Thread t1 = new Thread(new FileParser(doc1, q, 1));
		Thread t2 = new Thread(new FileParser(doc2, q, 2));
		Thread t3 = new Thread(new Consumer(q, 2));
		
		t1.start();
		t2.start();
		t3.start();

		t1.join();
		t2.join();
		t3.join();
		
	} // Process
	
} // Processor
