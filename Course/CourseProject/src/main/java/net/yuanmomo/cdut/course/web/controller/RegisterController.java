package net.yuanmomo.cdut.course.web.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yhb.dao.IClassTableDAO;
import org.yhb.dao.IUserTableDAO;
import org.yhb.dao.Business.IClassTableDAOBusiness;
import org.yhb.dao.Business.IUserTableDAOBusiness;
import org.yhb.dao.Business.ValidationBusiness;
import org.yhb.util.BeanFactory;
import org.yhb.util.MD5;
import org.yhb.vo.ClassDetail;
import org.yhb.vo.UserTable;
import org.yhb.vo.entity.UserLoginEntity;

@Controller
@RequestMapping("/register.do")
public class RegisterController{
	
	//如果直接登录注册页面，则跳转至首页，然后跳转至登录页面
	@RequestMapping
	public String defaultForwardToLogin(HttpServletRequest req,
			HttpServletResponse resp){
		try {
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	//从login.jsp点击注册，跳转至该页面
	@RequestMapping(params="type=stu")
	public String forwardToStuRegister(HttpServletRequest req,
			HttpServletResponse resp,ModelMap modelMap) throws Exception {
		return "stu_register";
	}
	
	
	//学生注册
	@RequestMapping(params="submit")
	public String doStuRigesterSubmit(HttpServletRequest req,
			HttpServletResponse resp,ModelMap modelMap){
		//get the parameters from the form
		String stuNumber=req.getParameter("studentNumber").trim();
		String stuName=req.getParameter("studentName").trim();
		String password=req.getParameter("password1");
		String email=req.getParameter("email").trim();
		String phone=req.getParameter("phone").trim();
		int classId=0;
		
		if(stuNumber==null || "".equals(stuNumber) ||!stuNumber.matches("\\d{12,13}")){
			modelMap.put("stuName", stuName);
			modelMap.put("email", email);
			modelMap.put("phone", phone);
			modelMap.put("errorMsg","请输入正确的学号");
			return "stu_register";
		}
		
		//new a student and set its studentNumber
		//检查该学号所在专业是否添加进了选课系统
		//专升本，学号3开头 开放3开头的学生
		UserTable user=new UserTable();
		user.setStuNumber(stuNumber);
		if(stuNumber!=null && !stuNumber.startsWith("3")){
			boolean isLegal=ValidationBusiness.validateUserAvailable(user);
			if(!isLegal){
				modelMap.put("errorMsg","您的学号所在专业没有添加选课系统！");
				return "stu_register";
			}
		}
		//the stuNumber is exist?
		IUserTableDAOBusiness userBusiness=(IUserTableDAOBusiness)BeanFactory.getBean("IUserTableDAOBusiness");
		boolean isExist=false;
		isExist=userBusiness.isStuNumberExist(stuNumber);
		if(!isExist){//学号没有被注册
			//检验其它数据
			if(stuName==null || "".equals(stuName)){
				modelMap.put("stuNumber", stuNumber);
				modelMap.put("email", email);
				modelMap.put("phone", phone);
				modelMap.put("errorMsg","请输入您的姓名");
				return "stu_register";
			}
			
			if(password==null || "".equals(password)){
				modelMap.put("errorMsg","请输入密码");
				return "stu_register";
			}
			
			if(email==null ||"".equals(email) || !email.matches(".*?@.*")){
				modelMap.put("stuNumber", stuNumber);
				modelMap.put("stuName", stuName);
				modelMap.put("phone", phone);
				modelMap.put("errorMsg","请输入正确的邮箱地址");
				return "stu_register";
			}
			
			if(phone==null ||"".equals(phone) || !phone.matches("\\d*")){
				modelMap.put("stuNumber", stuNumber);
				modelMap.put("stuName", stuName);
				modelMap.put("email", email);
				modelMap.put("errorMsg","请输入正确的电话号码");
				return "stu_register";
			}
			try {
				classId = Integer.parseInt(req.getParameter("className").trim());
				if(classId<=0){
					modelMap.put("stuNumber", stuNumber);
					modelMap.put("stuName", stuName);
					modelMap.put("email", email);
					modelMap.put("phone", phone);
					modelMap.put("errorMsg","请选择您的班级");
					return "stu_register";
				}
			} catch (Exception e) {
				
			}
			//判断该学生所输入班级号和学号是否匹配
			//如果学号以3开头，则不判断
			if(!stuNumber.startsWith("3")){
				IClassTableDAOBusiness classDao=(IClassTableDAOBusiness)BeanFactory.getBean("IClassTableDAOBusiness");
				ClassDetail c=classDao.getClassByClassId(classId);
				boolean isMatch=false;
				if(user.getStuNumber()!=null && c!=null && c.getClassNameNumber()!=null){
					if(user.getStuNumber().startsWith(c.getClassNameNumber())){
						isMatch=true;
					}
				}
				if(!isMatch){
					modelMap.put("phone", phone);
					modelMap.put("stuName", stuName);
					modelMap.put("email", email);
					modelMap.put("errorMsg","学号和所选班级不匹配，请重新输入");
					return "stu_register";
				}
			}
			//数据无误，插入数据
			user.setUserName(stuName);
			user.setPassword(MD5.getMD5(password));
			user.setPhone(phone);
			user.setEmail(email);
			user.setStuClassId(classId);
			Date date=new Date();
			
			//添加注册时间和IP地址，以及上次登录时间和IP地址
			user.setRegTime(date);
			user.setLastLoginTime(date);
			String ip=req.getRemoteAddr();
			user.setRegIp(ip);
			user.setLastLoginIp(ip);
			user.setRole(3);
			
			boolean flag=userBusiness.doInsertAStu(user);
			if(flag){
				//System.out.println("插入成功");
				//查询用户，取得该用户的uerId
				UserLoginEntity e=new UserLoginEntity();
				e.setStuNumber(user.getStuNumber());
				e.setPassword(user.getPassword());
				user=userBusiness.getUserByLogin(e);
				//将用户写入Session
				req.getSession().setAttribute("user", user);
				
				//设置页面显示参数
				modelMap.put("time",1);
				modelMap.put("message","注册成功，正在跳转，请等待...");
				
				//添加跳转地址，注册成功后，需要进行跳转
				modelMap.put("url","login.do?success");
				return "global/notify";
			}else{
				modelMap.put("errorMsg","程序出错，请与管理员联系");
				return "stu_register";
			}
		}else{//学号已经存在，注册了
			modelMap.put("stuName", stuName);
			modelMap.put("email", email);
			modelMap.put("phone", phone);
			modelMap.put("stuNumber", stuNumber);
			modelMap.put("errorMsg","该学号已经存在，如果你被恶意注册，你报告老师");
			return "stu_register";
		}
	}
}
