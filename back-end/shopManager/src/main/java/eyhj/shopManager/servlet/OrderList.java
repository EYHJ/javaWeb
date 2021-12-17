package eyhj.shopManager.servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import eyhj.shopManager.util.DBPool;
import eyhj.shopManager.util.Interact;
import eyhj.shopManager.bean.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderList
 */
public class OrderList extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public OrderList() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Interact ins=new Interact();
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		LinkedList<Aorder> orderList=new LinkedList<Aorder>();
		boolean isError=false;
		
		try {
			
			//connect db
			conn=DBPool.getConnection();
			pstm=conn.prepareStatement("select * from `order`;");
			rs=pstm.executeQuery();
			
			//judge
			if(rs.next()) {
				do {
					Aorder aorder=new Aorder();
					aorder.sid=rs.getInt("sid");
					aorder.id=rs.getString("id");
					aorder.log=rs.getString("log");
					orderList.add(aorder);
				}while(rs.next());
				
				ins.code=200;
				ins.message="获取订单信息成功";
				ins.data=orderList;
			}
			else {
				ins.code=200;
				ins.message="无订单信息";
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
		
		Interact.sendJson(response, ins);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
