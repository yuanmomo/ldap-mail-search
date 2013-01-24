package net.yuanmomo.cdut.course.web.util;

public class StringContainsChi{
	public static boolean isAChineseStr(String str){
		boolean flag=true;
		str=str.trim().replace(" ", "");
		if(str!=null&&!"".equals(str)){
			char[] temp=str.toCharArray();
			for(int i=0;i<temp.length;i++){
				if(temp[i]<0x0391 || temp[i]>0xFFE5){
					flag=false;//某个字符不是汉字，退出
					break;
				}
			}
		}
		return flag;
	}
}