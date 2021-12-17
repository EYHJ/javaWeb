package eyhj.shop.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import eyhj.shop.bean.Userinfo;
import eyhj.shop.util.DBPool;
import eyhj.shop.util.Interact;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class User
 */
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public User() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Interact ins=new Interact();
		Userinfo user=new Userinfo();
		String account=(String)request.getSession().getAttribute("account");

		if(account==null) {
			ins.code=250;
			ins.message="未登录";
			Interact.sendJson(response, ins);
			return;
		}

		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		
		try {
			conn=DBPool.getConnection();
			pstm=conn.prepareStatement(
					"select * from user_info where account=?;");
			pstm.setString(1, account);
			rs=pstm.executeQuery();
			
			if(rs.next()) {
				user.account=account;
				user.username=rs.getString("username");
				user.email=rs.getString("email");
				user.location=rs.getString("location");
			}
			else {
				account=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBPool.release(conn, pstm, rs);
		}
		
		if(account!=null) {
			ins.code=200;
			ins.message="获取用户信息成功";
			ins.data=user;
		}
		Interact.sendJson(response, ins);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Interact ins=new Interact();
		JsonObject json=null;
		String account=null;
		String username=null;
		String password=null;
		String email=null;
		String location=null;
		
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		boolean isError=false;
		
		try {
			//make json
			json=Interact.parseJson(request);
			
			account=json.get("account").getAsString();
			username=json.get("username").getAsString();
			password=json.get("password").getAsString();
			email=json.get("email").getAsString();
			location=json.get("location").getAsString();

			//connect db
			conn=DBPool.getConnection();
			pstm=conn.prepareStatement(
					"insert into user_account(`account`,`password`) values(?,?);");
			pstm.setString(1, account);
			pstm.setString(2, password);
			pstm.executeUpdate();
			DBPool.release(pstm);
			pstm=conn.prepareStatement(
					"insert into user_info(`account`,username,email,location) "
					+ "values(?,?,?,?);");
			pstm.setString(1, account);
			pstm.setString(2, username);
			pstm.setString(3, email);
			pstm.setString(4, location);
			pstm.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			ins.code=403;
			ins.message="账号已被注册";
			Interact.sendJson(response, ins);
			isError=true;
		}catch(Exception e) {
			e.printStackTrace();
			Interact.sendJson(response, ins);
			isError=true;
		}
		finally {
			DBPool.release(conn, pstm, rs);
			if(isError)
				return;
		}
		
		ins.code=200;
		ins.message="注册成功";
		Interact.sendJson(response, ins);
	}

}
