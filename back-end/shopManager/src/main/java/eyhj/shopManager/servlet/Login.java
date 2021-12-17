package eyhj.shopManager.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonObject;

import eyhj.shopManager.util.DBPool;
import eyhj.shopManager.util.Interact;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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

			if(account.equals("smAdmin")&&password.equals("smAdPwsm")) {
				rpw=password;
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
			ins.message="µÇÂ¼Ê§°Ü";
		}
		else {
			//success to login
			
			ins.code=200;
			ins.message="µÇÂ¼³É¹¦£¡";
			
			session.setAttribute("account", account);
			
			Cookie cookie=new Cookie("JSESSIONID",session.getId());
			cookie.setMaxAge(1800);
			response.addCookie(cookie);
		}
		Interact.sendJson(response, ins);
	}

}
