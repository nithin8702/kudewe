package kudewe.reports.services.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import kudewe.reports.services.FeedService;

public class FeedServiceImplementation implements FeedService {
	private String urlTwitterFeed;
	private String urlRseFeed;
	private String proxyHost;
	private String proxyPort;
	private String proxyUser;
	private String proxyPass;
	
	public void setUrlTwitterFeed(String urlTwitterFeed) {
		this.urlTwitterFeed = urlTwitterFeed;
	}
	
	public void setUrlRseFeed(String urlRseFeed) {
		this.urlRseFeed = urlRseFeed;
	}
	
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public void setProxyPass(String proxyPass) {
		this.proxyPass = proxyPass;
	}
	
	private String get(String url) {
		URL urlGet;
		URLConnection con;
		BufferedReader in = null;
		String line;
		StringBuilder result = new StringBuilder();
		
		if (proxyHost != null && proxyPort != null) {
			
			if (proxyUser != null && proxyPass != null) {
				Authenticator.setDefault(new SimpleAuthenticator(proxyUser, proxyPass));
			}
			
			Properties systemProperties = System.getProperties();
			systemProperties.setProperty("http.proxyHost", proxyHost);
			systemProperties.setProperty("http.proxyPort", proxyPort);
		}
		
		try {
			urlGet = new URL(url);
			con = urlGet.openConnection();
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			in.close();
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString().replaceAll("<link xmlns=\"http://www.w3.org/2005/Atom\"", "<linkatom xmlns=\"http://www.w3.org/2005/Atom\"");
	}

	@Override
	public String getTwitterFeed() {
		return get(urlTwitterFeed);
	}

	@Override
	public String getRseFeed() {
		return get(urlRseFeed);
	}

}
