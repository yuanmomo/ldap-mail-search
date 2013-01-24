<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>资料管理</h2>
    </div>
    <div class="contentbox">
        <form action="user.do?c=adminAddTea&filter=false"  onsubmit="return checkTeaSave();"  method="post">
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
        </form>         
    </div>
</div>
