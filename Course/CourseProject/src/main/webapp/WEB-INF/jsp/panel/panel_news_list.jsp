<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
        <!-- Content Box Start -->
        <div class="contentcontainer">
            <div class="headings altheading">
                <h2>系统公告</h2>
            </div>
            <div class="contentbox">
            
            <form name="form" action="" onsubmit=""  method="post" >
                <c:forEach var="vo" items="${news}" varStatus="voStatu">
                    <p style="margin-top: 5px;"><c:out value="${voStatu.count}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="margin-right: 20px;color:green">
                    <fmt:formatDate value="${vo.publishTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                    <a href="javascript:void(0);" onclick="viewNews(${vo.id});">
                    ${vo.title}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <c:if test="${user.role==1}">
                    	<a href="news.do?c=edit&id=${vo.id}">[编辑]</a>  
                    	<a href="javascript:void(0);" onclick="if(confirm('确定删除?')){window.location.href='news.do?c=delete&id=<c:out value="${vo.id}"/>'}">[删除]</a>
                	</c:if>
                </c:forEach>
             	   <div style="clear: both;"><br/><br/><br/><br/><br/>
                	${pageSplit}
              	  </div>
                </form>
            </div>
        </div>
        <!-- Content Box End -->