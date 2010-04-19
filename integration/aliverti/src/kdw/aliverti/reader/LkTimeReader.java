package kdw.aliverti.reader;

import java.util.Calendar;
import kdw.aliverti.model.DateValue;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class LkTimeReader implements ItemReader<DateValue>{
	private Calendar currentCalendar;
	private int endYear = Calendar.getInstance().get(Calendar.YEAR);
	
	public void setStartYear(int startYear) throws java.text.ParseException {
		currentCalendar = Calendar.getInstance();
		currentCalendar.clear();
		currentCalendar.set(startYear, 0, 1);
	}
	
	public DateValue read() throws Exception, UnexpectedInputException,
			ParseException {
		DateValue dateValue = null;
		
		if (currentCalendar.get(Calendar.YEAR) <= endYear) {
			dateValue = new DateValue(currentCalendar.getTime());
			currentCalendar.add(Calendar.DATE, 1);
		}
		
		return dateValue;
	}
	
}
