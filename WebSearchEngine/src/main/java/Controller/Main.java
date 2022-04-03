package Controller;

public class Main {
	public String hello()
	{
		return "hello";
	}
	
	public static void main(String [] args)
	{
		String [] words;
		SearchKeywords sk = new SearchKeywords();
		words = sk.getKeywordsFromInput("Hello man whats up now a days");
		for(String i : words)
		{
			System.out.println(i);
		}
	}

}
