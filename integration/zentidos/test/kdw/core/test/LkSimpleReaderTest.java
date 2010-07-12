package kdw.core.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import kdw.core.model.LkSimple;
import kdw.core.reader.LkSimpleReader;

import org.junit.Test;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.UnexpectedInputException;


public class LkSimpleReaderTest {
	
	@Test
	public void reader_empty_returnNull() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		// Arrange
		ItemReader<LkSimple> lkSimpleReader = new LkSimpleReader();
		
		// Act
		
		// Assert
		assertNull(lkSimpleReader.read());
	}
	
	@Test
	public void reader_with1Item_returnA() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		// Arrange
		List<String> items = new ArrayList<String>();
		items.add("A");

		LkSimpleReader lkSimpleReader = new LkSimpleReader();
		lkSimpleReader.setItems(items);
		
		// Act
		LkSimple item = lkSimpleReader.read();
		
		// Assert
		assertEquals("A", item.getName());
		assertEquals(1, item.getId());
		assertNull(lkSimpleReader.read());
	}

	@Test
	public void reader_with2Item_returnAandB() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		// Arrange
		List<String> items = new ArrayList<String>();
		items.add("A");
		items.add("B");

		LkSimpleReader lkSimpleReader = new LkSimpleReader();
		lkSimpleReader.setItems(items);
		
		// Act
		LkSimple item1 = lkSimpleReader.read();
		LkSimple item2 = lkSimpleReader.read();
		
		// Assert
		assertEquals("A", item1.getName());
		assertEquals(1, item1.getId());
		assertEquals("B", item2.getName());
		assertEquals(2, item2.getId());
		assertNull(lkSimpleReader.read());
	}
}
