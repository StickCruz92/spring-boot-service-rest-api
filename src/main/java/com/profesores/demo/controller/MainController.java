package com.profesores.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	
	@RequestMapping("/")
    @ResponseBody
	public String index() {
		String response = "Bienvenido a: <a href='https://www.google.com.co'> Click aquí</a>";
		
		return response;
	}
}
