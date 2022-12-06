package com.xupmtp.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: simon
 */
@Controller
@RequestMapping("/main")
public class LoginController {

	@GetMapping("login")
	public String login() {
		return "login.html";
	}
}
