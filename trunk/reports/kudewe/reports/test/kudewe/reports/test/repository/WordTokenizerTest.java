package kudewe.reports.test.repository;

import static org.junit.Assert.*;
import kudewe.reports.repository.WordTokenizer;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class WordTokenizerTest extends BaseTest {
	@Autowired
	private WordTokenizer wordTokenizer;
	
	@Test
	public void tokenizerDslFilter() {
		wordTokenizer.setText("select {${brand}}");
		assertEquals("select {", wordTokenizer.getNextToken("${"));
		assertEquals("brand", wordTokenizer.getNextToken("}"));
		assertEquals("}", wordTokenizer.getNextToken());
	}
	
	@Test
	public void tokenizerDslFilterDefault() {
		wordTokenizer.setText("select {${brand.default([Brand].[Aliverti])}.Members}");
		
		assertEquals("select {", wordTokenizer.getNextToken("${"));
		assertEquals("brand", wordTokenizer.getNextToken("."));
		assertEquals("default", wordTokenizer.getNextToken("("));
		assertEquals("[Brand].[Aliverti]", wordTokenizer.getNextToken(")"));
		assertEquals("", wordTokenizer.getNextToken("}"));
		assertEquals(".Members}", wordTokenizer.getNextToken());
	}
	
	@Test
	public void tokenizerNotExists() {
		wordTokenizer.setText("select {[Time].[2009]}");
		assertEquals("select {[Time].[2009]}", wordTokenizer.getNextToken("${"));
		assertEquals("", wordTokenizer.getNextToken("}"));
		assertEquals("", wordTokenizer.getNextToken());
	}
	
	@Test
	public void tokenizerLower() {
		wordTokenizer.setText("select {[Time].[2009]} WHERE ${brand}");
		assertEquals("select {[Time].[2009]} ", wordTokenizer.getNextToken("where"));
	}
	
	@Test
	public void hasNext() {
		wordTokenizer.setText("select {${brand.default([Brand].[Aliverti])}.Members}");
		
		assertEquals(true, wordTokenizer.hasNext("${"));
		assertEquals("select {", wordTokenizer.getNextToken("${"));
		assertEquals(false, wordTokenizer.hasNext("${"));
	}
}
