package net.yuanmomo.cdut.course.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yuanmomo.cdut.course.web.controller.panel.BuildLeftBar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yhb.dao.IMajorTableDAO;
import org.yhb.dao.Business.IMajorTableDAOBusiness;
import org.yhb.util.BeanFactory;
import org.yhb.util.FinalDatas;
import org.yhb.util.PageSplit;
import org.yhb.vo.MajorTable;
import org.yhb.vo.UserTable;

@Controller
@RequestMapping("/major.do")
public class MajorController {

	@RequestMapping(params = "c=list")
	public String viewListMajor(HttpServletRequest req,
			HttpServletResponse resp, ModelMap modelMap) {
		UserTable user = (UserTable) req.getSession().getAttribute("user");
		if (user.getRole() == 1) {
			IMajorTableDAOBusiness dao = (IMajorTableDAOBusiness) BeanFactory
					.getBean("IMajorTableDAOBusiness");

			// 处理分页情况
			PageSplit page = (PageSplit) BeanFactory.getBean("pageSplit");
			page.setParameters(req);

			// 默认是只查找信息科学技术学院下的专业
			List<MajorTable> majorList = dao.getMajors(page.getCurrentPage(),
					page.getPageSize());
			page.setResultCount(dao.getCount());

			modelMap.put("pageSplit", page.split("major.do?c=list"));
			modelMap.put("majors", majorList);
			req.setAttribute("module", "maj_list");

			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "major");
			modelMap.put("leftBar", leftBar);

			return "panel/panel";
		} else {
			modelMap.put("message", "对不起，你没有权限，查看失败");
			modelMap.put("time", 3);
			modelMap.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}

	@RequestMapping(params = "c=add")
	public String editMajor(HttpServletRequest request, ModelMap map) {
		String idStr = request.getParameter("id");
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(idStr);
			// 初始化右边的编辑窗口
			IMajorTableDAOBusiness dao = (IMajorTableDAOBusiness) BeanFactory
					.getBean("IMajorTableDAOBusiness");
			MajorTable major = dao.getById(id);
			map.put("major", major);
		}

		// 页面左边边栏操作栏目
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "major");
		map.put("leftBar", leftBar);

		request.setAttribute("module", "maj_edit");
		return "panel/panel";
	}

	/**
	 * 保存和更新专业
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=save")
	public String saveMajor(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		// 取得页面传过来的ID
		IMajorTableDAOBusiness dao = (IMajorTableDAOBusiness) BeanFactory
				.getBean("IMajorTableDAOBusiness");
		String idString = request.getParameter("id");
		MajorTable major =null;
		int id = 0;
		if (idString != null && !"".equals(idString) && !"0".equals(idString)) {
			id = Integer.parseInt(idString);
			major=dao.getById(id);
		}

		UserTable user = (UserTable) request.getSession().getAttribute("user");

		if (user.getRole() == 1) {
			// 接受页面端参数，进行验证
			String majorNumber = request.getParameter("majorNumber").trim();
			String majorName = request.getParameter("majorName").trim();

			if(major==null){
				major=new MajorTable();
			}
			major.setMajorId(id);
			major.setMajorName(majorName);
			major.setMajorNumber(majorNumber);

			// 验证专业编号不为空
			if (majorNumber == null || "".equals(majorNumber)) {
				major.setMajorNumber(null);
				modelMap.put("major", major);
				modelMap.put("error", "请输入专业号码");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "major");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "maj_edit");
				return "panel/panel";
			}
			// 判断专业号是否已经存在
			int majorId = dao.isMajorNumberExsit(majorNumber);
			if (majorId != 0 && majorId != id) {
				major.setMajorNumber(null);
				modelMap.put("major", major);
				modelMap.put("error", "该专业号已经存在");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "major");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "maj_edit");
				return "panel/panel";
			}
			// 验证专业名称不能为空
			if (majorName == null || "".equals(majorName)) {
				major.setMajorName(null);
				modelMap.put("major", major);
				modelMap.put("error", "请输入专业名称");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "major");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "maj_edit");
				return "panel/panel";
			}
			// 判断专业名是否已经存在
			majorId = dao.isMajorNameExsit(majorName);
			if (majorId != 0 && majorId != id) {
				major.setMajorName(null);
				modelMap.put("major", major);
				modelMap.put("error", "该专业名称已经存在");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "major");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "maj_edit");
				return "panel/panel";
			}
			// 数据验证完毕
			// 如果有id 则更新
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")
					&& !request.getParameter("id").equals("0")) {
				major.setMajorId((Integer.parseInt(request.getParameter("id"))));

				boolean flag = dao.doUpdate(major);

				if (flag) {
					modelMap.put("message", "更新专业成功");
				} else {
					modelMap.put("message", "对不起，更新专业失败");
				}
			} else {
				// 没有则添加
				// 默认都是信息科学技术学院
				major.setInCollege(1);
				boolean flag = dao.doInsert(major);
				if (flag) {
					modelMap.put("message", "添加专业成功");
				} else {
					modelMap.put("message", "对不起，添加专业失败！");
				}
			}
			modelMap.put("url", "major.do?c=list");
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
	public String deleteMajor(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		modelMap.put("url", "major.do?c=list");
		modelMap.put("time", "3");
		if (user.getRole() == 1) {// 是管理员和老师
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")) {
				int id = Integer.parseInt(request.getParameter("id"));
				IMajorTableDAOBusiness dao = (IMajorTableDAOBusiness) BeanFactory
						.getBean("IMajorTableDAOBusiness");
				MajorTable m = new MajorTable();
				m.setMajorId(id);
				boolean flag = dao.doDelete(m);
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
