package kudewe.reports.test.repository;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kudewe.reports.repository.IncludeParser;
import kudewe.reports.repository.UrlMapper;
import kudewe.reports.test.common.BaseTest;

public class IncludeParserFileSystemTest extends BaseTest {
	@Autowired
	private IncludeParser includeParser;
	
	@Resource
	private UrlMapper urlMapper;
	
	@Test
	public void oneInclude_parse() {
		String result = includeParser.parse(urlMapper.GetDashBoardDefinitionPath("sales/dashBoardOneInclude"));
		
		assertNotNull(result);
		assertTrue("Result is empty", result.length() > 0);
		assertTrue("Result doesn't contain <dashboard>", result.contains("<dashboard>"));
		assertTrue("Result doesn't contain <filters>", result.contains("<filters>"));
		assertFalse("Result contains <include", result.contains("<include"));
		assertTrue("Result doesn't contain <name>productsByMonth</name>", result.contains("<name>productsByMonth</name>"));
	}
	
	@Test
	public void withOutInlude_parse() {
		String result = includeParser.parse(urlMapper.GetDashBoardDefinitionPath("sales/dashBoardWithOutInclude"));
		
		assertNotNull(result);
		assertTrue("Result is empty", result.length() > 0);
		assertTrue("Result doesn't contain <dashboard>", result.contains("<dashboard>"));
		assertTrue("Result doesn't contain <filters>", result.contains("<filters>"));
		assertFalse("Result contains <include", result.contains("<include"));
	}
	
	@Test
	public void twoInclude_parse() {
		String result = includeParser.parse(urlMapper.GetDashBoardDefinitionPath("sales/dashBoardTwoInclude"));
		
		assertNotNull(result);
		assertTrue("Result is empty", result.length() > 0);
		assertTrue("Result doesn't contain <dashboard>", result.contains("<dashboard>"));
		assertTrue("Result doesn't contain <filters>", result.contains("<filters>"));
		assertFalse("Result contains <include", result.contains("<include"));
		assertTrue("Result doesn't contain <name>productsByMonth</name>", result.contains("<name>productsByMonth</name>"));
		assertTrue("Result doesn't contain <name>salesYearly</name>", result.contains("<name>salesYearly</name>"));
		assertTrue("Result doesn't contain <filter>", result.contains("</filter>"));
		assertTrue("Result doesn't contain <name>brand</name>", result.contains("<name>brand</name>"));
		assertTrue("Result doesn't contain <name>month</name>", result.contains("<name>month</name>"));
	}
	
	@Test
	public void parentInclude_parse() {
		String result = includeParser.parse(urlMapper.GetDashBoardDefinitionPath("sales/dashBoardParentInclude"));
		
		assertNotNull(result);
		assertTrue("Result is empty", result.length() > 0);
		assertTrue("Result doesn't contain <dashboard>", result.contains("<dashboard>"));
		assertTrue("Result doesn't contain <filters>", result.contains("<filters>"));
		assertFalse("Result contains <include", result.contains("<include"));
		assertTrue("Result doesn't contain <name>productsByMonth</name>", result.contains("<name>productsByMonth</name>"));
		assertTrue("Result doesn't contain <name>salesYearly</name>", result.contains("<name>salesYearly</name>"));
		assertTrue("Result doesn't contain <filter>", result.contains("</filter>"));
		assertTrue("Result doesn't contain <name>brand</name>", result.contains("<name>brand</name>"));
		assertTrue("Result doesn't contain <name>month</name>", result.contains("<name>month</name>"));
	}
	
	@Test
	public void allFiltersInclude_parse() {
		String result = includeParser.parse(urlMapper.GetDashBoardDefinitionPath("sales/dashBoardAllFiltersInclude"));
		
		assertNotNull(result);
		assertTrue("Result is empty", result.length() > 0);
		assertTrue("Result doesn't contain <dashboard>", result.contains("<dashboard>"));
		assertTrue("Result doesn't contain <filters>", result.contains("<filters>"));
		assertFalse("Result contains <include", result.contains("<include"));
		assertTrue("Result doesn't contain <name>productsByMonth</name>", result.contains("<name>productsByMonth</name>"));
		assertTrue("Result doesn't contain <name>salesYearly</name>", result.contains("<name>salesYearly</name>"));
		assertTrue("Result doesn't contain <filter>", result.contains("</filter>"));
		assertTrue("Result doesn't contain <name>brand</name>", result.contains("<name>brand</name>"));
		assertTrue("Result doesn't contain <name>month</name>", result.contains("<name>month</name>"));
	}
}
