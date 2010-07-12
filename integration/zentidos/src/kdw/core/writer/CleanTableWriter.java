package kdw.core.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class CleanTableWriter extends SimpleJdbcDaoSupport implements ItemWriter<String> {
	
	public static final String DELETE_TABLE =
		"DELETE FROM ";
	
	public void write(List<? extends String> tables) throws Exception {
		// TODO Auto-generated method stub
		for (String table : tables) {
			deleteTable(table);
		}
	}

	private void deleteTable(String table) {
		getSimpleJdbcTemplate().getJdbcOperations().execute(DELETE_TABLE + table);
	}
}
