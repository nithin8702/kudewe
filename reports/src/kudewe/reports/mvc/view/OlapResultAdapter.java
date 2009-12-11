package kudewe.reports.mvc.view;

import java.math.BigDecimal;

import mondrian.olap.Cell;
import mondrian.olap.Result;

/**
 * Adapter for olap result. It is used by the view to perform iterations over axis data using jstl.
 * @author fer
 *
 */
public class OlapResultAdapter {
	/**
	 * Olap result
	 */
	private Result result;
	
	/**
	 * Current row iterator
	 */
	private int row;
	
	/**
	 * Current col iterator
	 */
	private int col;
	
	/**
	 * Set the olap result
	 * @param result Olap result
	 */
	public void setResult(Result result) {
		this.result = result;
	}
	
	/**
	 * Return current row iterator and increment it
	 */
	public int getRow() {
		int oldRow = row;
		row++;
		col = 0;
		return oldRow;
	}

	/**
	 * Return current col iterator and increment it
	 */
	public int getCol() {
		int oldCol = col;
		col++;
		return oldCol;
	}
	
	/**
	 * Return current cell according row and col iterator
	 */
	public Cell getCell() {
		int[] pos = {col, row};
		return result.getCell(pos);
	}
	
	/**
	 * Return current cell according row and col iterator
	 */
	public BigDecimal getCellValueBigDecimal() {
		BigDecimal newValue = null;
		 
		try {
			String oldValue = getCell().getFormattedValue().replace(",", "");
			if (oldValue.trim().length() == 0) {
				oldValue = "0";
			}
			newValue = new BigDecimal(oldValue);
		} catch (NumberFormatException e){
			throw new NumberFormatException("value: " + getCell().getFormattedValue());
		}
		return newValue;
	}
	
	/**
	 * Clone current olap result wrapper
	 * @return A new instance of olap result wrapper
	 */
	public OlapResultAdapter clone() {
		return new OlapResultAdapter();
	}
}
