package com.example.springvirtualstore.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class BusinessInfo {

	private int business_id;
	private String user_name;
	private String product_name;
	private int business_sales;
	private int business_state;
	private Date create_at;
	private Date updata_at;
}
