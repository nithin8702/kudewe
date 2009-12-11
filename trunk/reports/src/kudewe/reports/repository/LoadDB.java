package kudewe.reports.repository;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gdata.util.ServiceException;

public interface LoadDB {

	public abstract void loadDB(String dataBaseAlias);

}