package kdw.core.reader;

import java.util.List;

import kdw.core.model.LkSimple;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class LkSimpleReader implements ItemReader<LkSimple>{
	private List<String> items;
	private int index = 0;
	
	public void setItems(List<String> items) {
		this.items = items;
	}
	
	public LkSimple read() throws Exception, UnexpectedInputException,
			ParseException {
		LkSimple item = null;
		if (items != null && index < items.size()) {
			item = new LkSimple();
			item.setName(items.get(index));
			index++;
			item.setId(index);
		}
		return item;
	}
	
}
