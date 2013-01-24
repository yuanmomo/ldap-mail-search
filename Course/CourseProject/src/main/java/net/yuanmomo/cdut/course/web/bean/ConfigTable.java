package net.yuanmomo.cdut.course.web.bean;

public class ConfigTable{
	private int config_Id;
	private String config_Name;
	private String config_Value;
	private int isDeleted;
	
	public int getConfig_Id() {
		return config_Id;
	}
	public void setConfig_Id(int configId) {
		config_Id = configId;
	}
	public String getConfig_Name() {
		return config_Name;
	}
	public void setConfig_Name(String configName) {
		config_Name = configName;
	}
	public String getConfig_Value() {
		return config_Value;
	}
	public void setConfig_Value(String configValue) {
		config_Value = configValue;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
