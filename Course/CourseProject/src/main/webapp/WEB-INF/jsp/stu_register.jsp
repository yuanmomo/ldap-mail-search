<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户注册</title>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/register.css" rel="stylesheet" type="text/css" />
<link href="themes/blue/styles.css" rel="stylesheet" type="text/css" />
<!--script language="javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" ></script-->
<script language="javascript" src="scripts/jquery.js"></script>
<script language="javascript" src="js/common.js"  ></script>
<script language="javascript" src="js/check.js"  ></script>
</head>
<body onload="fetchColleges();"> 
	<div id="registercontainer">
    	<div id="registerbox">
            <div id="innerregister">
                <form id="registerform" onsubmit="return checkFun();" action="register.do?submit=submit&filter=false" method="post">
                <span style="font-size: 20px;"><span style="color:red;font-size:27px;font-weight: bold;">以下选项* 为必填项</span></span><br/>
                <div id='colloegeDiv' style="color:#DDDDDD;text-shadow: 1px 1px 1px #222222; ">学院:
                	<select name="col" onchange="fetchMajor();" id="col" style="width:170px;">
                             <option value="0">请选择学院</option>
                         </select>
                         <span style="color:red;font-size: 20px;font-weight: bold;">*</span>
                </div><br />
	              <div id='majordiv' style="color:#DDDDDD;text-shadow: 1px 1px 1px #222222;">
	              	专业:
	              	<select name="major" id="major" style="width:170px;">
	                 <option value="0">请选择专业</option></select>
	                 <span style="color:red;font-size: 20px;font-weight: bold;">*</span>
	              </div><br />
                      <div id='className' style="color:#DDDDDD;text-shadow: 1px 1px 1px #222222;">
                      	班级:<select name="className" id="className" style="width:170px;">
                         <option value="0">请选择班级</option></select>
                         <span style="color:red;font-size: 20px;font-weight: bold;">*</span>
                      </div>  
                	 <br/>
                     	学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:<input name="studentNumber" id="studentNumber" class="registerinput" value="${stuNumber}" style="width:145px;"/>
                     		  <span style="color:red;font-size: 20px;font-weight: bold; margin-top: 10px">*</span><br/>
                     	姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:<input type="text" name="studentName" id="studentName" class="registerinput" value="${stuName}" style="width:145px;"/>
							 <span style="color:red;font-size: 20px;font-weight: bold; margin-top: 10px">*</span><br/>
                    	密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:<input type="password" name="password1" id="password1" class="registerinput" style="width:145px;"/>
                    		 <span style="color:red;font-size: 20px;font-weight: bold; margin-top: 10px">*</span><br/>
                     	确认密码:<input type="password" name="password2" id="password2" class="registerinput" style="width:145px;"/>
                     		<span style="color:red;font-size: 20px;font-weight: bold; margin-top: 10px">*</span><br/>
                     	邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:<input type="text" name="email"  id="email" class="registerinput"  value="${email}" style="width:145px;"/>
                		    <span style="color:red;font-size: 20px;font-weight: bold; margin-top: 10px">*</span><br/>
                	           电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:<input type="text" name="phone"  id="phone" class="registerinput"  value="${phone}"style="width:145px;"/>
                			<span style="color:red;font-size: 20px;font-weight: bold; margin-top: 10px">*</span><br/>
                        <p>&nbsp;</p>
                        <div id="errorMsg" class="errorbox" >
                        <p style="font-size: 18px; color:red;">
                            <c:out value="${errorMsg}" />
                        </p>
                        </div>
                        <p>&nbsp;</p>
                        <input type="submit" class="registerbtn" value="注册" /><br />
                </form>
            </div>
        </div>
    </div>

</body>
</html>