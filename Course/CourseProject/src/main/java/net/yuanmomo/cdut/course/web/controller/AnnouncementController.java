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

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.yuanmomo.cdut.course.web.bean.Announcement;
import net.yuanmomo.cdut.course.web.bean.UserTable;
import net.yuanmomo.cdut.course.web.util.BeanFactory;
import net.yuanmomo.cdut.course.web.util.BuildLeftBar;
import net.yuanmomo.cdut.course.web.util.FinalDatas;
import net.yuanmomo.cdut.course.web.util.PageSplit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 新闻控制
 * 
 * @author princehaku
 */
@Controller
@RequestMapping("/news.do")
public class AnnouncementController extends BasicController {

	/**
	 * 显示新闻页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=view")
	public String viewAnnouncement(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		String idStr = request.getParameter("id");// 都可以查看公告信息，不用权限检测
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(request.getParameter("id"));
			Announcement news = this.announcementBusiness
					.getAnnouncementById(id);
			modelMap.put("news", news);
		}
		return "panel/news";
	}

	/**
	 * 新闻删除
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=delete")
	public String deleteAnnouncement(HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		modelMap.put("url", "news.do?c=newslist");
		modelMap.put("time", "3");
		if (user.getRole() == 1) {// 是管理员
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")) {
				int id = Integer.parseInt(request.getParameter("id"));
				boolean flag = this.announcementBusiness.doDelete(id);
				if (flag) {
					modelMap.put("message", "删除成功");
				} else {
					modelMap.put("message", "对不起，删除失败");
				}
			}
		} else {
			modelMap.put("message", "对不起，你没有权限，删除失败");
		}
		return "global/notify";
	}

	@RequestMapping(params = "c=newslist")
	public String viewNewsList(HttpServletRequest request, ModelMap map) {
		// 初始化右边的公告列表
		PageSplit page = new PageSplit();
		// 设置参数
		page.setParameters(request);

		List<Announcement> news = this.announcementBusiness.adminGetAnnouList(
				page.getCurrentPage(), page.getPageSize());
		map.put("news", news);
		page.setResultCount(this.announcementBusiness.getCount());

		map.put("pageSplit", page.split("news.do?c=newslist"));
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		request.setAttribute("module", "news_list");

		// 页面左边边栏操作栏目
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, null);
		map.put("leftBar", leftBar);

		return "panel/panel";
	}

	@RequestMapping(params = "c=edit")
	public String editNews(HttpServletRequest request, ModelMap map) {
		String idStr = request.getParameter("id");
		if (idStr != null && !"".equals(idStr)) {
			int id = Integer.parseInt(idStr);
			// 初始化右边的编辑窗口
			Announcement news = this.announcementBusiness
					.getAnnouncementById(id);
			map.put("news", news);
		}

		// 页面左边边栏操作栏目
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, null);
		map.put("leftBar", leftBar);

		request.setAttribute("module", "news_edit");
		return "panel/panel";
	}

	/**
	 * 保存和更新用户 必须要求有浏览权限
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=save")
	public String saveAnnouncement(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		// 取得页面传过来的ID
		String idString = request.getParameter("id");
		int id = 0;
		if (idString != null && !"".equals(idString) && !"0".equals(idString)) {
			id = Integer.parseInt(idString);
		}

		UserTable user = (UserTable) request.getSession().getAttribute("user");
		modelMap.put("url", "news.do?c=newslist");
		modelMap.put("time", "3");
		if (user.getRole() == 1) {
			// 初始化该公告
			Announcement a = null;
			a = new Announcement();
			a.setId(id);
			String title = request.getParameter("title").trim();
			String content = request.getParameter("content").trim();
			if (title == null || "".endsWith(title)) {
				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, null);
				modelMap.put("leftBar", leftBar);
				modelMap.put("module", "news_edit");
				a.setContent(content);
				modelMap.put("news", a);
				modelMap.put("error", "请输入标题！");
				return "panel/panel";
			}
			a.setTitle(request.getParameter("title"));
			if (content == null || "".equals(content)) {
				// 页面左边边栏操作栏目
				String leftBar = BuildLeftBar.build(user,
						FinalDatas.xmlFileBasicLocation, null);
				modelMap.put("leftBar", leftBar);
				modelMap.put("module", "news_edit");
				a.setTitle(title);
				modelMap.put("news", a);
				modelMap.put("error", "请输入内容！");
				return "panel/panel";
			}
			a.setContent(request.getParameter("content"));
			a.setPublishTime(new Date());

			// 如果有id 则更新
			if (id > 0) {
				boolean flag = this.announcementBusiness.doUpdate(a);
				if (flag) {
					modelMap.put("message", "更新成功");
				} else {
					modelMap.put("message", "对不起，更新失败");
				}
			} else {
				// 没有则添加
				boolean flag = this.announcementBusiness.doInsert(a);
				if (flag) {
					modelMap.put("message", "发布成功");
				} else {
					modelMap.put("message", "对不起，发布失败！");
				}
			}

		} else {
			modelMap.put("message", "对不起，你没有权限，发布失败");
		}
		return "global/notify";
	}
}
