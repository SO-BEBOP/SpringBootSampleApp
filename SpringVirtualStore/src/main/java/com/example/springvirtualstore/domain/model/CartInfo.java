package com.example.springvirtualstore.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class CartInfo {

	private int cart_id;
	private String user_name;
	private String product_name;
	private int cart_price;
	private int cart_state;
	private Date create_at;
	private Date updata_at;
}
