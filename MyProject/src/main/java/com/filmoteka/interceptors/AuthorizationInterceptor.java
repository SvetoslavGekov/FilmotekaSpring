package com.filmoteka.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Get the session object
		System.out.println("Hey, I'm not in the controller yet");
		HttpSession session = request.getSession(false);

		// If there is no session at all -> nobody has logged in -> redirect to the login page
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendRedirect(request.getContextPath()+"/unauthorized");
			return false;
		}

		// Check if the session is newly created
		if (session.isNew()) {
			// Set the IP of the request which called the server
			session.setAttribute("ip", request.getRemoteAddr());
			
		}
		// If the session is old (not invalidated)
		else {
			// Check if the stored IP corresponds to the current caller IP or if there is a set user
			String sessionIp = (String) session.getAttribute("ip");

			if (sessionIp == null || !sessionIp.equals(request.getRemoteAddr()) || (session.getAttribute("USER") == null)) {
				// If not (potential session highjacking) -> invalidate session and redirect to the index
				session.invalidate();
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.sendRedirect(request.getContextPath()+"/unauthorized");
				return false;	
			}
		}

		// If the IP is the same as the one stored and there is a logged in user ->continue with the chain
		return true;
	}
}
