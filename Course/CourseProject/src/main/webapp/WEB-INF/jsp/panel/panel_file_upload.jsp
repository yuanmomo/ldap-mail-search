<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>文件上传</h2>
    </div>
    <div class="contentbox">
        <form action="file.do?c=upload"  onsubmit="return checkFileSave();"  method="post" enctype="multipart/form-data">
            <div style="color: red; font-size: 20px">
                <label><strong><c:out value="${error}"></c:out></strong></label>
            </div>
            <span style="font-size: 20px;">以下选项 <span style="color:red;font-size:27px;font-weight: bold;">*</span> 为必填项</span><br/> 
            <p>
                <label><strong>请选择上传文件：</strong></label>
                 <input type="file" name="file" id="file" class="inputbox" /> 
                  <span style="color:red;font-size: 27px;font-weight: bold;">*</span> 
                 <br />
            </p>
            <p>
                <label><strong>查看成员:</strong></label>
                <input type="radio" name="userRole" id="userRole" value="0"  checked="checked"/> 所有成员
                <input type="radio" name="userRole" id="userRole" value="2" /> 教师
                <input type="radio" name="userRole" id="userRole" value="3" /> 学生
                 <span style="color:red;font-size: 27px;font-weight: bold;">*</span> 
            </p>
            <input type="submit" class="btn" value="上传">
        </form>         
    </div>
</div>
 