<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>专业管理</h2>
            </div>
            <div class="contentbox">
                <form name="form" action="" onsubmit=""  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                    		<td width="5%">序号</td>
                            <td width="10%">专业号码</td>
                            <td width="10%">专业名称</td>
                           	<td width="10%">操作</td>
                            <%--<th width="10%"><input type="checkbox" id="checkboxall" value="" name=""></th> --%>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${majors}" varStatus="voStatu">
                    	<tr class="alt">
                    	<td width="5%"><c:out value="${voStatu.count}"></c:out></td>
                            <td><c:out value="${vo.majorNumber}"></c:out></td>
                            <td><c:out value="${vo.majorName}"></c:out></td>
                               	<td>
                                    <a title="" href="major.do?c=add&id=<c:out value="${vo.majorId}"/>"><img alt="Edit" src="img/icons/icon_edit.png"></a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a onclick="if(confirm('确定删除?')){window.location.href='major.do?c=delete&id=<c:out value="${vo.majorId}"/>'}" href="javascript:void(null);">
                                    <img alt="Unapprove" src="img/icons/icon_unapprove.png"></a>
                           		</td>
                            <%-- <td><input type="checkbox" name="checkall[]" value="<c:out value="${vo.course_Id}"/>"></td>  --%>
                        </tr>
                        <%-- <input type="hidden" name="id[]" value="<c:out value="${vo.userId}"/>" /> --%>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                	<ul>
                           <li><a href="major.do?c=add"><img border="0" alt="Add" src="img/icons/icon_add.png"> 添加</a></li>
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