/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.strep.web;

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
