package net.yuanmomo.cdut.course.web.util;

import javax.servlet.http.HttpServletRequest;

public class PageSplit {
	private int currentPage;//当前页数
	private int pageSize;//每页显示行数
	private int pagesCount;//总页数
	private int resultCount; //结果集的总行数

	public PageSplit() {
		this.currentPage=1;
		this.pageSize=20;
	}
	public PageSplit(int currentPage, int pageSize, int resultCount) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.resultCount = resultCount;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPagesCount() {
		return pagesCount;
	}
	public void setPagesCount(int pagesCount) {
		this.pagesCount = pagesCount;
	}
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public void setParameters(HttpServletRequest req){
		// 得到currentPage,pageSize两个参数
		String currentPageStr = req.getParameter("currentPage");
		String pageSizeStr = req.getParameter("pageSize");
		if (currentPageStr != null & !"".equals(currentPageStr)) {
			try {
				currentPage = Integer.parseInt(currentPageStr);
			} catch (Exception e) {
				currentPage = 1;
			}
		}
		if (pageSizeStr != null & !"".equals(pageSizeStr)) {
			try {
				pageSize = Integer.parseInt(pageSizeStr);
			} catch (Exception e) {
				pageSize = 10;
			}
		}
	}
	
	public String split(String actionURL){
		StringBuffer buffer=new StringBuffer();
		if(this.resultCount==0){
			this.pagesCount=1;
		}else{
			this.pagesCount = ((resultCount % pageSize) == 0) ? (resultCount / pageSize)
				: (resultCount / pageSize + 1);
		}
		//首页按钮
		buffer.append("<input type=\"button\" onClick=\"go(1)\" value=\"首页\"");
		if(currentPage==1){
			buffer.append(" disabled ");
		}
		buffer.append("/>");
		//上一页按钮
		buffer.append("<input type=\"button\" onClick=\"go(").append(currentPage-1).append(")\" value=\"上一页\" ");
		if(currentPage==1){
			buffer.append(" disabled ");
		}
		buffer.append("/>");
		//下一页按钮
		buffer.append("<input type=\"button\" onClick=\"go(").append(currentPage+1).append(")\" value=\"下一页\" ");
		if(currentPage==pagesCount){
			buffer.append(" disabled ");
		}
		buffer.append("/>");
		//尾页按钮
		buffer.append("<input type=\"button\" onClick=\"go(").append(pagesCount).append(")\" value=\"尾页\"");
		if(currentPage==pagesCount){
			buffer.append(" disabled ");
		}
		buffer.append("/>");

		//两个hidden标签，保存了currentPage和pageSize两个值
		buffer.append("<input type=\"hidden\" value=\"").append(currentPage).append("\" name=\"currentPage\" />");
		buffer.append("<input type=\"hidden\" value=\"").append(pageSize).append("\" name=\"pageSize\" />");
		
		
		//初始化该Select
		buffer.append("一共有").append(pagesCount).append("页，现在显示第").append(currentPage).append("页,跳转至");
		buffer.append("<select name=\"page\" onchange=\"go(this.value)\">");
		for(int i=1;i<=pagesCount;i++)
		{
			buffer.append("<option value=\"").append(i).append("\"");
			if(currentPage==i){
				buffer.append(" selected ");
			}
			buffer.append(">");
			buffer.append(i).append("</option>");
		}	
		buffer.append("</select>页");
		
		
		//初始化javascript
		buffer.append("<script type=\"text/javascript\">" +
							"function go(n)	{" +
								"document.getElementsByName(\"currentPage\")[0].value=n;" +
								"document.form.action=\"").append(actionURL).append("\";" +
								"document.form.submit();" +
							"}" +
						"</script>");
		return buffer.toString();
	}
	
}