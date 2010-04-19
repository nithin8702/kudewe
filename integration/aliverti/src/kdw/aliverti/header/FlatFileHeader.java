package kdw.aliverti.header;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

public class FlatFileHeader implements FlatFileHeaderCallback {
	
	private String header;
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public void writeHeader(Writer writter) throws IOException {
		writter.write(header);
	}

}
