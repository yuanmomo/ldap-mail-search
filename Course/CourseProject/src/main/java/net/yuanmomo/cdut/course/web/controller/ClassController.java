package net.yuanmomo.cdut.course.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yuanmomo.cdut.course.web.controller.panel.BuildLeftBar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yhb.dao.IClassTableDAO;
import org.yhb.dao.IMajorTableDAO;
import org.yhb.dao.Business.IClassTableDAOBusiness;
import org.yhb.dao.Business.IMajorTableDAOBusiness;
import org.yhb.util.BeanFactory;
import org.yhb.util.FinalDatas;
import org.yhb.util.PageSplit;
import org.yhb.vo.ClassDetail;
import org.yhb.vo.ClassTable;
import org.yhb.vo.MajorTable;
import org.yhb.vo.UserTable;

@Controller
@RequestMapping("/class.do")
public class ClassController {
	
	@RequestMapping(params="c=list")
	public String viewListClass(HttpServletRequest req,
			HttpServletResponse resp,ModelMap modelMap){
		UserTable user=(UserTable)req.getSession().getAttribute("user");
		if(user.getRole()==1){
			IClassTableDAOBusiness dao=(IClassTableDAOBusiness)BeanFactory.
				getBean("IClassTableDAOBusiness");
			
			// 处理分页情况
			PageSplit page=(PageSplit)BeanFactory.getBean("pageSplit");
			page.setParameters(req);
			
			//默认是只查找信息科学技术学院下的专业
			List<ClassDetail> classes=dao.getAllClasses(page.getCurrentPage(),page.getPageSize());
			page.setResultCount(dao.getCount());
			
			modelMap.put("pageSplit",page.split("class.do?c=list"));
			modelMap.put("cs",classes);
			req.setAttribute("module", "class_list");
			
			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "class");
			modelMap.put("leftBar", leftBar);
			
			return "panel/panel";
		}else{
			modelMap.put("message", "对不起，你没有权限，查看失败");
			modelMap.put("time",3);
			modelMap.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
	
	
	@RequestMapping(params = "c=add")
	public String editClass(HttpServletRequest request, ModelMap map) {
		String idStr = request.getParameter("id");
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(idStr);
			// 初始化右边的编辑窗口
			IClassTableDAOBusiness dao=(IClassTableDAOBusiness)BeanFactory.
						getBean("IClassTableDAOBusiness");
			ClassDetail c=dao.getClassByClassId(id);
			map.put("c",c);
		}
		
		//初始化专业列表
		IMajorTableDAOBusiness majorDao=(IMajorTableDAOBusiness)BeanFactory.
						getBean("IMajorTableDAOBusiness");
		List<MajorTable> majors=majorDao.getMajorsByCollegeId(1);
		map.put("majors",majors);
		
		// 页面左边边栏操作栏目
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "class");
		map.put("leftBar", leftBar);

