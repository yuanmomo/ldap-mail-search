<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>班级管理</h2>
            </div>
            <div class="contentbox">
                <form name="form" action="" onsubmit=""  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
							<td width="10%">序号</td>
                            <td width="20%">文件名称</td>
                            <td width="20%">上传时间</td>
                            <c:if test="${user.role==1}">
								<td width="20%">所属角色</td>
								<td width="10%">下载次数</td>
							</c:if>
                           	<td width="20%">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${files}" varStatus="voStatu">
                    	<tr class="alt">
                    		<td><c:out value="${voStatu.count}"></c:out></td>
                            <td><c:out value="${vo.fileName}"></c:out></td>
                            <td><fmt:formatDate value="${vo.uploadTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                            <c:if test="${user.role==1}">
								<td><c:out value="${vo.userRoleName}"></c:out></td>
								<td><c:out value="${vo.downLoadTimes}"></c:out></td>
								<td>
	                                 <a onclick="if(confirm('确定删除?')){window.location.href='file.do?c=delete&id=<c:out value="${vo.fileId}"/>'}" href="javascript:void(null);">
	                                <img alt="Unapprove" src="img/icons/icon_unapprove.png"></a>
                       			</td>
							</c:if>
							<c:if test="${user.role>1}">
								<td>
									<a href="uploadfiles/download?fileId=<c:out value="${vo.fileId}"/>">下载</a>
                       			</td>
							</c:if>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                    <div class="bulkactions">
                    	${pageSplit}
                    	<%-- 
                    	<select id="bulkaction">
                        	<option value="0">批量操作</option>
                        	<option value="1">删除</option>
                        </select>
                        <input type="submit" class="btn" value="确认">
                         --%>
                    </div>
                </div>
                </form>
            </div>
        </div>