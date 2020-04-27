package com.example.springvirtualstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeStoreController {

	@GetMapping("/")
	public String getHomeStore(Model model) {
		model.addAttribute("contents", "blank :: blank_contents");
		return "/home";
	}

	@PostMapping("/logout")
	public String postLogout() {

		return "redirect:/home";
	}

	// TODO 購入処理
	@PostMapping("/")
	public void postBuyBtnRequest(@RequestParam() String str, Model model) {

	}
}
