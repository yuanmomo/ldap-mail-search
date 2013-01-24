package net.yuanmomo.cdut.course.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yuanmomo.cdut.course.web.bean.FileTable;
import net.yuanmomo.cdut.course.web.bean.UserTable;

import org.springframework.beans.factory.BeanFactory;

public class DownloadFileFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//得到当前用户
		UserTable user=null;
		user=(UserTable)((((HttpServletRequest)arg0).getSession()).getAttribute("user"));
		String fileIdStr=((HttpServletRequest)arg0).getParameter("fileId");
		IFileTableDAOBusiness fileDao=(IFileTableDAOBusiness)BeanFactory.getBean("IFileTableDAOBusiness");
		
		if(fileIdStr!=null && !"".equals(fileIdStr) && fileIdStr.matches("\\d+")){
			int fileId=Integer.parseInt(fileIdStr);
			FileTable file=fileDao.getFileByFileId(fileId);
			if(file!=null){
				if(file.getUserRole()==0 || file.getUserRole()==user.getRole()){
					//当前用户的角色和该文件允许查看的角色匹配，则能下载
					file.setDownLoadTimes(file.getDownLoadTimes()+1);
					fileDao.doUpdate(file);
					((HttpServletResponse)arg1).setContentType("application/x-download");
					String fileName=new String(file.getFileName().replace(" ", "_").getBytes("gb2312"),"iso8859-1");
					((HttpServletResponse)arg1).setHeader("Content-Disposition", "attachment;filename="+fileName);
					((HttpServletRequest)arg0).getRequestDispatcher(file.getFileName()).forward(arg0, arg1);
				}else{
					//最后跳至文件列表
					((HttpServletResponse)arg1). sendRedirect("/file.do?c=list");
				}
			}else {
				//最后跳至文件列表
				((HttpServletResponse)arg1). sendRedirect("/file.do?c=list");
			}
		}else {
			//最后跳至文件列表
			((HttpServletResponse)arg1). sendRedirect("/file.do?c=list");
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
