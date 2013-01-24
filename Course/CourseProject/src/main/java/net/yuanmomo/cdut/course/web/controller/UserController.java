package net.yuanmomo.cdut.course.web.controller;

import java.text.SimpleDateFormat;
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
import org.yhb.dao.Business.IUserTableDAOBusiness;
import org.yhb.dao.Business.ValidationBusiness;
import org.yhb.util.BeanFactory;
import org.yhb.util.FinalDatas;
import org.yhb.util.MD5;
import org.yhb.util.PageSplit;
import org.yhb.vo.ClassDetail;
import org.yhb.vo.CourseSource;
import org.yhb.vo.CourseTable;
import org.yhb.vo.CourseType;
import org.yhb.vo.MajorTable;
import org.yhb.vo.PositionTable;
import org.yhb.vo.StudentDetail;
import org.yhb.vo.TeacherPosition;
import org.yhb.vo.UserTable;

@Controller
@RequestMapping("/user.do")
public class UserController {
	
	//管理员添加课题时必须输入老师的姓名
	@RequestMapping(params="isTeacherExist")
	public String isTeacherExist(String userName,HttpServletRequest req,
			HttpServletResponse resp,ModelMap modelMap){
		IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
		UserTable user =dao.isTeaNameExist(userName);
		if(user!=null){
			modelMap.put("json","[{\"id\":\""+user.getUserId()+"\",\"message\":\"该教师存在\"}]");
		}else{
			modelMap.put("json","[{\"id\":\"\",\"message\":\"对不起，该教师用户不存在\"}]");
		}
		return "json";
	}
	
	
	@RequestMapping(params="c=listTeachers")
	public String viewListTeachers(HttpServletRequest req,
			HttpServletResponse resp,ModelMap modelMap){
		UserTable user=(UserTable)req.getSession().getAttribute("user");
		if(user.getRole()==1){
			// 初始化右边的选题列表
			
			// 处理分页情况
			PageSplit page=(PageSplit)BeanFactory.getBean("pageSplit");
			page.setParameters(req);
			
			// 查询要列表显示的教师
			ICourseTableDAOBusiness courseDao=(ICourseTableDAOBusiness)
				BeanFactory.getBean("ICourseTableDAOBusiness");
			IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.
				getBean("IUserTableDAOBusiness");
			List<TeacherPosition> teacherList=dao.getTeachers(page.getCurrentPage(),page.getPageSize());
			if(teacherList!=null && teacherList.size()>0){
				Iterator<TeacherPosition> ite=teacherList.iterator();
				while(ite.hasNext()){
					TeacherPosition temp=ite.next();
					System.out.println("*********************"+courseDao.getCountOneTeacher(temp.getUserId()));
					temp.setCurrentCoursesCount(courseDao.getCountOneTeacher(temp.getUserId()));
				}
			}
			page.setResultCount(dao.getTeachersCount());
			
			modelMap.put("pageSplit",page.split("user.do?c=listTeachers"));
			modelMap.put("teachers",teacherList);
			
			req.setAttribute("module", "tea_list");
			
			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);
			return "panel/panel";
		}else {
			modelMap.put("message", "对不起，你没有权限，查看失败");
			modelMap.put("time",3);
			modelMap.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
	
	@RequestMapping(params = "c=viewTea")
	public String viewTeacher(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		String idStr = request.getParameter("id");// 查看选题详细信息
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(request.getParameter("id"));
			IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
			TeacherPosition user=dao.getTeacherPositionById(id);
			modelMap.put("tea", user);
		}
		return "panel/tea_view";
	}
	
