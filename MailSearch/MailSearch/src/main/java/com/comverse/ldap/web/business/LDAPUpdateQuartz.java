package com.comverse.ldap.web.business;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.comverse.ldap.web.util.DateConvertUtil;


public class LDAPUpdateQuartz{
	private static Logger logger = Logger.getLogger(LDAPUpdateQuartz.class);
	
	@Resource(name="personBusiness")
	private PersonBusiness personBussiness=null;

	public void setPersonBussiness(PersonBusiness personBussiness) {
		this.personBussiness = personBussiness;
	}

	public void work(){
		logger.debug("Start to update DB info by LDAP, start time is"+DateConvertUtil.dateToString(new Date()));
		try {
			this.personBussiness.updateLDAPToDB();
			logger.debug("End update DB, end time is"+DateConvertUtil.dateToString(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Exception throwed when update DB"+e.toString());
		}
	}
}
