package ie.gmit.sw;

/**
 * 
 * @author Mark Gilmore
 * @author G00214777
 * 
 * creates shingles
 * 
 */

public class Word {

	private  int docId;
	private int hashCode;
	
	/**
	 * 
	 * @param docId
	 * @param hashCode
	 */
	
	public Word(int docId, int hashCode) {
		super();
		this.docId = docId;
		this.hashCode = hashCode;
	}
	
	public  int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	
	public int getHashCode() {
		return hashCode;
	}
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	
} // word