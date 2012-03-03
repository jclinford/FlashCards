package sjsu.cs286.proj2;

public class FlashCards {
	
	public FlashCards(String w, String d){
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