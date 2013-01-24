<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>职称资料</h2>
    </div>
    <div class="contentbox">
        <form action="position.do?c=savePosition"  onsubmit="return checkPositionSave();"  method="post">
            <input type="hidden" name="id" value="${position.position_Id}"> <br>
            <div style="color: red; font-size: 20px">
                <label><strong><c:out value="${error}"></c:out></strong></label>
            </div>
             <span style="font-size: 20px;">以下选项 <span style="color:red;font-size:27px;font-weight: bold;">*</span> 为必填项</span><br/> 
            <p>
                <label><strong>职称名称:</strong></label>
                 <input type="text" name="positionName" id="positionName" value="<c:out value="${position.position_Name}" />" class="inputbox" /> 
                 <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
                 <br />
                <span class="smltxt">(例如：讲师)</span>
            </p>
            <p>
                <label><strong>默认带生人数:</strong></label>
                <input type="text" name="defaultStuCount" id="defaultStuCount" value="<c:out value="${position.default_Stu_Count}" />" class="inputbox" />
                <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
                 <br />
                <span class="smltxt">(例如：5。)</span>
            </p>
            <input type="submit" class="btn" value="保存">
        </form>         
    </div>
</div>
