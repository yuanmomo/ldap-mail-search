<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>资料管理</h2>
    </div>
    <div class="contentbox">
        <form action="user.do?c=saveStu"  onsubmit="return checkStuSave();"  method="post">
            <input type="hidden" name="id" value="${stu.userId}"> <br>
            <div style="color: red; font-size: 20px">
                <label><strong><c:out value="${error}"></c:out></strong></label>
            </div>
            <span style="font-size: 20px;">以下选项 <span style="color:red;font-size:27px;font-weight: bold;">*</span> 为必填项</span><br/> 
            <p>
                <label><strong>学号:</strong></label>
                 <input type="text" name="stuNumber" id="stuNumber" value="<c:out value="${stu.stuNumber}" />" class="inputbox" /> <br />
            </p>
            <p>
                <label><strong>姓名:</strong></label>
                <input type="text" name="userName" id="userName" value="<c:out value="${stu.userName}" />" class="inputbox" />
                 <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
                 <br />
            </p>
            <p>
                <label><strong>密码:</strong></label>
                <input type="text" name="password" value="" class="inputbox"> <br>
                <span class="smltxt" style="color: red">(如不修改:请留空)</span>
            </p>
            <p>
                <label><strong>EMAIL:</strong></label>
                <input type="text" name="email" id="email" value="<c:out value="${stu.email}" />" class="inputbox">
                 <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
                 <br>
            </p> 
            <p>
                <label><strong>电话号码:</strong></label>
                <input type="text" name="phone" id="phone" value="<c:out value="${stu.phone}" />" class="inputbox">
                 <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
                 <br>
            </p>
            <p>
                <label><strong>班级:</strong></label>
                <select id="stuClass" name="stuClass" style="width:150px;">
                	<c:forEach items="${classList}" var="classTemp">
                		<c:choose>
                			<c:when test="${classTemp.classId==stu.stuClassId}">
                				<option value="${classTemp.classId}" selected="selected">
                					${classTemp.classNameChi}
                				</option>
                			</c:when>
                			<c:otherwise>
                				<option value="${classTemp.classId}">
                					${classTemp.classNameChi}
                				</option>
                			</c:otherwise>
                		</c:choose>
                	</c:forEach>
                </select>
                <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
                 <span style="width:300px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <input type="submit" class="btn" value="保存">
            </p>
        </form>         
    </div>
</div>
