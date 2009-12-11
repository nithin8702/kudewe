package kudewe.reports.repository.googlespreadsheet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import kudewe.reports.repository.LoadDB;


import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class LoadDBGoogleSpreadSheet implements LoadDB {
	private Connection connection = null;
	private SpreadsheetService service;
	
	/* (non-Javadoc)
	 * @see tocla.repository.googlespreadsheet.LoadDB#loadDB(java.lang.String)
	 */
	public void loadDB(String dataBaseAlias)
	{
		if (isGoogleSpreadSheet(dataBaseAlias)) {
			
			// login google spreadsheet
			try {
				connectGoogleSpreadSheet("fernando.claverino", "aconcagua");
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// login local db
			try {
				connectLocalDB();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    try {
		    	FileInputStream fstream;
		    	fstream = new FileInputStream(getGoogleSpreadSheetConfigFileName(dataBaseAlias));
		    	
		    	// Get the object of DataInputStream
			    DataInputStream in = new DataInputStream(fstream);
			    BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    
		    	//Read File Line By Line
		    	String strLine;
				while ((strLine = br.readLine()) != null)   {
					String[] entry = strLine.split("=");
					if (entry.length == 2) {
						loadTable(new URL(entry[1]), entry[0]);
					}
				}
				//Close the input stream
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String getGoogleSpreadSheetConfigFileName(String dataBaseAlias) {
		return "/home/fer/workspace/toclasaas/toclasaas/data/google/hsqldb/" + dataBaseAlias + ".googleSpreadSheet";
	}
	
	private boolean isGoogleSpreadSheet(String dataBaseAlias) {
		 File f = new File(getGoogleSpreadSheetConfigFileName(dataBaseAlias));
		 return f.exists();
	}
	
	private void connectLocalDB() throws ClassNotFoundException, SQLException
	{
		// Load the HSQL Database Engine JDBC driver
        // hsqldb.jar should be in the class path or made part of the current jar
        Class.forName("org.hsqldb.jdbcDriver");
        
		connection = DriverManager.getConnection("jdbc:hsqldb:file:/home/fer/workspace/toclasaas/toclasaas/data/google/hsqldb/db", "sa", "");
	}
	
	private void loadTable(URL listFeedUrl, String tableName) throws IOException, ServiceException, SQLException {
		ListFeed feed = service.getFeed(listFeedUrl, ListFeed.class);
		Statement statement = connection.createStatement();
		
    	statement.executeUpdate("delete from " + tableName);
    	
	    for (ListEntry entry : feed.getEntries()) {
	    	StringBuilder fields = new StringBuilder(); 
	    	StringBuilder values = new StringBuilder();
	    	
	    	for (String tag : entry.getCustomElements().getTags()) {
	    	    if (fields.length() > 0) {
	    	    	fields.append(", ");
	    	    }
	    		fields.append(tag);  
	    		
	    		if (values.length() > 0) {
	    			values.append(", ");
	    	    }
	    		values.append("'" + entry.getCustomElements().getValue(tag) + "'");
	    	}
	    	
	    	String sql = "insert into " + tableName + " (" + fields.toString() + ") values (" + values.toString() + ")";
	    	statement.executeUpdate(sql);
	    }	
	    
	    connection.commit();
	}
	
	/**
	 * Log in to Google, under a Google Spreadsheets account.
	 * 
	 * @param username name of user to authenticate (e.g. yourname@gmail.com)
	 * @param password password to use for authentication
	 * @throws AuthenticationException if the service is unable to validate the
	 *         username and password.
	 */
	private void connectGoogleSpreadSheet(String username, String password) throws AuthenticationException {
		service = new SpreadsheetService("tocla");
		// Authenticate
		service.setUserCredentials(username, password);
	}
}
