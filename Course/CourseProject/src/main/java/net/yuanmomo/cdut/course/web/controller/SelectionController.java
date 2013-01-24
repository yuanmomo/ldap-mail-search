package net.yuanmomo.cdut.course.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yhb.dao.ICourseTableDAO;
import org.yhb.dao.ITimeTableDAO;
import org.yhb.dao.IUserTableDAO;
import org.yhb.dao.business.SelectionBusiness;
import org.yhb.dao.Business.ICourseTableDAOBusiness;
import org.yhb.dao.Business.ITimeTableDAOBusiness;
import org.yhb.dao.Business.IUserTableDAOBusiness;
import org.yhb.util.BeanFactory;
import org.yhb.vo.CourseTable;
import org.yhb.vo.TimeTable;
import org.yhb.vo.UserTable;

@Controller
@RequestMapping("/selection.do")
public class SelectionController {

	//	  obj.statu：
	//    1  当前选题编号错误，请刷新选题列表后再进行选题操作
	//    2  当前选题不存在，请刷新选题列表
	//    3  该课题已经被选，请选择其它课题
	//    4  对不起，您只能选则一个选题
	//    5  当前选题没有带生老师，请选择其它选题
	//    6 选题时间不在规定时间内，无法进行操作
	
	//    7 当前退选课题与当前登录用户所选课题不匹配，请与管理员联系
	//    8 学生当前还没有进行选题
	//    9
	
