package com.example.springvirtualstore.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.springvirtualstore.domain.model.CartTbl;
import com.example.springvirtualstore.domain.repository.CartDao;

@Repository("CartDaoJdbcImpl")
public class CartDaoJdbcImpl implements CartDao {
	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int count() throws DataAccessException {

		//全件取得してカウント
		int count = jdbc.queryForObject(
				"SELECT COUNT(*) FROM cart_tbl", Integer.class);
		return count;
	}

	@Override
	public int insertOne(CartTbl cartTbl) throws DataAccessException {

		//取引テーブルに１件登録するSQL
		String sql = "INSERT INTO cart_tbl("
				+ " cart_user_id,"
				+ " cart_product_id"
				+ ")VALUES(?,?)";
		//１件挿入
		int rowNumber = jdbc.update(sql,
				cartTbl.getCart_user_id(),
				cartTbl.getCart_product_id());

		return rowNumber;
	}

	@Override
	public CartTbl selectOne(String cartId) throws DataAccessException {
		//１件取得
		Map<String, Object> map = jdbc.queryForMap(
				"SELECT * FROM cart_tbl" + " WHERE cart_id=?", cartId);
		//結果返却用の変数
		CartTbl cartTbl = new CartTbl();
		//取得したデータを結果返却用の変数にセットしていく
		cartTbl.setCart_id((Integer) map.get("cart_id"));
		cartTbl.setCart_user_id((Integer) map.get("cart_user_id"));
		cartTbl.setCart_product_id((Integer) map.get("cart_product_id"));
		cartTbl.setCart_state((Integer) map.get("cart_state"));
		cartTbl.setCreate_at((Date) map.get("create_at"));
		cartTbl.setUpdata_at((Date) map.get("updata_at"));

		return cartTbl;
	}

	@Override
	public List<CartTbl> selectMany() throws DataAccessException {
		//cart_tblテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM cart_tbl");
		//結果返却用の変数
		List<CartTbl> cartList = new ArrayList<>();
		//取得したデータを結果返却用のListに格納していく
		for (Map<String, Object> map : getList) {
			//Cartインスタンスの生成
			CartTbl cartTbl = new CartTbl();
			//Cartインスタンスに取得したデータをセットする
			cartTbl.setCart_id((Integer) map.get("cart_id"));
			cartTbl.setCart_user_id((Integer) map.get("cart_user_id"));
			cartTbl.setCart_product_id((Integer) map.get("cart_product_id"));
			cartTbl.setCart_state((Integer) map.get("cart_state"));
			cartTbl.setCreate_at((Date) map.get("create_at"));
			cartTbl.setUpdata_at((Date) map.get("updata_at"));
			//結果返却用のListに追加
			cartList.add(cartTbl);
		}
		return cartList;
	}

	@Override
	public List<CartTbl> selectManyFromStateParam(
			String userId, String cartState) throws DataAccessException {
		// state param(0:論理削除 / 1 :未購入 / 2 :購入済)
		List<Map<String, Object>> getList = jdbc.queryForList(
				"SELECT * FROM cart_tbl "
						+ " WHERE cart_user_id = ?"
						+ " AND cart_state = ?",
				userId, cartState);

		//結果返却用の変数
		List<CartTbl> cartList = new ArrayList<>();
		//取得したデータを結果返却用のListに格納していく
		for (Map<String, Object> map : getList) {
			//Cartインスタンスの生成
			CartTbl cartTbl = new CartTbl();
			//Cartインスタンスに取得したデータをセットする
			cartTbl.setCart_id((Integer) map.get("cart_id"));
			cartTbl.setCart_user_id((Integer) map.get("cart_user_id"));
			cartTbl.setCart_product_id((Integer) map.get("cart_product_id"));
			cartTbl.setCart_state((Integer) map.get("cart_state"));
			cartTbl.setCreate_at((Date) map.get("create_at"));
			cartTbl.setUpdata_at((Date) map.get("updata_at"));
			//結果返却用のListに追加
			cartList.add(cartTbl);
		}
		return cartList;
	}

	@Override
	public int updateOne(CartTbl cartTb) throws DataAccessException {

		//１件更新するSQL
		String sql = "UPDATE cart_tbl SET"
				+ " cart_user_id = ?,"
				+ " cart_product_id = ?,"
				+ " cart_state = ?"
				+ " WHERE"
				+ " cart_id = ?";
		//１件更新
		int rowNumber = jdbc.update(sql,
				cartTb.getCart_user_id(),
				cartTb.getCart_product_id(),
				cartTb.getCart_state(),
				cartTb.getCart_id());

		return rowNumber;
	}

	@Override
	public int updateOneFromStateParam(Integer userId) throws DataAccessException {

		int statePurchased = 2;
		int stateUntreated = 1;

		//状態を[購入済: 2]に更新するSQL
		String sql = "UPDATE cart_tbl SET"
				+ " cart_state = ?"
				+ " WHERE"
				+ " cart_user_id = ?"
				+ " AND"
				+ " cart_state = ?";

		//１件更新
		int rowNumber = jdbc.update(sql, statePurchased, userId, stateUntreated);

		return rowNumber;
	}

	@Override
	public int deleteOne(String cartId) throws DataAccessException {
		//１件削除
		int rowNumber = jdbc.update(
				"DELETE FROM cart_tbl WHERE cart_id  = ?", cartId);
		return rowNumber;
	}

	//	@Override
	//	public void cartCsvOut() throws DataAccessException {
	//		//テーブルのデータを全件取得するSQL
	//		String sql = "SELECT ∗ FROM cart_tbl";
	//		//ResultSetExtractorの生成
	//		CartRowCallbackHandler handler = new CartRowCallbackHandler();
	//		//SQL実行＆CSV出力
	//		jdbc.query(sql, handler);
	//	}

}
