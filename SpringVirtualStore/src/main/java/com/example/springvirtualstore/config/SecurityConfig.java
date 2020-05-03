package com.example.springvirtualstore.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private DataSource dataSource;

	private static final String USER_SQL = "SELECT"
			+ " user_name,"
			+ " user_password,"
			+ " true"
			+ " FROM"
			+ " user_mst"
			+ " WHERE"
			+ " user_name = ?";

	private static final String ROLE_SQL = "SELECT"
			+ " user_name,"
			+ " user_role"
			+ " FROM"
			+ " user_mst"
			+ " WHERE"
			+ " user_name = ?";

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
		// ポイント ３： 直 リンク の 禁止 
		// ログイン 不要 ページ の 設定 
		http
				.authorizeRequests()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/sass/**").permitAll()
				.antMatchers("/webfonts/**").permitAll()
				.antMatchers("/fonts/**").permitAll()

				//権限無し直リンク許可
				.antMatchers("/").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/home").permitAll()
				.antMatchers("/management").permitAll()
				.antMatchers("/signup").permitAll()

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
				.passwordParameter("password")
				//ログイン成功後の遷移先
				.defaultSuccessUrl("/", true);

		http
				.logout()
				.logoutRequestMatcher(
						new AntPathRequestMatcher("/logout"))
				.logoutUrl("/logout")
				.logoutSuccessUrl("/");

		// CSRF 対策 を 無効 に 設定（ 一時的）
		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(USER_SQL)
				.authoritiesByUsernameQuery(ROLE_SQL)
				.passwordEncoder(passwordEncoder());
	}
}
