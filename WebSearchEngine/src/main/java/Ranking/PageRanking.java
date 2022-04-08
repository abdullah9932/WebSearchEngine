package Ranking;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import Controller.WebEngine;
import ExternalClasses.In;
import ExternalClasses.TST;
import Search.SearchKeywords;

public class PageRanking {
	//getting list of frequency of words present in the file using tst
			public static HashMap<String, Integer> getlistOfFrequencies(String[] Words)
			{
				
				//initialized variables
				String pathOfFile, indexOfFile, txtFileName, filePath;
				int counter = 0;
				int frequency;
				File directory;
				File[] filesList;
				
				//creating a hashmap of words frequencies found in textfiles
				HashMap<String,Integer> wordsFrequencyList = new HashMap<String, Integer>();
			    
				//directory path
				directory = new File(WebEngine.mainPath +"/src/Files/TextFiles"); 
				
				//path for each files
				filePath = WebEngine.mainPath +"/src/Files/TextFiles/";
				
				//list of files present in the directory
				filesList = directory.listFiles();
				
				//iterating on each file present in filesList
		        for (File file : filesList)
		        {
		        	//getting filename
		            txtFileName = file.getName();
		            
		            //creating path of file with file name and filepath
		            pathOfFile = filePath + txtFileName;
		            
		            //reading file
		            In in = new In(pathOfFile);
		            
		            //reading first line in text file to get the url for index
		            indexOfFile = in.readLine();
		            
		            //initializing TST object
		            TST<Integer> tst = new TST<Integer>();
		            
		            //reading file data into tst
			        tst = SearchKeywords.readFileIntoTST(pathOfFile);
			      
			        //iterating to the input words
			        for (String word: Words) 
			        {	       
			        	//checking if tst have that word
			        	if (tst.contains(word))
			        	{
			        		//getting the frequency of the matched word
			        		 frequency = tst.get(word);
			        		 
			        		//StdOut.println(word+" "+frequency);
			        		 
			        		//adding frequency into the counter
			        		//counter adds frequencies of all words 
			        		counter = counter + frequency;        		
			        	}
			        }
			        
			        //adding index and counter in hashmap
			        wordsFrequencyList.put(indexOfFile, counter);
		        }
		        
		        //StdOut.println(wordsFrequencyList);
				return wordsFrequencyList;
			}

			
			//Sorting hashmap in descending order to show the results with highest frequencies
			public static HashMap<String, Integer> sortHashMapInDescending(HashMap<String,Integer> listOfFrequency)
			{	
				LinkedHashMap<String, Integer> reverseSortedFreqList = new LinkedHashMap<>();
				listOfFrequency.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> reverseSortedFreqList.put(x.getKey(), x.getValue()));
				
				return reverseSortedFreqList;		
			}
			


}