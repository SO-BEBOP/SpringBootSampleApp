package com.example.springvirtualstore.domain.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("business_info_tbl")
public class BusinessTbl {
	@Id
	private int business_id;
	@Id
	private int business_user_id;
	private int business_sales;
	private int business_state;
	private Date create_at;
	private Date updata_at;
}
