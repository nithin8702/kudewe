package kudewe.reports.services.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import kudewe.reports.services.FeedService;

public class FeedServiceImplementation implements FeedService {
	private String urlRseFeed;
	
	public void setUrlRseFeed (String urlRseFeed) {
		this.urlRseFeed = urlRseFeed;
	}
	
	@Override
	public String getRseFeed() {
		URL url;
		URLConnection con;
		BufferedReader in = null;
		String line;
		StringBuilder result = new StringBuilder();
		
		try {
			url = new URL(urlRseFeed);
			con = url.openConnection();
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString().replaceAll("<link xmlns=\"http://www.w3.org/2005/Atom\"", "<linkatom xmlns=\"http://www.w3.org/2005/Atom\"");
	}

}