		request.setAttribute("module", "class_edit");
		return "panel/panel";
	}
	/**
	 * 保存和更新班级
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=save")
	public String saveClass(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		//取得页面传过来的ID
		String idString=request.getParameter("id");
		int id=0;
		if(idString!=null&&!"".equals(idString)&&!"0".equals(idString)){
			id=Integer.parseInt(idString);
		}
		
		IClassTableDAOBusiness dao=(IClassTableDAOBusiness)BeanFactory.
						getBean("IClassTableDAOBusiness");
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		if (user.getRole() == 1) {
			// 接受页面端参数，进行验证
			
			String classNumber = request.getParameter("classNumber").trim();
			String className = request.getParameter("className").trim();
			String inMajor=request.getParameter("majorId").trim();
			
			ClassTable c=new ClassTable();
			c.setClassId(id);
			c.setClassNameChi(className);
			c.setClassNameNumber(classNumber);
			try {
				c.setInMajor(Integer.parseInt(inMajor));
			} catch (Exception e) {
				//默认保存到信息工程学院
				c.setInMajor(1);
			}
			
			// 验证班级编号不为空
			if (classNumber == null || "".equals(classNumber)) {
				c.setClassNameNumber(null);
				modelMap.put("c",c);
				modelMap.put("error", "请输入班级号码");
				
				//初始化专业列表
				IMajorTableDAOBusiness majorDao=(IMajorTableDAOBusiness)BeanFactory.
								getBean("IMajorTableDAOBusiness");
				List<MajorTable> majors=majorDao.getMajorsByCollegeId(1);
				modelMap.put("majors",majors);

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "class");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "class_edit");
				return "panel/panel";
			}
			//判断班级编号是否已经存在
			int classId=0;
			classId=dao.isClassNumberExsit(classNumber);
			if(classId!=0 && classId!=id){
				c.setClassNameNumber(null);
				modelMap.put("c",c);
				modelMap.put("error", "该班级号码已经存在");
				
				//初始化专业列表
				IMajorTableDAOBusiness majorDao=(IMajorTableDAOBusiness)BeanFactory.
								getBean("IMajorTableDAOBusiness");
				List<MajorTable> majors=majorDao.getMajorsByCollegeId(1);
				modelMap.put("majors",majors);

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "class");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "class_edit");
				return "panel/panel";
			}
			// 验证班级名称不能为空
			if (className == null || "".equals(className)) {
				c.setClassNameChi(null);
				modelMap.put("c", c);
				modelMap.put("error", "请输入班级名称");

				//初始化专业列表
				IMajorTableDAOBusiness majorDao=(IMajorTableDAOBusiness)BeanFactory.
								getBean("IMajorTableDAOBusiness");
				List<MajorTable> majors=majorDao.getMajorsByCollegeId(1);
				modelMap.put("majors",majors);
				
				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "class");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "class_edit");
				return "panel/panel";
			}
			//判断班级名是否已经存在
			classId=dao.isClassNameExsit(className);
			if(classId!=0 && classId!=id){
				c.setClassNameChi(null);
				modelMap.put("c", c);
				modelMap.put("error", "该班级名已经存在");

				//初始化专业列表
				IMajorTableDAOBusiness majorDao=(IMajorTableDAOBusiness)BeanFactory.
								getBean("IMajorTableDAOBusiness");
				List<MajorTable> majors=majorDao.getMajorsByCollegeId(1);
				modelMap.put("majors",majors);
				
				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "class");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "class_edit");
				return "panel/panel";
			}
			// 验证所在专业不能为空
			if (inMajor == null || "".equals(inMajor)||"0".equals(inMajor)) {
				c.setInMajor(0);
				modelMap.put("c", c);
				modelMap.put("error", "请选择班级所在专业");

				//初始化专业列表
				IMajorTableDAOBusiness majorDao=(IMajorTableDAOBusiness)BeanFactory.
								getBean("IMajorTableDAOBusiness");
				List<MajorTable> majors=majorDao.getMajorsByCollegeId(1);
				modelMap.put("majors",majors);
				
				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "class");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "class_edit");
				return "panel/panel";
			}
			// 数据验证完毕
			// 如果有id 则更新
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")
					&& !request.getParameter("id").equals("0")) {
				c.setClassId((Integer
						.parseInt(request.getParameter("id"))));
				boolean flag = dao.doUpdate(c);

				if (flag) {
					modelMap.put("message", "更新班级成功");
				} else {
					modelMap.put("message", "对不起，更新班级失败");
				}
			} else {
				// 没有则添加
				boolean flag = dao.doInsert(c);
				if (flag) {
					modelMap.put("message", "添加班级成功");
				} else {
					modelMap.put("message", "对不起，添加班级失败！");
				}
			}
			modelMap.put("url", "class.do?c=list");
		} else {
			modelMap.put("message", "对不起，你没有权限，添加失败");
			modelMap.put("url", "news.do?c=newslist");
		}
		modelMap.put("time", "3");
		return "global/notify";
	}
	
	
	/**
	 * 专业删除
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=delete")
	public String deleteClass(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		modelMap.put("url", "class.do?c=list");
		modelMap.put("time", "3");
		if (user.getRole() == 1) {// 是管理员
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")) {
				int id = Integer.parseInt(request.getParameter("id"));
				IClassTableDAOBusiness dao=(IClassTableDAOBusiness)BeanFactory.
				getBean("IClassTableDAOBusiness");
				boolean flag = dao.doDelete(id);
				if (flag) {
					modelMap.put("message", "删除成功");
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
}
