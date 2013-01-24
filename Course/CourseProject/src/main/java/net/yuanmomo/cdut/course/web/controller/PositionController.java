package net.yuanmomo.cdut.course.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yuanmomo.cdut.course.web.controller.panel.BuildLeftBar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yhb.dao.IPositionTableDAO;
import org.yhb.dao.Business.IPositionTableDAOBusiness;
import org.yhb.util.BeanFactory;
import org.yhb.util.FinalDatas;
import org.yhb.util.PageSplit;
import org.yhb.vo.PositionTable;
import org.yhb.vo.UserTable;

@Controller
@RequestMapping("/position.do")
public class PositionController {
	
	@RequestMapping(params="c=positionList")
	public String viewListPosition(HttpServletRequest req,
			HttpServletResponse resp,ModelMap modelMap){
		UserTable user=(UserTable)req.getSession().getAttribute("user");
		if(user.getRole()==1){
			IPositionTableDAOBusiness dao=(IPositionTableDAOBusiness)BeanFactory.
				getBean("IPositionTableDAOBusiness");
			
			// 处理分页情况
			PageSplit page=(PageSplit)BeanFactory.getBean("pageSplit");
			page.setParameters(req);
			
			//默认是只查找信息科学技术学院下的专业
			List<PositionTable> positions=dao.getAll(page.getCurrentPage(),page.getPageSize());
			page.setResultCount(dao.getAllCount());
			
			modelMap.put("pageSplit",page.split("position.do?c=positionList"));
			modelMap.put("positions",positions);
			req.setAttribute("module", "posi_list");
			
			// 页面左边边栏操作栏目
			String leftBar = BuildLeftBar.build(user,
					FinalDatas.xmlFileBasicLocation, "position");
			modelMap.put("leftBar", leftBar);
			
			return "panel/panel";
		}else{
			modelMap.put("message", "对不起，你没有权限，查看失败");
			modelMap.put("time",3);
			modelMap.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
	
	
	@RequestMapping(params = "c=addPosition")
	public String editPosition(HttpServletRequest request, ModelMap map) {
		String idStr = request.getParameter("id");
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(idStr);
			// 初始化右边的编辑窗口
			IPositionTableDAOBusiness dao=(IPositionTableDAOBusiness)BeanFactory.
						getBean("IPositionTableDAOBusiness");
			PositionTable position=dao.getPositionById(id);
			map.put("position",position);
		}
		
		// 页面左边边栏操作栏目
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "position");
		map.put("leftBar", leftBar);

		request.setAttribute("module", "posi_edit");
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
	@RequestMapping(params = "c=savePosition")
	public String savePosition(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		//取得页面传过来的ID
		String idString=request.getParameter("id");
		int id=0;
		if(idString!=null&&!"".equals(idString)&&!"0".equals(idString)){
			id=Integer.parseInt(idString);
		}
		
		UserTable user = (UserTable) request.getSession().getAttribute("user");

		IPositionTableDAOBusiness dao=(IPositionTableDAOBusiness)BeanFactory.
					getBean("IPositionTableDAOBusiness");
		if (user.getRole() == 1) {
			// 接受页面端参数，进行验证
			String pName = request.getParameter("positionName").trim();
			String pCount = request.getParameter("defaultStuCount").trim();

			PositionTable position=new PositionTable();
			position.setPosition_Id(id);
			position.setPosition_Name(pName);
			int defaultCount=0;
			try {
				defaultCount=Integer.parseInt(pCount);
			} catch (Exception e) {
				// TODO: handle exception
				defaultCount=0;
			}
			
			position.setDefault_Stu_Count(defaultCount);
			
			// 验证职称名称不为空
			if (pName == null || "".equals(pName)) {
				position.setPosition_Name(null);
				modelMap.put("position",position);
				modelMap.put("error", "请输入职称名称");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "position");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "posi_edit");
				return "panel/panel";
			}
			//判断职称名称是否存在
			int positionId=0;
			positionId=dao.isPositionNameExit(pName);
			if(positionId!=0 && positionId!=id){
				position.setPosition_Name(null);
				modelMap.put("position",position);
				modelMap.put("error", "该职称名称已经存在");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "position");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "posi_edit");
				return "panel/panel";
			}
			//判断默认带生人数不为空
			if (pCount == null || "".equals(pCount)) {
				position.setDefault_Stu_Count(0);
				modelMap.put("position",position);
				modelMap.put("error", "请输入默认带生人数");

				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, "position");
				modelMap.put("leftBar", leftBar);

				request.setAttribute("module", "posi_edit");
				return "panel/panel";
			}
			// 数据验证完毕
			// 如果有id 则更新
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")
					&& !request.getParameter("id").equals("0")) {

				position.setPosition_Id(Integer.parseInt(request.getParameter("id")));
				boolean flag=dao.doUpdate(position);
				if (flag) {
					modelMap.put("message", "更新职称成功");
				} else {
					modelMap.put("message", "对不起，更新职称失败");
				}
			} else {
				// 没有则添加
				boolean flag = dao.doInsert(position);
				if (flag) {
					modelMap.put("message", "添加职称成功");
				} else {
					modelMap.put("message", "对不起，添加职称失败！");
				}
			}
			modelMap.put("url", "position.do?c=positionList");
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
	@RequestMapping(params = "c=deletePosition")
	public String deletePosition(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		modelMap.put("url", "position.do?c=positionList");
		modelMap.put("time", "3");
		if (user.getRole() == 1) {// 是管理员
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")) {
				int id = Integer.parseInt(request.getParameter("id"));
				IPositionTableDAOBusiness dao=(IPositionTableDAOBusiness)BeanFactory.
						getBean("IPositionTableDAOBusiness");
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
