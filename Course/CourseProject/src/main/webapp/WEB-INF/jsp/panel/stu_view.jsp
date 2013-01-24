<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>毕业设计选题系统</title>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/wysiwyg.css" rel="stylesheet" type="text/css" />
<link href="themes/blue/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
		<div class="contentcontainer" style="width: 750px;">
			<div class="headings">
                <h2>学生查看</h2>
            </div>
            <div class="contentbox">
                <br />
                	<p>
                        <label><strong>学号:</strong></label>
                        <c:out value="${stu.stuNumber}" />
                    </p>
                	<p>
                        <label><strong>姓名:</strong></label>
                        <c:out value="${stu.userName}" />
                    </p>
                    <p>
                        <label><strong>班级:</strong></label>
                        <c:out value="${stu.classNameChi}" />
                    </p>
                    <p>
                        <label><strong>所选课题:</strong></label>
                        <c:out value="${stu.courseName}" />
                    </p>
                    <p>
                        <label><strong>指导老师:</strong></label>
                        <c:out value="${stu.teacherName}" />
                    </p>
                <p>注册时间 :<br />
                	<fmt:formatDate value="${stu.regTime}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
                <p>最后登录时间 :<br />
               		<fmt:formatDate value="${stu.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
                <div style="clear: both;"></div>
            </div>
        </div>
</body>
</html>