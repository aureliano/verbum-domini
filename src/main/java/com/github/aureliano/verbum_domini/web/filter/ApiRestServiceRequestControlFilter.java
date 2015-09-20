package com.github.aureliano.verbum_domini.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.web.ServiceRequestController;

@WebFilter(
	filterName = "ApiRestServiceRequestControlFilter",
	displayName = "API Rest Service Request Control Filter",
	description = "Prevent user abuses",
	urlPatterns = "/verbumdomini/apirest/*"
)
public class ApiRestServiceRequestControlFilter implements Filter {

	private static final Logger logger = Logger.getLogger(ApiRestServiceRequestControlFilter.class);
	
	private ServiceRequestController serviceRequestController;
	
	public ApiRestServiceRequestControlFilter() {
		this.serviceRequestController = ServiceRequestController.instance();
	}

	@Override
	public void destroy() { }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		logger.warn("API Rest Service request control filter is disabled!");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("Initializing servlet filter " + filterConfig.getFilterName());
		
		logger.info("Application configuration for preventing users abuses.");
		logger.info("Maximum requests per hour: " + this.serviceRequestController.getMaxRequestsPerHour());
		logger.info("Delay between requests: " + this.serviceRequestController.getDelayBetweenRequests());
		logger.info("Maximum threads per user: " + this.serviceRequestController.getMaxThreadsPerUser());
	}
}