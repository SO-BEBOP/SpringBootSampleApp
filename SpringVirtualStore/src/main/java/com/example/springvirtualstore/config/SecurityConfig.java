package com.example.springvirtualstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//静的リソースを除外    
		//静的リソースへのアクセスには、セキュリティを適用しない
		web.ignoring().antMatchers(
				"/webjars/**",
				"/images/**",
				"/css/**",
				"/javascript/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// ログイン不要ページの設定 
		http
				.authorizeRequests()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/sass/**").permitAll()
				.antMatchers("/webfonts/**").permitAll()
				.antMatchers("/fonts/**").permitAll()
				.antMatchers("/fonts/**").permitAll()

				//権限無し直リンク許可
				.antMatchers("/").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/home").permitAll()
				.antMatchers("/signup").permitAll()

				// アクセス権限設定
				//	.antMatchers("/products_info").hasAuthority("ROLE_ ADMIN")

				//それ以外は直リンク禁止
				.anyRequest().authenticated();

		http
				.formLogin()
				//ログイン処理のパス
				.loginProcessingUrl("/login")
				//ログインページの指定
				.loginPage("/login")
				//ログイン失敗時の遷移先
				.failureUrl("/login")
				//ログインページのユーザー名
				.usernameParameter("userName")
				//ログインページのパスワード
				.passwordParameter("password");
		//ログイン成功後の遷移先指定
		//	.defaultSuccessUrl("/", true);

		http
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login");

		// CSRF対策を無効に設定（ 一時的）
		//	http.csrf().disable();

		// CSRFを無効にするURLを 設定     
		//		RequestMatcher csrfMatcher = new RestMatcher("/user_info/∗∗");
		//RESTのみCSRF対策を無効に設定
		//		http.csrf().requireCsrfProtectionMatcher(csrfMatcher);
	}

	// DefaultのUserDetailsを使う場合
	//	@Autowired
	//	private DataSource dataSource;
	//
	//	private static final String USER_SQL = "SELECT"
	//			+ " user_name,"
	//			+ " user_password,"
	//			+ " true"
	//			+ " FROM"
	//			+ " user_mst"
	//			+ " WHERE"
	//			+ " user_name = ?";
	//
	//	private static final String ROLE_SQL = "SELECT"
	//			+ " user_name,"
	//			+ " user_role"
	//			+ " FROM"
	//			+ " user_mst"
	//			+ " WHERE"
	//			+ " user_name = ?";

	// DefaultのUserDetailsを使う場合
	//	@Override
	//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//
	//		auth.jdbcAuthentication()
	//				.dataSource(dataSource)
	//				.usersByUsernameQuery(USER_SQL)
	//				.authoritiesByUsernameQuery(ROLE_SQL)
	//				.passwordEncoder(passwordEncoder());
	//	}
}
