package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;







public class WebCrawler {
	//HashSet is used as it prevents the duplicate value
	private static Set<String> crawledURLS = new HashSet<String>();
	private static int maxLimit = 1500; //depth is 2 as our system took more time above that to crawl
	private static String urlRegex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";

	public static void crawlerStart(String URL, int limit) throws IOException {
		// TODO Auto-generated method stub
		Pattern urlPattern = Pattern.compile(urlRegex);
		PrintWriter urlsFile = new PrintWriter(
				new FileWriter(System.getProperty("user.dir") + "/src/Files/URLSExtracted/Urls.txt"));
		if(limit<= maxLimit) {
			try {
				Document getDoc = Jsoup.connect(URL).get();
				limit++;
				if (limit < maxLimit) {
					Elements elementsLinks = getDoc.select("a[href]");
					 for(Element element : elementsLinks){
					       
					        crawlerStart(element.absUrl("href"), limit);
					        if (!crawledURLS.contains(element.absUrl("href"))) {
					        	savingDoc(getDoc,URL);
					        	System.out.println(element.absUrl("href"));
					        	crawledURLS.add(element.absUrl("href")); 
					        	urlsFile.println(element.absUrl("href"));
					        }    
					    }
				}
			}catch (Exception e){
				System.out.println("Exception "+e);
			}
		}
		
		
    	urlsFile.close();
		
	}
	private static void savingDoc(Document getDoc, String URL) throws FileNotFoundException {
		// TODO Auto-generated method stub
		try {
			PrintWriter html = new PrintWriter(
					new FileWriter(System.getProperty("user.dir") + "/src/Files/HtmlFiles/"+getDoc.title().replaceAll("\\W", "") + ".html"));		
			html.write(getDoc.toString());
			html.close();
	
			processHtmlToText(System.getProperty("user.dir") + "/src/Files/HtmlFiles/" + getDoc.title().replaceAll("\\W", "")+ ".html", URL,getDoc.title().replaceAll("\\W", "") + ".txt");
		}
		catch (Exception e){
			System.out.println("Exception" + e);
		}
		
	}
	private static void processHtmlToText(String file, String URL, String fileName) throws IOException {
		// TODO Auto-generated method stub
		File textfile= new File(file);
		Document textdoc= Jsoup.parse(textfile, "UTF-8");
		String words = textdoc.text().toLowerCase();
		words = URL + "\n" + words;
		PrintWriter printwriter= null;

		try {
			File saveFile= new File(System.getProperty("user.dir") + "/src/Files/TextFiles/" + fileName);
			printwriter = new PrintWriter(saveFile);
			printwriter.println(words);
		}finally {
			if(printwriter!=null) {
				printwriter.close();
			}
		}
	}

}
