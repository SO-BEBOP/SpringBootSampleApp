package com.example.springvirtualstore.domain.form;

import javax.validation.constraints.NotBlank;

import com.example.springvirtualstore.domain.valid.ValidGroup1;

import lombok.Data;

@Data
public class ProductRegistForm {

	private int productId;

	@NotBlank(groups = ValidGroup1.class)
	private String productName;

	private int price;

	private int stock;

}
