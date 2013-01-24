package com.comverse.ldap.web.util;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.comverse.ldap.web.bean.LDAPServerBean;
import com.comverse.ldap.web.resource.FileLocation;

public class LDAPServer {
	private static Logger logger = Logger.getLogger(LDAPServer.class);
	private static LDAPServer server=null;
	private static LDAPServerBean serverInfo=null;
	private LDAPServer(){}
	
	public static LDAPServer getServer(){
		if(server==null){
			LDAPServerInner.getLDAPServerInstance();
		}
		return server;
	}
	public static void ldapServerDestroy(){
		server=null;
		serverInfo=null;
	}
	private LDAPServer loadPropertiesFile(){
		logger.debug("Start to read the LDAPServer.properties file, and load the properties.");
		PropertiesReadUtil read=new PropertiesReadUtil();
		Properties pro=read.read(FileLocation.LDAPSERVER_PROPERTIES_LOCATION);
		if(pro==null){
			logger.debug("The Properties object return a null");
			return null;
		}else{
			serverInfo.setServerHost(pro.getProperty("serverip"));
			serverInfo.setInitial_context_factory(pro.getProperty("initial_context_factory"));
			serverInfo.setSearchBase(pro.getProperty("searchbase"));
			serverInfo.setUser(pro.getProperty("user"));
			serverInfo.setPassword(pro.getProperty("password"));
			serverInfo.setSecurity_authentication(pro.getProperty("security_authentication"));
			serverInfo.setSearchFilter(pro.getProperty("search_filter"));
			serverInfo.setDomainName(pro.getProperty("domainname"));
			logger.debug("The properties are set successfully.");
			return server;
		}
	}
	
	private static class LDAPServerInner{
		public static void getLDAPServerInstance(){
			server=new LDAPServer();
			serverInfo=new LDAPServerBean();
			server.loadPropertiesFile();
		} 
	}
	public LDAPServerBean getServerInfo() {
		return serverInfo;
	}
}
