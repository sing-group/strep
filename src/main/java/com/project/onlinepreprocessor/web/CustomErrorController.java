package com.project.onlinepreprocessor.web;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;

/**
 * This controller responds to all requests related to errors
 */
@Controller
public class CustomErrorController implements ErrorController
{
	@RequestMapping("/error")
	public String handleError(HttpServletRequest httpRequest)
	{
		Object httpCode = httpRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(httpCode != null)
		{
			Integer code = Integer.valueOf(httpCode.toString());
			
			switch(code) {
				case 404:
					return "error-404";
				case 500:
					return "error-500";
				default:
					return "error";
						}
		}
		
		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	
}