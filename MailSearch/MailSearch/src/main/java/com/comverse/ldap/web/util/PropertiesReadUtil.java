package com.comverse.ldap.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesReadUtil {
	private Logger logger = Logger.getLogger(PropertiesReadUtil.class);
	private String fileName;
	private String fileLocation;
	
	public PropertiesReadUtil() {
	}
	public PropertiesReadUtil(String fileName, String fileLocation) {
		this.fileName = fileName;
		this.fileLocation = fileLocation;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Properties read() {
		return this.read(this.fileName,this.fileLocation);
	}

	public Properties read(String fileLocation) {
		String fileName=fileLocation.substring(fileLocation.lastIndexOf("/")+1);
		return this.read(fileName,fileLocation);
	}

	public Properties read(String fileName,String fileLocation) {
		logger.debug("Start to read properties file: "+fileName+", location is "+fileLocation);
		if(fileLocation==null){
			logger.debug("The "+fileName+" file's location is null");
			return null;
		}
		if(fileName==null){
			logger.debug("File name is null, will substring a file name by the file location");
			fileName=fileLocation.substring(fileLocation.lastIndexOf("/")+1);
			logger.debug("the file name is "+ fileName +" after substring");
		}
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(new URI(fileLocation))));
			logger.debug(fileName + " file load success!!");
			return prop;
		} catch (Exception e) {
			logger.debug(fileName + " file load failed or"
					+ " file location invalid, Please check the path of the file");
			return null;
		}
	}
}
