package eyhj.shop.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import eyhj.shop.bean.CommodityInfo;
import eyhj.shop.util.DBPool;
import eyhj.shop.util.Interact;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class goods
 */
public class Commodity extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Commodity() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Interact ins=new Interact();
		String id=null;
		CommodityInfo commodity=null;
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		boolean isError=false;
		
		try {
			id=request.getParameter("id");
			if(id==null)
				throw new Exception("request has no parameter");
			
			//connect db
			conn=DBPool.getConnection();
			pstm=conn.prepareStatement("select * from goods where id=?;");
			pstm.setString(1, id);
			rs=pstm.executeQuery();
			
			//judge
			if(rs.next()) {
				commodity=new CommodityInfo();
				commodity.id=id;
				commodity.name=rs.getString("name");
				commodity.price=rs.getDouble("price");
				commodity.describe=rs.getString("describe");
				commodity.img_raw=rs.getString("img_raw");
				
				ins.code=200;
				ins.message="获取商品 id="+id+" 成功";
				ins.data=commodity;
			}
			else {
				ins.code=404;
				ins.message="商品 id="+id+" 不存在";
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

}
