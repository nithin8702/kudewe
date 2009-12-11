package kudewe.reports.test.repository;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import kudewe.reports.model.Filter;
import kudewe.reports.repository.QueryBuilder;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class QueryBuilderTest extends BaseTest {
	@Autowired
	private QueryBuilder queryBuilder;
	
	@Test
	public void buildQueryWithOutFilters() {
		Map<String, Filter> filters = getEmptyFilters();
		String query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WHERE [Time].[All]";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WHERE ([Time].[All])";
		assertEquals(queryExpected, queryBinded);
		
		query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WHERE ([Time].[All], ${brand}, ${month})";
		queryBinded = queryBuilder.buildQuery(query, filters);
		assertEquals(queryExpected, queryBinded);
		
		query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WHERE (${brand}, ${month})";
		queryBinded = queryBuilder.buildQuery(query, filters);
		queryExpected = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] ";
		assertEquals(queryExpected, queryBinded);
		
		query = "select {[Measures].[SalePrice]} ON COLUMNS,\n{[Products].members} ON ROWS\nfrom [Sales]\nWHERE ${brand}\n \t";
		queryBinded = queryBuilder.buildQuery(query, filters);
		queryExpected = "select {[Measures].[SalePrice]} ON COLUMNS,\n{[Products].members} ON ROWS\nfrom [Sales]\n";
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithOutWhere() {
		Map<String, Filter> filters = getEmptyFilters();
		String query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales]";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = query;
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithFilterBrand() {
		Map<String, Filter> filters = getFilters();
		
		String query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where ([Time].[All], ${brand})";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WHERE ([Time].[All], [Brand].[Aliverti])";
		
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithFilterMonth() {
		Map<String, Filter> filters = getFilters();
		
		String query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where ${month}";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WHERE ([Time].[2009].[01].[01])";
		
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithTwoFilters() {
		Map<String, Filter> filters = getFilters();
		
		String query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where (${month}, ${product})";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WHERE ([Time].[2009].[01].[01], [Product].[Mochila Ataque])";
		
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithFilterOnWhereAfterComma() {
		Map<String, Filter> filters = getEmptyFilters();
		
		// After comma
		String query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where ([Time].[2009],${brand})";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WHERE ([Time].[2009])";
		assertEquals(queryExpected, queryBinded);
		
		// After comma and space
		query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where ([Time].[2009], ${brand})";
		queryBinded = queryBuilder.buildQuery(query, filters);
		assertEquals(queryExpected, queryBinded);
		
		// After comma and multiple spaces
		query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where ([Time].[2009],    ${brand})";
		queryBinded = queryBuilder.buildQuery(query, filters);
		assertEquals(queryExpected, queryBinded);
		
		// 2 filters
		query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where ([Time].[2009],    ${brand}, ${client})";
		queryBinded = queryBuilder.buildQuery(query, filters);
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithFilterOnWhereAfterWhere() {
		Map<String, Filter> filters = getEmptyFilters();
		
		// After where
		String query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where ${brand}";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] ";
		assertEquals(queryExpected, queryBinded);
		
		// After where and multiple spaces
		query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where     ${brand}";
		queryBinded = queryBuilder.buildQuery(query, filters);
		assertEquals(queryExpected, queryBinded);
		
		// Different case
		query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WheRE ${brand}";
		queryBinded = queryBuilder.buildQuery(query, filters);
		assertEquals(queryExpected, queryBinded);
		
		// 2 filters
		query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WheRE (${brand}, ${client})";
		queryBinded = queryBuilder.buildQuery(query, filters);
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithFilterOnWhereAfterWhereBeforeCondition() {
		Map<String, Filter> filters = getEmptyFilters();
		
		// After where, before condition
		String query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where (${brand}, [Time].[2000])";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] WHERE ([Time].[2000])";
		assertEquals(queryExpected, queryBinded);
		
		// After where, before condition
		query = "select {[Measures].[SalePrice]} ON COLUMNS, {[Products].members} ON ROWS from [Sales] where (${brand}, ${client}, [Time].[2000])";
		queryBinded = queryBuilder.buildQuery(query, filters);
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithRowMonth() {
		Map<String, Filter> filters = getFilters();
		
		String query = "select {[Measures].[SalePrice]} ON COLUMNS,NON EMPTY {${month}} ON ROWS from [Sales] where [Products].[Jacket]";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Measures].[SalePrice]} ON COLUMNS,NON EMPTY {[Time].[2009].[01].[01]} ON ROWS from [Sales] WHERE ([Products].[Jacket])";
		
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithColMonth() {
		Map<String, Filter> filters = getFilters();
		
		String query = "select {${month}} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] where [Products].[Jacket]";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Time].[2009].[01].[01]} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] WHERE ([Products].[Jacket])";
		
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithMonthDefault() {
		Map<String, Filter> filters = getEmptyFilters();
		
		String query = "select {${month.default([Time].[2009])}} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] where [Products].[Jacket]";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Time].[2009]} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] WHERE ([Products].[Jacket])";
		
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithMonthDefaultAndValue() {
		Map<String, Filter> filters = getFilters();
		
		String query = "select {${month.default([Time].[2009])}} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] where [Products].[Jacket]";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Time].[2009].[01].[01]} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] WHERE ([Products].[Jacket])";
		
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithMonthDefaultAndMembers() {
		Map<String, Filter> filters = getEmptyFilters();
		
		String query = "select {${month.default([Time].[2009])}.members} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] where [Products].[Jacket]";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {[Time].[2009].members} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] WHERE ([Products].[Jacket])";
		
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithParent() {
		Map<String, Filter> filters = getFilters();
		
		String query = "select {...} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] where ${month.parent(year2009)}";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {...} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] WHERE ([Time].[2009].[01].[01])";
		assertEquals(queryExpected, queryBinded);
		
		query = "select {...} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] where ${month.parent(year2008)}";
		queryBinded = queryBuilder.buildQuery(query, filters);
		queryExpected = "select {...} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] WHERE ([Time].[2008])";
		assertEquals(queryExpected, queryBinded);
	}
	
	@Test
	public void buildQueryWithParentAndDefault() {
		Map<String, Filter> filters = getFilters();
		
		String query = "select {...} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] where ${month2010.default([Time].[2010].[01].[01]).parent(year2010).default([Time].[2010])}";
		String queryBinded = queryBuilder.buildQuery(query, filters);
		String queryExpected = "select {...} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] WHERE ([Time].[2010].[01].[01])";
		assertEquals(queryExpected, queryBinded);
		
		query = "select {...} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] where ${month2010.default([Time].[2010].[01].[01]).parent(year2011).default([Time].[2011])}";
		queryBinded = queryBuilder.buildQuery(query, filters);
		queryExpected = "select {...} ON COLUMNS,NON EMPTY {[Measures].[SalePrice]} ON ROWS from [Sales] WHERE ([Time].[2011])";
		assertEquals(queryExpected, queryBinded);
	}
	
	private Map<String, Filter> getEmptyFilters() {
		return new HashMap<String, Filter>();
	}
	
	private Map<String, Filter> getFilters() {
		Map<String, Filter> filters = getEmptyFilters();
		
		Filter filterBrand = new Filter("brand", "[Brand].[Aliverti]");
		filters.put(filterBrand.getName(), filterBrand);
		
		Filter filterMonth = new Filter("month", "[Time].[2009].[01].[01]");
		filters.put(filterMonth.getName(), filterMonth);
		
		Filter filterYear2009 = new Filter("year2009", "[Time].[2009]");
		filters.put(filterYear2009.getName(), filterYear2009);
		
		Filter filterYear2008 = new Filter("year2008", "[Time].[2008]");
		filters.put(filterYear2008.getName(), filterYear2008);
		
		Filter filterProduct = new Filter("product", "[Product].[Mochila Ataque]");
		filters.put(filterProduct.getName(), filterProduct);
		
		return filters;
	}
}
