<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>初始化设置</h2>
            </div>
            <div class="contentbox">
                <form name="form" action="system.do?c=doInitial" method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                    		<td width="20%">序号</td>
                            <td width="30%">初始化项名</td>
                           	<td width="50%">是否初始化</td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr>
                    		<td>1.</td>
                    		<td>公告</td>
                    		<td>
                    			<input type="radio" name="news" value="1"/>是
                    			<input type="radio" name="news" value="0" checked="checked"/>否
                    		</td>
                    	</tr>
                    	<tr>
                    		<td>2.</td>
                    		<td>学生信息</td>
                    		<td>
                    			<input type="radio" name="student" value="1"/>是
                    			<input type="radio" name="student" value="0" checked="checked"/>否
                    		</td>
                    	</tr>
                    	<tr>
                    		<td>3.</td>
                    		<td>课题信息</td>
                    		<td>
                    			<input type="radio" name="course" value="1"/>是
                    			<input type="radio" name="course" value="0" checked="checked"/>否
                    		</td>
                    	</tr>
                    	<tr>
                    		<td>4.</td>
                    		<td>教师信息</td>
                    		<td>
                    			<input type="radio" name="teachers" value="1"/>是
                    			<input type="radio" name="teachers" value="0" checked="checked"/>否
                    		</td>
                    	</tr>
                    	<tr>
                    		<td colspan="3"><input type="submit" value="确定"/></td>
                    	</tr>
                    </tbody>
                </table>
                </form>
            </div>
        </div>