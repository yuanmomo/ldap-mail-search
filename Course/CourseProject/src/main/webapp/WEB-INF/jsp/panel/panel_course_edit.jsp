<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>题目管理</h2>
            </div>
            <div class="contentbox">
            	<span style="color:red; font-size: 25px;"><c:out value="${error}"></c:out></span>
                <form action="course.do?c=save"  onsubmit="return checkCourse();"  method="post">
                        <input type="hidden" name="id" value="${course.course_Id}"> <br>
 						<c:if test="${user.role==2}">
						 <input type="hidden" name="teacherId" id="teacherId" value="${course.userId}"> <br>
						</c:if>
                       <span style="font-size: 20px;">以下选项 <span style="color:red;font-size:27px;font-weight: bold;">*</span> 为必填项</span><br/>  
            	    <p>
                        <label for="textfield"><strong>课题名称:</strong></label>
                        <input type="text" name="title" value="<c:out value="${course.course_Name}" />" class="inputbox" id="title"> 
                        <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
                        <br>
                        <span class="smltxt">(比如:基于服务的数据集成共享平台)</span>
                    </p>
            	    <p style="width:330px">
                        <label for="textfield"><strong>课题描述:&nbsp;&nbsp;&nbsp;<span style="color:red;font-size: 27px;font-weight: bold;">*</span></strong></label>
                       <textarea cols="75" rows="10" name="desp" id="desp"><c:out value="${course.course_Description}" /></textarea>
                    	
                    </p>
                    
                    <c:if test="${user.role==1}">
	                    <p>
	                        <label for="smallbox"><strong>指导老师:</strong></label>
	                      	<c:if test="${user.role==1}">
	                        	<select name="teacherId" style="width: 200px;">
									<option value="0" selected="selected">请选择指导教师</option> 
									<c:forEach var="vo" items="${teacherList}">
										<c:choose>
				                			<c:when test="${course.userId==vo.userId}">
				                				<option value="${vo.userId}" selected="selected">${vo.userName}</option>
				                			</c:when>
				                			<c:otherwise>
				                				<option value="${vo.userId}">${vo.userName}</option>
				                			</c:otherwise>
				                		</c:choose>
									</c:forEach>                  
	                        	</select>
	                        </c:if>
	                        <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
	                        <br/>
	                    </p>
                    </c:if>
                    
					<input type="hidden" name="sourceOption" id="sourceOption" value="<c:out value="${sourceOption}"/>" />
                    <input type="submit" class="btn" value="保存">
                </form>         
            </div>
</div>
