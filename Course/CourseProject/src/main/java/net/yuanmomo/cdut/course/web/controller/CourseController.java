/*  Copyright 2010 princehaku
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Created on : Sep 23, 2011, 11:55:45 PM
 *  Author     : princehaku
 */
package net.yuanmomo.cdut.course.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import net.yuanmomo.cdut.course.web.controller.panel.BuildLeftBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.yhb.dao.Business.IClassTableDAOBusiness;
import org.yhb.dao.Business.ICourseSourceDAOBusiness;
import org.yhb.dao.Business.ICourseTableDAOBusiness;
import org.yhb.dao.Business.ICourseTypeDAOBusiness;
import org.yhb.dao.Business.IMajorTableDAOBusiness;
import org.yhb.dao.Business.IPositionTableDAOBusiness;
import org.yhb.dao.Business.ITimeTableDAOBusiness;
import org.yhb.dao.Business.IUserTableDAOBusiness;
import org.yhb.util.BeanFactory;
import org.yhb.util.FinalDatas;
import org.yhb.util.PageSplit;
import org.yhb.vo.ClassDetail;
import org.yhb.vo.CourseDetail;
import org.yhb.vo.CourseTable;
import org.yhb.vo.CourseTea;
import org.yhb.vo.MajorTable;
import org.yhb.vo.StudentDetail;
import org.yhb.vo.TimeTable;
import org.yhb.vo.UserTable;

/**
 * 新闻控制
 * 
 * @author princehaku
 */
@Controller
@RequestMapping("/course.do")
public class CourseController {

