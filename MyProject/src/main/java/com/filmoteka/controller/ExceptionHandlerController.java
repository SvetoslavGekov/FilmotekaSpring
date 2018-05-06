package com.filmoteka.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {
	public static final String DEFAULT_ERROR_VIEW = "error";
	private static final String WRONG_URL = "Sorry, you've made an invalid request.";
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e){
			
		// Catch the exception and redirect it to the errors page.
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
	
	@ExceptionHandler({ TypeMismatchException.class })
	public ModelAndView handleNumberFormatException(Exception ex) throws Exception {
		// Catch the exception and redirect it to the errors page.
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", new Exception(WRONG_URL, ex));
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
}
