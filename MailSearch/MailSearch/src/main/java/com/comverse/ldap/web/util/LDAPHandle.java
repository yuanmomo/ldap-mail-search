package com.comverse.ldap.web.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

import com.comverse.ldap.web.bean.LDAPServerBean;
import com.comverse.ldap.web.bean.Person;
import com.comverse.ldap.web.resource.FinalString;

public class LDAPHandle {
	private static Logger logger = Logger.getLogger(LDAPHandle.class);

	private static DirContext context = null;

	public static String test(LDAPServerBean server) {
		logger.debug("Start to test the ldap server is useful??");
		Hashtable<Object, Object> env = new Hashtable<Object, Object>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				server.getInitial_context_factory());
		env.put(Context.PROVIDER_URL, server.getServerHost());
		env.put(Context.SECURITY_AUTHENTICATION,
				server.getSecurity_authentication());
		env.put(Context.SECURITY_PRINCIPAL, server.getUser());
		env.put(Context.SECURITY_CREDENTIALS, server.getPassword());
		try {
			context = new InitialDirContext(env);
			if (context != null) {
				logger.debug("Initial the Dir Context successfully.");
			}
		} catch (javax.naming.AuthenticationException e) {
			logger.debug(e.toString());
			logger.debug(FinalString.LDAP_AUTHENTICATION_ERROR);
			return FinalString.LDAP_AUTHENTICATION_ERROR;
		} catch (Exception e) {
			logger.debug(e.toString());
			logger.debug(FinalString.UNKNOWN_ERROR);
			return FinalString.UNKNOWN_ERROR;
		}
		return FinalString.LDAP_TEST_SUCCESS;
	}

	public static List<Person> getPersonListByLDAP(LDAPServerBean server)
			throws Exception {
		if (context == null) {
			logger.debug("Dir Context Object is null, will to intial the context");
			test(server);
		}
		List<Person> personListByAD = new ArrayList<Person>();

		// Specify the scope of the search
		SearchControls constraints = new SearchControls();
		constraints.setSearchScope(server.getSearchScope());
		if (server.getSearchFilter() == null) {
			server.setSearchFilter("(cn=*)");
		}
		try {
			// AD limit, the search result max is 1000
			String[] searchFilterArrays = server.getSearchFilter().split("_");
			String searchFilter = null;
			// 97='a', 122='z', search from (mail=a*@*) to (mail=z*@*)
			for (int charTemp = 97; charTemp <= 122; charTemp++) {
				searchFilter = searchFilterArrays[0] + (char) charTemp
						+ searchFilterArrays[1];
				NamingEnumeration<SearchResult> results = context.search(
						server.getSearchBase(), searchFilter, constraints);
				while (results != null && results.hasMoreElements()) {
					Person p = new Person();
					SearchResult oneResult = results.next();
					String dn = oneResult.getName();
					p.setDn(dn); // dn is unique to erver person
					// DN is OK, then set OU, OU is in DN, like this:
					// DN :::::: CN=Yuan\,hong bin(hong bin),OU=Comverse-CD,OU=OU-ChengDu
					int index = dn.indexOf("OU");
					if (index > 0) {
						String ouStr=dn.substring(index);
						//ou like these:
						//	OU=users,OU=OU-Prison; OU=HR-CD,OU=OU-ChengDu,
						//	OU=TEAM-1,OU=A3,OU=OU-ChengDu
						//	OU=Resigned,OU=OU-MDTC; 
						ouStr=ouStr.replaceAll("OU=users,", "").replaceAll("OU=Users,", "").
								replaceAll("OU=", "").replaceAll("OU-","");
						p.setOu(ouStr); // set organization
					}
					dn += " , " + server.getSearchBase();
					logger.debug("Distinguished Name is " + dn);
					String[] attrArray = { "displayName", "mail", "cn", "sn",
							"description","userPrincipalName"};

					Attributes attrs = null;
					try {
						// no exception, get the needed attributes from the node
						attrs = context.getAttributes(dn, attrArray);
						if (attrs != null) {
							for (int k = 0; k < attrArray.length; k++) {
								Attribute item = (Attribute) attrs
										.get(attrArray[k]);
								if (item != null) {
									NamingEnumeration<?> enum2 = item.getAll();
									while (enum2 != null
											&&enum2.hasMoreElements()) {
										setAttributes(p,attrArray[k],enum2.nextElement().toString());
									}
								}
							}
						}
					} catch (Exception e) {
						// throw Exception, then list all the attributes and 
						// set needed into the Person obj
						attrs = oneResult.getAttributes();
						NamingEnumeration<? extends Attribute> ne = attrs
								.getAll();
						while (ne != null && ne.hasMoreElements()) {
							Attribute attr = (Attribute) ne.next();
							String attrName = attr.getID();
							if ("displayName".equals(attrName)
									|| "mail".equals(attrName)
									|| "cn".equals(attrName)
									|| "sn".equals(attrName)
									|| "description".equals(attrName)
								    || "userPrincipalName".equals(attrName)) {
								Enumeration<?> details = attr.getAll();
								String attrValue=null;
								while (details != null
										&& details.hasMoreElements()) {
									String tempValue=details.nextElement()+"";
									if(tempValue!=null){
										attrValue += tempValue;
									}
								}
								setAttributes(p,attrName,attrValue);
							}
						}
					}
					personListByAD.add(p);
					
					// One person's data are OK, insert or update the database;
					//personBusiness.insertSingle(p);
				}
			}
			return personListByAD;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return personListByAD;
		}finally{
			context.close();
			context=null;
		}
	}
	private static void setAttributes(Person p,String attrName,String attrValue){
		if ("displayName".equals(attrName)) {
			attrValue=attrValue.replaceAll("\\(.*\\)","");
			if(attrValue.endsWith(" ")){
				attrValue=attrValue.substring(0,attrValue.lastIndexOf(' '));
			}
			p.setName(attrValue);
		} else if ("mail".equals(attrName)) {
			p.setMail(attrValue);
		} else if ("cn".equals(attrName)) {
			attrValue=attrValue.replaceAll("\\(.*\\)","");
			if(attrValue.endsWith(" ")){
				attrValue=attrValue.substring(0,attrValue.lastIndexOf(' '));
			}
			p.setName(attrValue);
			p.setCn(attrValue.replaceAll(","," "));
		} else if ("sn".equals(attrName)) {
			p.setSn(attrValue);
		} else if ("description".equals(attrName)) {
			p.setDescription(attrValue);
		} else if("userPrincipalName".equals(attrName)){
			int index =attrValue.indexOf("@");
			if(index >0){
				p.setLoginName(attrValue.substring(0,index));
			}else{
				p.setLoginName(attrValue);
			}
		}
	}
}