package net.yuanmomo.cdut.course.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yuanmomo.cdut.course.web.controller.panel.BuildLeftBar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yhb.dao.IAnnouncementDAO;
import org.yhb.dao.ICourseTableDAO;
import org.yhb.dao.ITimeTableDAO;
import org.yhb.dao.IUserTableDAO;
import org.yhb.dao.Business.IAnnouncementDAOBusiness;
import org.yhb.dao.Business.ICourseTableDAOBusiness;
import org.yhb.dao.Business.ITimeTableDAOBusiness;
import org.yhb.dao.Business.IUserTableDAOBusiness;
import org.yhb.util.BeanFactory;
import org.yhb.util.FinalDatas;
import org.yhb.vo.CourseTable;
import org.yhb.vo.StudentDetail;
import org.yhb.vo.TimeTable;
import org.yhb.vo.UserTable;

@Controller
@RequestMapping("system.do")
public class SystemController {
	
	@RequestMapping(params="c=list")
	public String viewTimeConfigsList(HttpServletRequest request,
			HttpServletResponse resp,ModelMap map){
		UserTable user=(UserTable)request.getSession().getAttribute("user");
		if(user.getRole()==1){
			ITimeTableDAOBusiness dao=(ITimeTableDAOBusiness)BeanFactory.
				getBean("ITimeTableDAOBusiness");
			//所有的时间配置项
			List<TimeTable> timeList=dao.doGetAllTimes();
			
			map.put("times",timeList);
			request.setAttribute("module", "time_list");
			
			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "system");
			map.put("leftBar", leftBar);
			
			return "panel/panel";
		}else{
			map.put("message", "对不起，你没有权限，查看失败");
			map.put("time",3);
			map.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
	@RequestMapping(params="c=initial")
	public String doInitialPage(HttpServletRequest request,
			HttpServletResponse resp,ModelMap map){
		UserTable user=(UserTable)request.getSession().getAttribute("user");
		if(user.getRole()==1){
			//所有的时间配置项
			request.setAttribute("module", "initial_list");
			
			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "system");
			map.put("leftBar", leftBar);
			
			return "panel/panel";
		}else{
			map.put("message", "对不起，你没有权限，查看失败");
			map.put("time",3);
			map.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
	
	@RequestMapping(params="c=doInitial")
	public String doSytemInitial(HttpServletRequest request,
			HttpServletResponse resp,ModelMap map){
		String news=request.getParameter("news");
		String student=request.getParameter("student");
		String course=request.getParameter("course");
		String teachers=request.getParameter("teachers");
		
		System.out.println(news);
		System.out.println(student);
		System.out.println(course);
		System.out.println(teachers);
		
		UserTable user=(UserTable)request.getSession().getAttribute("user");
		IAnnouncementDAOBusiness annouDao=(IAnnouncementDAOBusiness)BeanFactory.getBean("IAnnouncementDAOBusiness");
		IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
		ICourseTableDAOBusiness courseDao=(ICourseTableDAOBusiness)BeanFactory.getBean("ICourseTableDAOBusiness");
		if(user.getRole()==1){
			map.put("message", "恭喜，系统初始化成功！！");
			map.put("time",3);
			map.put("url", "news.do?c=newslist");
			try {
				if(news!=null && "1".equals(news)){
					//删除公告信息
					annouDao.initialSystem();
				}
				if(student!=null && "1".equals(student)){
					//删除学生信息
					userDao.initialSystemDeleteStudents();
				}
				if(course!=null && "1".equals(course)){
					//删除课题
					courseDao.initialSystem();
				}
				if(teachers!=null && "1".equals(teachers)){
					//删除课题
					userDao.initialSystemDeleteTeachers();
				}
			} catch (Exception e) {
				map.put("message", "对不起，系统初始化失败，请联系管理员！！");
			}
			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "news");
			map.put("leftBar", leftBar);
		}else{
			map.put("message", "对不起，你没有权限，初始化失败");
			map.put("time",3);
			map.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
	
	
	@RequestMapping(params = "c=editTime")
	public String editTimeConfig(HttpServletRequest request, ModelMap map) {
		String idStr = request.getParameter("id");
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(idStr);
			// 初始化右边的编辑窗口
			ITimeTableDAOBusiness dao=(ITimeTableDAOBusiness)BeanFactory.
			getBean("ITimeTableDAOBusiness");
			TimeTable time=dao.getATimeTableById(id);
			map.put("time",time);
			// 页面左边边栏操作栏目
			UserTable user = (UserTable) request.getSession().getAttribute("user");
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "system");
			map.put("leftBar", leftBar);

			request.setAttribute("module", "time_edit");
			return "panel/panel";
		}
		map.put("message", "对不起，操作有误，将返回");
		map.put("time",3);
		map.put("url", "news.do?c=newslist");
		return "global/notify";
	}
	
	/**
	 * 保存和更新专业
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=saveTime")
	public String saveTimeConfig(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		String idStr = request.getParameter("id");
		if (idStr == null || "".equals(idStr)|| !idStr.matches("\\d")) {
			modelMap.put("message", "对不起，操作有误，将返回");
			modelMap.put("time",3);
			modelMap.put("url", "news.do?c=newslist");
			return "global/notify";
		}
		
		UserTable user = (UserTable) request.getSession().getAttribute("user");

		ITimeTableDAOBusiness dao=(ITimeTableDAOBusiness)BeanFactory.
				getBean("ITimeTableDAOBusiness");
		if (user.getRole() == 1) {
			// 接受页面端参数，进行验证
			String timeStart = request.getParameter("timeStart").trim();
			String timeEnd = request.getParameter("timeEnd").trim();
			String isUsed=request.getParameter("isUsed");
			String timeName=request.getParameter("timeName");
			TimeTable time=new TimeTable();
			if(timeName==null||"".equals(timeName)){
				time=dao.getATimeTableById(Integer.parseInt(idStr));
			}else{
				time.setTimeName(timeName);
			}
			time.setTimeId(Integer.parseInt(idStr));
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			try {
				time.setTimeStart(format.parse(timeStart));
				time.setTimeEnd(format.parse(timeEnd));
				time.setIsUsed(Integer.parseInt(isUsed));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// 验证开始时间不为空
			if (timeStart == null || "".equals(timeStart)) {
				time.setTimeStart(null);
				modelMap.put("time",time);
				modelMap.put("error", "请输入开始时间");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "system");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "time_edit");
				return "panel/panel";
			}
			//判断截止日期不为空
			if (timeEnd == null || "".equals(timeEnd)) {
				time.setTimeEnd(null);
				modelMap.put("time",time);
				modelMap.put("error", "请输入截止时间");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "system");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "time_edit");
				return "panel/panel";
			}
			//判断截止应该大于开始日期
			Date date1=time.getTimeStart();
			Date date2=time.getTimeEnd();
			if(date1.getTime()>date2.getTime()){
				time.setTimeEnd(null);
				time.setTimeStart(null);
				modelMap.put("time",time);
				modelMap.put("error", "开始时间和截止时间错误");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "system");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "time_edit");
				return "panel/panel";
			}
			// 数据验证完毕
			//更新该项设置
			boolean flag = dao.doUpdate(time);
			if (flag) {
				modelMap.put("message", "更新设置成功");
			} else {
				modelMap.put("message", "对不起，更新设置失败");
			}
			modelMap.put("url", "system.do?c=list");
		} else {
			modelMap.put("message", "对不起，你没有权限，更新失败");
			modelMap.put("url", "news.do?c=newslist");
		}
		modelMap.put("time", "3");
		return "global/notify";
	}
	
	@RequestMapping(params = "c=saveConfig")
	public String saveConfig(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		String idStr = request.getParameter("id");
		if (idStr == null || "".equals(idStr)|| !idStr.matches("\\d")) {
			modelMap.put("message", "对不起，操作有误，将返回");
			modelMap.put("time",3);
			modelMap.put("url", "news.do?c=newslist");
			return "global/notify";
		}
		
		UserTable user = (UserTable) request.getSession().getAttribute("user");

		ITimeTableDAOBusiness dao=(ITimeTableDAOBusiness)BeanFactory.
				getBean("ITimeTableDAOBusiness");
		if (user.getRole() == 1) {
			// 接受页面端参数，进行验证
			String isUsed=request.getParameter("isUsed");
			String timeName=request.getParameter("timeName");
			
			TimeTable time=new TimeTable();
			if(timeName==null||"".equals(timeName)){
				time=dao.getATimeTableById(Integer.parseInt(idStr));
			}else{
				time.setTimeName(timeName);
			}
			time.setTimeId(Integer.parseInt(idStr));
			try {
				time.setIsUsed(Integer.parseInt(isUsed));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 数据验证完毕
			//更新该项设置
			boolean flag = dao.doUpdate(time);
			if (flag) {
				modelMap.put("message", "更新设置成功");
			} else {
				modelMap.put("message", "对不起，更新设置失败");
			}
			modelMap.put("url", "system.do?c=list");
		} else {
			modelMap.put("message", "对不起，你没有权限，更新失败");
			modelMap.put("url", "news.do?c=newslist");
		}
		modelMap.put("time", "3");
		return "global/notify";
	}
	@RequestMapping(params = "c=atuoAssign")
	public String doAutoAssign(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		if (user.getRole() == 1) {
			IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
			ICourseTableDAOBusiness courseDao=(ICourseTableDAOBusiness)BeanFactory.getBean("ICourseTableDAOBusiness");
			//查找当前所有的学生列表
			List<StudentDetail> leftStudents=userDao.getStudentsWithClass();
			System.out.println("当前一共有"+leftStudents.size()+"个学生");
			//去掉已选课的学生
			if(leftStudents!=null &&  leftStudents.size()>0){
				Iterator<StudentDetail> stuIte=leftStudents.iterator();
				while(stuIte.hasNext()){
					StudentDetail s=stuIte.next();
					if(s.getStuCourseId()!=0 && s.getStuTeacherId()!=0){
						stuIte.remove();
					}
				}
				//剩下的学生还未选课
				System.out.println("当前一共有"+leftStudents.size()+"个学生");
				
				//所有的老师
				List<UserTable> leftTeachers=userDao.getAllTeachers();
				System.out.println("当前一共有"+leftTeachers.size()+"个老师");
				//当前所有老师还剩多少个学生空位可以选
				int needStudentsCount=0;
				if(leftTeachers!=null && leftTeachers.size()>0){
					Iterator<UserTable> ite=leftTeachers.iterator();
					while(ite.hasNext()){
						UserTable u=ite.next();
						//判断该老师是否被选满
						//没有出够题目的老师不参与分配
						int cs=(int)(u.getTeaMaxStu()*1.5);
						if(u.getTeaMaxStu()<=u.getTeaCurrentStu() || courseDao.getCountOneTeacher(u.getUserId())<cs){
							ite.remove();
						}else{
							needStudentsCount+=(u.getTeaMaxStu()-u.getTeaCurrentStu());
						}
					}
				}
				//剩下为选满的老师
				System.out.println("当前一共有"+leftTeachers.size()+"个老师");
				//剩下多好个空位可以被学生选择
				System.out.println("当前还有"+needStudentsCount+"个学生可以选课题");	
				//遍历学生和老师，一个学生的向老师中加入，如果老师被填满，则选下一个老师
				//剩余的可选学生总人数大于当前未选课题的学生人数
				if(leftStudents.size()<needStudentsCount){
					//查出所有未被选课题
					List<CourseTable> leftCoursese=courseDao.doQuery();
					if(leftCoursese!=null && leftCoursese.size()>0){
						Iterator<CourseTable> courseIte=leftCoursese.iterator();
						while(courseIte.hasNext()){
							CourseTable c=courseIte.next();
							if(c.getIsSelected()!=0){
								courseIte.remove();
							}
						}
					}
					//剩下为备选课题
					System.out.println("当前一共有"+leftCoursese.size()+"个课题未被选");
					
					///开始自动分配
					for(UserTable teacher:leftTeachers){
						//判断是否还剩下未选课题学生
						if(leftStudents==null || leftStudents.size()==0){
							//没有剩余学生，分配完成
							modelMap.put("message", "所有学生都已经参与分配课题，分配完成！！");
							modelMap.put("url", "news.do?c=newslist");
							modelMap.put("time", "3");
							return "global/notify";
						}
						//首先得到该老师未备选的课题有几个
						List<CourseTable> leftCourByTea=courseDao.doQueryOneTeacher(teacher.getUserId());
						//去掉已经被选的
						if(leftCourByTea!=null && leftCourByTea.size()>0){
							Iterator<CourseTable> courIte=leftCourByTea.iterator();
							while(courIte.hasNext()){
								CourseTable c=courIte.next();
								if(c.getIsSelected()!=0){
									courIte.remove();
								}
							}
						}
						//该老师需要的学生人数
						int needStuCount=teacher.getTeaMaxStu()-teacher.getTeaCurrentStu();
						//剩余的题目数必须小于当前的需要学生人数
						if(leftCourByTea.size()>=needStuCount){
							for(int i=0;i<needStuCount;i++){
								//判断是否还剩下未选课题学生
								if(leftStudents==null || leftStudents.size()==0){
									//没有剩余学生，分配完成
									modelMap.put("message", "剩余学生已经完全分配，分配完成！！");
									modelMap.put("url", "news.do?c=newslist");
									modelMap.put("time", "3");
									return "global/notify";
								}
								StudentDetail stu=leftStudents.get(0);
								UserTable student=userDao.getUserById(stu.getUserId());
								//在该老师剩余课题中拿出一个课题
								CourseTable course=leftCourByTea.get(0);
								
								// 刷新学生信息
								student.setStuTeacherId(course.getTeacher());
								student.setStuCourseId(course.getCourse_Id());
								// 刷新课题信息
								course.setIsSelected(1);
								course.setStudent(student.getUserId());
								// 刷新该课题对应的教师当前带生人数
								teacher.setTeaCurrentStu(teacher.getTeaCurrentStu()+1);
								
								//更新数据库
								userDao.doUpdate(student);
								userDao.doUpdate(teacher);
								courseDao.doUpdate(course);
								
								//删除该条数据，剩余列表的学生和该老师剩余课题
								leftCourByTea.remove(0);
								leftStudents.remove(0);
							}
						}
					}
					modelMap.put("message", "分配完成");
					modelMap.put("url", "news.do?c=newslist");
					modelMap.put("time", "3");
					return "global/notify";
				}else{
					modelMap.put("message", "对不起，当前所有教师剩余的可被选学生总数少于未选学生人数，无法分配！！");
					modelMap.put("url", "news.do?c=newslist");
					modelMap.put("time", "3");
					return "global/notify";
				}
			}
		} else {
			modelMap.put("message", "对不起，你没有权限，分配失败");
			modelMap.put("url", "news.do?c=newslist");
		}
		modelMap.put("time", "3");
		return "global/notify";
	}
}
