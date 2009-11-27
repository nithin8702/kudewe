package kdw.aliverti.test;

import static org.junit.Assert.*;

import java.util.Calendar;

import kdw.aliverti.model.DateValue;
import kdw.aliverti.reader.LkTimeReader;

import org.junit.Test;
import org.springframework.batch.item.UnexpectedInputException;


public class LkTimeReaderTest {
	
	@Test
	public void readCurentYear() throws UnexpectedInputException, org.springframework.batch.item.ParseException, Exception {
		Calendar calendar = Calendar.getInstance();
		DateValue date;
		DateValue endDate = null;
		
		LkTimeReader reader = new LkTimeReader();
		reader.setStartYear(calendar.get(Calendar.YEAR));
		
		int count = 2;
		
		date = reader.read();
		assertEquals(1, date.getMonth());
		assertEquals(1, date.getDay());
		
		date = reader.read();
		assertEquals(1, date.getMonth());
		assertEquals(2, date.getDay());
		
		do {
			date = reader.read();
			if (date != null) {
				count ++;
				endDate = date;
			}
		} while (date != null);
		
		assertEquals(12, endDate.getMonth());
		assertEquals(31, endDate.getDay());
		
		// Get days count in year
		Calendar calendarLastDay = Calendar.getInstance();
		calendarLastDay.set(calendar.get(Calendar.YEAR), 11, 31);
		
		assertEquals(calendarLastDay.get(Calendar.DAY_OF_YEAR), count);
	}
	
}
