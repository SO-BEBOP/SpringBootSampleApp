package com.example.springvirtualstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springvirtualstore.domain.model.BusinessTbl;
import com.example.springvirtualstore.domain.model.CartInfo;
import com.example.springvirtualstore.domain.repository.UserDetailsImpl;
import com.example.springvirtualstore.domain.service.BusinessService;
import com.example.springvirtualstore.domain.service.CartService;
import com.example.springvirtualstore.domain.service.InformationService;

@Controller
public class CartInfoController {

	@Autowired
	private CartService cartService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private InformationService infomationService;

	@GetMapping("/cart_info")
	public String getCartList(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

		model.addAttribute("contents", "cart_info :: cartinfo_contents");
		List<CartInfo> cartInfoList = infomationService.getUserCartInfoList(userDetails.getUserId());
		model.addAttribute("cartInfoList", cartInfoList);

		int totalPrice = 0;
		if (cartInfoList != null) {
			for (CartInfo cartPrice : cartInfoList) {
				totalPrice += cartPrice.getCart_price();
			}
		}
		model.addAttribute("total", totalPrice);

		return "home";
	}

	//取引削除用処理.
	@PostMapping(path = "/cart_info", params = "buy")
	public String postCartDetailBuy(
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			@RequestParam Integer total, Model model) {

		boolean result = false;

		System.out.println("購入ボタンの処理");
		result = cartService.updateOneFromStateParam(userDetails.getUserId());

		BusinessTbl businessTbl = new BusinessTbl();
		businessTbl.setBusiness_user_id(userDetails.getUserId());
		businessTbl.setBusiness_sales(total);
		result = businessService.insert(businessTbl);

		if (result == true) {
			System.out.println("OK");
		} else {
			System.out.println("NG");
		}

		//一覧画面を表示
		return getCartList(userDetails, model);
	}

	//取引削除用処理.
	@PostMapping(path = "/cart_info", params = "delete")
	public String postCartDetailDelete(
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			@RequestParam String id, Model model) {
		System.out.println("削除ボタンの処理");

		boolean result = cartService.deleteOne(String.valueOf(id));
		if (result == true) {
			model.addAttribute("result", "削除成功");
		} else {
			model.addAttribute("result", "削除失敗");
		}
		//取引一覧画面を表示
		return getCartList(userDetails, model);
	}
}
