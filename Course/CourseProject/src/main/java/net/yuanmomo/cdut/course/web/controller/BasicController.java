package net.yuanmomo.cdut.course.web.controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.yuanmomo.cdut.course.web.business.AnnouncementBusiness;

import org.springframework.ui.ModelMap;

import com.comverse.ldap.web.bean.Person;
import com.comverse.ldap.web.business.ConfigurationBusiness;
import com.comverse.ldap.web.business.PersonBusiness;

public class BasicController {
	@Resource(name="announcementBusiness")
	protected AnnouncementBusiness announcementBusiness=null;
}
