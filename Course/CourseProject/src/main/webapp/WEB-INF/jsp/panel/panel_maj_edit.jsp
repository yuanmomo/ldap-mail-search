<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>专业资料</h2>
    </div>
    <div class="contentbox">
        <form action="major.do?c=save"  onsubmit="return checkMajorSave();"  method="post">
            <input type="hidden" name="id" value="${major.majorId}"> <br>
            <div style="color: red; font-size: 20px">
                <label><strong><c:out value="${error}"></c:out></strong></label>
            </div>
             <span style="font-size: 20px;">以下选项 <span style="color:red;font-size:27px;font-weight: bold;">*</span> 为必填项</span><br/> 
            <p>
                <label><strong>专业号:</strong></label>
                 <input type="text" name="majorNumber" id="majorNumber" value="<c:out value="${major.majorNumber}" />" class="inputbox" /> 
                 <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
                 <br />
                <span class="smltxt">(例如：20080501)</span>
            </p>
            <p>
                <label><strong>专业名称:</strong></label>
                <input type="text" name="majorName" id="majorName" value="<c:out value="${major.majorName}" />" class="inputbox" />
                <span style="color:red;font-size: 27px;font-weight: bold;">*</span>
                 <br />
                <span class="smltxt">(例如：电子信息工程。)</span>
            </p>
            <input type="submit" class="btn" value="保存">
        </form>         
    </div>
</div>
