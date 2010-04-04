package kdw.aliverti.test;

import java.util.ArrayList;
import java.util.List;

import kdw.aliverti.reader.TableReader;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.UnexpectedInputException;


public class TableReaderTest {
	
	@Test
	public void reader_empty_returnNull() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		// Arrange
		ItemReader<String> tableReader = new TableReader();
		
		// Act
		
		// Assert
		assertNull(tableReader.read());
	}
	
	@Test
	public void reader_with1Table_returnLkTime() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		// Arrange
		List<String> tables = new ArrayList<String>();
		tables.add("lk_time");

		TableReader tableReader = new TableReader();
		tableReader.setTables(tables);
		
		// Act
			
		// Assert
		assertEquals("lk_time", tableReader.read());
		assertNull(tableReader.read());
	}
	
	@Test
	public void reader_with2Tables_returnLkTimeAndLkProduct() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		// Arrange
		List<String> tables = new ArrayList<String>();
		tables.add("lk_time");
		tables.add("lk_product");

		TableReader tableReader = new TableReader();
		tableReader.setTables(tables);
		
		// Act
		
		// Assert
		assertEquals("lk_time", tableReader.read());
		assertEquals("lk_product", tableReader.read());
		assertNull(tableReader.read());
	}
}
