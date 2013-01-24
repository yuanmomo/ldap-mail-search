package net.yuanmomo.cdut.course.web.util;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Web application lifecycle listener.
 * @author yuanmomo
 */
public class StartUp implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		 try{
          ApplicationContext ctx=WebApplicationContextUtils.
          			getWebApplicationContext(sce.getServletContext());
          BeanFactory.getInstance().setContext(ctx);
      }catch(Exception ex){
          ex.printStackTrace();
      }
      System.out.println("选题系统启动成功 时间:"+new Date().toString());
	}
}

