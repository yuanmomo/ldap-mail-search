<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>学生管理</h2>
            </div>
            <div class="contentbox">
                <form name="form" action="" onsubmit=""  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                    		<td width="3%">序号</td>
                            <td width="10%">学号</td>
                            <td width="7%">姓名</td>
                            <td width="10%">
	                            <c:if test="${user.role==1}">
	                            	班级
	                            </c:if>
	                            <c:if test="${user.role==2}">
	                            	专业
	                            </c:if>
	                        </td>
                            <td width="7%">电话</td>
                            <td width="13%">Email</td>
                            <td width="20%">已选课题</td>
                            <c:if test="${user.role==2}">
	                            <td width="15%">题目来源</td>
	                            <td width="15%">题目类型</td>
                            </c:if>
                            <c:if test="${user.role==1}">
                            	<td width="10%">指导教师</td>
                            	<td width="10%">操作</td>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${students}" varStatus="voStatu">
                    	<tr class="alt">
                    		<td width="5%"><c:out value="${voStatu.count}"></c:out></td>
                            <td><a onclick="viewStu(<c:out value="${vo.userId}"/>)" href="javascript:void(0);"><c:out value="${vo.stuNumber}"></c:out></a></td>
                            <td><a onclick="viewStu(<c:out value="${vo.userId}"/>)" href="javascript:void(0);"><c:out value="${vo.userName}"></c:out></a></td>
                            <td>
                            	<c:if test="${user.role==1}">
                            		<c:out escapeXml="false" value="${vo.classNameChi}"/>
                            	</c:if>
                            	<c:if test="${user.role==2}">
                            		<c:out escapeXml="false" value="${vo.majorName}"/>
                            	</c:if>
                           	</td>
                            <td><c:out escapeXml="false" value="${vo.phone}"/></td>
                            <td><c:out escapeXml="false" value="${vo.email}"/></td>
                            <td>
                            	<a title="" href="course.do?c=edit&id=<c:out value="${vo.stuCourseId}"/>&sourceOption=stuList">
									<c:out value="${vo.courseName}"></c:out>
								</a>
                            </td>
                            <c:if test="${user.role==2}">
                            	<td>
                            	<input type="hidden" value="${vo.stuCourseId}" name="courseId[]" />
                           		<select id="courseSource<c:out value="${vo.stuCourseId}" />" style="width:140px;">
				                	<c:forEach items="${sourceList}" var="source">
				                		<c:choose>
				                			<c:when test="${source.id==vo.stuCourseSourceId}">
				                				<option value="${source.id}" selected="selected">${source.sourceName}</option>
				                			</c:when>
				                			<c:otherwise>
				                				<option value="${source.id}">${source.sourceName}</option>
				                			</c:otherwise>
				                		</c:choose>
				                	</c:forEach>
				                </select>
                            	</td>
                            	<td>
									<select id="courseType<c:out value="${vo.stuCourseId}" />" style="width:140px;" >
					                	<c:forEach items="${typeList}" var="type">
					                		<c:choose>
					                			<c:when test="${type.id==vo.stuCourseTypeId}">
					                				<option value="${type.id}" selected="selected">${type.typeName}</option>
					                			</c:when>
					                			<c:otherwise>
					                				<option value="${type.id}">${type.typeName}</option>
					                			</c:otherwise>
					                		</c:choose>
					                	</c:forEach>
					                </select>
                            	</td>
                            </c:if>
                           	<c:if test="${user.role==1}">
                           		<td>
									<c:out value="${vo.teacherName}"></c:out>
                         		  	</td>
                               	<td>
                                    <a title="" href="user.do?c=stuEdit&id=<c:out value="${vo.userId}"/>"><img alt="Edit" src="img/icons/icon_edit.png"></a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a onclick="if(confirm('确定删除?')){window.location.href='user.do?c=delete&id=<c:out value="${vo.userId}"/>'}" href="javascript:void(null);">
                                    <img alt="Unapprove" src="img/icons/icon_unapprove.png"></a>
                           		</td>
                         	</c:if>
                        </tr>
                        <input type="hidden" name="id[]" value="<c:out value="${vo.userId}"/>" />
                        </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                	<ul>
                		<c:if test="${user.role==1}">
                           <li><a href="?c=stuEdit"><img border="0" alt="Add" src="img/icons/icon_add.png"> 添加</a></li>
                    	</c:if>
						<c:if test="${user.role==2}">
                           <li><a href="course.do?c=export"><img border="0" alt="Add" src="img/icons/icon_add.png"> <span style="font-size: 20px; font-weight: bold;color:blue">导出选题学生清单</span></a></li>
                           <li><a href="javascript:void(null);" onclick="buildCourseSourceAndTypeData()"><span style="font-size: 20px; font-weight: bold;color:red">保存修改</span></a></li>
                    	</c:if>
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
                 <span style="font-size: 15px"> 说明：①表中"题目来源"为："教师科研课题"（A）、"教师拟定"(B)、"学生自拟"(C)、"其它"(D)；<br />
                  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  	②表中"题目类型"为："理论研究"（A）、"应用研究"(B)、"技术开发"(C)、"其它"(D)；</span>
                </form>
            </div>
        </div>