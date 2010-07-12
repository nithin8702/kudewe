package kdw.core.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.util.StringUtils;

public class DateValue {
	private Date date;
	private static SimpleDateFormat idFormatter = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
	private static SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
	private static SimpleDateFormat monthNameFormatter = new SimpleDateFormat("MMMM yyyy", new Locale("es", "AR"));
	private static SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
	private static SimpleDateFormat dayNameFormatter = new SimpleDateFormat("EEEE", new Locale("es", "AR"));
	private static SimpleDateFormat weekFormatter = new SimpleDateFormat("w");
	
	public DateValue(Date date) {
		this.date = date;
	}
	
	public String getId() {
		return idFormatter.format(date);
	}
	
	public int getYear() {
		return Integer.parseInt(yearFormatter.format(date));
	}
	
	public int getQuarter() {
		int month = Integer.parseInt(monthFormatter.format(date));
		int quarter = month / 3;
		if (month % 3 > 0) {
			quarter++;
		}
		return quarter;
	}
	
	public int getMonth() {
		return Integer.parseInt(monthFormatter.format(date));
	}
	
	public String getMonthName() {
		return StringUtils.capitalize(monthNameFormatter.format(date));
	}
	
	public int getDay() {
		return Integer.parseInt(dayFormatter.format(date));
	}
	
	public String getDayName() {
		return StringUtils.capitalize(dayNameFormatter.format(date).substring(0, 2));
	}
	
	public int getWeek() {
		return Integer.parseInt(weekFormatter.format(date));
	}
}
