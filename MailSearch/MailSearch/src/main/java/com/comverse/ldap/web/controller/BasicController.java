package com.comverse.ldap.web.controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.ui.ModelMap;

import com.comverse.ldap.web.bean.Person;
import com.comverse.ldap.web.business.ConfigurationBusiness;
import com.comverse.ldap.web.business.PersonBusiness;

public class BasicController {
	@Resource(name="personBusiness")
	protected PersonBusiness personBusiness=null;
	
	@Resource(name="configurationBusiness")
	protected ConfigurationBusiness configBusiness=null;
	
	public void setPersonBusiness(PersonBusiness personBusiness) {
		this.personBusiness = personBusiness;
	}

	public void setConfigBusiness(ConfigurationBusiness configBusiness) {
		this.configBusiness = configBusiness;
	}
	
	// build return datas to json
	public String returnMessage(ModelMap map,String resultType,String message,String view){
		JSONObject obj=new JSONObject();
		obj.put(resultType, message);
		map.put("json", "["+obj.toString()+"]");
		return view;
	}
	public String listToJSON(String[] excludes,ModelMap map,List<? extends Person> dataList,String view){
		JsonConfig jc = new JsonConfig();  
        //排除不需要转换的属性，排除otherBeanAttr内部引用属性  
        jc.setExcludes(excludes);
        String result = JSONArray.fromObject(dataList,jc).toString();
        map.put("json", result);
		return view;
	}
}
