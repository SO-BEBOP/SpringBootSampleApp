package com.example.springvirtualstore.domain.form;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.springvirtualstore.domain.valid.ValidGroup1;
import com.example.springvirtualstore.domain.valid.ValidGroup2;
import com.example.springvirtualstore.domain.valid.ValidGroup3;

import lombok.Data;

@Data
public class SignupForm {

	private int userId;

	@NotBlank(groups = ValidGroup1.class)
	private String userName;

	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 4, max = 100, groups = ValidGroup2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$", groups = ValidGroup3.class)
	private String password;

	@NotNull(groups = ValidGroup1.class)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date birthday;

	private String gender;
}
