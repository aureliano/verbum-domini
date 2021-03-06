package com.github.aureliano.verbum_domini.helper;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.web.ServiceRequestController;
import com.github.aureliano.verbum_domini.core.web.ServiceRequestStatus;
import com.github.aureliano.verbum_domini.web.SessionKey;

public final class WebHelper {

	private static final Logger logger = Logger.getLogger(WebHelper.class);
	private static final String IP_ADDRESS_REGEX = "\\d+\\.\\d+\\.\\d+\\.\\d+";
	private static final Pattern PATTERN = Pattern.compile("[?&]?page=(\\d+)");
	
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
	
	public static String getServerIpAddress() {
		String host = null;
		
		try {
			InetAddress addr = InetAddress.getLocalHost();
			host = addr.getHostAddress();
		} catch (Exception ex) {
			logger.warn("Not able to get server host address.", ex);
			host = "unknown";
		}

		return host;
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
	
	public static void sendRedirect(String url) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		try {
			context.redirect(url);
		} catch (IOException ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	public static void addMessagesToContext(List<FacesMessage> messages) {
		FacesContext context = FacesContext.getCurrentInstance();
		for (FacesMessage message : messages) {
			context.addMessage(null, message);
		}
	}
	
	public static void addMessageToContext(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static void addMessageInfoToContext(String message) {
		addMessageToContext(new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}
	
	public static void setSessionAttribute(String key, Object value) {
		HttpSession session = getSession();
		if (session == null) {
			throw new VerbumDominiException("There is no session.");
		}
		
		session.setAttribute(key, value);
	}
	
	public static Object getSessionAttribute(String key) {
		HttpSession session = getSession();
		if (session == null) {
			throw new VerbumDominiException("There is no session.");
		}
		
		return session.getAttribute(key);
	}
	
	public static Object removeSessionAttribute(String key) {
		HttpSession session = getSession();
		if (session == null) {
			throw new VerbumDominiException("There is no session.");
		}
		
		Object value = session.getAttribute(key);
		session.removeAttribute(key);
		
		return value;
	}
	
	public static Object getRequestParameter(String key) {
		return getRequest().getParameter(key);
	}
	
	public static void showFacesMessageIfExist() {
		Object message = WebHelper.removeSessionAttribute(SessionKey.INFO_MESSAGE.name());
		if (message != null) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, message.toString(), message.toString());
			WebHelper.addMessageToContext(fm);
		}
	}
	
	public static Integer getCurrentDataPage() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (StringUtils.isEmpty(request.getQueryString())) {
			return 1;
		}
		
		Matcher matcher = PATTERN.matcher(request.getQueryString());
		
		String page = null;
		if (matcher.find()) {
			page = matcher.group(1);
		}
		
		return (page != null) ? Integer.parseInt(page) : 1;
	}
	
	public static Integer getEntityIdFromRequest(String key) {
		Object id = WebHelper.getRequestParameter(key);
		if (id == null) {
			return null;
		}
		
		return Integer.parseInt(id.toString());
	}
	
	private static HttpSession getSession() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		
		return request.getSession();
	}
	
	public static HttpServletRequest getRequest() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return (HttpServletRequest) context.getRequest();
	}
	
	protected static boolean checkIpAddress(String ip) {
		return ((ip != null) && (!ip.trim().isEmpty()) && (ip.matches(IP_ADDRESS_REGEX)));
	}
}