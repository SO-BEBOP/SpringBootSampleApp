package com.example.springvirtualstore.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.springvirtualstore.domain.model.ProductMst;

public interface ProductDao {

	public int count() throws DataAccessException;

	public int insertOne(ProductMst productMst) throws DataAccessException;

	public ProductMst selectOne(String productId) throws DataAccessException;

	public List<ProductMst> selectMany() throws DataAccessException;

	public int updateOne(ProductMst productMst) throws DataAccessException;

	public int deleteOne(String productId) throws DataAccessException;

	public void productCsvOut() throws DataAccessException;

}
