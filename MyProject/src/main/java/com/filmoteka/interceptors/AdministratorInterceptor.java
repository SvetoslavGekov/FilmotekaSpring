package com.filmoteka.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.filmoteka.model.User;



public class AdministratorInterceptor extends AuthorizationInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//Check if the parent preHandle returns true
		if(!super.preHandle(request, response, handler)) {
			return false;
		}
		
		//Check if user is admin
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("USER");
		if(!user.getIsAdmin()) {
			session.invalidate();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendRedirect(request.getContextPath()+"/unauthorized");
			return false;	
		}

		// If the IP is the same as the one stored and there is a logged in user ->continue with the chain
		return true;
	}
}
