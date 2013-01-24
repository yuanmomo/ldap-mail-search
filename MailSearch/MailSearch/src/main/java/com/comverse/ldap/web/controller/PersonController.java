package com.comverse.ldap.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comverse.ldap.web.bean.Person;
import com.comverse.ldap.web.resource.FinalString;
import com.comverse.ldap.web.util.DataValidate;
import com.comverse.ldap.web.util.DateConvertUtil;

@Controller(value = "loginController")
@RequestMapping("/user.do")
public class PersonController extends BasicController{
	private static Logger logger = Logger.getLogger(PersonController.class);
	
	@RequestMapping(params="option=login")
	public String login(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		String name = request.getParameter("username");
		if(!DataValidate.validateStringWithTrim(name)){
			return this.returnMessage(map, "error", FinalString.NAME_NULL_ERROR, "json");
		}
		String password = request.getParameter("password");
		if(!DataValidate.validateString(password)){
			return this.returnMessage(map, "error", FinalString.PASSWORD_NULL_ERROR, "json");
		}
		//user name and password both are OK, then check from LDAP
		Person p=new Person();
		p.setLoginName(name);
		p.setLoginPassord(password);
		String result=this.personBusiness.login(p);
		if(FinalString.LDAP_AUTHENTICATION_ERROR.equals(result)){
			return this.returnMessage(map, "error", FinalString.LOGIN_ERROR, "json");
		}else if(FinalString.UNKNOWN_ERROR.equals(result)){
			return this.returnMessage(map, "error", FinalString.UNKNOWN_ERROR, "json");
		}
		
		//login success, query the user's info from DB
		try {
			p=this.personBusiness.getUserByLoginName(p.getLoginName());
		} catch (Exception e) {
			e.printStackTrace();
			return this.returnMessage(map, "error", "Exception throwed when do login"+e.getMessage(),"json");
		}
		if(p!=null){
			JSONObject obj=new JSONObject();
			obj.put("username", p.getLoginName());
			obj.put("cellphone", p.getCellPhone());
			request.getSession().setAttribute("user",p);
			map.put("json","["+obj.toString()+"]");
			return "json";
		}else{
			//user is in LDAP, but not in DB, Maybe:
			//	1. The mail attribute in LDAP is null
			//	2. The DB it not the latest
			
			return this.returnMessage(map, "error", "User cannot be found in DB,please contact Admin!","json");
		}
	}
	@RequestMapping(params="option=logout")
	public String logout(ModelMap map, HttpServletRequest request,
			HttpServletResponse response){
		Object p=request.getSession().getAttribute("user");
		if(p==null){
			return this.returnMessage(map, "error", "You have not signed in.","json");
		}else{
			request.getSession().invalidate();
			return this.returnMessage(map, "success", "You just sign out.","json");
		}
	}
	@RequestMapping(params="option=saveCellphone")
	public String updateCellphone(ModelMap map, HttpServletRequest request,
			HttpServletResponse response){
		String phoneNumber=request.getParameter("cellphone");
		if(!DataValidate.validatePhoneNumber(phoneNumber)){
			//phone number format is wrong
			return this.returnMessage(map, "error", "Phone number is not correct!","json");
		}
		Person p=(Person)request.getSession().getAttribute("user");
		if(p==null || p.getLoginName()==null || "".equals(p.getLoginName())){
			//user not login
			return this.returnMessage(map, "error", "You are not login","json");
		}
		//get the person from DB
		try {
			p=this.personBusiness.getUserByLoginName(p.getLoginName());
			p.setCellPhone(phoneNumber);
			p.setUpdateTime(new Date());
			boolean flag=this.personBusiness.updateCellphone(p);
			if(flag){
				request.getSession().setAttribute("user",p);
				return this.returnMessage(map, "success", "Congratulations, update successfully.","json");
			}else{
				return this.returnMessage(map, "error", "Sorry, update faild.","json");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this.returnMessage(map, "error", "Exception throwed when do update cellphone"+e.getMessage(),"json");
		}
	}
	@RequestMapping(params="option=updateDB")
	public String updateLDAPToDBManually(ModelMap map, HttpServletRequest request,
			HttpServletResponse response){
		logger.debug("Start to update DB info by LDAP, start time is"+DateConvertUtil.dateToString(new Date()));
		try {
			this.personBusiness.updateLDAPToDB();
			logger.debug("End update DB, end time is"+DateConvertUtil.dateToString(new Date()));
			return this.returnMessage(map, "success", "Congratulations, update successfully.","json");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Exception throwed when update DB"+e.toString());
			return this.returnMessage(map, "error", "Exception throwed when do update LDAP to DB manually"+e.getMessage(),"json");
		}
	}
}
