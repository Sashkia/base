package com.common.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.common.service.UserService;

@Controller
public class BasicController {
	@Autowired
	public UserService userService;

	
	@RequestMapping(value = { "/", "/log**" }, method = RequestMethod.GET)
	public ModelAndView start() {
		
		return new ModelAndView("/log");		
	
	}

	@RequestMapping(value = "/index")
	public ModelAndView index(Map<String, Object> map,
			HttpServletRequest request) {
		map.put("User", userService.listContact(request
				.getUserPrincipal().getName()));
		return new ModelAndView("/index");
	}

}
