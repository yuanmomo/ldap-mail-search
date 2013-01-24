<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>专业资料</h2>
    </div>
    <div class="contentbox">
        <form action="system.do?c=<c:if test="${time.timeId<4}">saveTime</c:if><c:if test="${time.timeId==4}">saveConfig</c:if>"  onsubmit="return checkTimeSave();"  method="post">
            <input type="hidden" name="id" value="${time.timeId}"> <br>
            <div style="color: red; font-size: 20px">
                <label><strong><c:out value="${error}"></c:out></strong></label>
            </div>
             <span style="font-size: 20px;">以下选项 <span style="color:red;font-size:27px;font-weight: bold;">*</span> 为必填项</span><br/> 
            <p>
                <label><strong>设置名称(不能修改):</strong></label>
                 <input type="text" disabled="disabled" value="<c:out value="${time.timeName}" />" class="inputbox"  name="timeName" id="timeName" /> 
                 <span style="color:red;font-size: 20px;font-weight: bold; margin-top:10px;">*</span>
                 <br />
            </p>
            <c:if test="${time.timeId<4}">
            <p>
                <label><strong>开始时间：</strong></label>
                <input readonly type="text" name="timeStart" id="timeStart" value="<fmt:formatDate value="${time.timeStart}" pattern="yyyy-MM-dd HH:mm:ss" />" class='inputbox' onclick='popUpCalendar(this, this, "yyyy-mm-dd")' />
                <span style="color:red;font-size: 20px;font-weight: bold; margin-top:10px;">*</span>
                 <br />
            </p>
            <p>
                <label><strong>截止时间:</strong></label>
                <input type="text" name="timeEnd" id="timeEnd" value="<fmt:formatDate value="${time.timeEnd}" pattern="yyyy-MM-dd HH:mm:ss" />" class='inputbox' onclick='popUpCalendar(this, this, "yyyy-mm-dd")' size="11" readonly="readonly" />
                	<span style="color:red;font-size: 20px;font-weight: bold; margin-top:10px;">*</span>
                 <br />
            </p>
            </c:if>
            <p>
            	<c:if test="${time.timeId<4}">
                <label><strong>是否启用:</strong></label>
                </c:if>
                <c:if test="${time.timeId==4}">
                <label><strong>是否显示:</strong></label>
                </c:if>
	           		<select id="isUsed" name="isUsed" style="width:80px;">
            			<c:choose>
            				<c:when test="${time.isUsed==1}">
            					<option value="1" selected="selected">
            						是
            					</option>
            				<option value="0" >
            						否
            				</option>
            				</c:when>
            					<c:otherwise>
            						<option value="0" selected="selected">
            							否
            						</option>
            						<option value="1">
            							是
            						</option>
            					</c:otherwise>
            				</c:choose>
             		</select>
                 <br />
            </p>
            <input type="submit" class="btn" value="保存">
        </form>         
    </div>
</div>
