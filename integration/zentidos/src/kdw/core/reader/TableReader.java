package kdw.core.reader;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class TableReader implements ItemReader<String>{
	private List<String> tables;
	private int index = 0;
	
	public void setTables(List<String> tables) {
		this.tables = tables;
	}
	
	public String read() throws Exception, UnexpectedInputException, ParseException {
		String table = null;
		if (tables != null && index < tables.size()) {
			table = tables.get(index);
			index++;
		}
		return table;
	}
}
