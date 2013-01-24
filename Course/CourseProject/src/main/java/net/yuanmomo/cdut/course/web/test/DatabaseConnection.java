package net.yuanmomo.cdut.course.web.test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	//数据库驱动
	private static final String DBDRIVER = "com.mysql.jdbc.Driver";
	//url地址
	private static final String DBURL = "jdbc:mysql://localhost:3306/";
	//数据库名
	private static final String DBNAME = "projectsystem";
	//数据库用户名、密码
	private static final String DBUSER = "root";
	private static final String USERPWD = "root";
	private Connection conn = null;
	//关闭数据库连接
	public void close() throws SQLException {
		if(this.conn != null){
			this.conn.close();
		}	
	}
	public DatabaseConnection(){
		try {
			Class.forName(DBDRIVER);
			this.conn = DriverManager.getConnection(DBURL+DBNAME,DBUSER,USERPWD);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	//取得数据库的连接
	public Connection getConnection() {
		return this.conn;
	}
	public void removeConnection() throws Exception{
		try{
			if(this.conn!=null && !this.conn.isClosed()){
				this.conn.close();
			}
		}catch (Exception e) {
			throw e;
		}
	}
}
//	public static void main(String[] args){
//		Connection con=new DatabaseConnection().getConnection();
////		String sql="insert into test (mytime) values(?)";
//		String sql="select mytime from test";
//		PreparedStatement psmt=null;
//		ResultSet rs=null;
//		try {
//			psmt=con.prepareStatement(sql);
//			Date d=new Date();
////			java.sql.Date d2=new java.sql.Date(d.getTime());
////			psmt.setString(1,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d));
//			rs=psmt.executeQuery();
//			while(rs.next()){
//				System.out.println(rs.getString(1));
//			}
////			System.out.println(d2);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			if(psmt!=null&& con!=null){
//				try {
//					psmt.close();
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//}
