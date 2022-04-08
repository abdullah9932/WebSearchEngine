package Controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import Crawler.WebCrawler;
import ExternalClasses.StdOut;
import Search.SearchTextFiles;
import SpellChecker.Spell_1;

public class WebEngine {
	public static String mainPath = "C:/abdullah/semester1/Project/WebSearchEngine/WebSearchEngine";
	private static Scanner inputURL = new Scanner(System.in);
	
	//checking validity of URL
	public static boolean ifValidURL(String URL){
        try {
        	System.out.println("Validating URL...");
        	URL obj = new URL(URL);
            HttpURLConnection CON = (HttpURLConnection) obj.openConnection();
            CON.setRequestMethod("GET");
            int response = CON.getResponseCode();
            if(response==200) {
            	 return true;
            }else {
            	return false;
            }
           
        }
        catch (Exception e) {
            return false;
        }
    }
        
	private static boolean checkURL(String URL) {
		
		if(ifValidURL(URL)) {
			 System.out.println("Enterd URL " + URL + " is valid");
			 return true;
		}
		else {
			System.out.println("Please try again....\n");
			return false;
		}	
	}

	public static void main(String[] args) throws Exception {


		System.out.println("Welcome to Forage-X Engine\n");
		System.out.println("*************************************************************************\n");
		System.out.println("Enter the URL you want to crawl");
		System.out.println("\n*************************************************************************\n");
		String URL = inputURL.next();
		if (!URL.startsWith("http://") && !URL.startsWith("https://"))
        {
           URL= "https://" + URL;
        }
		
		boolean afterCheck = checkURL(URL);
		if(afterCheck) 
		{
			System.out.println("Now, we can start crawling");
			WebCrawler.crawlerStart(URL, 0); 						
			System.out.println("Crawling done...");
			System.out.println("\n*************************************************************************\n");
			System.out.println("Thank you");
			System.out.println("\n*************************************************************************\n");
			
		}

		boolean searchAgain = false;
		while(searchAgain == false)
		{
			//searching and returning result in hashmap with its values
			HashMap<String, Integer> result = new HashMap();
			boolean checkResultIsNotEmpty = false;
			boolean haveResults = false;
			double start, end;
			Scanner scan = new Scanner(System.in);
			
			StdOut.println("\n");
			StdOut.println("*************************************************************************\n");
			StdOut.println("Enter Your Search Query\n");
			StdOut.println("*************************************************************************");
			StdOut.print("Input:");
			String query = scan.nextLine();
			SearchTextFiles search = new SearchTextFiles();
			start = System.currentTimeMillis();
			result = search.searchString(query);
			end = System.currentTimeMillis();
			
			//iterating in hashmap
			StdOut.println();
			StdOut.println("*************************************************************************\n");
			StdOut.println("Search Result of Query "+query+" is as follows:\n");
			StdOut.println("*************************************************************************");
			StdOut.println();
			
			int i = 0;
			for (HashMap.Entry<String, Integer> entry : result.entrySet()) 
			{
				if(i < 10)
				{
					if(entry.getValue()!= 0)
					{
						StdOut.println("url:"+entry.getKey());
						StdOut.println("frequency:"+entry.getValue());
						checkResultIsNotEmpty = false;
						haveResults = true;
						i++;
					}
						
					else
					{
						checkResultIsNotEmpty = true;
						break;
					}
				}
			}	
			StdOut.println("Total time taken to search:"+(end-start)+"ms");
			StdOut.println();
			
			if (checkResultIsNotEmpty == true && haveResults == false)
			{
				StdOut.print("Output:");
				StdOut.println("No Result Found");
				StdOut.println();
				StdOut.println("Running Spell Correction...");
	
	            String dictionaryFileName = WebEngine.mainPath + ("/src/Files/dictionary.txt");
	
	            Spell_1 corrector = new Spell_1();
	            
	            corrector.dict_file(dictionaryFileName);
	
	            String suggestion = corrector.suggestSimilarWord(query);
	
	            if (suggestion == null) {
	
	                suggestion = "No similar word found";
	
	            }
	
	            StdOut.println("Did you mean: " + suggestion);
	
	            StdOut.println("Type Yes to proceed with the suggestion");
	            StdOut.print("Input:");
	
	            String yes = scan.nextLine();
	
	            if(yes.toLowerCase().equals("yes")) {
	
	            	result = search.searchString(suggestion);
	    			
	    			//iterating in hashmap
	    			StdOut.println();
	    			StdOut.println("*************************************************************************\n");
	    			StdOut.println("Search Result of Query "+suggestion+" is as follows:\n");
	    			StdOut.println("*************************************************************************");
	    			StdOut.println();
	    			int j = 0;
	    			for (HashMap.Entry<String, Integer> entry : result.entrySet()) 
	    			{
	    				if(j < 10)
	    				{
	    					if(entry.getValue()!= 0)
	    					{
	    						StdOut.println("url:"+entry.getKey());
	    						StdOut.println("frequency:"+entry.getValue());
	    						checkResultIsNotEmpty = false;
	    						haveResults = true;
	    						j++;
	    					}
	    						
	    					else
	    					{
	    						checkResultIsNotEmpty = true;
	    						break;
	    					}
	    				}
	    			}	
	
	            }
	
	            else {
	
	                StdOut.println("User did not agree with the suggestion");
	
	            }
			}	
			StdOut.println();
			StdOut.println("Total time taken to search:"+(end-start)+"ms");
			StdOut.println();
			StdOut.println("*************************************************************************");
			StdOut.println("Do you want to search again?");
			StdOut.println("type y for yes and n for no");
			StdOut.print("Input:");
			String input = scan.nextLine();
			if (input.toLowerCase().contains("n"))
			{
				searchAgain = true;
				StdOut.println();
				StdOut.println("Exiting Search Engine...");
			}
			
			else if(input.toLowerCase().contains("y"))
			{
				searchAgain = false;
			}
			
			else
			{
				StdOut.println("Wrong Input");
			}
		}
	}
}
	
	
	
