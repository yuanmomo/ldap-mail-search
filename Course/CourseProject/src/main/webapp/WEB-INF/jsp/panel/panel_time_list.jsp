<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>时间管理</h2>
            </div>
            <div class="contentbox">
                <form name="form" action="" onsubmit=""  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                    		<td width="10%">设置序号</td>
                            <td width="20%">设置名称</td>
                            <td width="20%">开始时间</td>
                            <td width="20%">截止时间</td>
                            <td width="10">启用</td>                            	
                           	<td width="20%">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${times}" varStatus="voStatu">
                    	<tr class="alt">
                    	<td width="5%"><c:out value="${voStatu.count}"></c:out></td>
                            <td><c:out value="${vo.timeName}"></c:out></td>
                            <td>
                            	<fmt:formatDate value="${vo.timeStart}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                            <td>
                            	<fmt:formatDate value="${vo.timeEnd}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                            <td>
                					<c:choose>
                						<c:when test="${vo.isUsed==1}">
                								是
                						</c:when>
                						<c:otherwise>
                								否
                						</c:otherwise>
                					</c:choose>
                            </td>
							<td>
								<a title=""href="system.do?c=editTime&id=<c:out value="${vo.timeId}"/>"><img alt="Edit" src="img/icons/icon_edit.png">
								</a>
							</td>
						</tr>
                        </c:forEach>
                    </tbody>
                </table>
                </form>
            </div>
        </div>