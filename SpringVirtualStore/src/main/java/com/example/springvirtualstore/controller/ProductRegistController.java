package com.example.springvirtualstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springvirtualstore.domain.form.ProductRegistForm;
import com.example.springvirtualstore.domain.model.ProductMst;
import com.example.springvirtualstore.domain.service.ProductService;
import com.example.springvirtualstore.domain.valid.GroupOrder;

@Controller
public class ProductRegistController {

	@Autowired
	private ProductService productService;

	@GetMapping("/product_regist")
	public String getProductRegist(@ModelAttribute ProductRegistForm form, Model model) {
		model.addAttribute("contents", "product_regist :: productregist_contents");
		return "home";
	}

	@PostMapping("/product_regist")
	public String postProductRegist(@ModelAttribute @Validated(GroupOrder.class) ProductRegistForm form,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return getProductRegist(form, model);
		}
		System.out.println(form);

		ProductMst productMst = new ProductMst();

		productMst.setProduct_name(form.getProductName());
		productMst.setProduct_price(form.getPrice());
		productMst.setProduct_stock(form.getStock());

		boolean result = productService.insert(productMst);
		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}
		return "redirect:/products_info";
	}

}
