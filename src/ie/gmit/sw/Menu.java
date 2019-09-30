package ie.gmit.sw;

import java.util.Scanner;

/**
 * 
 * @author Mark Gilmore
 * @author G00214777
 * 
 * Menu class allows the user to choose files and read them in to the processor
 * 
 */

public class Menu {
	
	private Scanner scanner;

	public Menu() {
		
		/**
		 * 
		 * @param dir allows the user to choose a directory which has its files stored
		 * @param doc is the text document the user wishes to compare
		 * @param doc1 is the files path and name
		 * @param doc2 is the file in the home directory the user wishes to compare
		 * @param quit checks the choice of the user to continue or exit
		 * @param input takes in and stores users choice
		 * 
		 */
		
		char quit = 'n';
		String input;
		
		do {
			scanner = new Scanner(System.in);
			
			System.out.println("*** Document Comparison Service ***");
			
			// file directory
			System.out.print("Enter Subject Directory > ");
			String dir = scanner.next();
			
			// .txt file name
			System.out.print("\nPlease Query File or URL > ");
			String doc = scanner.next();
			
			// putting the file path and name together
			String doc1 = dir + "/" + doc;
			
			// file in home directory you want to compare
			System.out.print("\nPlease Enter a Second File for Comparison > ");
			String doc2 = scanner.next();
			
			// pass files to processor
			try {
				new Processor().Process(doc1, doc2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.print("\nWould you Like to Compare More Files y/n ??\n");
			input = scanner.next().toLowerCase();
			quit = input.charAt(0);
			
		} while(quit == 'y');
		
	}
} // Menu
