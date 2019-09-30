package ie.gmit.sw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;

/**
 * 
 * @author Mark Gilmore 
 * @author G00214777
 * 
 * Consumer Class Takes Shingles from the Blocking Queue
 *
 */

public class Consumer implements Runnable {
	private Map<Integer, Integer> doc1 = new TreeMap<>();
	private Map<Integer, Integer> doc2 = new TreeMap<>();
	private BlockingQueue<Word>q;
	private int fileCount;
	private ExecutorService pool = Executors.newFixedThreadPool(3);
    private double dotProduct = 0, magnitudeA = 0, magnitudeB = 0;
    
    /**
     * 
     * @param q blocking queue
     * @param fileCount counts files
     */

	
	public Consumer(BlockingQueue<Word>q, int fileCount) {
		super();
		this.q = q;
		this.fileCount = fileCount;
	}

	@Override
	public void run() {
		while (fileCount > 0) {
			try {
				Word w = q.take();
				if (w instanceof Poison) {
					fileCount--;
				} else {
					pool.execute(new Runnable() {
						public void run() {
							List<Integer> list = null;
							int shingle = w.getHashCode();
							
							/**
							 * Process Document One 
							 */
							if (w.getDocId() == 1) {
								if (!doc1.containsKey(shingle)) {
									list = new ArrayList<Integer>();
									Integer n = doc1.get(shingle);
									n = (n == null) ? 1 : ++n;
									list.add(n);
									doc1.get(shingle);
									doc1.put(shingle, n);
								}
							} // if
							
							/**
							 * Process Document two
							 */
							if (w.getDocId() == 2) {
								if (!doc2.containsKey(shingle)) {
									//if ()
									list = new ArrayList<Integer>();
									Integer n = doc2.get(shingle);
									n = (n == null) ? 1 : ++n;
									list.add(n);
									doc2.get(shingle);
									doc2.put(shingle, n);
								}
							} // if
						
						} // nested Run
					});
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // While
		
		/**
		 * Calculates the Cosine Similarity and Prints it Out
		 */
		
		// Calculates the Cosine Similarity and Prints it Out
		Map<Integer, Integer> d1 = doc1;
		Map<Integer, Integer> d2 = doc2;
		
		HashSet<Integer> intersection = new HashSet<>(d1.keySet());
        intersection.retainAll(d2.keySet());
		
        for (int item: intersection) {
        	dotProduct += d1.get(item) * d2.get(item);
        }
        
        for (int k : d1.keySet()) {
            magnitudeA += Math.pow(d1.get(k), 2);
        }

        for (int k : d2.keySet()) {
            magnitudeB += Math.pow(d2.get(k), 2);
        }
      
		double c = dotProduct / Math.sqrt(magnitudeA * magnitudeB);
		
		System.out.println("-----------------------------------");
        System.out.println("Simlarity: " + c * 100 + " %");
        System.out.println("-----------------------------------");
        
	} // Run

} // Consumer