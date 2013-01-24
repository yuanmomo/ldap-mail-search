/**
 * 
 */
package net.yuanmomo.cdut.course.web.business;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.yuanmomo.cdut.course.web.bean.ClassTable;
import net.yuanmomo.cdut.course.web.bean.CollegeTable;
import net.yuanmomo.cdut.course.web.bean.MajorTable;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author Harry
 *
 */
public class FetchDataBusiness {
	//将所有的学院转换为JSON
	public String fetchCollegeToJSON(){
		//[{"":"","":"","":""},{"":"","":"","":""}]
		JSONArray array=new JSONArray();
		ICollegeTableDAOBusiness dao=(ICollegeTableDAOBusiness)BeanFactory.getBean("ICollegeTableDAOBusiness");
		List<CollegeTable> res=dao.doQueryAll();
		Iterator<CollegeTable> ite=res.iterator();
		while(ite.hasNext()){
			CollegeTable coll=ite.next();
			JSONObject json = new JSONObject();
			try {
				json.put("collegeId", coll.getCollegeId());
				json.put("collegeName", coll.getCollegeName());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			array.put(json);
		}
		return array.toString();
	}
	
	//将给定学院下所有的专业转换为JSON
	public String fetchMajorToJSON(int collegeId){
		//[{"":"","":"","":""},{"":"","":"","":""}]
		JSONArray array=new JSONArray();
		IMajorTableDAOBusiness dao=(IMajorTableDAOBusiness)BeanFactory.getBean("IMajorTableDAOBusiness");
		List<MajorTable> res=dao.getMajorsByCollegeId(collegeId);
		Iterator<MajorTable> ite=res.iterator();
		while(ite.hasNext()){
			MajorTable major=ite.next();
			JSONObject json = new JSONObject();
			try {
				json.put("majorId", major.getMajorId());
				json.put("majorName", major.getMajorName());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			array.put(json);
		}
		return array.toString();
	}
	
	//将给定专业下所有的班级转换为JSON
	public String fetchClassesToJSON(int majorId){
		//[{"":"","":"","":""},{"":"","":"","":""}]
		JSONArray array=new JSONArray();
		IClassTableDAOBusiness dao=(IClassTableDAOBusiness)BeanFactory.getBean("IClassTableDAOBusiness");
		List<ClassTable> res=dao.getClassesByMajorId(majorId);
		Iterator<ClassTable> ite=res.iterator();
		while(ite.hasNext()){
			ClassTable c=ite.next();
			JSONObject json = new JSONObject();
			try {
				json.put("classId", c.getClassId());
				json.put("className", c.getClassNameChi());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			array.put(json);
		}
		return array.toString();
	}
	
}