	@RequestMapping(params = "c=viewStu")
	public String viewStudent(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		String idStr = request.getParameter("id");// 查看选题详细信息
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(request.getParameter("id"));
			IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
			StudentDetail user=dao.getStudentDetailById(id);
			modelMap.put("stu", user);
		}
		return "panel/stu_view";
	}
	
	@RequestMapping(params = "c=teaEdit")
	public String editTeacher(HttpServletRequest request, ModelMap map) {
		request.setAttribute("module", "tea_add");
		
		String idStr = request.getParameter("id");
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(idStr);
			// 初始化右边的编辑窗口
			IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
			TeacherPosition user=dao.getTeacherPositionById(id);
			request.setAttribute("module", "tea_edit");
			if(id==1){
				request.setAttribute("module", "tea_edit_admin");
			}
			map.put("teacher", user);
		}
		//初始化职称信息
		IPositionTableDAOBusiness positionDao=(IPositionTableDAOBusiness)
				BeanFactory.getBean("IPositionTableDAOBusiness");
		List<PositionTable> res=positionDao.doQueryAll();
		map.put("positionList", res);
		
		
		// 页面左边边栏操作栏目
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "user");
		map.put("leftBar", leftBar);
		return "panel/panel";
	}
	
	/**
	 * 用户删除
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=delete")
	public String deleteUser(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		modelMap.put("url", "user.do?c=listTeachers");
		modelMap.put("time", "3");
		if (user.getRole() == 1) {// 是管理员
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")) {
				int id = Integer.parseInt(request.getParameter("id"));
				IUserTableDAOBusiness userDao = (IUserTableDAOBusiness) BeanFactory
						.getBean("IUserTableDAOBusiness");
				//判断该学生是否选了课题，
				boolean flag=false;
				UserTable u=userDao.getUserById(id);
				if(u!=null){
					ICourseTableDAOBusiness courseDao = (ICourseTableDAOBusiness) BeanFactory
						.getBean("ICourseTableDAOBusiness");
					//是学生，并且选了课题
					if(u.getRole()==3&&u.getStuCourseId()>0){
						CourseTable c=courseDao.doGetACourseById(u.getStuCourseId());
						if(c!=null){
							c.setStudent(0);
							c.setIsSelected(0);
							courseDao.doUpdate(c);
						}
						//更新老师的信息，
						UserTable tea=userDao.getUserById(u.getStuTeacherId());
						tea.setTeaCurrentStu(tea.getTeaCurrentStu()-1);
						userDao.doUpdate(tea);
					}
					flag = userDao.doDelete(id);
					if (flag) {
						modelMap.put("message", "删除成功");
					} else {
						modelMap.put("message", "对不起，删除失败");
					}
				}
			}
		} else {
			modelMap.put("message", "对不起，你没有权限，删除失败");
			modelMap.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
	
	/**
	 * 保存和更新教师用户
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=adminAddTea")
	public String dodminAddTeacher(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
		UserTable user=(UserTable)request.getSession().getAttribute("user");
		if(user.getRole()!=1){
			modelMap.put("url", "news.do?c=newslist");
			modelMap.put("message", "对不起，你没有权限，操作失败");
			modelMap.put("time", "3");
			return "global/notify";
		}
		// 接受页面端参数，进行验证
		String userName = request.getParameter("userName");
		if(userName!=null){
			userName=userName.trim();
		}
		String password=request.getParameter("password");

		UserTable u=new UserTable();
		u.setUserName(userName);
		u.setPassword(password);
		
		// 验证用户名不能为空
		if (userName == null || "".equals(userName)) {
			u.setUserName(null);
			modelMap.put("teacher", u);
			//初始化职称信息
			IPositionTableDAOBusiness positionDao=(IPositionTableDAOBusiness)
					BeanFactory.getBean("IPositionTableDAOBusiness");
			List<PositionTable> res=positionDao.doQueryAll();
			modelMap.put("positionList", res);
			modelMap.put("error", "请输入正确的用户名");

			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(u,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);

			request.setAttribute("module", "tea_add_admin");
			return "panel/panel";
		}
		// 验证用户名不能为空
		if (password == null || "".equals(password)) {
			u.setPassword(null);
			modelMap.put("teacher", u);
			//初始化职称信息
			IPositionTableDAOBusiness positionDao=(IPositionTableDAOBusiness)
				BeanFactory.getBean("IPositionTableDAOBusiness");
			List<PositionTable> res=positionDao.doQueryAll();
			modelMap.put("positionList", res);
			modelMap.put("error", "请输入密码");

			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(u,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);

			request.setAttribute("module", "tea_add_admin");
			return "panel/panel";
		}
		// 数据验证完毕
		
		//添加
		if(((UserTable)request.getSession().getAttribute("user")).getRole()==1){
			u.setRole(2);
			u.setRegIp(request.getRemoteAddr());
			u.setRegTime(new Date());
			u.setLastLoginIp(request.getRemoteAddr());
			u.setLastLoginTime(new Date());
			u.setPassword(MD5.getMD5(password));
			
			boolean flag = dao.doInsertATea(u);
			if (flag) {
				modelMap.put("message", "添加教师用户成功");
			} else {
				modelMap.put("message", "对不起，添加教师用户失败！");
			}
			modelMap.put("url", "news.do?c=newslist");
		}else{
			modelMap.put("url", "news.do?c=newslist");
			modelMap.put("message", "对不起，你没有权限，添加失败");
			modelMap.put("time", "3");
			return "global/notify";
		}
		if(u.getRole()==1){
			modelMap.put("url", "user.do?c=listTeachers");
		}
		modelMap.put("time", "3");
		return "global/notify";
	}

	/**
	 * 保存和更新教师用户
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=saveTea")
	public String doSaveTeacher(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		
		IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
		UserTable user=(UserTable)request.getSession().getAttribute("user");
		if(user.getRole()==3){
			modelMap.put("url", "news.do?c=newslist");
			modelMap.put("message", "对不起，你没有权限，操作失败");
			modelMap.put("time", "3");
			return "global/notify";
		}
		// 接受页面端参数，进行验证
		String userName = request.getParameter("userName");
		if(userName!=null){
			userName=userName.trim();
		}
		String password=request.getParameter("password");
		String description=request.getParameter("teaDescription");
		if(description!=null){
			description=description.trim();
		}
		String email=request.getParameter("email");
		if(email!=null){
			email=email.trim();
		}
		String phone=request.getParameter("phone");
		if(phone!=null){
			phone=phone.trim();
		}
		
		int position=0;
		int teaMaxStu=0;
		
		if(user.getRole()==1){
			String teaMaxStuStr=request.getParameter("teaMaxStu");
			try {
				teaMaxStu=Integer.parseInt(teaMaxStuStr);
			} catch (Exception e) {
				// TODO: handle exception
				teaMaxStu=1;
			}
		}
		String positionStr=request.getParameter("teaPosition");
		
		try {
			position=Integer.parseInt(positionStr);
		} catch (Exception e) {
			position=1;
		}

		UserTable u=null;
		
		//看传递的参数是否有id，有则表示更新，没有则表示添加
		String id=request.getParameter("id");
		if(id!= null && !id.equals("")&& !id.equals("0")){
			u=dao.getUserById(Integer.parseInt(id));
		}else{
			u=new UserTable();
		}
		u.setUserName(userName);
		u.setTeaDescription(description);
		u.setEmail(email);
		u.setPhone(phone);
		if(user.getRole()==1){
			u.setTeaMaxStu(teaMaxStu);
		}
		
		u.setTeaPosition(position);
		
		// 验证用户名不能为空
		if (userName == null || "".equals(userName)) {
			u.setUserName(null);
			modelMap.put("teacher", u);
			//初始化职称信息
			IPositionTableDAOBusiness positionDao=(IPositionTableDAOBusiness)
					BeanFactory.getBean("IPositionTableDAOBusiness");
			List<PositionTable> res=positionDao.doQueryAll();
			modelMap.put("positionList", res);
			modelMap.put("error", "请输入正确的用户名");

			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(u,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);

			request.setAttribute("module", "tea_edit");
			return "panel/panel";
		}
//		// 验证用户名不能为空
//		if (user.getRole()==2 &&(password == null || "".equals(password))) {
//			modelMap.put("teacher", u);
//			//初始化职称信息
//			IPositionTableDAOBusiness positionDao=(IPositionTableDAOBusiness)
//					BeanFactory.getBean("IPositionTableDAOBusiness");
//			List<PositionTable> res=positionDao.doQueryAll();
//			modelMap.put("positionList", res);
//			modelMap.put("error", "请输入密码");
//
//			// 页面左边边栏操作栏目
//			String leftBar = BuildLeftBar.build(u,
//					FinalDatas.xmlFileBasicLocation, "user");
//			modelMap.put("leftBar", leftBar);
//
//			request.setAttribute("module", "tea_edit");
//			return "panel/panel";
//		}
		if(user.getRole()==2 &&(email==null ||"".equals(email) || !email.matches(".*?@.*"))){
			u.setEmail(null);
			modelMap.put("teacher", u);
			modelMap.put("error", "请输入正确的邮箱");

			//初始化职称信息
			IPositionTableDAOBusiness positionDao=(IPositionTableDAOBusiness)
					BeanFactory.getBean("IPositionTableDAOBusiness");
			List<PositionTable> res=positionDao.doQueryAll();
			modelMap.put("positionList", res);
			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(u,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);

			request.setAttribute("module", "tea_edit");
			return "panel/panel";
		}
		
		if(user.getRole()==2 &&(phone==null ||"".equals(phone) || !phone.matches("\\d*"))){
			u.setPhone(null);
			modelMap.put("teacher", u);
			modelMap.put("error", "请输入正确的电话号码");
			//初始化职称信息
			IPositionTableDAOBusiness positionDao=(IPositionTableDAOBusiness)
					BeanFactory.getBean("IPositionTableDAOBusiness");
			List<PositionTable> res=positionDao.doQueryAll();
			modelMap.put("positionList", res);
			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(u,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);

			request.setAttribute("module", "tea_edit");
			return "panel/panel";
		}
		// 数据验证完毕

		// 如果有id 则更新
		if (id!= null && !id.equals("")&& !id.equals("0")) {
			if(password!=null&&!"".equals(password)){
				u.setPassword(MD5.getMD5(password));
			}
			//用户当前登录的是教师用户，则应该在修改的时候更新sesssion里面的user
			UserTable sessUser=(UserTable)request.getSession().getAttribute("user");
			if(sessUser.getRole()==2){
				request.getSession().setAttribute("user", u);
			}
			//如果是管理员更新，并是更新自己的信息的时候
			if(sessUser.getRole()==1&&u.getRole()==1){
				request.getSession().setAttribute("user", u);
			}
			boolean flag=dao.doUpdate(u);
			
			if (flag) {
				modelMap.put("message", "更新教师成功");
				modelMap.put("url", "news.do?c=newslist");
			} else {
				modelMap.put("message", "对不起，更新教师失败");
				modelMap.put("url", "user.do?c=teaEdit");
			}
		} else {
			// 没有则添加
			if(((UserTable)request.getSession().getAttribute("user")).getRole()==1){
				u.setRole(2);
				u.setRegIp(request.getRemoteAddr());
				u.setRegTime(new Date());
				u.setLastLoginIp(request.getRemoteAddr());
				u.setLastLoginTime(new Date());
				u.setPassword(MD5.getMD5(password));
				
				boolean flag = dao.doInsertATea(u);
				if (flag) {
					modelMap.put("message", "添加教师用户成功");
				} else {
					modelMap.put("message", "对不起，添加教师用户失败！");
				}
				modelMap.put("url", "user.do?c=listTeachers");
			}else{
				modelMap.put("url", "user.do?c=listTeachers");
				modelMap.put("message", "对不起，你没有权限，添加失败");
				modelMap.put("time", "3");
				return "global/notify";
			}
		}
		if(u.getRole()==1){
			modelMap.put("url", "user.do?c=listTeachers");
		}
		modelMap.put("time", "3");
		return "global/notify";
	}
	
	// 跳转至导入教师用户页面
	@RequestMapping(params = "c=teaImport")
	public String importTeachers(HttpServletRequest request, ModelMap modelMap) {
		// 页面左边边栏操作栏目
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		if (user.getRole() == 1) {
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);
			request.setAttribute("module", "tea_import");
			return "panel/panel";
		}
		modelMap.put("time", "3");
		modelMap.put("message", "对不起，你没有权限，导入失败");
		modelMap.put("url", "news.do?c=newslist");
		return "global/notify";
	}

	// 上传文件，并导入
	@RequestMapping(params = "c=teaImportFile")
	public String importTeaByFile(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		
		//判断passwor是否合法
		String password=request.getParameter("password");
		if(password==null || "".equals(password)){

			modelMap.put("error", "请输入初始密码");	
			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);

			request.setAttribute("module", "tea_import");
			return "panel/panel";
		}
		
		if (user.getRole() == 1){
			modelMap.put("url", "user.do?c=listTeachers");
			modelMap.put("time", "3");
			modelMap.put("message", "保存失败");

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 专业
			MultipartFile file = multipartRequest.getFile("file");
			int total = 0;
			int success = 0;
			int faild = 0;
			StringBuilder sb = new StringBuilder();
			Map<String,Integer> positionMap=new HashMap<String,Integer>();
			if (file != null) {
				// 开始解析excel
				Workbook workbook = Workbook.getWorkbook(file.getInputStream());
				Sheet sheet = workbook.getSheet(0);
				total = sheet.getRows() - 1;
				for (int i = 1; i < sheet.getRows(); i++) {
					String userName = sheet.getCell(0, i).getContents();
					String teaDescription = sheet.getCell(1, i).getContents();
					String position=sheet.getCell(2,i).getContents();
					String phone = sheet.getCell(3, i).getContents();
					String email=sheet.getCell(4,i).getContents();
					String maxStu=sheet.getCell(5,i).getContents();
					if(userName!=null&& !"".equals(userName)){
						//用一个HashMap作为缓存，保存了导入文件中老师的职称对应在
						//PositionTable 表中的 position_Id号。
						//首先判断一个老师的职称在map中能否找到，不能找到则存入数据库，并读出该数据，加入Map
						
						//初始化该Map
						IPositionTableDAOBusiness dao=(IPositionTableDAOBusiness)BeanFactory.getBean("IPositionTableDAOBusiness");
						List<PositionTable> temp=dao.doQueryAll();
						Iterator<PositionTable> ite=temp.iterator();
						while(ite.hasNext()){
							PositionTable p=ite.next();
							positionMap.put(p.getPosition_Name(),p.getPosition_Id());
						}
						//查找Map是不是存在该职称
						if(position!=null && !"".equals(position)&&!positionMap.containsKey(position)){
							PositionTable p=new PositionTable();
							p.setPosition_Name(position);
							dao.doInsert(p);
							//查找该position，并放入Map
							p=dao.getPositionByName(position);
							positionMap.put(position, p.getPosition_Id());
						}
						UserTable u=new UserTable();
						u.setUserName(userName);
						u.setPassword(MD5.getMD5(password));
						u.setTeaDescription(teaDescription);
						u.setTeaPosition(positionMap.get(position));
						u.setEmail(email);
						u.setPhone(phone);
						u.setRegIp(request.getRemoteAddr());
						u.setLastLoginIp(request.getRemoteAddr());
						u.setRegTime(new Date());
						u.setLastLoginTime(new Date());
						u.setRole(2);
						try {
							u.setTeaMaxStu(Integer.parseInt(maxStu));
						} catch (Exception e) {
							u.setTeaMaxStu(0);
						}
						
						try {
							IUserTableDAOBusiness d=(IUserTableDAOBusiness)
								BeanFactory.getBean("IUserTableDAOBusiness");
							UserTable exist=d.isTeaNameExist(userName);
							if(exist==null){
								d.doInsertATea(u);
								success += 1;
							}else{
								faild+=1;
								sb.append("标题 ");
								sb.append(userName);
								sb.append("保存失败<br />");
								sb.append("原因 :该教师名字已经存在");
								sb.append("<br />");
							}
						} catch (Exception ex) {
							faild += 1;
							sb.append("标题 ");
							sb.append(userName);
							sb.append("保存失败<br />");
							sb.append("原因 :");
							sb.append(ex.getMessage());
							sb.append("<br />");
						}
					}
				}
				modelMap.put("message", "保存完成<br />总数: " + total + "条  成功:"
						+ success + "条" + "失败:" + faild + "条<br />"
						+ sb.toString());
				if (faild > 0) {
					modelMap.put("time", "600");
				}
			}
			return "global/notify";
		}
		modelMap.put("url", "news.do?c=newslist");
		modelMap.put("time", "3");
		modelMap.put("message", "对不起，你没有权限，导入失败");
		return "global/notify";
	}
	
	@RequestMapping(params="c=listStudents")
	public String viewListStudents(String userName,HttpServletRequest req,
			HttpServletResponse resp,ModelMap modelMap){
		UserTable user=(UserTable)req.getSession().getAttribute("user");
		if(user.getRole()==1 || user.getRole()==2){
			// 初始化右边的选题列表
			
			// 处理分页情况
			PageSplit page=(PageSplit)BeanFactory.getBean("pageSplit");
			page.setParameters(req);
			
			// 查询要列表显示的学生
			IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.
				getBean("IUserTableDAOBusiness");
			List<StudentDetail> studentList=null;
			if(user.getRole()==1){//管理员查看所有的学生
				studentList=dao.getStudentsWithClass(page.getCurrentPage(),page.getPageSize());
				Iterator<StudentDetail> ite=studentList.iterator();
				ICourseTableDAOBusiness courseDao=(ICourseTableDAOBusiness)BeanFactory.getBean("ICourseTableDAOBusiness");
				while(ite.hasNext()){
					StudentDetail s=ite.next();
					CourseTable course=courseDao.doGetACourseById(s.getStuCourseId());
					if(course!=null){
						s.setStuCourseId(course.getCourse_Id());
						s.setCourseName(course.getCourse_Name());
					}
				}
				page.setResultCount(dao.getStudentsCount());
			}else if(user.getRole()==2){//老师只能查看选了自己的学生信息
				page.setResultCount(dao.getStudentsCountByTeacherId(user.getUserId()));
				//查询题目类型和题目来源
				ICourseSourceDAOBusiness sourceDao=(ICourseSourceDAOBusiness)BeanFactory.getBean("ICourseSourceDAOBusiness");
				modelMap.put("sourceList",sourceDao.queryAllCourseSourceObj());
				ICourseTypeDAOBusiness typeDao=(ICourseTypeDAOBusiness)BeanFactory.getBean("ICourseTypeDAOBusiness");
				modelMap.put("typeList",typeDao.queryAllCourseTypeObj());
				studentList=dao.getStudentDetailByTeacherId(page.getCurrentPage(),page.getPageSize(), user.getUserId());
			}
			
			modelMap.put("students",studentList);
			modelMap.put("pageSplit",page.split("user.do?c=listStudents"));
			req.setAttribute("module", "stu_list");
			
			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);
			return "panel/panel";
		}else {
			modelMap.put("message", "对不起，你没有权限，查看失败");
			modelMap.put("time",3);
			modelMap.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
	
	@RequestMapping(params = "c=stuEdit")
	public String editStudent(HttpServletRequest request, ModelMap map) {
		String idStr = request.getParameter("id");
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(idStr);
			// 初始化右边的编辑窗口
			IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
			StudentDetail user=dao.getStudentDetailById(id);
			map.put("stu", user);
		}
		//初始化班级信息
		IClassTableDAOBusiness classDao=(IClassTableDAOBusiness)BeanFactory.getBean("IClassTableDAOBusiness");
		//取得所有的班级
		List<ClassDetail> classList=classDao.getAllClasses(1, 1000);
		map.put("classList", classList);
		
		// 页面左边边栏操作栏目
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "user");
		map.put("leftBar", leftBar);

		request.setAttribute("module", "stu_edit");
		return "panel/panel";
	}
	
	/**
	 * 保存和更新教师用户
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=saveStu")
	public String saveStudent(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable u=(UserTable)request.getSession().getAttribute("user");
		// 页面左边边栏操作栏目
		String leftBar = BuildLeftBar.build(u,
				FinalDatas.xmlFileBasicLocation, "user");
		modelMap.put("leftBar", leftBar);
		IUserTableDAOBusiness dao=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
		
		// 接受页面端参数，进行验证
		String stuNumber=request.getParameter("stuNumber").trim();
		String userName = request.getParameter("userName").trim();
		String password=request.getParameter("password");
		String email=request.getParameter("email").trim();
		String phone=request.getParameter("phone").trim();
		String stuClassStr=request.getParameter("stuClass");
		int stuClass=0;
		try {
			stuClass=Integer.parseInt(stuClassStr);
		} catch (Exception e) {
			// TODO: handle exception
		}

		UserTable user=null;
		
		//看传递的参数是否有id，有则表示更新，没有则表示添加
		String id=request.getParameter("id");
		if(id!= null && !id.equals("")&& !id.equals("0")){
			user=dao.getUserById(Integer.parseInt(id));
		}else{
			user=new UserTable();
		}
		user.setUserName(userName);
		user.setStuClassId(stuClass);
		user.setEmail(email);
		user.setPhone(phone);
		
		// 验证学号
		if (stuNumber == null || "".equals(stuNumber)
				||!stuNumber.matches("\\d{12,13}")) {
			//检查该学号所在专业是否添加进了选课系统
			//3打头，不验证
			if(!stuNumber.startsWith("3")){
				user.setStuNumber(stuNumber);
				boolean isLegal=ValidationBusiness.validateUserAvailable(user);
				if(!isLegal){
					modelMap.put("error","该学号所在专业没有添加选课系统！");
				}
			}
			modelMap.put("stu", user);
			
			//初始化班级列表
			IClassTableDAOBusiness classDao=(IClassTableDAOBusiness)BeanFactory.getBean("IClassTableDAOBusiness");
			//默认只取得电子信息专业的班级
			List<ClassDetail> classList=classDao.getAllClasses(1, 1000);
			modelMap.put("classList", classList);

//			// 页面左边边栏操作栏目
//			String leftBar = BuildLeftBar.build(user,
//					FinalDatas.xmlFileBasicLocation, "user");
//			modelMap.put("leftBar", leftBar);

			request.setAttribute("module", "stu_edit");
			return "panel/panel";
		}
		// 验证用户名不能为空
		if (userName == null || "".equals(userName)) {
			user.setUserName(null);
			modelMap.put("stu", user);
			
			//初始化班级列表
			IClassTableDAOBusiness classDao=(IClassTableDAOBusiness)BeanFactory.getBean("IClassTableDAOBusiness");
			//默认只取得电子信息专业的班级
			List<ClassDetail> classList=classDao.getAllClasses(1, 1000);
			modelMap.put("classList", classList);

//			// 页面左边边栏操作栏目
//			String leftBar = BuildLeftBar.build(user,
//					FinalDatas.xmlFileBasicLocation, "user");
//			modelMap.put("leftBar", leftBar);
			
			modelMap.put("error", "请输入正确的用户名");
			request.setAttribute("module", "stu_edit");
			return "panel/panel";
		}
		
		if(email==null ||"".equals(email) || !email.matches(".*?@.*")){
			user.setEmail(null);
			modelMap.put("stu", user);
			modelMap.put("error", "请输入正确的邮箱");
			
			//初始化班级列表
			IClassTableDAOBusiness classDao=(IClassTableDAOBusiness)BeanFactory.getBean("IClassTableDAOBusiness");
			//默认只取得电子信息专业的班级
			List<ClassDetail> classList=classDao.getAllClasses(1, 1000);
			modelMap.put("classList", classList);

//			// 页面左边边栏操作栏目
//			String leftBar = BuildLeftBar.build(user,
//					FinalDatas.xmlFileBasicLocation, "user");
//			modelMap.put("leftBar", leftBar);

			request.setAttribute("module", "stu_edit");
			return "panel/panel";
		}
		
		if(phone==null ||"".equals(phone) || !phone.matches("\\d*")){
			user.setPhone(null);
			modelMap.put("stu", user);
			modelMap.put("error", "请输入正确的电话号码");
			
			//初始化班级列表
			IClassTableDAOBusiness classDao=(IClassTableDAOBusiness)BeanFactory.getBean("IClassTableDAOBusiness");
			//默认只取得电子信息专业的班级
			List<ClassDetail> classList=classDao.getAllClasses(1, 1000);
			modelMap.put("classList", classList);
			
//			// 页面左边边栏操作栏目
//			String leftBar = BuildLeftBar.build(user,
//					FinalDatas.xmlFileBasicLocation, "user");
//			modelMap.put("leftBar", leftBar);

			request.setAttribute("module", "stu_edit");
			return "panel/panel";
		}
		// 数据验证完毕

		// 如果有id 则更新
		if (id!= null && !id.equals("")&& !id.equals("0")) {
			if(password!=null&&!"".equals(password)){
				user.setPassword(MD5.getMD5(password));
			}
			if(u.getRole()==3){
				request.getSession().setAttribute("user", user);
			}
			boolean flag=dao.doUpdate(user);
			if (flag) {
				modelMap.put("message", "更新学生信息成功");
			} else {
				modelMap.put("message", "对不起，更新学生信息失败");
			}
			modelMap.put("url", "news.do?c=newslist");
		} else {
			// 没有则添加
			if(u.getRole()==1){
				user.setStuNumber(stuNumber);
				user.setRole(3);
				user.setRegIp(request.getRemoteAddr());
				user.setRegTime(new Date());
				user.setLastLoginIp(request.getRemoteAddr());
				user.setLastLoginTime(new Date());
				user.setPassword(MD5.getMD5(password));
				
				boolean flag = dao.doInsertAStu(user);
				if (flag) {
					modelMap.put("message", "添加学生用户成功");
				} else {
					modelMap.put("message", "对不起，添加学生用户失败！");
				}
				modelMap.put("url", "user.do?c=listStudents");
			}else{
				modelMap.put("url", "news.do?c=newslist");
				modelMap.put("message", "对不起，你没有权限，添加失败");
				modelMap.put("time", "3");
				return "global/notify";
			}
		}
		modelMap.put("time", "3");
		return "global/notify";
	}
	// 跳转至导入教师用户页面
	@RequestMapping(params = "c=stuImport")
	public String importStudents(HttpServletRequest request, ModelMap modelMap) {
		// 页面左边边栏操作栏目
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		if (user.getRole() == 1) {
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);
			request.setAttribute("module", "stu_import");
			return "panel/panel";
		}
		modelMap.put("time", "3");
		modelMap.put("message", "对不起，你没有权限，导入失败");
		modelMap.put("url", "news.do?c=newslist");
		return "global/notify";
	}
	
	// 跳转至导入教师用户页面
	@RequestMapping(params = "c=stuImportWithCourse")
	public String importStudentsWithCourse(HttpServletRequest request, ModelMap modelMap) {
		// 页面左边边栏操作栏目
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		if (user.getRole() == 1) {
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "user");
			modelMap.put("leftBar", leftBar);
			request.setAttribute("module", "stu_import_with_courses");
			return "panel/panel";
		}
		modelMap.put("time", "3");
		modelMap.put("message", "对不起，你没有权限，导入失败");
		modelMap.put("url", "news.do?c=newslist");
		return "global/notify";
	}
	
	
	// 上传文件，并导入
	@RequestMapping(params = "c=stuImportFile")
	public String importStuByFile(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		
		if (user.getRole() == 1){
			modelMap.put("url", "user.do?c=listStudents");
			modelMap.put("time", "3");
			modelMap.put("message", "保存失败");

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 专业
			MultipartFile file = multipartRequest.getFile("file");
			int total = 0;
			int success = 0;
			int faild = 0;
			StringBuilder sb = new StringBuilder();
//			Map<String,Integer> classMap=new HashMap<String,Integer>();
			if (file != null) {
				// 开始解析excel
				Workbook workbook = Workbook.getWorkbook(file.getInputStream());
				Sheet sheet = workbook.getSheet(0);
				total = sheet.getRows() - 1;
				for (int i = 1; i < sheet.getRows(); i++) {
					String stuNumber=sheet.getCell(0,i).getContents();
					String userName = sheet.getCell(1, i).getContents();
//					String classNameChi = sheet.getCell(2, i).getContents();
//					String phone = sheet.getCell(3, i).getContents();
//					String email=sheet.getCell(4,i).getContents();
					
//					String classNameNum=stuNumber.substring(0,10);
//					//用一个HashMap作为缓存，保存了导入文件中学生的班级对应在
//					//ClassTable 表中的 classId号。
//					//首先判断一个班级的名字在map中能否找到，不能找到则存入数据库，并读出该数据，加入Map
//					
//					//初始化该Map
//					IClassTableDAOBusiness dao=(IClassTableDAOBusiness)BeanFactory.getBean("IClassTableDAOBusiness");
//					//默认是只查找电子的班级
//					List<ClassTable> temp=dao.getClassesByMajorId(1);
//					Iterator<ClassTable> ite=temp.iterator();
//					while(ite.hasNext()){
//						ClassTable c=ite.next();
//						classMap.put(c.getClassNameNumber(),c.getClassId());
//					}
//					//查找Map是不是存在该班级
//					if(!classMap.containsKey(classNameNum)){
//						ClassTable c=new ClassTable();
//						c.setClassNameNumber(classNameNum);
//						c.setClassNameChi(classNameChi);
//						c.setInMajor(1);
//						dao.doInsert(c);
//						//查找该class，并放入Map
//						c=dao.getClassByClassNameNumber(classNameNum);
//						classMap.put(classNameNum, c.getClassId());
//					}
					UserTable u=new UserTable();
					u.setUserName(userName);
					u.setStuNumber(stuNumber);
					//u.setStuClassId(classMap.get(classNameNum));
					u.setPassword(MD5.getMD5("12345"));
//					u.setEmail(email);
//					u.setPhone(phone);
					u.setEmail("");
					u.setPhone("");
					u.setRegIp(request.getRemoteAddr());
					u.setLastLoginIp(request.getRemoteAddr());
					u.setRegTime(new Date());
					u.setLastLoginTime(new Date());
					u.setRole(3);

					try {
						IUserTableDAOBusiness d=(IUserTableDAOBusiness)
							BeanFactory.getBean("IUserTableDAOBusiness");
						boolean isExist=false;
						isExist=d.isStuNumberExist(u.getStuNumber());
						if(!isExist){
							d.doInsertAStu(u);
							success += 1;
						}else{
							faild += 1;
							sb.append("姓名 ");
							sb.append(userName);
							sb.append("; 学号").append(stuNumber);
							sb.append("保存失败<br />");
							sb.append("原因 :该学生已经存在");
							sb.append("<br />");
						}
					} catch (Exception ex) {
						faild += 1;
						sb.append("姓名 ");
						sb.append(userName);
						sb.append("; 学号").append(stuNumber);
						sb.append("保存失败<br />");
						sb.append("原因 :");
						sb.append(ex.getMessage());
						sb.append("<br />");
					}
				}
				modelMap.put("message", "保存完成<br />总数: " + total + "条  成功:"
						+ success + "条" + "失败:" + faild + "条<br />"
						+ sb.toString());
				if (faild > 0) {
					modelMap.put("time", "600");
				}
			}
			return "global/notify";
		}
		modelMap.put("url", "news.do?c=newslist");
		modelMap.put("time", "3");
		modelMap.put("message", "对不起，你没有权限，导入失败");
		return "global/notify";
	}
	
	// 上传文件，并导入
	@RequestMapping(params = "c=stuImportWithCourseFile")
	public String importStudentsWithCourseFile(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		
		if (user.getRole() == 1){
			modelMap.put("url", "user.do?c=listStudents");
			modelMap.put("time", "3");
			modelMap.put("message", "保存失败");

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 专业
			MultipartFile file = multipartRequest.getFile("file");
			int total = 0;
			int success = 0;
			int faild = 0;
			StringBuilder sb = new StringBuilder();
			IUserTableDAOBusiness d=(IUserTableDAOBusiness)
				BeanFactory.getBean("IUserTableDAOBusiness");
			ICourseTableDAOBusiness courseDao=(ICourseTableDAOBusiness)BeanFactory.getBean("ICourseTableDAOBusiness");
			if (file != null) {
				// 开始解析excel
				Workbook workbook = Workbook.getWorkbook(file.getInputStream());
				Sheet sheet = workbook.getSheet(0);
				total = sheet.getRows() - 1;
				for (int i = 1; i < sheet.getRows(); i++) {
					String stuNumber=sheet.getCell(0,i).getContents();
					boolean isExist=false;
					isExist=d.isStuNumberExist(stuNumber);
					String userName = sheet.getCell(1, i).getContents();
					if(isExist){
						faild += 1;
						sb.append("姓名 ");
						sb.append(userName);
						sb.append("; 学号").append(stuNumber);
						sb.append("保存失败<br />");
						sb.append("原因 :该学生已经存在");
						sb.append("<br />");
						continue;
					}
					String password=sheet.getCell(2,i).getContents();
					
					String courseName=sheet.getCell(4,i).getContents();
					int count=courseDao.isCourseNameExist(courseName);
					if(count>0){
						faild += 1;
						sb.append("姓名 ");
						sb.append(userName);
						sb.append("; 学号").append(stuNumber);
						sb.append("保存失败<br />");
						sb.append("原因 :该课题已经存在");
						sb.append("<br />");
						continue;
					}
					
					String teacherName=sheet.getCell(5,i).getContents();
					
					//初始化教师信息
					UserTable teacher=d.isTeaNameExist(teacherName);
					if(teacher!=null){
						//初始化学生信息
						UserTable u=new UserTable();
						u.setUserName(userName);
						u.setStuNumber(stuNumber);
						u.setPassword(MD5.getMD5(password));
						u.setEmail("@");
						u.setPhone("1");
						u.setRegIp(request.getRemoteAddr());
						u.setLastLoginIp(request.getRemoteAddr());
						u.setRegTime(new Date());
						u.setLastLoginTime(new Date());
						u.setRole(3);
						u.setStuTeacherId(teacher.getUserId());
						d.doInsertAStu(u);
						//插入学生，再根据姓名查询一次，得到学生用户的ID
						UserTable student=d.getStudentByStuNumber(stuNumber);
						
						//初始化课题信息
						CourseTable course=new CourseTable();
						course.setCourse_Name(courseName);
						course.setCourse_Description(courseName);
						course.setTeacher(teacher.getUserId());
						course.setIsSelected(1);
						course.setPublishDate(new Date());
						course.setStudent(student.getUserId());
						courseDao.doInsert(course);
						//插入课题，再根据课题名再查询一次，得到课题的ID
						course=courseDao.doGetACourseByName(courseName);
						
						//更新学生信息
						student.setStuCourseId(course.getCourse_Id());
						d.doUpdate(student);
						
						//更新教师信息
						teacher.setTeaMaxStu(teacher.getTeaMaxStu()+1);
						teacher.setTeaCurrentStu(teacher.getTeaCurrentStu()+1);
						d.doUpdate(teacher);
						success++;
					}else{
						faild += 1;
						sb.append("姓名 ");
						sb.append(userName);
						sb.append("; 学号").append(stuNumber);
						sb.append("保存失败<br />");
						sb.append("原因 :");
						sb.append("该指导教师不存在");
						sb.append("<br />");
					}
				}
				modelMap.put("message", "保存完成<br />总数: " + total + "条  成功:"
						+ success + "条" + "失败:" + faild + "条<br />"
						+ sb.toString());
				if (faild > 0) {
					modelMap.put("time", "30");
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
    @RequestMapping(params = "c=teaExport")
    public String exportTeachers(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
    	//当前导出操作用户
        UserTable user=(UserTable)request.getSession().getAttribute("user");
    	if(user.getRole()==1){
	        response.setContentType("application/xls");
	        SimpleDateFormat sd = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	        String dateString = sd.format(new Date());
	        response.addHeader("Content-Disposition", "attachment;filename=teacher_export_"+ dateString+ ".xls");
	
	        //创建工作区
	        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
	        WritableSheet sheet = workbook.createSheet("选题", 0);
	        
	        //教师操作
	        IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)
					BeanFactory.getBean("IUserTableDAOBusiness");
	        IPositionTableDAOBusiness posiDao=(IPositionTableDAOBusiness)BeanFactory.getBean("IPositionTableDAOBusiness");
	        List<UserTable> teachers=null;
	        if(user.getRole()==1){//管理员
	        	teachers=userDao.getAllTeachers();
	        }
	        if(teachers!=null && teachers.size()>0){
	        	 int i = 0;
 		        //初始化表格头
 		        sheet.addCell(new Label(0, 0, "编号"));
 		        sheet.addCell(new Label(1, 0, "教师姓名"));
 		        sheet.addCell(new Label(2, 0, "职称"));
 		        sheet.addCell(new Label(3, 0, "联系电话"));
 		        sheet.addCell(new Label(4, 0, "Email"));
 		        sheet.addCell(new Label(5, 0, "带生情况"));
	        	
	        	//准备一个详细的course对象
	        	TeacherPosition teaTemp=null;
	    		Iterator<UserTable> ite=teachers.iterator();
	    		while(ite.hasNext()){
	    			UserTable temp=ite.next();//对当前课题处理，查找教师和学生信息
	    			teaTemp=new TeacherPosition();
	    			teaTemp.setUserName(temp.getUserName());
	    			teaTemp.setEmail(temp.getEmail());
	    			teaTemp.setPhone(temp.getPhone());
	    			teaTemp.setTeaCurrentStu(temp.getTeaCurrentStu());
	    			teaTemp.setTeaMaxStu(temp.getTeaMaxStu());
	    			
	    			//添加教师职称信息
	    			if(temp.getTeaPosition()>0){
	    				PositionTable p=posiDao.getPositionById(temp.getTeaPosition());
	    				if(p!=null){
	    					teaTemp.setPosition_Name(p.getPosition_Name());
	    				}
	    			}
	    			
	    			//一个 CourseDetail对象准备完毕
	    			i++;
	                sheet.addCell(new Label(0, i, String.valueOf(i)));
	                sheet.addCell(new Label(1, i, teaTemp.getUserName()));
	                sheet.addCell(new Label(2, i, teaTemp.getPosition_Name()));
	                sheet.addCell(new Label(3, i, teaTemp.getPhone()));
	                sheet.addCell(new Label(4, i, teaTemp.getEmail()));
	                String str=String.valueOf(teaTemp.getTeaCurrentStu())+"/"+String.valueOf(teaTemp.getTeaMaxStu());
	                sheet.addCell(new Label(5, i, str));
	    		}
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
    
    
    /**导出课题信息
     * 必须要求有导出
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=stuExport")
    public String exportStudents(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
    	//当前导出操作用户
        UserTable user=(UserTable)request.getSession().getAttribute("user");
    	if(user.getRole()==1){
	        response.setContentType("application/xls");
	        SimpleDateFormat sd = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	        String dateString = sd.format(new Date());
	        response.addHeader("Content-Disposition", "attachment;filename=student_export_"+ dateString+ ".xls");
	
	        //创建工作区
	        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
	        WritableSheet sheet = workbook.createSheet("选题", 0);
	        //创建表格头栏目
	        sheet.mergeCells(0, 0, 15, 0);
	        sheet.addCell(new Label(0,0,"成都理工大学二○一一届毕业设计（论文）题目、成绩及推荐优秀综合信息表"));
	        sheet.mergeCells(0, 1, 15, 1);
	        sheet.addCell(new Label(0,1,"学院名称（签章）：信科院       专业名称：电子信息科学与技术  主管院长（签字）：           教务科长（签字）：            填表日期:2011.6.11"));
	        sheet.mergeCells(0, 2, 0, 3);
	        sheet.addCell(new Label(0,2,"序号"));
	        sheet.mergeCells(1, 2, 1, 3);
	        sheet.addCell(new Label(1,2,"学生姓名"));
	        sheet.mergeCells(2, 2, 2, 3);
	        sheet.addCell(new Label(2,2,"学号"));
	        sheet.mergeCells(3, 2, 3, 3);
	        sheet.addCell(new Label(3,2,"题目名称"));
	        
	        sheet.mergeCells(4, 2, 7, 2);
	        sheet.addCell(new Label(4,2,"成绩"));
	        sheet.addCell(new Label(4,3,"总评"));
	        sheet.addCell(new Label(5,3,"评阅"));
	        sheet.addCell(new Label(6,3,"评审"));
	        sheet.addCell(new Label(7,3,"答辩"));
	        
	        sheet.mergeCells(8, 2, 8, 3);
	        sheet.addCell(new Label(8,2,"校院优秀"));
	        sheet.mergeCells(9, 2, 9, 3);
	        sheet.addCell(new Label(9,2,"题目来源"));
	        sheet.mergeCells(10, 2, 10, 3);
	        sheet.addCell(new Label(10,2,"题目类型"));
	        
	        sheet.mergeCells(11, 2, 12, 2);
	        sheet.addCell(new Label(11,2,"指导教师"));
	        sheet.addCell(new Label(11,3,"姓名"));
	        sheet.addCell(new Label(12,3,"职称"));
	        
	        sheet.mergeCells(13, 2, 14, 2);
	        sheet.addCell(new Label(13,2,"评审教师"));
	        sheet.addCell(new Label(13,3,"姓名"));
	        sheet.addCell(new Label(14,3,"职称"));
	        
	        sheet.mergeCells(15, 2, 15, 3);
	        sheet.addCell(new Label(15,2,"备注"));
	        //教师操作
	        IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)
					BeanFactory.getBean("IUserTableDAOBusiness");
	        IClassTableDAOBusiness classDao=(IClassTableDAOBusiness)
					BeanFactory.getBean("IClassTableDAOBusiness");
			IMajorTableDAOBusiness majorDao=(IMajorTableDAOBusiness)
						BeanFactory.getBean("IMajorTableDAOBusiness");
			ICourseTableDAOBusiness courseDao=(ICourseTableDAOBusiness)BeanFactory.getBean("ICourseTableDAOBusiness");
	        IPositionTableDAOBusiness posintionDao=(IPositionTableDAOBusiness)BeanFactory.getBean("IPositionTableDAOBusiness");
			ICourseSourceDAOBusiness courseSourceDao=(ICourseSourceDAOBusiness)BeanFactory.getBean("ICourseSourceDAOBusiness");
			ICourseTypeDAOBusiness courseTypeDao=(ICourseTypeDAOBusiness)BeanFactory.getBean("ICourseTypeDAOBusiness");
	        
			//CourseSource表的数据，
			Map<Integer,CourseSource> courseSourceMap=new HashMap<Integer, CourseSource>();
			//CourseType表的数据，
			Map<Integer,CourseType> courseTypeMap=new HashMap<Integer, CourseType>();
			List<CourseSource> sourceList=courseSourceDao.queryAllCourseSourceObj();
			List<CourseType> typeList=courseTypeDao.queryAllCourseTypeObj();
			for(CourseSource temp:sourceList){
				courseSourceMap.put(temp.getId(), temp);
			}
			for(CourseType temp:typeList){
				courseTypeMap.put(temp.getId(), temp);
			}
			
			
			List<StudentDetail> students=null;
	        if(user.getRole()==1){//管理员
	        	students=userDao.getStudentsWithClass();
	        }
	        if(students!=null && students.size()>0){
        	 int i = 4;
		        //初始化表格头
		        Iterator<StudentDetail> ite=students.iterator();
	    		while(ite.hasNext()){
	    			StudentDetail temp=ite.next();//对当前课题处理，查找教师和学生信息
	    			
	    			if(temp.getStuClassId()>0){
	    				//添加该学生班级信息
	    				ClassDetail c=classDao.getClassByClassId(temp.getStuClassId());
	    				if(c!=null && c.getInMajor()>0){
	    					//添加该班级的专业信息
	    					MajorTable m=majorDao.getById(c.getInMajor());
	    					if(m!=null){
	    						temp.setMajorName(m.getMajorName());
	    					}
	    				}
	    			}
	    			//添加该学生所选课题信息以及带题教师信息
	    			if(temp.getStuCourseId()>0){
	    				CourseTable c=courseDao.doGetACourseById(temp.getStuCourseId());
	    				if(c!=null){
	    					temp.setCourseName(c.getCourse_Name());
	    					temp.setStuCourseSourceChar(courseSourceMap.get(c.getCourseSource()).getSourceChar());
	    					temp.setStuCourseTypeChar(courseTypeMap.get(c.getCourseType()).getTypeChar());
	    					if(c.getTeacher()>0){
	    						UserTable u=userDao.getUserById(c.getTeacher());
	    						if(u!=null){
	    							temp.setTeacherName(u.getUserName());
	    							temp.setTeaPositionName(posintionDao.getPositionById(u.getTeaPosition()).getPosition_Name());
	    						}
	    					}
	    				}
	    			}
	                sheet.addCell(new Label(0, i, String.valueOf(i-3)));
	                sheet.addCell(new Label(1, i, temp.getUserName()));
	                sheet.addCell(new Label(2, i, temp.getStuNumber()));
	                sheet.addCell(new Label(3, i, temp.getCourseName()));
	                sheet.addCell(new Label(9, i, temp.getStuCourseSourceChar()));
	                sheet.addCell(new Label(10, i, temp.getStuCourseTypeChar()));
	                sheet.addCell(new Label(11, i, temp.getTeacherName()));
	                sheet.addCell(new Label(12, i, temp.getTeaPositionName()));
	                i++;
	    		}
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
}