	//    10  选题成功
	//    11退选成功
	
	
	@RequestMapping(params = "c=select")
	public String doSelect(int id, ModelMap map, HttpServletRequest request) {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		// 判断是否在选题时间内
		// if(!(d.getTime()>=py.getDtstart().getTime()&&d.getTime()<=py.getDtend().getTime())){
		// json.put("statu",6);
		// json.put("msg", "选入失败  选题时间已结束");
		// modelMap.put("json", json);
		// return "json";
		// }
		// 用户身份，学生才能选题
		JSONObject json = new JSONObject();
		ITimeTableDAOBusiness dao=(ITimeTableDAOBusiness)BeanFactory.
			getBean("ITimeTableDAOBusiness");
		TimeTable  time=dao.getATimeTableByName("选题系统开放时间");
		if(time.getIsUsed()==1){//是否开启了时间限制系统
			Date now=new Date();
			if(now.getTime() <(time.getTimeStart().getTime())){
				try {
					json.put("statu", 2);
					json.put("msg", "还没有到选题时间，请等待通知");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.put("json", json);
				return "json";
			}
			if(now.getTime() >(time.getTimeEnd().getTime())){
				try {
					json.put("statu", 2);
					json.put("msg", "选题系统开放时间已经结束，你不能再进行选题");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.put("json", json);
				return "json";
			}
		}
		if (user==null||user.getRole() != 3 ) {
			map.put("message", "当前用户不能进行选题操作，请使用学生用户登录");
			map.put("url", "login.do");
			map.put("time", "3");
			return "global/notify";
		}
		//选题id是否合法
		if (id<=0) {
			try {
				json.put("statu", 1);
				json.put("msg", "当前选题编号错误，请刷新选题列表后再进行选题操作");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("json", json);
			return "json";
		}
		ICourseTableDAOBusiness courseDao = (ICourseTableDAOBusiness) BeanFactory
				.getBean("ICourseTableDAOBusiness");
		IUserTableDAOBusiness userDao = (IUserTableDAOBusiness) BeanFactory
				.getBean("IUserTableDAOBusiness");
		CourseTable course = courseDao.doGetACourseById(id);
		//判断该选题是否存在
		if (course == null) {
			try {
				json.put("statu", 2);
				json.put("msg", "当前选题不存在，请刷新选题列表");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("json", json);
			return "json";
		}
		// 首先判断该课题是否被选
		if (course.getIsSelected() != 0) {
			try {
				json.put("statu", 3);
				json.put("msg", "该课题已经被选，请选择其它课题");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("json", json);
			return "json";
		}
		
		// 再判断该学生是否已经选题
		if (user.getStuCourseId() != 0) {
			try {
				json.put("statu", 4);
				json.put("msg", "对不起，您只能选则一个选题.");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("json", json);
			return "json";
		} 
		// 再判断当前课题的所属教师带生人数是否已经达到最大带生人数
		UserTable teacher = userDao.getUserById(course.getTeacher());
		if (teacher== null) {
			try {
				json.put("statu", 5);
				json.put("msg", "当前选题没有带生老师，请选择其它选题");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("json", json);
			return "json";
		}
		if (teacher.getTeaCurrentStu() >= teacher.getTeaMaxStu()) {
			map.put("message", "对不起，该课题所属老师带生人数已经达到上限。系统将自动刷新选题列表");
			map.put("url", "course.do?c=list");
			map.put("time", "3");
			return "global/notify";
		}
		/***
		 * 以下代码移植到SelectionBusiness中  为了添加事务，当初系统设计的不合理
		 */
//		// 教师带生人数没有达到上限，课题没有被选择，学生也没有选到课题
//		// 刷新学生信息
//		user.setStuTeacherId(course.getTeacher());
//		user.setStuCourseId(id);
//		// 刷新课题信息
//		course.setIsSelected(1);
//		course.setStudent(user.getUserId());
//		// 刷新该课题对应的教师当前带生人数
//		teacher.setTeaCurrentStu(teacher.getTeaCurrentStu()+1);
//		
//		// 刷新Session中的学生用户信息
//		request.getSession().setAttribute("user", user);
//		
//		//更新数据库
//		userDao.doUpdate(user);
//		userDao.doUpdate(teacher);
//		courseDao.doUpdate(course);
		try {
			SelectionBusiness business=(SelectionBusiness)BeanFactory.getBean("selectionBusiness");
			business.doSelect(user, teacher, id, course, userDao, courseDao);
			// 刷新Session中的学生用户信息
			request.getSession().setAttribute("user", user);
			try {
				json.put("statu", 10);
				json.put("msg", "选题成功");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			// 刷新Session中的学生用户信息
			// TODO: handle exception
			user.setStuTeacherId(0);
			user.setStuCourseId(0);
			try {
				json.put("statu", 9);
				json.put("msg", "服务器异常");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
		}
		
		map.put("json", json);
		return "json";
	}
	@RequestMapping(params = "c=deSelect")
	public String doDeselect(int id, ModelMap map, HttpServletRequest request) {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		// 判断是否在选题时间内
		ITimeTableDAOBusiness dao=(ITimeTableDAOBusiness)BeanFactory.
		getBean("ITimeTableDAOBusiness");
		JSONObject json = new JSONObject();
		TimeTable time=dao.getATimeTableByName("选题系统开放时间");
		if(time.getIsUsed()==1){//是否开启了时间限制系统
			Date now=new Date();
			if(now.getTime() <(time.getTimeStart().getTime())){
				try {
					json.put("statu", 2);
					json.put("msg", "对不起，操作有误。");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.put("json", json);
				return "json";
			}
			if(now.getTime() >(time.getTimeEnd().getTime())){
				try {
					json.put("statu", 2);
					json.put("msg", "选题系统开放时间已经结束，你不能退选题目");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.put("json", json);
				return "json";
			}
		}
		// 用户身份，学生才能退选
		if (user==null||user.getRole() != 3 ) {
			map.put("message", "当前用户不能进行选题操作，请使用学生用户登录");
			map.put("url", "login.do");
			map.put("time", "3");
			return "global/notify";
		}
		//判断用户是否选择了需要退选的课题
		if(user.getStuCourseId()==0 || user.getStuCourseId()!=id){
			try {
				json.put("statu", 7);
				json.put("msg", "当前退选课题与当前登录用户所选课题不匹配，请与管理员联系");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("json", json);
			return "json";
		}
		// 再判断该学生是否已经选题
		if (user.getStuCourseId() == 0) {
			try {
				json.put("statu", 8);
				json.put("msg", "对不起，该学生还没有进行选题.");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("json", json);
			return "json";
		} 
		ICourseTableDAOBusiness courseDao = (ICourseTableDAOBusiness) BeanFactory
				.getBean("ICourseTableDAOBusiness");
		IUserTableDAOBusiness userDao = (IUserTableDAOBusiness) BeanFactory
				.getBean("IUserTableDAOBusiness");
		CourseTable course = courseDao.doGetACourseById(id);
		//判断需要退选的选题是否存在
		if (course == null) {
			try {
				json.put("statu", 7);
				json.put("msg", "当前需要退选的选题不存在，请与管理员联系");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("json", json);
			return "json";
		}

		/***
		 * 以下代码移植到SelectionBusiness中  为了添加事务，当初系统设计的不合理
		 */
		//学生确实选了该课题，并且学生存在，该课题也存在
		//进行退选
		//更新学生信息
//		user.setStuCourseId(0);
//		user.setStuTeacherId(0);
//		userDao.doUpdate(user);
		
//		//更新课题信息
//		course.setStudent(0);
//		course.setIsSelected(0);
//		courseDao.doUpdate(course);
//		//更新该课题对应教师信息
//		UserTable teacher = userDao.getUserById(course.getTeacher());
//		if(teacher!=null){
//			// 刷新该课题对应的教师当前带生人数
//			teacher.setTeaCurrentStu(teacher.getTeaCurrentStu()-1);
//			userDao.doUpdate(teacher);
//		}
		try{
			SelectionBusiness business=(SelectionBusiness)BeanFactory.getBean("selectionBusiness");
			business.doDeselect(user, id, course, userDao, courseDao);
			request.getSession().setAttribute("user",user);
			try {
				json.put("statu", 11);
				json.put("msg", "退选成功");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
			//拋出异常，重新设置数据
			//设置学生数据
			user.setStuCourseId(course.getCourse_Id());
			user.setStuTeacherId(course.getTeacher());
			try {
				json.put("statu", 9);
				json.put("msg", "服务器异常");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
		}
		map.put("json", json);
		return "json";
	}
}
