package com.example.springvirtualstore.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springvirtualstore.domain.model.LoginUserDetails;
import com.example.springvirtualstore.domain.repository.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (username == null) {
			throw new UsernameNotFoundException(username + " is not found");
		}
		LoginUserDetails loginUser = userService.selectLoginUser(username);

		System.out.println(loginUser);

		return new UserDetailsImpl(loginUser);
	}
}