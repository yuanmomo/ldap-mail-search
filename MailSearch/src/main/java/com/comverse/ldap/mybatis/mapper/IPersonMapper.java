package com.comverse.ldap.mybatis.mapper;

import java.util.List;

import com.comverse.ldap.web.bean.Person;


public interface IPersonMapper {
	public int insert(Person person) throws Exception;
	public int update(Person person) throws Exception;
	public int updateCellphone(Person person) throws Exception;
	public int invalid(List<Person> personList) throws Exception;
	public int isExist(String dn) throws Exception;
	public Person getByDn(String dn) throws Exception;
	public Person getByLoginName(String loginName) throws Exception;
	public List<Person> search(String keyStr) throws Exception;
}
