package com.comverse.ldap.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.comverse.ldap.web.util.LDAPServer;

public class LDAPServerListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		LDAPServer.ldapServerDestroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		LDAPServer.getServer();
	}
}
