package net.yuanmomo.cdut.course.web.dao.impl;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.ConfigTable;
import net.yuanmomo.cdut.course.web.dao.IConfigTableDAO;
import net.yuanmomo.cdut.course.web.util.BasicSqlSupport;

public class IConfigTableDAOImpl extends BasicSqlSupport implements IConfigTableDAO {

	public boolean doInsert(ConfigTable config) {
		boolean flag=false;
		int count=this.session.delete("net.yuanmomo.cudt.course.web.dao.mapper.Config.doInsert",config);
		if(count>0){
			flag=true;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public List<ConfigTable> getConfigsByConfigName(String name) {
		List<ConfigTable> res=null;
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.Config.getConfigsByConfigName", name);
		return res;
	}
}
