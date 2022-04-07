package SpellChecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.TreeMap;

public class Spell_1{

	Trie trie = new Trie();
	Map<String, Integer> dict = new HashMap<>();
	
	public void dict_file(String file) throws IOException {	
		FileReader f_read = null;
		BufferedReader b_read = null;
		try {
			//reading file
			f_read = new FileReader(file);
			//buffer stream
			b_read = new BufferedReader(f_read);
			
			String line = "";
			
			while ((line = b_read.readLine()) != null) {   
				
				if (!line.contains(" ")) {
					AddWord(line.toLowerCase());
				} 
				else {
					String[] strs= line.split("\\s");
					for(String str: strs) {
						AddWord(str.toLowerCase());
					}
				}
			}
			
			
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
		finally {
			//closing streams
			f_read.close();
			b_read.close();
		}
	}
	
	public void AddWord(String str) {
		dict.put(str, dict.getOrDefault(str, 0)+1);
		//adding the string into the trie
		trie.add(str);
	}

	
	public String suggestSimilarWord(String inputWord) {
		if (inputWord.length()==0 || inputWord==null )
			return null;
		
		//converting the inout word into lowercase
		String s = inputWord.toLowerCase();
		String res=null;
		
		TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> map = new TreeMap<>();		
		TrieNode node = trie.find(s);			
		if(node == null) {
			for (String w: dict.keySet()) {
				int dist = editDistance(w, s);				
				TreeMap<Integer, TreeSet<String>> similarWords = map.getOrDefault(dist, new TreeMap<>());
				int freq = dict.get(w);
				TreeSet<String> set = similarWords.getOrDefault(freq, new TreeSet<>());
				set.add(w);			
				similarWords.put(freq, set);
				map.put(dist, similarWords);		
			}		
			res= map.firstEntry().getValue().lastEntry().getValue().first();
		 } else if (node !=null) {
			 res = s;
		 }
		 return res;
	}
	
	public int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
	 
		return dp[len1][len2];
	}
	
}