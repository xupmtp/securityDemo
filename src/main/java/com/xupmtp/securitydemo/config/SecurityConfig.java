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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // 單純解決IEDA找不到HttpSecurity問題, 不加不影響執行
public class SecurityConfig {

	@Resource
	DataSource dataSource;

	@Bean
	// userDetailsService透過@Bean自動註冊, 不使用Autowired注入, 試試這種寫法
	SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
		// 登出, /test/logout記得加白名單,否則會導到登入頁
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/logout");

		// 設置無權限時導入的頁面
		http.exceptionHandling().accessDeniedPage("/403_page.html");

		return http.formLogin()// 啟用自定義表單頁面
						// 登入頁設置, 不加使用預設頁
						// 加入thymeleaf, 改用URL
						.loginPage("/main/login")
						.loginProcessingUrl("/main/login")
						//登入URL, 實現由框架完成, 只需指定需要的URL
						.loginProcessingUrl("/user/login")
						// 成功時導向頁面 要使用thymeleaf只能透過controller轉發
//						.defaultSuccessUrl("/main.html").permitAll()
						// 指定不需驗證的URL
						// 加入thymeleaf後, 登入URL記得加白名單, 否則會無限遞迴
						.and().authorizeHttpRequests().antMatchers("/main/login", "/test/logout").permitAll()
						// 指定URL需admins角色才可登入
//						.antMatchers("/test/hello").hasAnyAuthority("role", "admins")
						// role權限以"ROLE_XXX"開頭
//						.antMatchers("/test/hello").hasAnyRole("role", "admins")
						// 指定受保護的URL, 這裡是設置全部(除了上面的白名單)
						.anyRequest().authenticated()
						// 啟用自動登入(記住我)功能, 使用注入的datasource
						.and().rememberMe().tokenRepository(persistentTokenRepository())
						// 自動登入有效時間(秒)
						.tokenValiditySeconds(60)
						// 使用自訂的登入驗證機制
						.and().authenticationProvider(authenticationProvider(userDetailsService)).build();
	}

	/**
	 * 配置remember me功能使用到的數據來源(datasource)
	 */
	@Bean
	PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcRepository = new JdbcTokenRepositoryImpl();
		jdbcRepository.setDataSource(dataSource);
		// 無table會自動建立, 已建過需註解
//		jdbcRepository.setCreateTableOnStartup(true);
		return jdbcRepository;
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
