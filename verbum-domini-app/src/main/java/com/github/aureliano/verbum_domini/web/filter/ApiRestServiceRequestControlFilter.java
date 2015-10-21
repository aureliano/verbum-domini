package com.github.aureliano.verbum_domini.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.web.ServiceRequestController;
import com.github.aureliano.verbum_domini.core.web.ServiceRequestStatus;
import com.github.aureliano.verbum_domini.helper.WebHelper;

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
		
		String ipAddress = WebHelper.retrieveIPAddress(request);
		ServiceRequestStatus status = this.serviceRequestController.track(ipAddress);
		
		if (ServiceRequestStatus.SUCCESS.equals(status)) {
			try {
				chain.doFilter(request, response);
			} finally {
				this.serviceRequestController.removeRequestThread(ipAddress);
			}
		} else {
			logger.warn("Request denied. User: " + ipAddress + " Code: " + status);
			
			StringBuilder message = new StringBuilder()
				.append("User request denied by politics against abuses control. ")
				.append(WebHelper.buildPreventingAbuseMessage(status));
			this.serviceRequestController.removeRequestThread(ipAddress);
			
			throw new WebApplicationException(message.toString(), Response.Status.FORBIDDEN);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("Initializing servlet filter " + filterConfig.getFilterName());
		
		logger.info("Application configuration for preventing users abuses.");
		logger.info("Maximum requests per hour: " + this.serviceRequestController.getMaxRequestsPerHour());
		logger.info("Interval between requests: " + this.serviceRequestController.getDelayBetweenRequests());
		logger.info("Maximum threads per user: " + this.serviceRequestController.getMaxThreadsPerUser());
	}
}