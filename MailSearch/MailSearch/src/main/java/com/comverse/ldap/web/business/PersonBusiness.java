package com.comverse.ldap.web.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comverse.ldap.web.bean.Configuration;
import com.comverse.ldap.web.bean.LDAPServerBean;
import com.comverse.ldap.web.bean.Person;
import com.comverse.ldap.web.resource.FinalString;
import com.comverse.ldap.web.util.DateConvertUtil;
import com.comverse.ldap.web.util.KeyStringConventer;
import com.comverse.ldap.web.util.LDAPHandle;
import com.comverse.ldap.web.util.LDAPServer;

@Service
public class PersonBusiness extends BasicBusiness {
	private int updateCount;
	private int insertCount;
	private int deleteCount;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public boolean insertSingle(Person person) throws Exception {
		return (this.iPersonMapper.insert(person) >0 ? true : false);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public String login(Person p) {
		// initial the LDAP server
		LDAPServerBean server = LDAPServer.getServer().getServerInfo();
		server.setUser(p.getLoginName() + "@" + server.getDomainName());
		server.setPassword(p.getLoginPassord());
		String loginResult = LDAPHandle.test(server);
		return loginResult;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public String updateLDAPToDB() throws Exception {
		// reset the insert/update/delete count
		this.insertCount = 0;
		this.updateCount = 0;
		this.deleteCount = 0;

		// check : now -lastUpdateTime>=updateCycle
		Configuration lastUpdateTime = this.iConfigurationMapper
				.getByName("lastUpdateTime");
		if (lastUpdateTime != null && lastUpdateTime.getValue() != null) {
			Date last = DateConvertUtil.stringToDate(lastUpdateTime.getValue());
			if ((new Date().getTime() - last.getTime()) <= 24 * 3600) {
				return FinalString.UPDATE_LDAP_TO_DB_TIME_ERROR;
			}
		}
		// initial the LDAP server
		LDAPServerBean server = LDAPServer.getServer().getServerInfo();

		// the newest person info of the LDAP
		List<Person> personList = LDAPHandle.getPersonListByLDAP(server);

		// persons in newest person info of LDAP but not in DB
		List<Person> notInDBPersonList = new ArrayList<Person>();

		// persons both in DB and newest person info of LDAP
		List<Person> inBothPersonList = new ArrayList<Person>();

		if (personList != null && personList.size() > 0) {
			boolean isExist = false;
			for (Person p : personList) {
				p.setUpdateTime(new Date());
				// First invalid the persons not in personList but in DB
				isExist = this.iPersonMapper.isExist(p.getDn())>0 ? true : false;
				if (isExist) {
					// get this person from db
					Person temp = this.iPersonMapper.getByDn(p.getDn());
					inBothPersonList.add(temp);
					boolean noChanged = p.equals(temp);
					if (!noChanged) {
						this.iPersonMapper.update(p);
						updateCount++;
					}
				} else {
					notInDBPersonList.add(p);
				}
			}
			// invalid the persons in DB but is deleted in LDAP
			// this is in both, and in SQL use "not in"
			if (inBothPersonList != null && inBothPersonList.size() > 0) {
				deleteCount = this.iPersonMapper.invalid(inBothPersonList);
			}

			// insert the persons who are not in DB
			for (Person p : notInDBPersonList) {
				this.iPersonMapper.insert(p);
				insertCount++;
			}
			lastUpdateTime.setValue(DateConvertUtil.dateToString(new Date()));
			this.iConfigurationMapper.update(lastUpdateTime);
			updateDBChangeCount(); // update the change count in DB
			return FinalString.UPDATE_LDAP_TO_DB_SUCCESS;
		}
		return FinalString.UPDATE_LDAP_TO_DB_FAILED;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public List<Person> search(String keyValuesStr) throws Exception {
		return this.iPersonMapper.search(KeyStringConventer.convert(keyValuesStr));
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public Person getUserByLoginName(String loginName) throws Exception {
		return this.iPersonMapper.getByLoginName(loginName);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public boolean updateCellphone(Person person) throws Exception {
		return (this.iPersonMapper.updateCellphone(person)>0 ? true : false);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	private void updateDBChangeCount() throws Exception {
		Configuration updateCount = this.iConfigurationMapper
				.getByName("updateCount");
		Configuration insertCount = this.iConfigurationMapper
				.getByName("insertCount");
		Configuration deleteCount = this.iConfigurationMapper
				.getByName("deleteCount");
		updateCount.setValue(this.updateCount + "");
		insertCount.setValue(this.insertCount + "");
		deleteCount.setValue(this.deleteCount + "");
		this.iConfigurationMapper.update(updateCount);
		this.iConfigurationMapper.update(insertCount);
		this.iConfigurationMapper.update(deleteCount);
	}
}
