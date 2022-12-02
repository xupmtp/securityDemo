package com.xupmtp.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 單純解決IEDA找不到HttpSecurity問題, 不加不影響執行
public class SecurityConfig {

	// userDetailsService透過@Bean自動註冊, 不使用Autowired注入, 試試這種寫法
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
		// 設置無權限時導入的頁面
		http.exceptionHandling().accessDeniedPage("/403_page.html");

		return http.formLogin()// 啟用自定義表單頁面
// 登入頁設置, 不加使用預設頁
						.loginPage("/login.html")
						//登入URL, 實現由框架完成, 只需指定需要的URL
						.loginProcessingUrl("/user/login")
						// 成功時導向頁面
						.defaultSuccessUrl("/test/index").permitAll()
						// 指定不需驗證的URL
						.and().authorizeHttpRequests().antMatchers("/pass/*").permitAll()
						// 指定URL需admins角色才可登入
//						.antMatchers("/test/hello").hasAnyAuthority("role", "admins")
						// role權限以"ROLE_XXX"開頭
//						.antMatchers("/test/hello").hasAnyRole("role", "admins")
						// 指定受保護的URL, 這裡是設置全部(除了上面的白名單)
						.anyRequest().authenticated()
						// 關閉CSRF保護
						.and().csrf().disable()
						// 使用自訂的登入驗證機制
						.authenticationProvider(authenticationProvider(userDetailsService)).build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