	/**
	 * 显示课题详情
	 * 
	 * @param request  
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=view")
	public String viewCourse(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		String idStr = request.getParameter("id");// 查看选题详细信息
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(request.getParameter("id"));
			ICourseTableDAOBusiness dao = (ICourseTableDAOBusiness) BeanFactory
					.getBean("ICourseTableDAOBusiness");
			CourseTable course = dao.doGetACourseById(id);
			modelMap.put("course", course);
			
			UserTable currentUser = (UserTable) request.getSession().getAttribute("user");
			if(currentUser.getRole()==3){
				ITimeTableDAOBusiness confiDao=(ITimeTableDAOBusiness)BeanFactory.
				getBean("ITimeTableDAOBusiness");
				TimeTable config=confiDao.getATimeTableByName("教师姓名对学生是否可见");
				boolean showTeaName=config.getIsUsed()==1?true:false;
				if(!showTeaName){
					return "panel/course_view";
				}
			}
			
			// 根据选题查找指导老师信息
			IUserTableDAOBusiness userDao = (IUserTableDAOBusiness) BeanFactory
					.getBean("IUserTableDAOBusiness");
			UserTable user = userDao.getUserById(course.getTeacher());
			modelMap.put("teaName", user.getUserName());
			
			//如果是老师，则还需要查找该选题是否被选以及学生信息
			if(course.getStudent()>0){
				StudentDetail stu=userDao.getStudentDetailById(course.getStudent());
				modelMap.put("stu",stu);
			}
		}
		return "panel/course_view";
	}

	/**
	 * 课题删除
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=delete")
	public String deleteCourse(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		modelMap.put("url", "course.do?c=list");
		modelMap.put("time", "4");
		//对于教师用户，修改课题时需要判断其是否在学生的选课期间
		//检查是否在学生的选课时间段内,已经出题时间内
		ITimeTableDAOBusiness timeDao=(ITimeTableDAOBusiness)BeanFactory.
				getBean("ITimeTableDAOBusiness");
		//当前时间
		long now=new Date().getTime();
		TimeTable selectTime=timeDao.getATimeTableByName("选题系统开放时间");
		if(selectTime.getIsUsed()==1){//是否开启了选题时间限制系统
			if(now>=(selectTime.getTimeStart().getTime())
			   && now<=(selectTime.getTimeEnd().getTime())){
				modelMap.put("message", "对不起，学生选课期间，老师您不能删除课题");
				modelMap.put("url", "news.do?c=newslist");
				return "global/notify";
			}
		}
		if (user.getRole() == 1 || user.getRole()==2) {// 是管理员和老师
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")) {
				int id = Integer.parseInt(request.getParameter("id"));
				ICourseTableDAOBusiness dao = (ICourseTableDAOBusiness) BeanFactory
						.getBean("ICourseTableDAOBusiness");
				CourseTable course=dao.doGetACourseById(id);
				//判断该课题是否存在
				boolean flag=false;
				if(course!=null){
					//删除课题是要删除选了该课题的学生信息
					IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
					UserTable stu=null;
					if(course.getStudent()>0){
						stu=userDao.getUserById(course.getStudent());
					}
					if(stu!=null){
						stu.setStuCourseId(0);
						stu.setStuTeacherId(0);
						userDao.doUpdate(stu);
					}
					//更新课题和学生信息
					flag = dao.doDelete(id);
				}
				if (flag) {
					modelMap.put("message", "删除成功");
					if(user.getRole()==2){
						int temp=(Integer)request.getSession().getAttribute("teaCurrentCourses");
						request.getSession().setAttribute("teaCurrentCourses",temp-1);
					}
				} else {
					modelMap.put("message", "对不起，删除失败");
				}
			}
		} else {
			modelMap.put("message", "对不起，你没有权限，删除失败");
			modelMap.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
	@RequestMapping(params = "c=selected")
	public String viewSelectedByStu(HttpServletRequest request, ModelMap map) {
		// 初始化右边的选题列表
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		//得到当前操作用户
		
		// 查询要列表显示的课题
		ICourseTableDAOBusiness dao = ((ICourseTableDAOBusiness) BeanFactory
				.getBean("ICourseTableDAOBusiness"));
		//根据不同的身份，查询得到不同的结果
		
		if(user.getRole()==3&&user.getStuCourseId()>0){//学生只能查看自己已经选择的课题信息
			CourseTea course=dao.doGetACourseWithTeaById(user.getStuCourseId());
			
			// 并且对课题的描述字段course_Description 处理，只显示前100个字符
			// 可以根据配置选项，选择是否显示教师姓名
			String strTemp = "<br /><a style=\"color:red\" href=\"javascript:void(0);\" onclick=\"viewCourse(";
			String strTemp2 = ")\" >.......请点击课题查看详情</a>";
			// 处理课题描述
			if (course!=null&&course.getCourse_Description().length() > 50) {
				course.setCourse_Description(new StringBuffer(course
						.getCourse_Description().substring(0, 50)).append(
						strTemp).append(course.getCourse_Id()).append(strTemp2)
						.toString());
			}
			List<CourseTea> courses=new ArrayList<CourseTea>();
			courses.add(course);
			request.setAttribute("courses", courses);
		}
		// 处理分页代码，需要使用DAO查询结果的总行数
		request.setAttribute("module", "course_list");

		// 页面左边边栏操作栏目
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "course");
		map.put("leftBar", leftBar);

		return "panel/panel";
	}
	
	@RequestMapping(params = "c=list")
	public String viewCourseList(HttpServletRequest request, ModelMap map) {
		// 初始化右边的选题列表
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		//得到当前操作用户
		
		// 处理分页情况
		PageSplit page=(PageSplit)BeanFactory.getBean("pageSplit");
		page.setParameters(request);
		page.setPageSize(30);
		
		// 查询要列表显示的课题
		ICourseTableDAOBusiness dao = ((ICourseTableDAOBusiness) BeanFactory
				.getBean("ICourseTableDAOBusiness"));
		//根据不同的身份，查询得到不同的结果
		
		if(user.getRole()==2){//教师只能查看自己的课题
			List<CourseTable> courses=dao.doQueryOneTeacher(page.getCurrentPage(),page.getPageSize(), user.getUserId());
			page.setResultCount(dao.getCountOneTeacher(user.getUserId()));
			
			// 并且对课题的描述字段course_Description 处理，只显示前100个字符
			// 可以根据配置选项，选择是否显示教师姓名
			Iterator<CourseTable> ite = courses.iterator();
			String strTemp = "<br /><a style=\"color:red\" href=\"javascript:void(0);\" onclick=\"viewCourse(";
			String strTemp2 = ")\" >.......请点击课题查看详情</a>";
			while (ite.hasNext()) {
				CourseTable temp = ite.next();
	
				// 处理课题描述
				if (temp.getCourse_Description().length() > 50) {
					temp.setCourse_Description(new StringBuffer(temp
							.getCourse_Description().substring(0, 50)).append(
							strTemp).append(temp.getCourse_Id()).append(strTemp2)
							.toString());
				}
			}
			request.setAttribute("courses", courses);
		}else if(user.getRole()==1){//管理员可以查看所有的课题
			List<CourseTea> courses= dao.doQueryAndTeacher(page.getCurrentPage(),page.getPageSize());
			page.setResultCount(dao.getCount());
		
			// 并且对课题的描述字段course_Description 处理，只显示前100个字符
			Iterator<CourseTea> ite = courses.iterator();
			String strTemp = "<br /><a style=\"color:red\" href=\"javascript:void(0);\" onclick=\"viewCourse(";
			String strTemp2 = ")\" >.......请点击课题查看详情</a>";
			while (ite.hasNext()) {
				CourseTea temp = ite.next();
	
				// 处理课题描述
				if (temp.getCourse_Description().length() > 50) {
					temp.setCourse_Description(new StringBuffer(temp
							.getCourse_Description().substring(0, 50)).append(
							strTemp).append(temp.getCourse_Id()).append(strTemp2)
							.toString());
				}
			}
			request.setAttribute("courses", courses);
		}else if(user.getRole()==3){
			//学生只能查看规定的可以
			//1.老师如果没有出够题目，那么该老师的课题对学生是不可见的
			//首先查看哪些教师出够题目
			IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
			ICourseTableDAOBusiness courseDao=(ICourseTableDAOBusiness)BeanFactory.getBean("ICourseTableDAOBusiness");
			List<UserTable> teachers=userDao.getAllTeachers();
			Iterator<UserTable> ite=null;
			if(teachers!=null && teachers.size()>0){
				ite=teachers.iterator();
			
				//需要显示其课题老师的所有编号的list
				List<Integer> teaIds=new ArrayList<Integer>();
				if(teachers!=null&& teachers.size()>0){
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
				}
				if(teaIds!=null&& teaIds.size()>0){
					List<CourseTea> results=courseDao.doGetCoursesByTeaIds(page.getCurrentPage(), page.getPageSize(), teaIds);
					page.setResultCount(courseDao.getCountOfTeaIds(teaIds));
					// 可以根据配置选项，选择是否显示教师姓名
					ITimeTableDAOBusiness confiDao=(ITimeTableDAOBusiness)BeanFactory.
						getBean("ITimeTableDAOBusiness");
					TimeTable config=confiDao.getATimeTableByName("教师姓名对学生是否可见");
					boolean showTeaName=config.getIsUsed()==1?true:false;
					// 并且对课题的描述字段course_Description 处理，只显示前100个字符
					Iterator<CourseTea> tesIte = results.iterator();
					String strTemp = "<br /><a style=\"color:red\" href=\"javascript:void(0);\" onclick=\"viewCourse(";
					String strTemp2 = ")\" >.......请点击课题查看详情</a>";
					while (tesIte.hasNext()) {
						CourseTea temp = tesIte.next();
						//显示教师名字
						if(!showTeaName){
							temp.setUserName(null);
						}
						// 处理课题描述
						if (temp.getCourse_Description().length() > 50) {
							temp.setCourse_Description(new StringBuffer(temp
									.getCourse_Description().substring(0, 50)).append(
									strTemp).append(temp.getCourse_Id()).append(strTemp2)
									.toString());
						}
					}
					request.setAttribute("courses", results);
				}else{
					page.setResultCount(0);
				}
			}
		}
		// 处理分页代码，需要使用DAO查询结果的总行数
		String pageSplit = page.split("course.do?c=list");
		map.put("pageSplit", pageSplit);
		request.setAttribute("module", "course_list");

		// 页面左边边栏操作栏目
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "course");
		map.put("leftBar", leftBar);

		return "panel/panel";
	}

	@RequestMapping(params = "c=edit")
	public String editCourse(HttpServletRequest request, ModelMap map) {
		String idStr = request.getParameter("id");
		UserTable user=(UserTable)request.getSession().getAttribute("user");
		String sourceOption=request.getParameter("sourceOption");
		map.put("sourceOption", sourceOption);
		if(user==null){
			map.put("message", "对不起，你的身份不明确");
			map.put("url", "news.do?c=newslist");
			return "global/notify";
		}
		
		//检查是否在学生的选课时间段内,已经出题时间内
		//判断是否是添加题目
		boolean isAdd=false;
		if (idStr == null || "".equals(idStr)||"0".equals(idStr)) {
			isAdd=true;
		}
		//如果是添加题目要判断是否在出题时间内
		if(isAdd){
			if(user.getRole()==1){//管理员要初始化教师下拉框
				IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
				List<UserTable> teacherList=userDao.getAllTeachers();
				map.put("teacherList", teacherList);
			}
			if(user.getRole()==2){//对于教师用户，添加课题时需要判断其是否在学生的选课期间
				String res=this.checkTeacherOptionOfTime(map);
				if(res!=null){
					return res;
				}
			}
		}
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(idStr);
			// 初始化右边的编辑窗口
			CourseTea course = ((ICourseTableDAOBusiness) BeanFactory
					.getBean("ICourseTableDAOBusiness"))
					.doGetACourseWithTeaById(id);
			map.put("course", course);
		}
		
		// 页面左边边栏操作栏目
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "course");
		map.put("leftBar", leftBar);

		request.setAttribute("module", "course_edit");
		return "panel/panel";
	}

	/**
	 * 保存和更新课题
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=save")
	public String saveCourse(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		
		//取得页面传过来的ID
		String idString=request.getParameter("id");
		int id=0;
		if(idString!=null&&!"".equals(idString)&&!"0".equals(idString)){
			id=Integer.parseInt(idString);
		}
		
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		
		ICourseTableDAOBusiness dao = (ICourseTableDAOBusiness) BeanFactory
				.getBean("ICourseTableDAOBusiness");
		IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");

		//检查是否在学生的选课时间段内,已经出题时间内
		if(id==0 && user.getRole()==2){//对于教师用户，修改课题时需要判断其是否在学生的选课期间
			String res=this.checkTeacherOptionOfTime(modelMap);
			if(res!=null){
				return res;
			}
		}
		if (user.getRole() == 1|| user.getRole()==2) {
			// 接受页面端参数，进行验证
			String courseName = request.getParameter("title").trim();
			String courseDesc = request.getParameter("desp").trim();
			String teaIdStr = request.getParameter("teacherId").trim();
			int teaId = 0;
			try {
				teaId = Integer.parseInt(teaIdStr);
			} catch (Exception e) {
				teaId = 0;
			}
			String userName = request.getParameter("teacherName");

			Date date = new Date();

			CourseTea course = dao.doGetACourseWithTeaById(id);
			if(course==null){
				course=new CourseTea();
			}
			course.setCourse_Id(id);
			course.setCourse_Name(courseName);
			course.setCourse_Description(courseDesc);
			course.setPublishDate(date);
			course.setUserName(userName);
			course.setUserId(teaId);
			//初始化教师下拉框
			List<UserTable> teacherList=userDao.getAllTeachers();
			modelMap.put("teacherList", teacherList);
			// 验证课题题目不为空
			if (courseName == null || "".equals(courseName)) {
				course.setCourse_Name(null);
				modelMap.put("course", course);
				modelMap.put("error", "请输入课题题目");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "course");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "course_edit");
				return "panel/panel";
			}
			// 验证课题名称不存在
			int courseId=dao.isCourseNameExist(course.getCourse_Name());
			if (courseId!=0 && id!=courseId) {
				course.setCourse_Name(null);
				modelMap.put("course", course);
				modelMap.put("error", "该课题已经存在，请重新输入课题名称");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "course");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "course_edit");
				return "panel/panel";
			}
			// 验证课题描述不能为空
			if (courseDesc == null || "".equals(courseDesc)) {
				course.setCourse_Description(courseDesc);
				modelMap.put("course", course);
				modelMap.put("error", "请输入课题描述");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "course");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "course_edit");
				return "panel/panel";
			}
			// 验证带题老师不能为空
			if (user.getRole()!=2&&teaId <= 0) {
				course.setUserId(0);
				modelMap.put("course", course);
				modelMap.put("error", "请输入正确的带题老师");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "course");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "course_edit");
				return "panel/panel";
			}
			// 数据验证完毕
			// 如果有id 则更新
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")
					&& !request.getParameter("id").equals("0")) {
				course.setCourse_Id(Integer
						.parseInt(request.getParameter("id")));
				boolean flag = dao.doUpdate(course);

				if (flag) {
					modelMap.put("message", "更新课题成功");
				} else {
					modelMap.put("message", "对不起，更新课题失败");
				}
			} else {
				// 没有则添加
				
				//老师添加课题，那么课题的所属老师就是session用户的ID
				if(user.getRole()==2){
					course.setUserId(user.getUserId());
				}
				boolean flag = dao.doInsert(course);
				if (flag) {
					//如果是教师更新，则sesssion中的teaCurrentCourses+1
					if(user.getRole()==2){
						int currentCourses=(Integer)request.getSession().getAttribute("teaCurrentCourses");
						request.getSession().setAttribute("teaCurrentCourses",currentCourses+1);
					}
					modelMap.put("message", "添加课题成功");
				} else {
					modelMap.put("message", "对不起，添加课题失败！");
				}
			}

		} else {
			modelMap.put("message", "对不起，你没有权限，添加失败");
			modelMap.put("url", "news.do?c=newslist");
		}
		String sourceOption=request.getParameter("sourceOption");
		if(sourceOption==null || "".equals(sourceOption)){
			modelMap.put("url", "course.do?c=list");
		}else{
			modelMap.put("url", "user.do?c=listStudents");
		}
		
		modelMap.put("time", "3");
		return "global/notify";
	}

	// 跳转至导入课题页面
	@RequestMapping(params = "c=import")
	public String importCourse(HttpServletRequest request, ModelMap modelMap) {
		// 页面左边边栏操作栏目
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		//检查是否在学生的选课时间段内,已经出题时间内
		if(user.getRole()==2){//对于教师用户，修改课题时需要判断其是否在学生的选课期间
			String res=this.checkTeacherOptionOfTime(modelMap);
			if(res!=null){
				return res;
			}
		}
		if (user.getRole() == 1 || user.getRole() == 2) {
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "course");
			modelMap.put("leftBar", leftBar);
			request.setAttribute("module", "course_import");
			return "panel/panel";
		}
		modelMap.put("time", "3");
		modelMap.put("message", "对不起，你没有权限，导入失败");
		modelMap.put("url", "news.do?c=newslist");
		return "global/notify";
	}

	// 上传文件，并导入
	@RequestMapping(params = "c=importfile")
	public String importByFile(HttpServletRequest request, ModelMap modelMap)

			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		if(user.getRole()==2){//对于教师用户，修改课题时需要判断其是否在学生的选课期间
			String res=this.checkTeacherOptionOfTime(modelMap);
			if(res!=null){
				return res;
			}
		}
		if (user.getRole() == 1 || user.getRole() == 2) {
			modelMap.put("url", "course.do?c=list");
			modelMap.put("time", "3");
			modelMap.put("message", "保存失败");

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 专业
			MultipartFile file = multipartRequest.getFile("file");
			int total = 0;
			int success = 0;
			int faild = 0;
			StringBuilder sb = new StringBuilder();
			CourseTea courseTea=null;
			if (file != null) {
				// 开始解析excel
				Workbook workbook = Workbook.getWorkbook(file.getInputStream());
				Sheet sheet = workbook.getSheet(0);
				total = sheet.getRows() - 2;
				for (int i = 2; i < sheet.getRows(); i++) {
					String index=sheet.getCell(0,i).getContents();
					String title = sheet.getCell(1, i).getContents();
					String desc = sheet.getCell(2, i).getContents();
					String teaName=null;
					
					courseTea = new CourseTea();
					courseTea.setCourse_Name(title);
					courseTea.setCourse_Description(desc);
					courseTea.setPublishDate(new Date());
					
					if(title!=null && !"".equals(title)
							&&desc!=null&& !"".equals(desc)){
							
						if(user.getRole()==1){
							teaName=sheet.getCell(3,i).getContents();
							Map<String,Integer> teacherList=new HashMap<String,Integer>();
							
							//用一个HashMap作为缓存，保存了导入文件中老师的姓名对应在
							//userTable 表中的 userId号。
							//首先判断一个老师的姓名在map中能否找到，不能找到则查询数据库，加入Map
							if(!teacherList.containsKey(teaName)){
								IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
								UserTable temp =dao.isTeaNameExist(teaName);
								if(temp!=null&&temp.getUserId()>0){
									teacherList.put(teaName, temp.getUserId());
								}
							}
							courseTea.setUserId(teacherList.get(teaName));
						}else if(user.getRole()==2){
							courseTea.setUserId(user.getUserId());
						}
						
						try {
							//课题名称存在
							ICourseTableDAOBusiness dao=(ICourseTableDAOBusiness)
								BeanFactory.getBean("ICourseTableDAOBusiness");
							int id=dao.isCourseNameExist(courseTea.getCourse_Name());
							if(id==0){
								dao.doInsert(courseTea);
								success += 1;
							}else{
								faild += 1;
								sb.append("<p>&nbsp;标题: &nbsp;");
								sb.append(title);
								sb.append("保存失败 &nbsp;");
								sb.append("原因 :该课题已经存在，请修改未导入成功的课题");
								sb.append("<br /><br />");
							}
						} catch (Exception ex) {
							faild += 1;
							sb.append("<p>&nbsp;标题: &nbsp;");
							sb.append(title);
							sb.append("保存失败&nbsp;");
							sb.append("原因 ：");
							sb.append(ex.getMessage());
							sb.append("<br /><br />");
						}
					}else{
						faild += 1;
						sb.append("<p>第"+index+"行");
						sb.append("保存失败&nbsp;");
						sb.append("原因 :该课题信息不全");
						sb.append("<br /><br />");
					}
				}
				if(user.getRole()==2){
					int currentCourses=(Integer)request.getSession().getAttribute("teaCurrentCourses");
					request.getSession().setAttribute("teaCurrentCourses",currentCourses+success);
				}
				modelMap.put("message", "保存完成<br />总数: " + total + "条  成功:"
						+ success + "条" + "失败:" + faild + "条<br />"
						+ sb.toString());
				if (faild > 0) {
					modelMap.put("time", "999900");
					modelMap.put("importError",1);
				}
			}
			return "global/notify";
		}
		modelMap.put("url", "news.do?c=newslist");
		modelMap.put("time", "3");
		modelMap.put("message", "对不起，你没有权限，导入失败");
		return "global/notify";
	}
	
	/**导出课题信息
     * 必须要求有导出
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=export")
    public String exportCourses(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
    	//当前导出操作用户
        UserTable user=(UserTable)request.getSession().getAttribute("user");
    	if(user.getRole()==1||user.getRole()==2){
	        response.setContentType("application/xls");
	        SimpleDateFormat sd = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	        String dateString = sd.format(new Date());
	        response.addHeader("Content-Disposition", "attachment;filename=caption_export_"+ dateString+ ".xls");
	
	        //创建工作区
	        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
	        WritableSheet sheet = workbook.createSheet("选题", 0);
	        
	        //创建表格头栏目
	        sheet.mergeCells(0, 0, 8, 0);
	        sheet.addCell(new Label(0,0,"成都理工大学二○一二届毕业设计（论文）选题题目汇总表"));
	        sheet.mergeCells(0, 1, 8, 1);
	        sheet.addCell(new Label(0,1,"学院（签章）：       专业名称：       主管院长（签名）：           填表人（签名）：　　　　　　填表日期："));
	        sheet.addCell(new Label(0,2,"序号"));
	        sheet.addCell(new Label(1,2,"学生姓名"));
	        sheet.addCell(new Label(2,2,"学号"));
	        sheet.addCell(new Label(3,2,"毕业设计（论文）题目"));
	        sheet.addCell(new Label(4,2,"题目来源"));
	        sheet.addCell(new Label(5,2,"题目类型"));
	        sheet.addCell(new Label(6,2,"指导教师"));
	        sheet.addCell(new Label(7,2,"职称"));
	        sheet.addCell(new Label(8,2,"备注"));
	        
	        //课题操作
	        ICourseTableDAOBusiness dao=(ICourseTableDAOBusiness)BeanFactory.getBean("ICourseTableDAOBusiness");
	        List<CourseTable> course=null;
	        if(user.getRole()==1){//管理员
	        	course=dao.doQuery();
	        }else if(user.getRole()==2){//教师
	        	///得到一个教师的所有课题
	        	course=dao.doQueryOneTeacher(user.getUserId());
	        }
	        if(course!=null && course.size()>0){
	        	 int i = 3;
	        	//准备一个详细的course对象
	        	CourseDetail courDetTemp=null;
	    		Iterator<CourseTable> ite=course.iterator();
	    		IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)
	    							BeanFactory.getBean("IUserTableDAOBusiness");
	    		IClassTableDAOBusiness classDao=(IClassTableDAOBusiness)
	    							BeanFactory.getBean("IClassTableDAOBusiness");
	    		IMajorTableDAOBusiness majorDao=(IMajorTableDAOBusiness)
	    							BeanFactory.getBean("IMajorTableDAOBusiness");
	    		ICourseSourceDAOBusiness sourceDao=(ICourseSourceDAOBusiness)BeanFactory.getBean("ICourseSourceDAOBusiness");
				ICourseTypeDAOBusiness typeDao=(ICourseTypeDAOBusiness)BeanFactory.getBean("ICourseTypeDAOBusiness");
				IPositionTableDAOBusiness positionDao=(IPositionTableDAOBusiness)BeanFactory.getBean("IPositionTableDAOBusiness");
				
	    		while(ite.hasNext()){
	    			CourseTable temp=ite.next();//对当前课题处理，查找教师和学生信息
	    			if(temp.getIsSelected()==0){
	    				continue;//课题没有被选择，则不输出
	    			}
	    			courDetTemp=new CourseDetail();
	    			courDetTemp.setCourseName(temp.getCourse_Name());
	    			
	    			UserTable stu=null;
	    			UserTable tea=null;
	    			if(temp.getStudent()>0){
	    				//添加课题的学生信息
	    				stu=userDao.getUserById(temp.getStudent());
	    				if(stu!=null){
		    				courDetTemp.setStuName(stu.getUserName());
		    				courDetTemp.setStuEmail(stu.getEmail());
		    				courDetTemp.setStuNumber(stu.getStuNumber());
		    				courDetTemp.setStuPhone(stu.getPhone());
		    				if(stu.getStuClassId()>0){
		    					//添加该学生班级信息
		    					ClassDetail c=classDao.getClassByClassId(stu.getStuClassId());
		    					if(c!=null){
		    						courDetTemp.setStuClassName(c.getClassNameChi());
		    						if(c.getInMajor()>0){
		    							//添加该班级的专业信息
		    							MajorTable m=majorDao.getById(c.getInMajor());
		    							if(m!=null){
		    								courDetTemp.setStuMajorName(m.getMajorName());
		    							}
		    						}
		    					}
		    				}
	    				}
	    				
	    			}
	    			//添加该课题的教师信息
	    			if(temp.getTeacher()>0){
	    				tea=userDao.getUserById(temp.getTeacher());
	    				courDetTemp.setTeaEmail(tea.getEmail());
	    				courDetTemp.setTeaName(tea.getUserName());
	    				courDetTemp.setTeaPhone(tea.getPhone());
	    				courDetTemp.setTeaPositionName(positionDao.getPositionById(tea.getTeaPosition()).getPosition_Name());
	    			}
	    			
	    			//设置题目类型和题目来源
	    			courDetTemp.setStuCourseSourceChar(
	    					sourceDao.getCourseSourceObjById(temp.getCourseSource()).getSourceChar());
	    			courDetTemp.setStuCourseTypeChar(
	    					typeDao.getCourseTypeObjById(temp.getCourseType()).getTypeChar());

	    			//一个 CourseDetail对象准备完毕
	                sheet.addCell(new Label(0, i, String.valueOf(i-2)));
	                sheet.addCell(new Label(1, i, courDetTemp.getStuName()));
	                sheet.addCell(new Label(2, i, courDetTemp.getStuNumber()));
	                sheet.addCell(new Label(3, i, courDetTemp.getCourseName()));
	                sheet.addCell(new Label(4, i, courDetTemp.getStuCourseSourceChar()));
	                sheet.addCell(new Label(5, i, courDetTemp.getStuCourseTypeChar()));
	                sheet.addCell(new Label(6, i, courDetTemp.getTeaName()));
	                sheet.addCell(new Label(7, i, courDetTemp.getTeaPositionName()));
	                i++;
	    		}
	    		//输出表格尾
	    		sheet.mergeCells(0, i+1, 8, i+1);
	 	        sheet.addCell(new Label(0,i+1,"说明：①表中“题目来源”为：“教师科研课题”（A）、“教师拟定”(B)、“学生自拟”(C)、“其它”(D)；" +
	 	        		"②表中“题目类型”为：“理论研究”（A）、“应用研究”(B)、“技术开发”(C)、“其它”(D)；"));
	    		
	    		workbook.write();
 		        workbook.close();
 		        response.getOutputStream().flush();
 		        response.getOutputStream().close();
	    	}
    	}else{
    		modelMap.put("url", "news.do?c=newslist");
    		modelMap.put("time", "3");
    		modelMap.put("message", "对不起，你没有权限，导出失败");
    		return "global/notify";
    	}
    	return null;
    }
    
    
    
    
    
	private  String checkTeacherOptionOfTime(ModelMap modelMap){
		//对于教师用户，修改课题时需要判断其是否在学生的选课期间
		//检查是否在学生的选课时间段内,已经出题时间内
		modelMap.put("time", "4");
		modelMap.put("url", "news.do?c=newslist");
		ITimeTableDAOBusiness timeDao=(ITimeTableDAOBusiness)BeanFactory.
		getBean("ITimeTableDAOBusiness");
		//是否在课题导入时间内
		//当前时间
		long now=new Date().getTime();
		TimeTable importTime=timeDao.getATimeTableByName("课题导入时间");
		if(importTime.getIsUsed()==1){
			if(now<(importTime.getTimeStart().getTime())
			   || now >(importTime.getTimeEnd().getTime())){
				modelMap.put("message", "对不起，现在不属于添加课题时段，老师您不能添加课题");
				return "global/notify";
			}
		}
		TimeTable selectTime=timeDao.getATimeTableByName("选题系统开放时间");
		if(selectTime.getIsUsed()==1){//是否开启了选题时间限制系统
			if(now>=(selectTime.getTimeStart().getTime())
			   && now<=(selectTime.getTimeEnd().getTime())){
				modelMap.put("message", "对不起，学生选课期间，老师您不能修改或者添加课题");
				return "global/notify";
			}
		}
		return null;
	}
	
	@RequestMapping(params = "c=UpdateCourseSourseAndType")
	public String updateCourseSourseAndType(HttpServletRequest request, ModelMap map) {
		
		// 初始化右边的选题列表
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		//得到当前操作用户
		
		// 页面左边边栏操作栏目
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "course");
		map.put("leftBar", leftBar);
		
		//判断权限是否正确
		if(user.getRole()==2){
			//该字符串由    courseId_sourseId_typeId,courseId_sourseId_typeId,courseId_sourseId_typeId....构成
			//courseId 课题编号，sourseId 是 课题来源编号，typeId是课题类型编号
			String data=request.getParameter("courseAllData");
			JSONArray array=new JSONArray();//返回的JSON消息
			int count=0;//修改 成功的课题总数
			if(data!=null && !"".equals(data)){
				String[] courseArray=data.split(",");
				if(courseArray!=null && courseArray.length>0){
					ICourseTableDAOBusiness dao=(ICourseTableDAOBusiness)BeanFactory.getBean("ICourseTableDAOBusiness");
					for(String s:courseArray){
						String[] ids=s.split("_");
						if(ids.length==3){//包含三个Id
							CourseTable c=dao.doGetACourseById(Integer.parseInt(ids[0]));
							if(c!=null){
								c.setCourseSource(Integer.parseInt(ids[1]));
								c.setCourseType(Integer.parseInt(ids[2]));
								dao.doUpdate(c);
								count++;
							}
						}
					}
				}
				if(count==0){
					JSONObject obj=new JSONObject();
					try {
						obj.put("result", "更新失败，请稍后再试或者联系管理员！！");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					array.put(obj);
				}else if(count==courseArray.length){
					JSONObject obj=new JSONObject();
					try {
						obj.put("result", "更新所有课题成功");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					array.put(obj);
				}else{
					JSONObject obj=new JSONObject();
					try {
						obj.put("result", "部分课题修改成功");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					array.put(obj);
				}
			}
			JSONObject obj=new JSONObject();
			try {
				obj.put("url", "user.do?c=listStudents");
				array.put(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("json",array.toString());
			return "json";
		}else{//不是教师，不能修改
			map.put("url", "course.do?c=list");
			map.put("time", "2");
			map.put("message", "对不起，您不是教师，不能进行修改。。。");
			return "global/notify";
		}
	}
}
