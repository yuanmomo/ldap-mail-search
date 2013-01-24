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
<div class="contentcontainer" style="width:750px;">
            <div class="headings">
                <h2>选题查看</h2>
            </div>
            <div class="contentbox">
                <h3><c:out value="${course.course_Name}" /></h3><br />
                <p>标签 :<br /><c:out value="${course.tag}" escapeXml="false"/></p>
                <p style="scroll-y:auto;">描述 :<br /><c:out value="${course.course_Description}" escapeXml="false"/></p>
                <p>指导教师 :<br /><c:out value="${teaName}" escapeXml="false"/></p>
                <p>发布时间 :<br /> <fmt:formatDate value="${course.publishDate}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
                <c:if test="${course.student!=0 && (user.role==1 || user.role==2)}">
	                <p>被选情况 :<br /></p><br/>
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
               	</c:if>
                <div style="clear: both;"></div>
            </div>
            
        </div>
</body>
</html>