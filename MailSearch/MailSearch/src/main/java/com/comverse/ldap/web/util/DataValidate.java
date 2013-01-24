package com.comverse.ldap.web.util;

public class DataValidate {
	public static boolean validateStringWithTrim(String str){
		if(str!=null && str.trim()!=null && !"".equals(str.trim())){
			return true;
		}
		return false;
	}
	public static boolean validateString(String str){
		if(str!=null && !"".equals(str)){
			return true;
		}
		return false;
	}
	public static boolean validatePhoneNumber(String number){
		if(number!=null && number.length()==11 &&
			(number.matches("^0{0,1}(13[4-9]|147|15[0-2]|15[7-9]18[278])[0-9]{8}$"))
			||number.matches("^0{0,1}(133|153|180|189)[0-9]{8}$")
			||number.matches("^0{0,1}(13[0-2]|15[56]|18[56])[0-9]{8}$")){
			return true;
		}
		return false;
	}
	public static void main(String args[]){
		System.out.println(validatePhoneNumber("15528339991"));
		System.out.println(validatePhoneNumber("18899419001"));
		System.out.println(validatePhoneNumber("13699419001"));
	}
}
