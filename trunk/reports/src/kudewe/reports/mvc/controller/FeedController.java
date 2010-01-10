package kudewe.reports.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kudewe.reports.services.FeedService;
import mondrian.olap.InvalidArgumentException;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * A controller for feed service
 * Execute a service that performs HTTP GET over other domains
 * @author fer
 *
 */
public class FeedController extends AbstractController {
	/**
	 * Service that performs HTTP GET over other domains
	 */
	private FeedService FeedService;
	
	/**
	 * Setter injection
	 * @param feedService Service that performs HTTP GET over other domains
	 */
	public void setFeedService(FeedService FeedService) {
		this.FeedService = FeedService;
	}
	
	/**
	 * Execute a service that returns the #rse feed
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final String startUrlToken = "/feed/";
		final String endUrlToken = ".";
		
		// Get feed name
		String requestedUrl = request.getRequestURI();
		String feedName = requestedUrl.substring(requestedUrl.indexOf(startUrlToken) + startUrlToken.length(), requestedUrl.indexOf(endUrlToken));
		
		String feed;
		if ("rse".equals(feedName)) {
			feed = FeedService.getRseFeed();
		} else if ("twitter".equals(feedName)) {
			feed = FeedService.getTwitterFeed();
		} else {
			throw new IllegalArgumentException(feedName + "is not a valid feedName");
		}
		
		// Map to view
		return new ModelAndView("/jsp/feed.jsp", "feed", feed);
	}
}
