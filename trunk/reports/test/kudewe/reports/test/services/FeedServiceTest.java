package kudewe.reports.test.services;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kudewe.reports.services.FeedService;
import kudewe.reports.test.common.BaseTest;


public class FeedServiceTest extends BaseTest {
	@Autowired
	private FeedService feedService;
	
	@Test
	public void getRseFeed() {
		String feed = feedService.getRseFeed();
		assertNotNull(feed);
		assertFalse("Contains atom link", feed.contains("<link xmlns=\"http://www.w3.org/2005/Atom\""));
	}
}
