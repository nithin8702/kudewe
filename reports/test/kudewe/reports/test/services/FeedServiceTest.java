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
		assertTrue("Feed is empty", feed.length() > 0);
		assertFalse("Contains atom link", feed.contains("<link xmlns=\"http://www.w3.org/2005/Atom\""));
		
	}
	
	@Test
	public void getTwitterFeed() {
		String feed = feedService.getTwitterFeed();
		assertNotNull(feed);
		assertTrue("Feed is empty", feed.length() > 0);
		assertTrue("Contains doesn't contain <name>kudewe</name>", feed.contains("<name>kudewe</name>"));
		
	}
}
