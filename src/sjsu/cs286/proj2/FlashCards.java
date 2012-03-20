package sjsu.cs286.proj2;
/**
 * 
 * @author John Linford, Shailesh Benake
 *
 */
public class FlashCards {

	// A flashCard contains the word (front of card)
	// and a description (back of the card)
	public FlashCards(String w, String d) {
		word = w;
		descp = d;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	String word;
	String descp;

}