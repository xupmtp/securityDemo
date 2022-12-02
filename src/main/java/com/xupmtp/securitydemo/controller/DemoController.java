package com.xupmtp.securitydemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class DemoController {

	@GetMapping("hello")
	@Secured({"ROLE_adm", "ROLE_test1"})
	public String hello() {
		return "Hello Simon";
	}

	@GetMapping("index")
	public String index() {
		return "This is index";
	}

	@PostMapping("update")
	@PreAuthorize("hasAnyAuthority('admins')")
	public String update() {
		return "update string";
	}
	@PostMapping("update1")
	@PostAuthorize("hasAnyAuthority('admins')") // 執行完後才驗證權限
	public String update1() {
		System.out.println("update...");
		return "update string";
	}

	@GetMapping("logout")
	public String logout() {
		return "logout";
	}
}