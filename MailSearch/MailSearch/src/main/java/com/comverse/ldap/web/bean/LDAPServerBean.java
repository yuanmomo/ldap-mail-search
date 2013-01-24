package com.comverse.ldap.web.bean;


public class LDAPServerBean {
	
	private String serverHost;
	private String domainName;
	private String initial_context_factory="com.sun.jndi.ldap.LdapCtxFactory";
	private String searchBase;
	private String user;
	private String password;
	private String security_authentication="simple";
	private int searchScope=2;
	private String searchFilter;
	
	public LDAPServerBean(){
		
	}
	public String getServerHost() {
		return serverHost;
	}
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	public String getInitial_context_factory() {
		return initial_context_factory;
	}
	public void setInitial_context_factory(String initial_context_factory) {
		this.initial_context_factory = initial_context_factory;
	}
	public String getSearchBase() {
		return searchBase;
	}
	public void setSearchBase(String searchBase) {
		this.searchBase = searchBase;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecurity_authentication() {
		return security_authentication;
	}
	public void setSecurity_authentication(String security_authentication) {
		this.security_authentication = security_authentication;
	}
	public int getSearchScope() {
		return searchScope;
	}
	public void setSearchScope(int searchScope) {
		this.searchScope = searchScope;
	}
	public String getSearchFilter() {
		return searchFilter;
	}
	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
}
