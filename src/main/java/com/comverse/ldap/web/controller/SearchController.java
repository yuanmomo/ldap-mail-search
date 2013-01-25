package com.comverse.ldap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comverse.ldap.web.bean.Configuration;
import com.comverse.ldap.web.bean.Person;
import com.comverse.ldap.web.resource.FinalString;
import com.comverse.ldap.web.util.DataValidate;

@Controller(value = "ldapSheetController")
@RequestMapping("/search.do")
public class SearchController extends BasicController {

	// default forward to search.jsp
	@RequestMapping
	public String updatePersonToDB(HttpServletRequest request, ModelMap map)
			throws Exception {
		Configuration config = this.configBusiness.getByName("lastUpdateTime");
		Configuration updateCount=this.configBusiness.getByName("updateCount");
		Configuration insertCount=this.configBusiness.getByName("insertCount");
		Configuration deleteCount=this.configBusiness.getByName("deleteCount");
		map.put("updateCount", updateCount.getValue());
		map.put("insertCount", insertCount.getValue());
		map.put("deleteCount", deleteCount.getValue());
		map.put("lastUpdateTime", config.getValue());
		return "search";
	}
	@RequestMapping(params = "option=searchByName")
	public String searchByName(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		String keyWords = request.getParameter("keyWords");
		if (DataValidate.validateStringWithTrim(keyWords)) {
			try {
				List<Person> result = this.personBusiness.search(keyWords
						.trim().toUpperCase());
				if(result!=null && result.size()>0){
					return this.listToJSON(new String[]{"dn","cn","sn","id","invalid","updateTime",
						"loginPassord","phoneExt"}, map, result, "json");
				}else{
					return this.returnMessage(map, "message", "No record found.", "json");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return this.returnMessage(map, "error", "Exception throwed when do search"+e.getMessage(),"json");
			}
		} else {
			return this.returnMessage(map, "error", FinalString.KEY_WORDS_NULL_ERROR, "json");
		}
	}
}
