package com.example.springvirtualstore.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springvirtualstore.domain.model.BusinessInfo;
import com.example.springvirtualstore.domain.model.BusinessTbl;
import com.example.springvirtualstore.domain.model.ProductMst;
import com.example.springvirtualstore.domain.model.UserMst;

@Service
public class InformationService {

	@Autowired
	UserService userService;

	@Autowired
	ProductService productService;

	@Autowired
	BusinessService businessService;

	public List<BusinessInfo> getBusinessInfoList() {

		List<BusinessTbl> businessTblAll = businessService.selectMany();
		List<BusinessInfo> businessInfoList = new ArrayList<>();

		for (BusinessTbl business : businessTblAll) {
			BusinessInfo businessInfo = new BusinessInfo();
			UserMst user = userService.selectOne(Integer.valueOf(business.getBusiness_user_id()).toString());
			ProductMst product = productService.selectOne(
					Integer.valueOf(business.getBusiness_product_id()).toString());

			businessInfo.setBusiness_id(business.getBusiness_id());
			businessInfo.setUser_name(user.getUser_name());
			businessInfo.setProduct_name(product.getProduct_name());
			businessInfo.setBusiness_sales(business.getBusiness_sales());
			businessInfo.setBusiness_state(business.getBusiness_state());
			businessInfo.setCreate_at(business.getCreate_at());
			businessInfo.setCreate_at(business.getUpdata_at());

			businessInfoList.add(businessInfo);

			System.out.println(">> DEBUG : " + businessInfoList);
		}
		return businessInfoList;

	}

}
