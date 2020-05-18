package com.example.springvirtualstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springvirtualstore.domain.model.BusinessInfo;
import com.example.springvirtualstore.domain.repository.UserDetailsImpl;
import com.example.springvirtualstore.domain.service.BusinessService;
import com.example.springvirtualstore.domain.service.InformationService;

@Controller
public class BusinessInfoController {

	@Autowired
	private BusinessService businessService;

	@Autowired
	private InformationService infomationService;

	@GetMapping("/business_info")
	public String getBusinessList(Model model) {
		//コンテンツ部分に取引一覧を表示するための文字列を登録
		model.addAttribute("contents", "business_info :: businessinfo_contents");
		// 表示用に複合した取引情報Listを設定する。
		List<BusinessInfo> businessInfoList = infomationService.getBusinessInfoList();
		//Modelに取引リストを登録
		model.addAttribute("businessInfoList", businessInfoList);
		//データ件数を取得
		int count = businessService.count();
		model.addAttribute("businessListCount", count);

		return "home";
	}

	@GetMapping("/general_business_info")
	public String getPersonalBusinessList(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		//コンテンツ部分に取引一覧を表示するための文字列を登録
		model.addAttribute("contents", "business_info :: businessinfo_contents");
		// 表示用に複合した取引情報Listを設定する。
		List<BusinessInfo> businessInfoList = infomationService
				.gePersonalBusinessInfoList(String.valueOf(userDetails.getUserId()));
		//Modelに取引リストを登録
		model.addAttribute("businessInfoList", businessInfoList);
		//データ件数を取得
		int count = businessInfoList.size();
		model.addAttribute("businessListCount", count);

		return "home";
	}

	//取引削除用処理.
	@PostMapping(path = "/business_info", params = "delete")
	public String postBusinessDetailDelete(@RequestParam String id, Model model) {

		System.out.println("削除ボタンの処理");
		boolean result = businessService.deleteOne(String.valueOf(id));
		if (result == true) {
			model.addAttribute("result", "削除成功");
		} else {
			model.addAttribute("result", "削除失敗");
		}
		//取引一覧画面を表示
		return getBusinessList(model);
	}

	@GetMapping("/business_info/csv")
	public String businessCsvOut(Model model) throws DataAccessException {
		// 拡張用 
		return getBusinessList(model);
	}

	//	//取引一覧のCSV出力用処理.  
	//	@GetMapping("/businessList/csv")
	//	public ResponseEntity<byte[]> getBusinessListCsv(Model model) {
	//		//取引を全件取得して、CSVをサーバーに保存する
	//		businessService.businessCsvOut();
	//		byte[] bytes = null;
	//		try {//サーバーに保存されているsample.csvファイルをbyteで取得する
	//			bytes = businessService.getFile("sample.csv");
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//		//HTTPヘッダーの設定
	//		HttpHeaders header = new HttpHeaders();
	//		header.add("Content-Type", "text/csv;charset=UTF-8");
	//		header.setContentDispositionFormData("filename", "sample.csv");
	//		//sample.csvを戻す
	//		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	//	}

}
