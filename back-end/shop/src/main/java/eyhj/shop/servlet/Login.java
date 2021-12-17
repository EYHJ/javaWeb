package eyhj.shop.servlet;

import eyhj.shop.util.*;
import java.sql.*;
import java.io.IOException;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Interact ins=new Interact();
		JsonObject json=null;
		String account=null;
		String password=null;
		String rpw=null;
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		boolean isError=false;
		
		//repeat login
		HttpSession session=request.getSession();
		account=(String)session.getAttribute("account");
		if(account!=null) {
			ins.code=403;
			ins.message="ÄúÒÑµÇÂ¼£¡";
			Interact.sendJson(response, ins);
			return;
		}
		
		try {
			//make json
			json=Interact.parseJson(request);
			account=json.get("account").getAsString();
			password=json.get("password").getAsString();

			//connect db
			conn=DBPool.getConnection();
			pstm=conn.prepareStatement(
					"select password from user_account where `account`=?;");
			pstm.setString(1, account);
			rs=pstm.executeQuery();
			
			//judge
			if(rs.next()) {
				rpw=rs.getString("password");
			}
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
		
		if(rpw==null) {
			ins.code=403;
			ins.message="ÕËºÅ²»´æÔÚ";
		}
		else {
			//success to login
			if(password.equals(rpw)) {
				ins.code=200;
				ins.message="µÇÂ¼³É¹¦£¡";
				
				session.setAttribute("account", account);
				
				Cookie cookie=new Cookie("JSESSIONID",session.getId());
				cookie.setMaxAge(1800);
				response.addCookie(cookie);
			}
			else {
				ins.code=403;
				ins.message="ÃÜÂë´íÎó";
			}
		}
		Interact.sendJson(response, ins);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Interact ins=new Interact();
		String account=null;
		
		//repeat login
		HttpSession session=request.getSession();
		account=(String)session.getAttribute("account");
		if(account==null) {
			ins.code=403;
			ins.message="Î´µÇÂ¼£¡";
			Interact.sendJson(response, ins);
			return;
		}

		try {
			session.invalidate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			ins.code=200;
			ins.message="µÇ³ö³É¹¦";
			Interact.sendJson(response, ins);
		}
	}

}

