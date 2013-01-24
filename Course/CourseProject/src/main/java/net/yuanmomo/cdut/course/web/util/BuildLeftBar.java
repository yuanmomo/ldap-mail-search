/*  Copyright 2011 yuanmomo
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Author     : yuanmomo
 */
package net.yuanmomo.cdut.course.web.util;

import java.io.File;
import java.util.Iterator;

import net.yuanmomo.cdut.course.web.bean.UserTable;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BuildLeftBar {
	@SuppressWarnings("unchecked")
	public static String build(String xmlFilePath,String currentSelection) {
		try {
			StringBuilder content = new StringBuilder("<ul id=\"nav\">");
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(xmlFilePath));
			Element e = document.getRootElement();
			Iterator i = e.elementIterator();
			while (i.hasNext()) {
				Element e1 = (Element) i.next();
				if(currentSelection.equals(e1.attribute("name").getStringValue())){
					content.append("<li><a class='expanded heading'>");
				}else{
					content.append("<li><a class='collapsed heading'>");
				}
				content.append(e1.attribute("value").getStringValue());
				content.append("</a> <ul class='navigation'>");

				String baseUrl=e1.attribute("name").getStringValue()+".do";
				for (Iterator i2 = e1.elementIterator(); i2.hasNext();) {
					Element e2 = (Element) i2.next();
					content.append("<li>");
					content.append("<a href='").append(baseUrl).append("?c=").append(e2.attribute("name").getStringValue()).
							append("' title='").append(e2.getText()).append("'>"
							+ e2.getStringValue() + "</a>");
					content.append("</li>");

				}
				content.append("</ul></li>");
			}
			content.append("</ul>");
			return content.toString();
		} catch (Exception e) {
			return null;
		}
	}
	public static String build(UserTable user,String xmlLocationBase,String currentSelection){
		//判断用户类型admin为1，老师为2，学生为3
		int role=user.getRole();
		String xmlFilePath=null; //不同用户的xml左边边栏的配置文件位置
		if(role==1){
			xmlFilePath=xmlLocationBase+File.separator+"AdminMenu.xml";
		}else if(role==2){
			xmlFilePath=xmlLocationBase+File.separator+"TeacherMenu.xml";
		}else if(role==3){
			xmlFilePath=xmlLocationBase+File.separator+"StudentMenu.xml";
		}
		if(currentSelection==null){
			currentSelection="news"; //左边边栏的第一个栏目，默认第一个打开，其余的关闭
		}
		return build(xmlFilePath,currentSelection);
	}
}
