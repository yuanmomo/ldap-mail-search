<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="now" class="java.util.Date" /> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>毕业设计选题系统</title>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/register.css" rel="stylesheet" type="text/css" />
<link href="themes/blue/styles.css" rel="stylesheet" type="text/css" />
<!--script language="javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" ></script-->
<script language="javascript" src="scripts/jquery.js"></script>
<script language="javascript" src="js/common.js"  ></script>
<script language="javascript" src="js/check.js"  ></script>
</head>
<script language="javascript" >
		/**
		 * 更新登陆页面时间显示
		 * 
		 */
		function updateTime(){
			var date=new Date();
			var timeTag=document.getElementById("sysTime");
			var timeStr;
			var year=date.getFullYear();
			var month=parseInt(date.getMonth())+1;
			var day=date.getDate();
			var hour=date.getHours();
			var minute=(date.getMinutes())<10 ? "0"+date.getMinutes():date.getMinutes();
			var second=(date.getSeconds())<10 ? "0"+date.getSeconds():date.getSeconds();
			timeStr=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
			timeTag.innerText=timeStr;
		}
		setInterval(updateTime,1000);
</script>

<body onload="updateTime()">
	<div style="text-align:center; margin-top: 70px">
		<span style="font-size: 30px; font-weight: bold;">欢迎使用电子系毕业生选题系统</span> <br/><br/>
		<span style="font-size: 22px; " id="sysTime">&nbsp;
		</span>
	</div>
	<div id="registercontainer">
    	<div id="registerbox">
            <div id="registerheader">
            	<span style="color: red; float:left; margin-left: 80px">1.老师请直接用姓名和初始密码登录，</span>
            	<span style="color: red; float:left; margin-left: 92px">登录后请尽快修改密码并更新个人信息。</span><br/>
            	<span style="color: red; float: left; margin-left: 80px">2.学生请先注册，注册后用学号和密码登录。</span><br/>
            </div>
            <div id="innerregister">
                <form id="registerform" action="login.do?submit&filter=false" method="post" onsubmit="return checkLogin();">
                     <p>用户名:</p>
                     <input name="loginName" id="loginName" class="registerinput" value="${loginName}" style="width:220px"/>
                     <p>密码:</p>
                	<input type="password" name="loginPass" id="loginPass" class="registerinput" style="width:220px"/>
                      <div id="errorMsg" class="errorbox" >
                        <span style="font-size: 18px; color:red;">
                            <c:out value="${errorMsg}" />
                        </span>
                        </div>
                        <input type="submit" class="registerbtn" value="登录" /><br />
                                                    学生请<a href="register.do?type=stu&filter=false"">点击此处注册</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>