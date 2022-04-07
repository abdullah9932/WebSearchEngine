package Search;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import Controller.WebEngine;
import ExternalClasses.RadixSort;
import ExternalClasses.StdOut;

public class SearchTextFiles {
	
	public HashMap <String, Integer> searchString(String query)
	{
		String [] words;
		String fileName = "";
		boolean fileExist = false;
		int len;
		HashMap<String,Integer> listOfFrequencies = new HashMap<String,Integer>();
		
		//removing stopwords from the input query
		words = SearchKeywords.getKeywordsAndRemoveStopWords(query);
		len = words.length;
		
		//sorting the input using radix sort because the length of input is short
		RadixSort.radixSortA(words, len);
		
		//creating filename with search words
		for (String word : words) 
		{
			fileName = fileName + word + "_";
		}
		
		//adding extension to filename
		fileName = fileName + ".dat";
		
		//creating file object 
		File directory = new File(WebEngine.mainPath +"\\src\\Files\\SearchedQuery"); 
		
		//listing all the files present in the directory
        File[] files = directory.listFiles();
 
        //iterating on each file present in directory
		for (File file : files) 
		{
			//getting file name by built in function of file
			String newFileName = file.getName();

			//if file is already there, means file already exist of that query
			if (newFileName.compareTo(fileName) == 0) 
			{
				fileExist = true;
				break;
			}
		}
		
		//if already exist
		if (fileExist == true)
		{	
			//getting list of words frequencies in hashmap
			listOfFrequencies = SearchKeywords.getHashMapFromFile(words);	
		}
		
		//file not exist
		else if(fileExist == false)
		{
			//create a hashmap
			listOfFrequencies = new HashMap<String,Integer>();
			
			//get list of frequencies into hashmap
			listOfFrequencies = SearchKeywords.getlistOfFrequencies(words);
			
			//sort the hashmap into descending order
			listOfFrequencies = SearchKeywords.sortHashMapInDescending(listOfFrequencies);

			//store the hashmap for future use
			SearchKeywords.storeHashMapInFile(listOfFrequencies, words);
					
		}
		
		return listOfFrequencies;
		
	}

}
