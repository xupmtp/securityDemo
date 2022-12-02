package com.xupmtp.securitydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
// 啟用方法驗證註解
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@MapperScan(basePackages = "com.xupmtp.securitydemo.mapper") // 指定mapper package路徑
public class SecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}

}
