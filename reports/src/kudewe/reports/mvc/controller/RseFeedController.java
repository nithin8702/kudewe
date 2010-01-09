package kudewe.reports.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kudewe.reports.services.FeedService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * A controller for feed service
 * Execute a service that returns the #rse feed
 * @author fer
 *
 */
public class RseFeedController extends AbstractController {
	/**
	 * Service that return the Feeds
	 */
	private FeedService feedService;
	
	/**
	 * Setter injection
	 * @param feedService Service that return the Feeds
	 */
	public void setFeedService(FeedService feedService) {
		this.feedService = feedService;
	}
	
	/**
	 * Execute a service that returns the #rse feed
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Get filter
		String feed = feedService.getRseFeed();
		
		// Map to view
		return new ModelAndView("/jsp/feed.jsp", "feed", feed);
	}
}
