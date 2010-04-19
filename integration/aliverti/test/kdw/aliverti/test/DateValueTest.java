package kdw.aliverti.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import kdw.aliverti.model.DateValue;

import org.junit.Before;
import org.junit.Test;

public class DateValueTest {
	private DateValue date;
	private SimpleDateFormat formatter;
	
    @Before
    public void setUp() throws ParseException {
    	formatter = new SimpleDateFormat("yyyy/MM/dd");
        date = new DateValue(formatter.parse("2009/5/29")); 
    }
	
	@Test
	public void getValue() throws ParseException {
		assertEquals("20090529", date.getId());
		
		DateValue otherDate = new DateValue(formatter.parse("2009/7/4"));
		assertEquals("20090704", otherDate.getId());
	}
	
	@Test
	public void getYear() {
		assertEquals(2009, date.getYear());
	}

	@Test
	public void getQuarter() {
		assertEquals(2, date.getQuarter());
	}
	
	@Test
	public void getMonth() {
		assertEquals(5, date.getMonth());
	}
	
	@Test
	public void getMonthName() {
		assertEquals("Mayo 2009", date.getMonthName());
	}
	
	@Test
	public void getDay() {
		assertEquals(29, date.getDay());
	}
	
	@Test
	public void getDayName() {
		assertEquals("Vi", date.getDayName());
	}
}
