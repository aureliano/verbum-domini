package com.github.aureliano.verbum_domini.helper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.web.ServiceRequestController;
import com.github.aureliano.verbum_domini.core.web.ServiceRequestStatus;

public final class WebHelper {

	private static final String IP_ADDRESS_REGEX = "\\d+\\.\\d+\\.\\d+\\.\\d+";
	
	private WebHelper() {
		super();
	}
	
	public static String retrieveIPAddress(ServletRequest request) {
		String ipAddress = ((HttpServletRequest) request).getHeader("Remote_Addr");
		if (checkIpAddress(ipAddress)) {
			return ipAddress;
		}
		
		ipAddress = ((HttpServletRequest) request).getHeader("HTTP_X_FORWARDED_FOR");
		if (checkIpAddress(ipAddress)) {
			return ipAddress;
		}
		
		ipAddress = ((HttpServletRequest) request).getHeader("X-Real-IP");
		if (checkIpAddress(ipAddress)) {
			return ipAddress;
		}
		
		ipAddress = request.getRemoteAddr();
		if (checkIpAddress(ipAddress)) {
			return ipAddress;
		}
		
		throw new VerbumDominiException("Could not find a valid client IP address. Expected to match " + IP_ADDRESS_REGEX);
	}
	
	public static String buildPreventingAbuseMessage(ServiceRequestStatus status) {
		ServiceRequestController controller = ServiceRequestController.instance();
		
		switch (status) {
			case INTERVAL_BETWEEN_REQUEST_DISOBEYED : return "Interval between requests not respected. Must be lesser ( < ) than " + controller.getDelayBetweenRequests();
			case MAX_REQUESTS_PER_HOUR_REACHED : return "Maximum requests per hour not respected. Must be lesser or equal ( <= ) to " + controller.getMaxRequestsPerHour();
			case MAX_THREADS_PER_USER_REACHED : return "Maximum threads per user not respected. Must be lesser or equal ( <= ) to " + controller.getMaxThreadsPerUser();
			default : throw new IllegalArgumentException("Unsupported service request status: " + status);
		}
	}
	
	protected static boolean checkIpAddress(String ip) {
		return ((ip != null) && (!ip.trim().isEmpty()) && (ip.matches(IP_ADDRESS_REGEX)));
	}
}