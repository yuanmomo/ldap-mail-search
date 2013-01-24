<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>用户管理</h2>
            </div>
            <div class="contentbox">
                <form name="form" action="" onsubmit=""  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                    		<td width="5%">序号</td>
                            <td width="20%">教师姓名</td>
                            <td width="10%">职称</td>
                             <td width="10%">电话</td>
                            <td width="15%">Email</td>
                            <td width="10%">带生情况</td>
                            <td width="10%">出题情况</td>
                            <td width="10%">操作</td>
                            <%-- <td width="10%"><input type="checkbox" id="checkboxall" value="" name=""></td> --%>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${teachers}" varStatus="voStatu">
                    	<tr class="alt">
                    		<td width="5%"><c:out value="${voStatu.count}"></c:out></td>
                            <td><a onclick="viewTea(<c:out value="${vo.userId}"/>)" href="javascript:void(0);">
                            <c:out value="${vo.userName}"/></a></td>
                            <td><c:out escapeXml="false" value="${vo.position_Name}"/></td>
                            <td><c:out escapeXml="false" value="${vo.phone}"/></td>
                            <td><c:out escapeXml="false" value="${vo.email}"/></td>
                            <td><c:out escapeXml="false" value="${vo.teaCurrentStu}"/>/<c:out escapeXml="false" value="${vo.teaMaxStu}"/></td>
                            <td><c:out value="${vo.currentCoursesCount}" />/<fmt:formatNumber value="${vo.teaMaxStu*1.5}" pattern="#" type="number" /> </td>
                            <td>
                                   <a title="" href="user.do?c=teaEdit&id=<c:out value="${vo.userId}"/>"><img alt="Edit" src="img/icons/icon_edit.png"></a>
                                   &nbsp;&nbsp;&nbsp;&nbsp;
                                   <a onclick="if(confirm('确定删除?')){window.location.href='user.do?c=delete&id=<c:out value="${vo.userId}"/>'}" href="javascript:void(null);">
                                   <img alt="Unapprove" src="img/icons/icon_unapprove.png"></a>
                           </td>
                            <%-- <td><input type="checkbox" name="checkall[]" value="<c:out value="${vo.course_Id}"/>"></td> --%>
                        </tr>
                        <input type="hidden" name="id[]" value="<c:out value="${vo.userId}"/>" />
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