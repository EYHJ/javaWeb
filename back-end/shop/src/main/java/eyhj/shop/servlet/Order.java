package eyhj.shop.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import eyhj.shop.util.DBPool;
import eyhj.shop.util.Interact;
import eyhj.shop.util.Email;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Order
 */
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Order() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Interact ins=new Interact();
		JsonObject json=null;
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		boolean isError=false;
		
		StringBuilder report=new StringBuilder();
		double value=0.0;
		
		try {
			//make json
			json=Interact.parseJson(request);
			JsonArray jarr=json.get("list").getAsJsonArray();
			JsonObject tj;
			
			//connect db
			conn=DBPool.getConnection();
			
			//get user info
			String account=json.get("uid").getAsString();
			String username=null;
			String emailTo=null;
			pstm=conn.prepareStatement("select username, email from user_info where account=?;");
			pstm.setString(1, account);
			rs=pstm.executeQuery();
			if(rs.next()) {
				username=rs.getString("username");
				emailTo=rs.getString("email");
			}
			else {
				throw new Exception("找不到用户");
			}
			
			//get order info
			String id=null;
			String name=null;
			double price=0.0;
			int amount=0;
			
			for(int i=0;i<jarr.size();++i) {
				tj=jarr.get(i).getAsJsonObject();
				
				pstm=conn.prepareStatement("select price from goods where id=?;");
				id=tj.get("id").getAsString();
				pstm.setString(1, id);
				rs=pstm.executeQuery();
				
				if(rs.next()) {
					
					name=tj.get("name").getAsString();
					price=rs.getDouble("price");
					amount=tj.get("amount").getAsInt();
					
					report.append("商品编号："+id+"\n")
						.append("商品名："+name+"\n")
						.append("单价："+price+"\n")
						.append("数量："+amount+"\n")
						.append("小计："+price*amount+"\n\n");
					
					value+=price*amount;
					DBPool.release(pstm);
					DBPool.release(rs);
				}
				else {
					Interact.sendJson(response, ins);
					isError=true;
					break;
				}
			}
			report.append("总计："+value+"\n");
			
			//send email
			Email email=new Email(emailTo, account, username);
			email.append(report.toString());
			if(!email.send()) {
				throw new Exception("发送邮件失败");
			}
			
			//set goods counter
			for(int i=0;i<jarr.size();++i) {
				tj=jarr.get(i).getAsJsonObject();
				
				pstm=conn.prepareStatement("update goods set counter=counter+1 where id=?;");
				id=tj.get("id").getAsString();
				pstm.setString(1, id);
				pstm.executeUpdate();

				DBPool.release(pstm);
				DBPool.release(rs);
			}
			
			//write log
			pstm=conn.prepareStatement("insert into `order`(id,`log`) "
					+ "values(?,?);");
			pstm.setString(1, account);
			pstm.setString(2, report.toString());
			pstm.executeUpdate();
			
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
		ins.message="购买商品成功";
		Interact.sendJson(response, ins);
	}

}
