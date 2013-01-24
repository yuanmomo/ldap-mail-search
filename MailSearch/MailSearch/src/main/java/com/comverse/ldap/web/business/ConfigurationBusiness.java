package com.comverse.ldap.web.business;

import org.springframework.stereotype.Service;

import com.comverse.ldap.web.bean.Configuration;

@Service
public class ConfigurationBusiness extends BasicBusiness {
	
	public Configuration getByName(String name) throws Exception{
		if(name!=null && !"".equals(name)){
			return this.iConfigurationMapper.getByName(name);
		}
		return null;
	}
}
