package com.example.springvirtualstore.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springvirtualstore.domain.model.BusinessInfo;
import com.example.springvirtualstore.domain.model.BusinessTbl;
import com.example.springvirtualstore.domain.model.CartInfo;
import com.example.springvirtualstore.domain.model.CartTbl;
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

	@Autowired
	CartService cartService;

	public List<BusinessInfo> getBusinessInfoList() {

		List<BusinessTbl> businessTblAll = businessService.selectMany();
		List<BusinessInfo> businessInfoList = new ArrayList<>();

		for (BusinessTbl business : businessTblAll) {
			BusinessInfo businessInfo = new BusinessInfo();
			UserMst user = userService.selectOne(Integer.valueOf(business.getBusiness_user_id()).toString());

			businessInfo.setBusiness_id(business.getBusiness_id());
			businessInfo.setUser_name(user.getUser_name());
			businessInfo.setBusiness_sales(business.getBusiness_sales());
			businessInfo.setBusiness_state(business.getBusiness_state());
			businessInfo.setCreate_at(business.getCreate_at());
			businessInfo.setCreate_at(business.getUpdata_at());

			businessInfoList.add(businessInfo);

			System.out.println(">> DEBUG : " + businessInfoList);
		}
		return businessInfoList;

	}

	public List<CartInfo> getUserCartInfoList(Integer userId) {
		// 未処理の商品
		String cartState = "1";

		List<CartTbl> cartTblAll = cartService.selectManyFromStateParam(userId.toString(), cartState);
		List<CartInfo> cartInfoList = new ArrayList<>();

		for (CartTbl cart : cartTblAll) {
			CartInfo cartInfo = new CartInfo();
			UserMst user = userService.selectOne(Integer.valueOf(cart.getCart_user_id()).toString());
			ProductMst product = productService.selectOne(
					Integer.valueOf(cart.getCart_product_id()).toString());

			cartInfo.setCart_id(cart.getCart_id());
			cartInfo.setUser_name(user.getUser_name());
			cartInfo.setProduct_name(product.getProduct_name());
			cartInfo.setCart_price(product.getProduct_price());
			cartInfo.setCart_state(cart.getCart_state());
			cartInfo.setCreate_at(cart.getCreate_at());
			cartInfo.setUpdata_at(cart.getUpdata_at());

			cartInfoList.add(cartInfo);

		}
		System.out.println(">> DEBUG : " + cartInfoList);
		return cartInfoList;

	}

}
