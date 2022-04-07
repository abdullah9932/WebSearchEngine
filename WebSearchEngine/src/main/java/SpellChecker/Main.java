package SpellChecker;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import Controller.WebEngine;

public class Main {

	public static void main(String[] args) throws IOException {
		String query = "Mster";
		File directory;
		File[] filesList;
		String filePath,txtFileName,pathOfFile;
		
		//directory path
		directory = new File(WebEngine.mainPath +"\\src\\Files\\TextFiles"); 
				
		//path for each files
		filePath = WebEngine.mainPath +"\\src\\Files\\TextFiles\\";
				
		//list of files present in the directory
		filesList = directory.listFiles();
				
		//iterating on each file present in filesList
		for (File file : filesList)
		{
			//getting filename
		    txtFileName = file.getName();
		            
		    //creating path of file with file name and filepath
		    pathOfFile = filePath + txtFileName;
		
			String inputWord = query;
			
			Spell_1 corrector = new Spell_1();
			
			corrector.dict_file(pathOfFile);
			String suggestion = corrector.suggestSimilarWord(inputWord);
			if (suggestion == null) {
			    suggestion = "No similar word found";
			}
			
			System.out.println("Did you mean: " + suggestion);
		}
	}

}
