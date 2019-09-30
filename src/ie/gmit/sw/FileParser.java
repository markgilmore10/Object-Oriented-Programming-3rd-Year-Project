package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author Mark Gilmore
 * @author G00214777
 * 
 * FileParser Class Reads in Files and Parses Them
 *
 */

public class FileParser implements Runnable {
	private String file;
	private BlockingQueue<Word>q;
	private final int shingleSize = 3;
	private Deque<String> buffer = new LinkedList<>();
	private int id;
	
	/**
	 * 
	 * @param file in form of a string
	 * @param q blocking queue
	 * @param id document id
	 */

	public FileParser(String file, BlockingQueue<Word>q, int id) {
		super();
		this.file = file;
		this.q = q;
		this.id = id;
	}
	
	@Override
	public void run() {
		parse();
	}
	
	/**
	 * parse files to shingles
	 */

	public void parse() {
		String line = null;
		String[] words = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)));
			while ((line = br.readLine())!= null) {
				line = line.toLowerCase().replaceAll("[^A-Za-z0-9]"," ");
				if (line.length() > 0) {
					words = line.split("\\W+");
					addWords(words);
					Word w = getWord();
					w.setDocId(id);
					q.put(w);
				}	
			}
			flush();
			br.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	} // Parse

	/**
	 * takes in an array of words and adds them to the buffer
	 * @param words taken from FileParser buffer
	 */
	private void addWords(String[] words) {
		for (String s : words) {
			buffer.add(s);
		}
	} // addWords

	private Word getWord() {
		StringBuilder sb = new StringBuilder();
		int ss = 0;
		while (ss < shingleSize) {
			if (buffer.peek() != null) {
				sb.append(buffer.poll());
				ss++;
			} else {
				ss = shingleSize;
			}
		}
		if (sb.length() > 0) {
			return (new Word(id, sb.toString().hashCode()));
		} else {
			return null;
		}
	} // getWord
	
	/**
	 * 
	 * flushes buffer
	 * 
	 */
	// Flush Buffer
	private void flush() throws InterruptedException {
		while (buffer.size() > 0) {
			Word w = getWord();
			if (w != null) {
				q.put(w);
			}
		}
		q.put(new Poison(id, 0));
	}

} // File Parser