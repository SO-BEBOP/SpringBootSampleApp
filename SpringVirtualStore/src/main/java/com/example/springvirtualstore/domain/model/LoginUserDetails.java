package com.example.springvirtualstore.domain.model;

import lombok.Data;

@Data
public class LoginUserDetails {

	private int user_id;
	private String user_name;
	private String user_password;
	private String user_role;

}
