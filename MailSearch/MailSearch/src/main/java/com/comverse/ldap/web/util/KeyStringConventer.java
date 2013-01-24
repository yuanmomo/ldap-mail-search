package com.comverse.ldap.web.util;

public class KeyStringConventer {
	public static String convert(String keyStr){
		keyStr=keyStr.replaceAll("\\*","%");
		if(!keyStr.startsWith("^")&&!keyStr.startsWith("%")){
			keyStr="%"+keyStr;
		}else{
			keyStr=keyStr.replaceAll("\\^", "");
		}
		if(!keyStr.endsWith("$") && !keyStr.endsWith("%")){
			keyStr=keyStr+"%";
		}else{
			keyStr=keyStr.replaceAll("\\$", "");
		}
		return keyStr.replaceAll("\\ ", "%").replaceAll("\\.", "_");
	}
}
