<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>班级资料</h2>
    </div>
    <div class="contentbox">
        <form action="class.do?c=save"  onsubmit="return checkClassSave();"  method="post">
            <input type="hidden" name="id" value="${c.classId}"> <br>
            <div style="color: red; font-size: 20px">
                <label><strong><c:out value="${error}"></c:out></strong></label>
            </div>
            <span style="font-size: 20px;">以下选项 <span style="color:red;font-size:27px;font-weight: bold;">*</span> 为必填项</span><br/> 
            <p>
                <label><strong>班级号:</strong></label>
                 <input type="text" name="classNumber" id="classNumber" value="<c:out value="${c.classNameNumber}" />" class="inputbox" /> 
                  <span style="color:red;font-size: 27px;font-weight: bold;">*</span> 
                 <br />
                <span class="smltxt">(例如：2008050106)</span>
            </p>
            <p>
                <label><strong>班级名称:</strong></label>
                <input type="text" name="className" id="className" value="<c:out value="${c.classNameChi}" />" class="inputbox" /> 
                 <span style="color:red;font-size: 27px;font-weight: bold;">*</span> 
                <br />
                <span class="smltxt">(例如：信工六班。)</span>
            </p>
             <p>
                <label><strong>所在专业:</strong></label>
                <select id="majorId" name="majorId" style="width:300px;">
                	<c:forEach items="${majors}" var="major">
                		<c:choose>
                			<c:when test="${major.majorId==c.inMajor}">
                				<option value="${major.majorId}" selected="selected">
                					${major.majorName}
                				</option>
                			</c:when>
                			<c:otherwise>
                				<option value="${major.majorId}">
                					${major.majorName}
                				</option>
                			</c:otherwise>
                		</c:choose>
                	</c:forEach>
                </select>
                 <span style="color:red;font-size: 27px;font-weight: bold;">*</span> 
            </p>
            <input type="submit" class="btn" value="保存">
        </form>         
    </div>
</div>
