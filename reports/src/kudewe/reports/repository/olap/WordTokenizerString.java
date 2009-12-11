package kudewe.reports.repository.olap;

import kudewe.reports.repository.WordTokenizer;

public class WordTokenizerString implements WordTokenizer {
	private String text;
	private String ltext;
	
	@Override
	public void setText(String text) {
		this.text = text;
		this.ltext = text.toLowerCase();
	}
	
	@Override
	public String getNextToken(String tokenizer) {
		String token;
		if (tokenizer != null) {
			String search = tokenizer.toLowerCase();
			int tokenizerIndex = ltext.indexOf(search);
			if (tokenizerIndex >= 0) {
				token = text.substring(0, tokenizerIndex);
				text = text.substring(tokenizerIndex + tokenizer.length());
				ltext = ltext.substring(tokenizerIndex + tokenizer.length());
			} else {
				token = text;
				ltext = "";
				text = "";
			}
		} else {
			token = text;
			ltext = ""; 
			text = "";
		}
		
		return token;
	}
	
	@Override
	public String getNextToken() {
		return getNextToken(null);
	}
	
	@Override
	public boolean hasNext(String tokenizer) {
		return (ltext.indexOf(tokenizer.toLowerCase()) >= 0);
	}
	
	@Override
	public WordTokenizer clone() {
		return new WordTokenizerString();
	}
}
