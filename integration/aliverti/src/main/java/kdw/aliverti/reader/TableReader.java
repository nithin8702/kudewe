package kdw.aliverti.reader;

import java.util.List;

import kdw.aliverti.model.Table;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class TableReader implements ItemReader<Table>{
	private List<String> tables;
	private int index = 0;
	
	public void setTables(List<String> tables) {
		this.tables = tables;
	}
	
	public Table read() throws Exception, UnexpectedInputException, ParseException {
		Table table = null;
		if (tables != null && index < tables.size()) {
			table = new Table();
			table.setName(tables.get(index));
			index++;
		}
		return table;
	}



	
	
}
