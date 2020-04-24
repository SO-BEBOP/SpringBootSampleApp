package com.example.springvirtualstore.domain.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("user_mst")
public class UserMst {
	@Id
	private int user_id;
	private String user_name;
	private String user_password;
	private Date user_birthday;
	private String user_gender;
	private int user_state;
	private String user_role;
	private Date create_at;
	private Date updata_at;
}
