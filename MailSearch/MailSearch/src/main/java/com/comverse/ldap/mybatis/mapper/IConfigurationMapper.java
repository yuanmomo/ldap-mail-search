package com.comverse.ldap.mybatis.mapper;

import java.util.List;

import com.comverse.ldap.web.bean.Configuration;

public interface IConfigurationMapper {
	public int insert(Configuration config) throws Exception;
	public int update(Configuration config) throws Exception;
	public int unuse(Configuration config) throws Exception;
	public Configuration getByName(String name) throws Exception;
	public List<Configuration> queryAll() throws Exception;
}
