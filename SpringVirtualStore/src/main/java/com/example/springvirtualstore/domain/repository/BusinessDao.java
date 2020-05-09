package com.example.springvirtualstore.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.springvirtualstore.domain.model.BusinessTbl;

// ポイント： DataAccessException   
public interface BusinessDao {

	public int count() throws DataAccessException;

	public int insertOne(BusinessTbl businessTbl) throws DataAccessException;

	public BusinessTbl selectOne(String businessId) throws DataAccessException;

	public List<BusinessTbl> selectMany() throws DataAccessException;

	public List<BusinessTbl> selectPersonalBusiness(String userId) throws DataAccessException;

	//	public int updateOne(BusinessTbl businessTbl) throws DataAccessException;

	public int deleteOne(String businessId) throws DataAccessException;

	//	public void businessCsvOut() throws DataAccessException;

}
