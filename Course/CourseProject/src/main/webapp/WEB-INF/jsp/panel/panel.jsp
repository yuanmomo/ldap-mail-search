<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="panel_head.jsp" %>
<!-- Right Side/Main Content Start -->
    <div id="rightside">
    	<c:if test="${user.role==3}">
    		<span style="font-size: 25px; color: red">本年度共有题目${totalCourses}个，目前还剩余${leftCourses}个未选</span>
    	</c:if>
        <div id="noticesuccess" class="status success" style="display:none;position:absolute;
										top:350px;left:500px;width:300px;height:150xp;">
            <p class="closestatus"><a href="javascript:void(0)" onclick="$('#noticesuccess').hide();" title="Close">x</a></p>
        	<p id="noticesuccesscontent" class="content" style="color: green"></p>
        </div>
        
        <div id="noticeerror" class="status error" style="display:none;position:absolute;
										top:350px;left:500px;width:300px;height:150xp;">
        	<p class="closestatus"><a href="javascript:void(0)" onclick="$('#noticeerror').hide();" title="Close">x</a></p>
        	<p id="noticeerrorcontent" class="content" style="color: red"></p>
        </div>
        <c:if test="${user.role==2}">
        	<div style="font-size: 18px; font-weight: bold;">
      	 		<c:out value="${user.userName}" />老师，本年度您指导
        		<span style="font-weight: bold; color:red;font-size: 20px;" >
        			<c:out value="${user.teaMaxStu}"/>
        		</span>名毕业生，应至少出
        		<span style="font-weight: bold; color:red;font-size: 20px;" >
        			<fmt:formatNumber value="${user.teaMaxStu*1.5}" pattern="#" type="number"/> 
        		</span>
        		个课题，截止今天，您已完成
        		<span style="font-weight: bold; color:red;font-size: 20px;" >
				<c:if test="${teaCurrentCourses>0}">
        				<c:out value="${teaCurrentCourses}"/>
				</c:if>
				<c:if test="${teaCurrentCourses==0}">
        				0
				</c:if>
        		</span>
        		个立题。。<br />
        		<c:choose>
	        		<c:when test="${user.teaMaxStu*1.5>teaCurrentCourses&&user.teaMaxStu*1.5-0.5>teaCurrentCourses}">
	        			<span style="font-weight: bold; color:red;font-size: 20px;" >
	        				${teacherSpecialNotice}
	        			</span>
	        		</c:when>
	        		<c:otherwise>
						<span style="font-weight: bold;font-size: 20px;color:red;" >
	        				您的立题数量已经达到要求，谢谢！
	        			</span>	
	        		</c:otherwise>
        		</c:choose>
        	</div>
        </c:if>
        <% 
            String relativeUrlPath="panel_"+(String)request.getAttribute("module")+".jsp";
            pageContext.include(relativeUrlPath); 
        %>
        <div id="footer">
            &copy; Copyright 2011 信息科学与技术学院<br />powered by <a href="http://3momo.net">yuanmomo</a>  
        </div> 
          
    </div>
    <!-- Right Side/Main Content End -->
    <!--[if IE 6]>
    <style>#leftside{overflow:hidden;}</style>
    <![endif]--> 
        <!-- Left Dark Bar Start -->
    <div id="leftside">
    	<div class="user">
        	<img src="img/avatar.png" width="44" height="44" class="hoverimg" alt="Avatar" />
            <p>您好 :</p>
            <p class="username"><c:out value="${user.userName}"/></p>
            <p class="userbtn"><a href="user.do?c=<c:if test="${user.role==1 || user.role==2}">teaEdit</c:if><c:if test="${user.role==3}">stuEdit</c:if>&id=${user.userId}" title="">我的资料</a></p>
            <p class="userbtn"><a href="login.do?exit" title="">退出登录</a></p>
        </div>
        <%--
        <div class="notifications">
        	<p class="notifycount"><a href="" title="" class="notifypop"><c:out value=""/></a></p>
            <p><a href="" title="" class="notifypop">条新通知</a></p>
            <p class="smltxt"><a href="" title="" class="notifypop">(点击查看)</a></p>
        </div>
         --%>
        <!--左边操作边栏${leftbar} -->
        ${leftBar}
    </div>
    <!-- Left Dark Bar End --> 
    
    <!-- Notifications Box/Pop-Up Start --> 
    <div id="notificationsbox">
        <h4>通知</h4>
        <ul>
            <c:if test="">
                米有通知哇
            </c:if>
            <c:forEach var="vow" items="">
                <li>
                    <a href="#${}" title=""><img src="img/icons/icon_square_close.png" alt="Close" class="closenot" /></a>
                    <h5><a href="#${}" title=""></a></h5>
                    <p></p>
                </li>             
            </c:forEach>
        </ul>
    </div>
<%@include file="panel_foot.jsp" %>