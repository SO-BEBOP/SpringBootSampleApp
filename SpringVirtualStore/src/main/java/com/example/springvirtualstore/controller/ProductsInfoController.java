package com.example.springvirtualstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springvirtualstore.domain.form.ProductRegistForm;
import com.example.springvirtualstore.domain.model.ProductMst;
import com.example.springvirtualstore.domain.service.ProductService;

@Controller
public class ProductsInfoController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products_info")
	public String getProductList(Model model) {
		//コンテンツ部分に商品一覧を表示するための文字列を登録
		model.addAttribute("contents", "products_info :: productinfo_contents");
		//商品一覧の生成
		List<ProductMst> productList = productService.selectMany();
		//Modelに商品リストを登録
		model.addAttribute("productList", productList);
		//データ件数を取得
		int count = productService.count();
		model.addAttribute("productListCount", count);

		return "home";
	}

	@RequestMapping(value = "/products_detail", method = RequestMethod.GET)
	public String getProductDetail(@ModelAttribute ProductRegistForm form,
			@RequestParam(name = "productId", defaultValue = "non") String productId, Model model) {

		//商品ID確認（デバッグ）
		System.out.println("productId=" + productId);
		//コンテンツ部分に商品詳細を表示するための文字列を登録
		model.addAttribute("contents", "products_detail :: productsdetail_contents");

		//商品IDのチェック
		if (productId != null && productId.length() > 0) {
			//商品情報を取得
			ProductMst productMst = productService.selectOne(productId);
			//Productクラスをフォームクラスに変換
			form.setProductId(Integer.parseInt(productId));
			form.setProductName(productMst.getProduct_name());
			form.setPrice(productMst.getProduct_price());
			form.setStock(productMst.getProduct_stock());
			//Modelに登録
			model.addAttribute("ProductRegistForm", form);
		}
		return "home";
	}

	@PostMapping(value = "/products_detail", params = "update")
	public String postProductDetailUpdate(@ModelAttribute ProductRegistForm form,
			Model model) {
		System.out.println("更新ボタンの処理");
		System.out.println("productId=" + form.getProductId());

		ProductMst productMst = new ProductMst();
		//フォームクラスをProductクラスに変換
		productMst.setProduct_id(form.getProductId());
		productMst.setProduct_name(form.getProductName());
		productMst.setProduct_price(form.getPrice());
		productMst.setProduct_stock(form.getStock());

		boolean result = productService.updateOne(productMst);
		if (result == true) {
			model.addAttribute("result", "更新成功");
		} else {
			model.addAttribute("result", "更新失敗");
		} //商品一覧画面を表示
		return getProductList(model);
	}

	//商品削除用処理.
	@PostMapping(value = "/products_detail", params = "delete")
	public String postProductDetailDelete(@ModelAttribute ProductRegistForm form, Model model) {
		System.out.println("削除ボタンの処理");
		boolean result = productService.deleteOne(String.valueOf(form.getProductId()));
		if (result == true) {
			model.addAttribute("result", "削除成功");
		} else {
			model.addAttribute("result", "削除失敗");
		}
		//商品一覧画面を表示
		return getProductList(model);
	}

	@GetMapping("/products_info/csv")
	public String productCsvOut(Model model) throws DataAccessException {
		// 拡張用 
		return getProductList(model);
	}
}
