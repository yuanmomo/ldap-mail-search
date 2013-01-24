package net.yuanmomo.cdut.course.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.yuanmomo.cdut.course.web.bean.CourseTea;
import net.yuanmomo.cdut.course.web.bean.UserTable;
import net.yuanmomo.cdut.course.web.util.BeanFactory;

public class LoginFilterController implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		String param=((HttpServletRequest)arg0).getParameter("filter");
		if(param!=null&&"false".equals(param)){
			//提交路径后跟有filter=false，表示不过滤该请求
			arg2.doFilter(arg0, arg1);
		}else{
			UserTable user=null;
			user=(UserTable)((((HttpServletRequest)arg0).getSession()).getAttribute("user"));
			if(user!=null){//session中不为空时
				//不为空，表示已经成功登录
				ICourseTableDAOBusiness courseDao=(ICourseTableDAOBusiness)BeanFactory.getBean("ICourseTableDAOBusiness");
				
				//查找总课题数目，已经剩余课题数目
				//总题数
				int totalCourses=courseDao.getCount();
				int leftCourses=0;
				//剩余可选题目数
				IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
				List<UserTable> teachers=userDao.getAllTeachers();
				if(teachers!=null && teachers.size()>0){
					Iterator<UserTable> ite=teachers.iterator();
					//需要显示其课题老师的所有编号的list
					List<Integer> teaIds=new ArrayList<Integer>();
					while(ite.hasNext()){
						UserTable temp=ite.next();
						//判断该老师的出题是否够了a
						//并且，当前老师的带生人数要小于最大带生人数
						int cs=(int)(temp.getTeaMaxStu()*1.5);
						if(cs<=courseDao.getCountOneTeacher(temp.getUserId())
							&&temp.getTeaCurrentStu()<temp.getTeaMaxStu()){
							teaIds.add(temp.getUserId());
						}
					}
					if(teaIds!=null&& teaIds.size()>0){
						List<CourseTea> results=courseDao.doGetCoursesByTeaIds(teaIds);
						if(results!=null && results.size()>0){
							Iterator<CourseTea> iteTemp=results.iterator();
							while(iteTemp.hasNext()){
								CourseTea temp=iteTemp.next();
								if(temp.getIsSelected()==1){
									iteTemp.remove();
								}
							}
						}
						leftCourses=results.size();
					}
				}
				((HttpServletRequest)arg0).getSession().setAttribute("totalCourses",totalCourses);
				((HttpServletRequest)arg0).getSession().setAttribute("leftCourses",leftCourses);
				
				
				//如果是教师用户，判断其资料是否完全，
				//如果不完全，则始终跳转至教师个人资料编辑窗口
				//页面左边边栏操作栏目
				((HttpServletRequest)arg0).getSession().setAttribute("teaCurrentCourses",courseDao.getCountOneTeacher(user.getUserId()));
				if(user.getRole()==2&&
								(user.getEmail()==null || "".equals(user.getEmail())
							  || user.getPhone()==null || "".equals(user.getPhone())
							  || user.getTeaPosition()==0)){
					//跳转至教师登录页面
					((HttpServletRequest)arg0).setAttribute("error","请您完善个人资料，才能正常使用本系统其它功能");
					arg0.getRequestDispatcher("user.do?c=teaEdit&id="+user.getUserId()).forward(arg0, arg1);
				}
				arg2.doFilter(arg0, arg1);
			}else{
				//还未登录，跳转至登录页面
				arg0.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(arg0, arg1);
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
