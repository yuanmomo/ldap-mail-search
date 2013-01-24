<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>职称管理</h2>
            </div>
            <div class="contentbox">
                <form name="form" action="" onsubmit=""  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                    		<td width="10%">序号</td>
                            <td width="20%">职称名称</td>
                            <td width="20%">默认带生人数</td>
                           	<td width="20%">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${positions}" varStatus="voStatu">
                    	<tr class="alt">
                    		<td><c:out value="${voStatu.count}"></c:out></td>
                            <td><c:out value="${vo.position_Name}"></c:out></td>
                            <td><c:out value="${vo.default_Stu_Count}"></c:out></td>
                           	<td>
                                <a title="" href="position.do?c=addPosition&id=<c:out value="${vo.position_Id}"/>"><img alt="Edit" src="img/icons/icon_edit.png"></a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <a onclick="if(confirm('确定删除?')){window.location.href='position.do?c=deletePosition&id=<c:out value="${vo.position_Id}"/>'}" href="javascript:void(null);">
                                <img alt="Unapprove" src="img/icons/icon_unapprove.png"></a>
                       		</td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                	<ul>
                           <li><a href="position.do?c=addPosition"><img border="0" alt="Add" src="img/icons/icon_add.png"> 添加</a></li>
                    </ul>
                    <div class="bulkactions">
                    	${pageSplit}
                    </div>
                </div>
                </form>
            </div>
        </div>