package Controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import ExternalClasses.StdOut;

public class WebEngine {

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
	/*
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
		
			*/

		//searching and returning result in hashmap with its values
		HashMap<String, Integer> result = new HashMap();
		boolean checkResultIsNotEmpty = false;
		boolean haveResults = false;
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Search Query");
		String query = scan.nextLine();
		SearchTextFiles search = new SearchTextFiles();
		result = search.searchString(query);
		//iterating in hashmap
		StdOut.println("Search Result of Query "+query+" is as follows:");
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
		
		if (checkResultIsNotEmpty == true && haveResults == false)
		{
			StdOut.println("run edit distance");
		}
	}	
}
	
	
	
