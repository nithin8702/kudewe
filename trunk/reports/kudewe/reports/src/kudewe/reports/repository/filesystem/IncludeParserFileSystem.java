package kudewe.reports.repository.filesystem;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import kudewe.reports.repository.IncludeParser;
import kudewe.reports.repository.WordTokenizer;

public class IncludeParserFileSystem implements IncludeParser {
	private WordTokenizer wordTokenizer;
	
	public void setWordTokenizer(WordTokenizer wordTokenizer) {
		this.wordTokenizer = wordTokenizer;
	}
	
	@Override
	public String parse(String url) {
		String baseUrl = url.substring(0, url.lastIndexOf("/") + 1);
		StringBuilder result = new StringBuilder();
		
		// Clone word tokenizer to prevent scope problems when calls parse method recursively
		WordTokenizer wordTokenizer =  this.wordTokenizer.clone();
		
		wordTokenizer.setText(readFile(url));
		
		while (wordTokenizer.hasNext("<include")) {
			result.append(wordTokenizer.getNextToken("<include"));
			wordTokenizer.getNextToken("file");
			wordTokenizer.getNextToken("=");
			wordTokenizer.getNextToken("\"");
			String fileName = wordTokenizer.getNextToken("\"");
			result.append(parse(baseUrl + fileName));
		}
		
		result.append(wordTokenizer.getNextToken());
		
		return result.toString();
		
	}
	
	private String readFile(String url) {
		StringBuilder result = new StringBuilder();
		
		try {
	    	FileInputStream fstream;
	    	fstream = new FileInputStream(url);
	    	
	    	// Get the object of DataInputStream
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    
	    	//Read File Line By Line
	    	String line;
			while ((line = br.readLine()) != null)   {
				result.append(line);
			}
			//Close the input stream
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();
	}

}
