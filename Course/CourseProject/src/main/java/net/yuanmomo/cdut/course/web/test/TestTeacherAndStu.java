package net.yuanmomo.cdut.course.web.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.cdut.course.web.bean.CourseTable;
import net.yuanmomo.cdut.course.web.bean.UserTable;

public class TestTeacherAndStu {
	public static void main(String args[]) {
//		List<UserTable> list = new ArrayList<UserTable>();
//		DatabaseConnection dbc = new DatabaseConnection();
//		Connection con = dbc.getConnection();
//		String sql = "select userid,username,teacurrentstu from usertable where role=2";
//		PreparedStatement psmt = null;
//		ResultSet rs = null;
//		try {
//			psmt = con.prepareStatement(sql);
//			rs = psmt.executeQuery();
//			while (rs.next()) {
//				UserTable user = new UserTable();
//				user.setUserId(rs.getInt(1));
//				user.setUserName(rs.getString(2));
//				user.setTeaCurrentStu(rs.getInt(3));
//				list.add(user);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				rs.close();
//				psmt.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		System.out.println("一共 有多少个老师" + list.size());
//
//		try {
//			for (UserTable u : list) {
//				sql = "select count(*) from usertable where role=3 and isdeleted=0 and stuteacherid=?";
//				psmt = con.prepareStatement(sql);
//				psmt.setInt(1,u.getUserId());
//				rs = psmt.executeQuery();
//				while (rs.next()) {
//					int a = rs.getInt(1);
//					if (a > u.getTeaCurrentStu()) {
//						System.out.print("*************");
//					}
//					System.out.println(u.getUserId() + "          "
//							+ u.getTeaCurrentStu() + "       " + a);
//				}
//
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			try {
//				rs.close();
//				psmt.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		List<CourseTable> course = new ArrayList<CourseTable>();
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		String sql1 = "select course_id,course_name,student,teacher from coursetable where isdeleted=0 and isSelected=1";
		String sql2 = "select userid,userName,stuteacherid,stucourseid from usertable where isdeleted=0 and role=3 and userid=?";
		String sql3 = "select userid,userName from usertable where isdeleted=0 and role=2 and userid=?";
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = con.prepareStatement(sql1);
			rs = psmt.executeQuery();
			while (rs.next()) {
				CourseTable c=new CourseTable();
				c.setCourse_Id(rs.getInt(1));
				c.setCourse_Name(rs.getString(2));
				c.setStudent(rs.getInt(3));
				c.setTeacher(rs.getInt(4));
				course.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				psmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("一共有"+course.size()+"个课题被选");
		
		
		try {
			for(CourseTable c:course){
				//得到学生信息
				psmt = con.prepareStatement(sql2);
				psmt.setInt(1,c.getStudent());
				UserTable stu=null;
				rs = psmt.executeQuery();
				while (rs.next()) {
					stu=new UserTable();
					stu.setUserId(rs.getInt(1));
					stu.setUserName(rs.getString(2));
					stu.setStuTeacherId(rs.getInt(3));
					stu.setStuCourseId(rs.getInt(4));
				}
				
				//得到老师信息
				psmt = con.prepareStatement(sql3);
				psmt.setInt(1,c.getTeacher());
				UserTable tea=null;
				rs = psmt.executeQuery();
				while (rs.next()) {
					tea=new UserTable();
					tea.setUserId(rs.getInt(1));
					tea.setUserName(rs.getString(2));
				}
				
				//根据学生得到教师信息
				//得到老师信息
				psmt = con.prepareStatement(sql3);
				psmt.setInt(1,stu.getStuTeacherId());
				UserTable tea2=null;
				rs = psmt.executeQuery();
				while (rs.next()) {
					tea2=new UserTable();
					tea2.setUserId(rs.getInt(1));
					tea2.setUserName(rs.getString(2));
				}
				
//				if(!tea.getUserName().equals(tea2.getUserName())){
//					System.out.println(c.getCourse_Name()+c.getCourse_Id() +"   "+stu.getUserName()+stu.getUserId() +"  "+ tea.getUserName()+"   "+tea2.getUserName()+" " +tea2.getUserId());
//				}
				if(stu.getStuCourseId()!= c.getCourse_Id()){
					System.out.println(c.getCourse_Name()+c.getCourse_Id() +"   "+stu.getUserName()+stu.getUserId() +"  "+ tea.getUserName()+"   "+tea2.getUserName()+" " +tea2.getUserId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				psmt.close();
				con.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
}
