package com.github.aureliano.verbum_domini.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.helper.UrlHelper;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.SessionKey;

public class CheckUserAuthenticationFilter implements Filter {

	private static final Logger logger = Logger.getLogger(CheckUserAuthenticationFilter.class);
	
	public CheckUserAuthenticationFilter() {
		super();
	}

	@Override
	public void destroy() { }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		Object user = session.getAttribute(SessionKey.USER_LOGIN.name());
		if (user != null) {
			chain.doFilter(request, response);
			return;
		}
		
		String ipAddress = WebHelper.retrieveIPAddress(request);
		String uri = ((HttpServletRequest) request).getRequestURI();
		logger.warn("User with IP address " + ipAddress + " tried to access secured resource " + uri);
		
		session.setAttribute(SessionKey.ACCESS_DENIED.name(), true);
		session.setAttribute(SessionKey.REQUESTED_URI.name(), uri);
		
		String url = UrlHelper.buildWebAppUrl(request, "sign-in.xhtml");
		((HttpServletResponse) response).sendRedirect(url);
	}

	@Override
	public void init(FilterConfig config) throws ServletException { }
}