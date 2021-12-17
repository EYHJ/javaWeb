package eyhj.shop.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBPool {
	private static DataSource ds=null;
	static {
		BasicDataSource bds=new BasicDataSource();
		bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		bds.setUrl("jdbc:mysql://localhost:3306/webshop");
		bds.setUsername("xxx");
		bds.setPassword("xxx");
		bds.setInitialSize(4);
		bds.setMaxIdle(32);
		ds=bds;
	}
	
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	public static void release(Statement stm) {
		if(stm!=null) {
			try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stm=null;
		}
	}
	
	public static void release(ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs=null;
		}
	}
	
	public static void release(Connection conn) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn=null;
		}
	}
	
	public static void release(Connection conn,Statement stm,ResultSet rs) {
		release(rs);
		release(stm);
		release(conn);
	}

}
