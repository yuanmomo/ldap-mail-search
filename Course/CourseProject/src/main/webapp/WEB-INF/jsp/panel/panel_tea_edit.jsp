<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>资料管理</h2>
    </div>
    <div class="contentbox">
        <form action="user.do?c=saveTea&filter=false"  onsubmit="return checkTeaSave();"  method="post">
            <input type="hidden" name="id" value="${teacher.userId}"> <br>
           	<div style="color: red; font-size: 20px;">
               		<label><strong><c:out value="${error}"></c:out>&nbsp;</strong></label>
           	</div>
            <input type="submit" class="btn" value="保存"> <br/>
            <span style="font-size: 20px;">以下选项 <span style="color:red;font-size:27px;font-weight: bold;">*</span> 为必填项</span><br/>
            <p style="clare:both">
                <label><strong>姓名:</strong></label>
                <input type="text" name="userName" id="userName" value="<c:out value="${teacher.userName}" />" class="inputbox" />
                <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
                 <br />
            </p> 
            <p>
                <label><strong>密码:</strong></label>
                <input type="password" name="password" value="" class="inputbox"> <br>
                <span class="smltxt" style="font-size: 20px">(如不修改:请留空)</span>
            </p>
            <p>
                <label><strong>EMAIL:</strong></label>
                <input type="text" name="email" id="email" value="<c:out value="${teacher.email}" />" class="inputbox">
                <span style="color:red;font-size: 27px;font-weight: bold;">*</span> <br>
            </p> 
            <p>
                <label><strong>电话号码:</strong></label>
                <input type="text" name="phone" id="phone" value="<c:out value="${teacher.phone}" />" class="inputbox">
                <span style="color:red;font-size: 27px;font-weight: bold;">*</span> <br>
            </p> 
            <c:if test="${user.role!=3}">
            <p>
                <label><strong>职称:</strong></label>
                <select id="teaPosition" name="teaPosition" style="width:150px;">
                	<c:forEach items="${positionList}" var="position">
                		<c:choose>
                			<c:when test="${position.position_Id==teacher.teaPosition}">
                				<option value="${position.position_Id}" selected="selected">
                					${position.position_Name}
                				</option>
                			</c:when>
                			<c:otherwise>
                				<option value="${position.position_Id}">
               						${position.position_Name}
               					</option>
                			</c:otherwise>
                		</c:choose>
                	</c:forEach>
                </select>
                <span class="smltxt"><span style="color:red;font-size: 27px;font-weight: bold;">*</span></span>
            </p>
             <p style="width:450px">
                <label for="textfield"><strong>个人简介:</strong></label>
                <textarea cols="70" rows="5" name="teaDescription" id="teaDescription"><c:out value="${teacher.teaDescription}" /></textarea>
            </p>
            </c:if>
            <c:if test="${user.role==1 && user.userId!=teacher.userId}">
	            <p>
	                <label><strong>当前带生人数:</strong></label>
              	  <span><c:out value="${teacher.teaCurrentStu}"></c:out></span><br>
           		</p>
            	<p>
                	<label><strong>最大带生人数:<c:if test="${user.role==1}"></c:if></strong></label>
                	<input type="text" name="teaMaxStu" id="teaMaxStu" value="<c:out value="${teacher.teaMaxStu}" />" class="inputbox"> <br>
            	</p> 
            </c:if>
        </form>         
    </div>
</div>
