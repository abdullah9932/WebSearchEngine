package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import ExternalClasses.In;
import ExternalClasses.TST;

public class SearchKeywords {
	
	//getting string and removing stop words from string
	public static String[] getKeywordsAndRemoveStopWords(String inputText) 
	{
		//intializing variable
		int i = 0;
		String[] words;
		
		//reading file stopwords
		In in = new In("src/Files/stopwords.txt");	
		
		//converting string to lower case
		inputText = inputText.toLowerCase();
		
		//iterating in file
		while (!in.isEmpty()) 
		{
			//reading line
			String text = in.readLine();
			//converting text to lowercase
			text = text.toLowerCase();
			text = "\\b"+text+"\\b";
			
			//removing stopwords
			inputText = inputText.replaceAll(text,"");	
		}
		//tokenizing string
		StringTokenizer st  = new StringTokenizer(inputText, " ");
		
		//creating a list of string with length of tokens
		words = new String[st.countTokens()];
		
		while (st.hasMoreTokens()) 
		{ 
			//adding value word array
			words[i]=st.nextToken();
			i++;
        }
		return words;
	}
	

	//read file words into tst by having filepath
	public static TST<Integer> readFileIntoTST(String filePath)
	{
		String fileData;
		String [] tokens;
		int occurenceCounter=0;
		
		In in = new In(filePath);
		
		TST<Integer> st = new TST<Integer>();
		
		fileData = in.readAll();
		
		//removing the stopwords from file to be able to have less keywords in tst
		//helps in reducing the number of words
		tokens = getKeywordsAndRemoveStopWords(fileData);
		
		//iterating in tokens
		for(String token : tokens)
		{
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
	
	//getting list of frequency of words present in the file using tst
	public static HashMap<String, Integer> getlistOfFrequencies(String[] Words){
		
		//initialized variables
		String pathOfFile, indexOfFile, txtFileName, filePath;
		int counter = 0;
		int frequency;
		File directory;
		File[] filesList;
		
		//creating a hashmap of words frequencies found in textfiles
		HashMap<String,Integer> wordsFrequencyList = new HashMap<String, Integer>();
	    
		//directory path
		directory = new File("src/Files/TextFiles"); 
		
		//path for each files
		filePath = "src/Files/TextFiles/";
		
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
	        tst = readFileIntoTST(pathOfFile);
	      
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
	        		//counter adds frequencies present in all the files
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
		listOfFrequency.entrySet()
				  .stream()
				  .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
				  .forEachOrdered(x -> reverseSortedFreqList.put(x.getKey(), x.getValue()));
		
		return reverseSortedFreqList;		
	}
	
	//storing hashmap into .dat file for future use
	public static void storeHashMapInFile(HashMap<String, Integer> listOfFrequencies, String[] words) {

		//initializing variables
		String fileName = "";
		String filePath = "src/Files/SearchedQuery/";
		String finalPath;
		
		//creating filname with searchquery
		for (String word : words) 
		{
			fileName = fileName + word + "_";
		}

		//adding extension to the filename
		fileName = fileName + ".dat";
		
		 finalPath = filePath + fileName;

		try {

			FileOutputStream fileOut = new FileOutputStream(finalPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			//writing hashmap into .dat file
			out.writeObject(listOfFrequencies);
			out.close();
			fileOut.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	
	public static HashMap<String,Integer> getHashMapFromFile(String[] words)
	{
		//initializing variables
		String fileName = "";
		String filePath = "src/Files/SearchedQuery/";
		String finalPath;
		
		for (String str : words) {

			fileName = fileName + str + "_";
		}

		fileName = fileName + ".dat";
		
		finalPath = filePath + fileName;
		
		  HashMap<String,Integer> listOfFrequencies = new HashMap<String,Integer>();
		  listOfFrequencies = null;
		  
		  try{
			  
			  FileInputStream fileIn = new FileInputStream(finalPath);
			  ObjectInputStream in = new ObjectInputStream(fileIn);
			  
			  //reading hashmap from file
			  listOfFrequencies = (HashMap<String, Integer>)in.readObject();
			  in.close();
			  fileIn.close();
			  
		  } catch (Exception e){
			  
		  e.printStackTrace();
		  }
	
		  return listOfFrequencies;
		
	}
	
	

}
