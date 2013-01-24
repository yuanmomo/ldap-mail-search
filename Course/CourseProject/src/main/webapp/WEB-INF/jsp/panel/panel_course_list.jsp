<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>题库管理</h2>
            </div>
            <div class="contentbox">
                <form name="form" action="course.do?c=deleteGroup" onsubmit="return checkbulk()"  method="post">
            	<table width="100%">
                    	<tr>
                            <td width="5%">序号</td>
                            <td width="20%">题目</td>
                            <td width="45%">描述</td>
                            
                            <!-- 管理员显示 -->
							<c:if test="${user.role==1}">
								<td width="10%">指导老师</td>
								<td width="10%">被选情况</td>
								<td width="10%">操作</td>
								<!-- <td width="10%"><input type="checkbox" id="checkboxall" value="" name=""></td> -->
							</c:if>
							<!-- 教师显示  -->
							<c:if test="${user.role==2}">
								<td width="10%">被选情况</td>
								<td width="10%">操作</td>
								<!-- <td width="10%"><input type="checkbox" id="checkboxall" value="" name=""></td> -->
							</c:if>
							<!--  学生显示 -->
							<c:if test="${user.role==3}">
								<td width="10%">指导老师</td>
								<td width="10%">选入</td>
							</c:if>
                        </tr>
                        <c:forEach var="vo" items="${courses}" varStatus="voStatu">
                    	<tr class="alt">
                    		<td><c:out value="${voStatu.count}"></c:out></td>
                          	<td><a onclick="viewCourse(<c:out value="${vo.course_Id}"/>)" href="javascript:void(0);"><c:out value="${vo.course_Name}"/></a></td>
                            <td><c:out escapeXml="false" value="${vo.course_Description}"/></td>
							
							<%--管理员--%>
							<c:if test="${user.role==1}">
								<td><c:out value="${vo.userName}"/></td>
                            	<td><c:if test="${vo.student!=0}">已被选</c:if></td>
								<td>
                                    <a title="" href="course.do?c=edit&id=<c:out value="${vo.course_Id}"/>">
                                    	<img alt="Edit" src="img/icons/icon_edit.png" title="编辑">
                                    </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a onclick="if(confirm('确定删除?')){window.location.href='course.do?c=delete&id=<c:out value="${vo.course_Id}"/>'}" href="javascript:void(null);">
                                    	<img alt="Unapprove" src="img/icons/icon_unapprove.png" title="删除">
                                   	</a>
                            	</td>
								<!-- <td><input type="checkbox" name="checkall[]" value="<c:out value="${vo.course_Id}"/>"></td> -->
							</c:if>
							
							<%--//教师--%>
							<c:if test="${user.role==2}">
								<td width=""><c:if test="${vo.student!=0}">已被选</c:if></td>
								<td>
                                    <a title="" href="course.do?c=edit&id=<c:out value="${vo.course_Id}"/>">
                                    	<img alt="Edit" src="img/icons/icon_edit.png" title="编辑">
                                    </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a onclick="if(confirm('确定删除?')){window.location.href='course.do?c=delete&id=<c:out value="${vo.course_Id}"/>'}" href="javascript:void(null);">
                                    	<img alt="Unapprove" src="img/icons/icon_unapprove.png" title="删除">
                                   	</a>
                            	</td>
								<%-- <td><input type="checkbox" name="checkall[]" value="<c:out value="${vo.course_Id}"/>"></td> --%>
							</c:if>
							
							<%--//学生--%>
							<c:if test="${user.role==3}">
								<td><a onclick="viewTea(<c:out value="${vo.userId}"/>)" href="javascript:void(0);"><c:out value="${vo.userName}"/></a></td>
								<td>
									<c:choose>
										<c:when test="${user.stuCourseId==vo.course_Id}">
											<div id="img<c:out value="${vo.course_Id}"></c:out>">
												<a href="javascript:void(0)" style="color:red;font-size:20px" onclick="deSelectOption(<c:out value="${vo.course_Id}"/>)">退选</a>
											</div>
										</c:when>
										<c:otherwise>
											<div id="img<c:out value="${vo.course_Id}"></c:out>">
												<c:if test="${vo.student==0 }">
													<input type="button" value="选入" name="selectItem"  onclick="selectOption(<c:out value="${vo.course_Id}"/>)"/>
												</c:if>
											</div>
										</c:otherwise>
									</c:choose>
								</td>
							</c:if>
							
                        </tr>
                        </c:forEach>
                </table>
                <div class="extrabottom">
					<c:if test="${user.role!=3}">
                	<ul>
                            <li><a href="course.do?c=edit"><img border="0" alt="Add" src="img/icons/icon_add.png"> 添加</a></li>
                    </ul>
                    </c:if>
                    <div class="bulkactions">
                 		${pageSplit}
                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<%-- <select id="bulkaction">
                        	<option value="0">批量操作</option>
                        	<option value="1">删除</option>
                        </select>
                        <input type="submit" class="btn" value="确认">
                         --%>
                    </div>
                </div>
                </form>
                <div style="clear: both;"></div>
            </div>
            
        </div>