package com.example.springvirtualstore.domain.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("product_mst")
public class ProductMst {
	@Id
	private int product_id;
	private String product_name;
	private int product_price;
	private int product_stock;
	private int product_state;
	private Date final_sales;
	private Date create_at;
	private Date updata_at;
}
