package com.github.aureliano.verbum_domini.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.helper.UrlHelper;
import com.github.aureliano.verbum_domini.web.SessionKey;

public class SignOutServlet extends HttpServlet {

	private static final long serialVersionUID = 4972890591345155712L;
	private static final Logger logger = Logger.getLogger(SignOutServlet.class);

	public SignOutServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object user = session.getAttribute(SessionKey.USER_LOGIN.name());
		
		session.invalidate();
		logger.info("User " + user + " has just signed out.");
		
		response.sendRedirect(UrlHelper.buildWebAppUrl(request, ""));
	}
}