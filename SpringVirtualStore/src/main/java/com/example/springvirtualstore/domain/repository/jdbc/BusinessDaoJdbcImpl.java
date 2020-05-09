package com.example.springvirtualstore.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.springvirtualstore.domain.model.BusinessTbl;
import com.example.springvirtualstore.domain.repository.BusinessDao;

@Repository("BusinessDaoJdbcImpl")
public class BusinessDaoJdbcImpl implements BusinessDao {

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int count() throws DataAccessException {

		//全件取得してカウント
		int count = jdbc.queryForObject(
				"SELECT COUNT(*) FROM business_tbl", Integer.class);
		return count;
	}

	@Override
	public int insertOne(BusinessTbl businessTbl) throws DataAccessException {

		//取引テーブルに１件登録するSQL
		String sql = "INSERT INTO business_tbl("
				+ " business_user_id,"
				+ " business_sales"
				+ ")VALUES(?,?)";
		//１件挿入
		int rowNumber = jdbc.update(sql,
				businessTbl.getBusiness_user_id(),
				businessTbl.getBusiness_sales());

		return rowNumber;
	}

	@Override
	public BusinessTbl selectOne(String businessId) throws DataAccessException {
		//１件取得
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM business_tbl" + " WHERE business_id=?", businessId);
		//結果返却用の変数
		BusinessTbl businessTbl = new BusinessTbl();
		//取得したデータを結果返却用の変数にセットしていく
		businessTbl.setBusiness_id((Integer) map.get("business_id"));
		businessTbl.setBusiness_user_id((Integer) map.get("business_user_id"));
		businessTbl.setBusiness_sales((Integer) map.get("business_sales"));
		businessTbl.setBusiness_state((Integer) map.get("business_state"));
		businessTbl.setCreate_at((Date) map.get("create_at"));
		businessTbl.setUpdata_at((Date) map.get("updata_at"));

		return businessTbl;
	}

	/*	@Override
		public BusinessTbl selectPersonalBusiness(String userId) throws DataAccessException {
			//１件取得
			Map<String, Object> map = jdbc.queryForMap("SELECT * FROM business_tbl" + " WHERE business_user_id=?", userId);
			//結果返却用の変数
			BusinessTbl businessTbl = new BusinessTbl();
			//取得したデータを結果返却用の変数にセットしていく
			businessTbl.setBusiness_id((Integer) map.get("business_id"));
			businessTbl.setBusiness_user_id((Integer) map.get("business_user_id"));
			businessTbl.setBusiness_sales((Integer) map.get("business_sales"));
			businessTbl.setBusiness_state((Integer) map.get("business_state"));
			businessTbl.setCreate_at((Date) map.get("create_at"));
			businessTbl.setUpdata_at((Date) map.get("updata_at"));

			return businessTbl;
		}
	*/

	@Override
	public List<BusinessTbl> selectPersonalBusiness(String userId) throws DataAccessException {
		//business_tblテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc
				.queryForList("SELECT * FROM business_tbl" + " WHERE business_user_id=?", userId);
		//結果返却用の変数
		List<BusinessTbl> businessList = new ArrayList<>();
		//取得したデータを結果返却用のListに格納していく
		for (Map<String, Object> map : getList) {
			//Businessインスタンスの生成
			BusinessTbl businessTbl = new BusinessTbl();
			//Businessインスタンスに取得したデータをセットする
			businessTbl.setBusiness_id((Integer) map.get("business_id"));
			businessTbl.setBusiness_user_id((Integer) map.get("business_user_id"));
			businessTbl.setBusiness_sales((Integer) map.get("business_sales"));
			businessTbl.setBusiness_state((Integer) map.get("business_state"));
			businessTbl.setCreate_at((Date) map.get("create_at"));
			businessTbl.setUpdata_at((Date) map.get("updata_at"));
			//結果返却用のListに追加
			businessList.add(businessTbl);
		}
		return businessList;
	}

	@Override
	public List<BusinessTbl> selectMany() throws DataAccessException {
		//business_tblテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM business_tbl");
		//結果返却用の変数
		List<BusinessTbl> businessList = new ArrayList<>();
		//取得したデータを結果返却用のListに格納していく
		for (Map<String, Object> map : getList) {
			//Businessインスタンスの生成
			BusinessTbl businessTbl = new BusinessTbl();
			//Businessインスタンスに取得したデータをセットする
			businessTbl.setBusiness_id((Integer) map.get("business_id"));
			businessTbl.setBusiness_user_id((Integer) map.get("business_user_id"));
			businessTbl.setBusiness_sales((Integer) map.get("business_sales"));
			businessTbl.setBusiness_state((Integer) map.get("business_state"));
			businessTbl.setCreate_at((Date) map.get("create_at"));
			businessTbl.setUpdata_at((Date) map.get("updata_at"));
			//結果返却用のListに追加
			businessList.add(businessTbl);
		}
		return businessList;
	}

	// 更新処理未実装
	//	@Override
	//	public int updateOne(BusinessTbl businessTbl) throws DataAccessException {
	//
	//		//１件更新するSQL
	//		String sql = "UPDATE business_tbl SET"
	//				+ " business_id = ?,"
	//				+ " business_user_id = ?,"
	//				+ " business_sales = ?"
	//				+ " WHERE"
	//				+ " business_id = ?"
	//				+ " AND"
	//				+ " business_user_id = ?";
	//
	//		//１件更新
	//		int rowNumber = jdbc.update(sql,
	//				businessTbl.getBusiness_id(),
	//
	//				businessTbl.getBusiness_id());
	//
	//		return rowNumber;
	//	}

	@Override
	public int deleteOne(String businessId) throws DataAccessException {
		//１件削除
		int rowNumber = jdbc.update("DELETE FROM business_tbl WHERE business_id = ?", businessId);
		return rowNumber;
	}

	//	@Override
	//	public void businessCsvOut() throws DataAccessException {
	//		//テーブルのデータを全件取得するSQL
	//		String sql = "SELECT ∗ FROM business_tbl";
	//		//ResultSetExtractorの生成
	//		BusinessRowCallbackHandler handler = new BusinessRowCallbackHandler();
	//		//SQL実行＆CSV出力
	//		jdbc.query(sql, handler);
	//	}

}
