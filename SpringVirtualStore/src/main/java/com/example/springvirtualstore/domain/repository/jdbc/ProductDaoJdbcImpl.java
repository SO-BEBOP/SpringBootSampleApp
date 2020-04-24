package com.example.springvirtualstore.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.springvirtualstore.domain.model.ProductMst;
import com.example.springvirtualstore.domain.repository.ProductDao;

@Repository("ProductDaoJdbcImpl")
public class ProductDaoJdbcImpl implements ProductDao {

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int count() throws DataAccessException {
		//全件取得してカウント
		int count = jdbc.queryForObject(
				"SELECT COUNT(*) FROM product_mst", Integer.class);
		return count;
	}

	@Override
	public int insertOne(ProductMst productMst) throws DataAccessException {

		String sql = "INSERT INTO product_mst("
				+ " product_name,"
				+ " product_price,"
				+ " product_stock"
				+ ")VALUES(?,?,?)";
		//１件挿入
		int rowNumber = jdbc.update(sql,
				productMst.getProduct_name(),
				productMst.getProduct_price(),
				productMst.getProduct_stock());

		return rowNumber;
	}

	@Override
	public ProductMst selectOne(String productId) throws DataAccessException {
		//１件取得
		Map<String, Object> map = jdbc.queryForMap(
				"SELECT * FROM product_mst" + " WHERE product_id =?", productId);
		//結果返却用の変数
		ProductMst productMst = new ProductMst();
		//取得したデータを結果返却用の変数にセットしていく
		productMst.setProduct_id((Integer) map.get("product_id"));
		productMst.setProduct_name((String) map.get("product_name"));
		productMst.setProduct_price((Integer) map.get("product_price"));
		productMst.setProduct_stock((Integer) map.get("product_stock"));
		productMst.setProduct_state((Integer) map.get("product_state"));
		productMst.setFinal_sales((Date) map.get("final_sales"));
		productMst.setCreate_at((Date) map.get("create_at"));
		productMst.setUpdata_at((Date) map.get("updata_at"));

		return productMst;
	}

	@Override
	public List<ProductMst> selectMany() throws DataAccessException {
		//product_mstテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM product_mst");
		//結果返却用の変数
		List<ProductMst> productList = new ArrayList<>();
		//取得したデータを結果返却用のListに格納していく
		for (Map<String, Object> map : getList) {
			//Productインスタンスの生成
			ProductMst productMst = new ProductMst();
			//Productインスタンスに取得したデータをセットする
			productMst.setProduct_id((Integer) map.get("product_id"));
			productMst.setProduct_name((String) map.get("product_name"));
			productMst.setProduct_price((Integer) map.get("product_price"));
			productMst.setProduct_stock((Integer) map.get("product_stock"));
			productMst.setProduct_state((Integer) map.get("product_state"));
			productMst.setFinal_sales((Date) map.get("final_sales"));
			productMst.setCreate_at((Date) map.get("create_at"));
			productMst.setUpdata_at((Date) map.get("updata_at"));

			//結果返却用のListに追加
			productList.add(productMst);
		}
		return productList;
	}

	@Override
	public int updateOne(ProductMst productMst) throws DataAccessException {

		//１件更新するSQL
		String sql = "UPDATE product_mst SET"
				+ " product_name = ?,"
				+ " product_price = ?,"
				+ " product_stock  = ?"
				+ " WHERE"
				+ " product_id = ?";
		//１件更新
		int rowNumber = jdbc.update(sql,
				productMst.getProduct_name(),
				productMst.getProduct_price(),
				productMst.getProduct_stock(),
				productMst.getProduct_id());

		return rowNumber;
	}

	@Override
	public int deleteOne(String productId) throws DataAccessException {
		//１件削除
		int rowNumber = jdbc.update("DELETE FROM product_mst WHERE product_id = ?", productId);
		return rowNumber;
	}

	@Override
	public void productCsvOut() throws DataAccessException {
		//テーブルのデータを全件取得するSQL
		String sql = "SELECT ∗ FROM product_mst";
		//ResultSetExtractorの生成
		ProductRowCallbackHandler handler = new ProductRowCallbackHandler();
		//SQL実行＆CSV出力
		jdbc.query(sql, handler);
	}
}
