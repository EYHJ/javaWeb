package eyhj.shop.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import eyhj.shop.bean.CommodityInfo;
import eyhj.shop.util.DBPool;
import eyhj.shop.util.Interact;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CommodityList
 */
public class CommodityList extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CommodityList() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Interact ins=new Interact();
		//JsonObject json=null;
		ArrayList<CommodityInfo> commodityList=new ArrayList<CommodityInfo>();
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		boolean isError=false;
		
		try {
			//make json
			//pass
			
			//connect db
			conn=DBPool.getConnection();
			pstm=conn.prepareStatement("select `id`,`name`,`price`,`thumbnail_raw`,counter from goods;");
			rs=pstm.executeQuery();
			
			//do push
			while(rs.next()) {
				CommodityInfo item=new CommodityInfo();
				item.id=rs.getString("id");
				item.name=rs.getString("name");
				item.price=rs.getDouble("price");
				item.thumbnail_raw=rs.getString("thumbnail_raw");
				item.counter=rs.getInt("counter");
				
				commodityList.add(item);
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
		
		if(commodityList.isEmpty()) {
			ins.code=204;
			ins.message="无商品！";
		}
		else {
			ins.code=200;
			ins.message="获取商品列表成功";
			ins.data=commodityList;
		}
		
		Interact.sendJson(response, ins);
	}

}
