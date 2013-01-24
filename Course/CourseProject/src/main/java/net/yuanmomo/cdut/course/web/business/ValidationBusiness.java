package net.yuanmomo.cdut.course.web.business;

import net.yuanmomo.cdut.course.web.bean.UserTable;
import net.yuanmomo.cdut.course.web.util.BeanFactory;

public class ValidationBusiness {
	public static boolean validateUserAvailable(UserTable user){
		int majorId=0;
		//validate
		
		//1.get the new user's studentNumber.
		//2.try to validate whether the studentNumber is available according to the ConfigTable value of "current_available_major"
		
		IMajorTableDAOBusiness dao=(IMajorTableDAOBusiness)BeanFactory.getBean("IMajorTableDAOBusiness");
		majorId=dao.isMajorNumberExsit(user.getStuNumber().substring(0,8));

		return majorId>0? true:false;
	}
}
