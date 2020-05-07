package com.example.springvirtualstore.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.springvirtualstore.domain.model.CartTbl;

public interface CartDao {

	public int count() throws DataAccessException;

	public int insertOne(CartTbl cartTbl) throws DataAccessException;

	public CartTbl selectOne(String cartId) throws DataAccessException;

	public List<CartTbl> selectMany() throws DataAccessException;

	public List<CartTbl> selectManyFromStateParam(String userId, String cartState) throws DataAccessException;

	public int updateOne(CartTbl cartTbl) throws DataAccessException;

	public int updateOneFromStateParam(Integer userId) throws DataAccessException;

	public int deleteOne(String cartId) throws DataAccessException;

}
