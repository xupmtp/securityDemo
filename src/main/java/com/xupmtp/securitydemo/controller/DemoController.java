package com.xupmtp.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: simon
 */
@RestController
@RequestMapping("test")
public class DemoController {

	@GetMapping("hello")
	public String hello() {
		return "Hello Security";
	}
}