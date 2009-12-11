package kudewe.reports.repository;

public interface WordTokenizer {
	void setText(String text);
	String getNextToken(String tokenizer);
	String getNextToken();
	boolean hasNext(String tokenizer);
	WordTokenizer clone();
}
