package SpellChecker;

public class TrieNode {
	
	TrieNode[] nodes = new TrieNode[50];
	int count;
    boolean isEnd;

	public int getValue() {		
		return count;
	}
	
	public void incrementValue() {
		count++;	
	}
	
	public TrieNode[] getChildren() {
		return nodes;
	}	
}
