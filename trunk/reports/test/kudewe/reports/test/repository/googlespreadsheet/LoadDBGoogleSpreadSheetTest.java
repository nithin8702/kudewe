package kudewe.reports.test.repository.googlespreadsheet;

import java.io.IOException;
import java.sql.SQLException;

import kudewe.reports.repository.LoadDB;
import kudewe.reports.repository.googlespreadsheet.LoadDBGoogleSpreadSheet;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;

import com.google.gdata.util.ServiceException;



public class LoadDBGoogleSpreadSheetTest extends BaseTest {
	@Test
	public void loadDB() throws IOException, ServiceException, ClassNotFoundException, SQLException {
		LoadDB buildDB = new LoadDBGoogleSpreadSheet();
		buildDB.loadDB("db");
		//TODO faltan asserts
	}
	
	@Test
	public void loadDBUnExists() throws IOException, ServiceException, ClassNotFoundException, SQLException {
		LoadDB buildDB = new LoadDBGoogleSpreadSheet();
		buildDB.loadDB("dbUnExists");
	}
}
