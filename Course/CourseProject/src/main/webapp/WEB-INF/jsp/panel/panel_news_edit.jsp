<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>公告管理</h2>
            </div>
            <div class="contentbox">
                <form action="news.do?c=save"  onsubmit="return checktrunk();"  method="post">
                        <input type="hidden" name="id" value="${news.id}"> <br>
					<span style="color:red; font-size: 20px;"><c:out value="${error}"></c:out></span>
            	    <p>
                        <label for="textfield"><strong>标题:</strong></label>
                        <input type="text" name="title" id="title" value="<c:out value="${news.title}" />" class="inputbox" id="textfield"> <br>
                        
                    </p>
            	    <p>
                        <label for="textfield"><strong>内容:</strong></label>
                       <textarea cols="75" rows="10" name="content" id="context"><c:out value="${news.content}" /></textarea>
                    </p>
                    <input type="submit" class="btn" value="保存">
                </form>         
            </div>
</div>