<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
							<td width="5%">序号</td>
                            <td width="10%">班级号码</td>
                            <td width="10%">班级名称</td>
                            <td width="10%">所在专业</td>
                           	<td width="10%">操作</td>
                            <%--<th width="10%"><input type="checkbox" id="checkboxall" value="" name=""></th> --%>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${cs}" varStatus="voStatu">
                    	<tr class="alt">
                    		<td><c:out value="${voStatu.count}"></c:out></td>
                            <td><c:out value="${vo.classNameNumber}"></c:out></td>
                            <td><c:out value="${vo.classNameChi}"></c:out></td>
                            <td><c:out value="${vo.majorName}"></c:out></td>
                               	<td>
                                    <a title="" href="class.do?c=add&id=<c:out value="${vo.classId}"/>"><img alt="Edit" src="img/icons/icon_edit.png"></a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a onclick="if(confirm('确定删除?')){window.location.href='class.do?c=delete&id=<c:out value="${vo.classId}"/>'}" href="javascript:void(null);">
                                    <img alt="Unapprove" src="img/icons/icon_unapprove.png"></a>
                           		</td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                	<ul>
                           <li><a href="class.do?c=add"><img border="0" alt="Add" src="img/icons/icon_add.png"> 添加</a></li>
                    </ul>
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