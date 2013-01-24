package net.yuanmomo.cdut.course.web.dao;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.ConfigTable;

public interface IConfigTableDAO {
	//doInsert a config and value
	public boolean doInsert(ConfigTable config);
	//get configs by seach for a config_name
	public List<ConfigTable> getConfigsByConfigName(String name);
}
