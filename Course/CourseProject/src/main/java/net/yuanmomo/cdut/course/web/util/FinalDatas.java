package net.yuanmomo.cdut.course.web.util;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class FinalDatas implements ServletContextListener  {
	public static String xmlFileBasicLocation=null;
	

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		xmlFileBasicLocation=arg0.getServletContext().getRealPath("/")
		+ File.separator + "WEB-INF" + File.separator + "XMLFiles";
	}
	
}
