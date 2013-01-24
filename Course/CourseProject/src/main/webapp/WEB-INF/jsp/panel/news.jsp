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
                <h2>${news.title}</h2>
            </div>
            <div class="contentbox">
                <p>${news.content}</p>
                <p>发布于:  <fmt:formatDate value="${news.publishTime}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
                <br style="clear: both"/><br />
            </div>

        </div>
    </body>
</html>