package Controller;

import java.util.StringTokenizer;

import ExternalClasses.In;
import ExternalClasses.TST;

public class SearchKeywords {
	
	public static String[] getKeywordsFromInput(String inputText) 
	{
		int i = 0;
		In in = new In("stopwords.txt");	
		inputText = inputText.toLowerCase();
		
		while (!in.isEmpty()) 
		{
			String text = in.readLine();
			text = text.toLowerCase();
			text = "\\b"+text+"\\b";
			inputText = inputText.replaceAll(text,"");	
		}
		
		StringTokenizer st  = new StringTokenizer(inputText, " ");
		String[] words = new String[st.countTokens()];
		
		while (st.hasMoreTokens()) 
		{ 
			words[i]=st.nextToken();
			i++;
        }
		return words;
	}
	
	//read file words into tst by having filepath
	public TST<Integer> readFileIntoTST(String filePath)
	{
		String fileData,token;
		int occurenceCounter=0;
		
		In in = new In(filePath);
		
		TST<Integer> st = new TST<Integer>();
		
		fileData = in.readAll();
		
		//java library to break the words with whitespace
		StringTokenizer tokenizer = new StringTokenizer(fileData);
		
		//run till all tokens have been inserted
		while(tokenizer.hasMoreTokens())
		{
			token = tokenizer.nextToken().toLowerCase();
			//checking if token already exist in tree
			if(st.contains(token))
			{
				//exist so first get the value and then add 1 to the value
				occurenceCounter = st.get(token)+1;
				//insert/update the value in the key
				st.put(token,occurenceCounter);
			}
			//not found in tree
			else 
			{
				//make occurence 1
				occurenceCounter = 1;
				//add the token in tree with value of 1
				st.put(token,occurenceCounter);
			}
			
		}
		
		return st;
	}
	

}
