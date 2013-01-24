package com.comverse.ldap.web.business;

import org.springframework.beans.factory.annotation.Autowired;

import com.comverse.ldap.mybatis.mapper.IConfigurationMapper;
import com.comverse.ldap.mybatis.mapper.IPersonMapper;

public class BasicBusiness {
	@Autowired
	protected IConfigurationMapper iConfigurationMapper=null;
	
	@Autowired
	protected IPersonMapper iPersonMapper=null;

	public IConfigurationMapper getiConfigurationDAO() {
		return iConfigurationMapper;
	}

	public void setiConfigurationDAO(IConfigurationMapper iConfigurationDAO) {
		this.iConfigurationMapper = iConfigurationDAO;
	}

	public IPersonMapper getiPersonDAO() {
		return iPersonMapper;
	}

	public void setiPersonDAO(IPersonMapper iPersonDAO) {
		this.iPersonMapper = iPersonDAO;
	}
}
