<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>用户导入</h2>
    </div>
    <div class="contentbox">
        <form action="user.do?c=teaImportFile" enctype="multipart/form-data"    method="post">
            <p style="color:red; font-size: 20px"><c:out value="${error}"></c:out></p>	
            <p>
                <label><strong>初始密码:</strong></label>
                <input type="password" id="password" name="password" />
                <span class="smltxt">(所有导入的用户的默认密码)</span>
            </p>
            <p>
                <label><strong>上传文件:</strong></label>
                <input type="file" name="file" id="file" class="inputbox" /> <br />
                <span class="smltxt">(请务必在Excel模板<span><a style="color:green" href="files/admin/teacher_template.xls"><b> (点击下载) </b></a></span>的基础上修改)</span>
            </p> 
            <input type="submit" class="btn" value="开始导入">
        </form>         
    </div>
</div>
