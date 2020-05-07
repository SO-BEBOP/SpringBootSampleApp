package com.example.springvirtualstore.domain.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("cart_tbl")
public class CartTbl {
	@Id
	private int cart_id;
	private int cart_user_id;
	private int cart_product_id;
	private int cart_state;
	private Date create_at;
	private Date updata_at;
}
