package kudewe.reports.repository.olap;

import java.util.Map;
import java.util.StringTokenizer;

import kudewe.reports.model.Filter;
import kudewe.reports.repository.QueryBuilder;
import kudewe.reports.repository.WordTokenizer;


/**
 * Build a query binding filter values
 * @author fer
 *
 */
public class QueryBuilderOlap implements QueryBuilder {
	private final String startToken = "${";
	private final String endToken = "}";
	private WordTokenizer wordTokenizerSingleton;
	
	/**
	 * Setter injection
	 * @param wordTokenizer
	 */
	public void setWordTokenizer(WordTokenizer wordTokenizer) {
		wordTokenizerSingleton = wordTokenizer;
	}
	
	/**
	 * Build a query binding a query string with filters  
	 * @param query Query string
	 * @param filters Collection of filtersf
	 * @return Query with filter values
	 */
	@Override
	public String buildQuery(String query, Map<String, Filter> filters) {
		StringBuilder queryBinded = new StringBuilder();
		
		WordTokenizer wordTokenizer = wordTokenizerSingleton.clone();
		wordTokenizer.setText(query);
		
		// Iterate over internal dsl expressions
		while(wordTokenizer.hasNext(startToken)) {
			// get query part
			String queryPart = wordTokenizer.getNextToken(startToken);
			
			// get internal dsl expression
			String dslExpression = wordTokenizer.getNextToken(endToken);
			
			// get filter value, eval internal dsl expression
			String filterValue = evalDslExpression(dslExpression, filters);
			
			// Append query binded
			queryBinded.append(queryPart);
			if (filterValue != null) {
				queryBinded.append(filterValue);
			}
		}
		queryBinded.append(wordTokenizer.getNextToken());
		
		return cleanWhereFilters(queryBinded.toString());
	}
	
	/**
	 * Remove unused filters in where expression
	 * @param query
	 * @return
	 */
	private String cleanWhereFilters(String query) {
		StringBuilder newQuery = new StringBuilder();
		
		WordTokenizer wordTokenizer = wordTokenizerSingleton.clone();
		wordTokenizer.setText(query);
		
		if (wordTokenizer.hasNext("where")) {
			newQuery.append(wordTokenizer.getNextToken("where"));
			StringBuilder queryWhere = new StringBuilder();
			
			// Iterate over filters
			StringTokenizer filterTokenizer = new StringTokenizer(wordTokenizer.getNextToken(), ",()\n\t");
			while(filterTokenizer.hasMoreElements()) {
				String filter = filterTokenizer.nextToken().trim();
				if (filter.length() > 0) {
					if (queryWhere.length() > 0) {
						queryWhere.append(", ");
					}
					queryWhere.append(filter);
				}
			}
				
			if (queryWhere.length() > 0) {
				newQuery.append("WHERE (").append(queryWhere.toString()).append(")");
			}
		} else {
			newQuery.append(query);
		}
		
		return newQuery.toString();
	}
	
	/**
	 * Evaluate DSL expression and return filter value
	 * @param dslExpression
	 * @param filters
	 * @return
	 */
	private String evalDslExpression(String dslExpression, Map<String, Filter> filters) {
		// Get filter name
		StringTokenizer dslTokenizer = new StringTokenizer(dslExpression, ".(");
		String filterName = dslTokenizer.nextToken();
		
		// Get filter
		Filter filter;
		
		filter = getFilter(filters, filterName);
		
		Filter currentFilter = filter;
		while (dslTokenizer.hasMoreElements()) {
			String token =  dslTokenizer.nextToken();
			
			if ("default".equals(token)) {
				currentFilter = currentFilter.defaultValue(getDslExpressionValue(dslTokenizer));
			} else if("parent".equals(token)) {
				String parentName = getDslExpressionValue(dslTokenizer);
				Filter parent = getFilter(filters, parentName);
				currentFilter = currentFilter.parent(parent);
			}
		}
		
		return filter.getSelectedValue();
	}

	private Filter getFilter(Map<String, Filter> filters, String filterName) {
		Filter filter;
		if (filters.containsKey(filterName)) {
			filter = filters.get(filterName);
		} else {
			// If not, remove filter from query
			filter = new Filter(filterName);
		}
		return filter;
	}
	
	/**
	 * Get dsl expression value from a tokenizer
	 * @param dslTokenizer
	 * @return
	 */
	private String getDslExpressionValue(StringTokenizer dslTokenizer) {
		StringBuilder valueBuilder = new StringBuilder();
		boolean closeMethod = false;
		while (dslTokenizer.hasMoreElements() && !closeMethod) {
			if (valueBuilder.length() > 0) {
				valueBuilder.append(".");
			}
			String value = dslTokenizer.nextToken(); 
			if (value.endsWith(")")) {
				value = value.substring(0, value.length() - 1);
				closeMethod = true;
			}
			valueBuilder.append(value);
		}
		return valueBuilder.toString();
	}
}
