<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>题库导入</h2>
    </div>
    <div class="contentbox">
        <form action="course.do?c=importfile" enctype="multipart/form-data"  method="post">
            <p>
                <label><strong>上传文件:</strong></label>
                <input type="file" name="file" id="file" class="inputbox" /> <br />
                <span class="smltxt">(请务必在Excel模板<span><a style="color:green" href="files/<c:if test="${user.role==1}">admin/</c:if><c:if test="${user.role==2}">teacher/</c:if>course_template.xls"><b> (点击下载) </b></a></span>的基础上修改)</span>
            </p> 
            <input type="submit" class="btn" value="开始导入">
        </form>         
    </div>
</div>
