package com.example.springvirtualstore.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springvirtualstore.domain.model.CartTbl;
import com.example.springvirtualstore.domain.repository.UserDetailsImpl;
import com.example.springvirtualstore.domain.service.CartService;

@Controller
public class HomeStoreController {

	@Autowired
	private CartService cartService;

	@GetMapping("/")
	public String getHomeStore(Principal principal,
			@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		model.addAttribute("contents", "top_slider :: topslider_contents");

		if (userDetails != null) {
			System.out.println(
					"DEBUG UserDetails >>> " + userDetails.getUserId()
							+ " / " + userDetails.getUsername() + " / " + userDetails.getPassword());
		} else {
			System.out.println("DEBUG >>>" + "Not logged in.");
		}
		return "/home";
	}

	@RequestMapping(value = "/addcart", params = "productId", method = RequestMethod.POST)
	public String postAddCart(@AuthenticationPrincipal UserDetailsImpl userDetails,
			@RequestParam String productId, Model model) {

		if (userDetails == null) {
			model.addAttribute("flag", true);
			System.out.println("DEBUG >>> userDetails is NULL");
			return "redirect:/login";
		}

		System.out.println("DEBUG >>> カート登録処理");
		// insert用の変数     
		CartTbl cartTbl = new CartTbl();
		cartTbl.setCart_user_id(userDetails.getUserId());
		cartTbl.setCart_product_id(Integer.valueOf(productId));
		boolean result = cartService.insert(cartTbl);
		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}
		return "redirect:/";
	}

}
