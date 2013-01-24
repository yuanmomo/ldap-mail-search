package net.yuanmomo.cdut.course.web.controller;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yuanmomo.cdut.course.web.controller.panel.BuildLeftBar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.yhb.dao.IFileTableDAO;
import org.yhb.dao.Business.IFileTableDAOBusiness;
import org.yhb.util.BeanFactory;
import org.yhb.util.FinalDatas;
import org.yhb.util.PageSplit;
import org.yhb.vo.FileTable;
import org.yhb.vo.UserTable;

@Controller
@RequestMapping("/file.do")
public class FileController {
	@RequestMapping(params="c=list")
	public String viewListFiles(HttpServletRequest req,
			HttpServletResponse resp,ModelMap modelMap){
		//得到当前用户
		UserTable user=(UserTable)req.getSession().getAttribute("user");
		
		IFileTableDAOBusiness dao=(IFileTableDAOBusiness)BeanFactory.
			getBean("IFileTableDAOBusiness");
		
		// 处理分页情况
		PageSplit page=(PageSplit)BeanFactory.getBean("pageSplit");
		page.setParameters(req);
		
		List<FileTable> files=null;
		if(user.getRole()==1){
			files=dao.getAllFiles(page.getCurrentPage(),page.getPageSize());
			page.setResultCount(dao.getAllFilesCount());
			//处理什么角色查看什么文件
			if(files!=null && files.size()>0){
				Iterator<FileTable> ite=files.iterator();
				FileTable temp=null;
				while(ite.hasNext()){
					temp=ite.next();
					if(temp.getUserRole()==0){
						temp.setUserRoleName("教师和学生");
					}
					if(temp.getUserRole()==2){
						temp.setUserRoleName("教师");
					}
					if(temp.getUserRole()==3){
						temp.setUserRoleName("学生");
					}
				}
			}
			
		}else{
			files=dao.getFilesByRole(page.getCurrentPage(),page.getPageSize(), user.getRole());
			page.setResultCount(dao.getFilesCountByRole(user.getRole()));
		}
		
		
		
		modelMap.put("pageSplit",page.split("file.do?c=list"));
		modelMap.put("files",files);
		req.setAttribute("module", "file_list");
		
		// 页面左边边栏操作栏目
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "file");
		modelMap.put("leftBar", leftBar);
		
		return "panel/panel";
	}
	/**
	 * 文件删除
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "c=delete")
	public String deleteFile(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		UserTable user = (UserTable) request.getSession().getAttribute("user");
		modelMap.put("url", "file.do?c=list");
		modelMap.put("time", "3");
		if (user.getRole() == 1) {// 是管理员
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")) {
				int id = Integer.parseInt(request.getParameter("id"));
				IFileTableDAOBusiness dao=(IFileTableDAOBusiness)BeanFactory.
					getBean("IFileTableDAOBusiness");
				FileTable file=dao.getFileByFileId(id);
				File f=new File(request.getServletContext().getRealPath("/")+"uploadFiles"+File.separator+file.getFileName());
				if(f.exists()){
					f.delete();
				}
				boolean flag = dao.doDelete(id);
				if (flag) {
					modelMap.put("message", "删除成功");
				} else {
					modelMap.put("message", "对不起，删除失败");
				}
			}
		} else {
			modelMap.put("message", "对不起，你没有权限，删除失败");
			modelMap.put("url", "file.do?c=list");
		}
		return "global/notify";
	}
	@RequestMapping(params="c=add")
	public String fileUploadPage(HttpServletRequest req,
			HttpServletResponse resp,ModelMap modelMap){
		//得到当前用户
		UserTable user=(UserTable)req.getSession().getAttribute("user");
		
		// 页面左边边栏操作栏目
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "file");
		modelMap.put("leftBar", leftBar);
		if(user.getRole()==1){
			req.setAttribute("module", "file_upload");
			return "panel/panel";
		}else{
			modelMap.put("message", "对不起，你没有权限，查看失败");
			modelMap.put("time",3);
			modelMap.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
	@RequestMapping(params="c=upload")
	public String saveFile(HttpServletRequest request,
			HttpServletResponse resp,ModelMap modelMap){
		//得到当前用户
		UserTable user=(UserTable)request.getSession().getAttribute("user");
		// 页面左边边栏操作栏目
		String leftBar = BuildLeftBar.build(user,
				FinalDatas.xmlFileBasicLocation, "file");
		modelMap.put("leftBar", leftBar);
	
		///验证数据的合法性
		if(user.getRole()==1){
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				// 专业
				MultipartFile file = multipartRequest.getFile("file");
				modelMap.put("time",3);
				modelMap.put("url", "file.do?c=list");
				IFileTableDAOBusiness fileDao=(IFileTableDAOBusiness)BeanFactory.getBean("IFileTableDAOBusiness");
				
				if(!file.isEmpty()){//文件不为空
					String fileName=file.getOriginalFilename();
					if(fileName==null || "".equals(fileName)){
						modelMap.put("error", "空文件，请选择文件");
						request.setAttribute("module", "file_upload");
						return "panel/panel";
					}
					if(file.getSize()<=10240000){
						FileTable fileVO=new FileTable();
						fileVO.setFileName(fileName);
						//判断文件名是否已经存在
						FileTable exist=fileDao.getFileByFileName(fileName);
						if(exist!=null){
							modelMap.put("error", "该文件名已经存在，请修改文件名称");
							request.setAttribute("module", "file_upload");
							return "panel/panel";
						}
						String userRole=multipartRequest.getParameter("userRole");
						if(userRole==null || "".equals(userRole)|| !userRole.matches("\\d+")){
							userRole="0";
						}
						fileVO.setUserRole(Integer.parseInt(userRole));
						fileVO.setUploadIp(multipartRequest.getRemoteAddr());
						fileVO.setUploadTime(new Date());
						String filePath="uploadfiles"+File.separator+fileName;
						fileVO.setFilePath(filePath);
						//插入数据库传信息
						fileDao.doInsert(fileVO);
						FileCopyUtils.copy(file.getBytes(), new File(request.getServletContext().getRealPath("/")+filePath));
						modelMap.put("message", "上传成功！");
					}else{
						modelMap.put("message", "对不起，上传超过10MB，不能上传");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				modelMap.put("message", "对不起，上传失败！请重新上传"+e.toString());
				modelMap.put("time",3);
				modelMap.put("url", "file.do?c=list");
			}
			return "global/notify";
		}else{
			modelMap.put("message", "对不起，你没有权限，查看失败");
			modelMap.put("time",3);
			modelMap.put("url", "news.do?c=newslist");
		}
		return "global/notify";
	}
}
