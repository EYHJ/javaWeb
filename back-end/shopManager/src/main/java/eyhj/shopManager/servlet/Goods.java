package eyhj.shopManager.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonObject;

import eyhj.shopManager.util.DBPool;
import eyhj.shopManager.util.Interact;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Goods
 */
public class Goods extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Goods() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Interact ins=new Interact();
		JsonObject json=null;
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		boolean isError=false;
		
		try {
			//make json
			json=Interact.parseJson(request);
			String id=json.get("id").getAsString();
			String name=json.get("name").getAsString();
			Double price=json.get("price").getAsDouble();
			String describe=json.get("describe").getAsString();
			String img_raw=json.get("img_raw").getAsString();
			String thumbnail_raw=json.get("thumbnail_raw").getAsString();
			
			//connect db
			conn=DBPool.getConnection();
			pstm=conn.prepareStatement(
					"update goods set `name`=?, price=?, `describe`=?, img_raw=?, thumbnail_raw=? "
					+ "where id=?;");
			pstm.setString(6, id);
			pstm.setString(1, name);
			pstm.setDouble(2, price);
			pstm.setString(3, describe);
			pstm.setString(4, img_raw);
			pstm.setString(5, thumbnail_raw);
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
		ins.message="修改商品成功";
		Interact.sendJson(response, ins);
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
		
		try {
			//make json
			json=Interact.parseJson(request);
			String id=json.get("id").getAsString();
			String name=json.get("name").getAsString();
			Double price=json.get("price").getAsDouble();
			String describe=json.get("describe").getAsString();
			String img_raw=json.get("img_raw").getAsString();
			String thumbnail_raw=json.get("thumbnail_raw").getAsString();
			
			//connect db
			conn=DBPool.getConnection();
			pstm=conn.prepareStatement(
					"insert into goods(id, `name`, price, `describe`, img_raw, thumbnail_raw) "
					+ "values(?, ?, ?, ?, ?, ?);");
			pstm.setString(1, id);
			pstm.setString(2, name);
			pstm.setDouble(3, price);
			pstm.setString(4, describe);
			pstm.setString(5, img_raw);
			pstm.setString(6, thumbnail_raw);
			try {
				pstm.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
				ins.code=409;
				ins.message="该编号已被使用,请更换";
				Interact.sendJson(response, ins);
				isError=true;
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
		
		ins.code=200;
		ins.message="添加商品成功";
		Interact.sendJson(response, ins);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Interact ins=new Interact();
		JsonObject json=null;
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		boolean isError=false;
		
		try {
			//make json
			json=Interact.parseJson(request);
			String id=json.get("id").getAsString();
			
			//connect db
			conn=DBPool.getConnection();
			pstm=conn.prepareStatement(
					"delete from goods where id=?;");
			pstm.setString(1, id);
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
		ins.message="删除商品成功";
		Interact.sendJson(response, ins);
	}

}
