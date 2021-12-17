package eyhj.shop.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Interact {
	public int code;//返回状态码
	public String message;//返回消息
	public Object data;//返回数据体
	
	public Interact() {//默认构造
		code=400;
		message="请求错误";
		data=null;
	}
	
	//在HttpServletResponse中发送json
	public static void sendJson(HttpServletResponse res,Interact ins) throws IOException {
		res.setContentType("application/json");//设置返回类型为json
		PrintWriter writer=res.getWriter();
		writer.write(new Gson().toJson(ins));//写入序列化的数据
	}
	
	//将HttpServletRequest的数据转化为json
	public static JsonObject parseJson(HttpServletRequest req) throws IOException {
		StringBuffer buf=new StringBuffer();
		try {
            BufferedReader bufferedReader = req.getReader();
            char[] buff = new char[1024];
            int len;
            while((len = bufferedReader.read(buff)) != -1){
                buf.append(buff,0,len);
            }
        }catch (IOException e){
            System.out.println(e);
        }
		JsonObject json=JsonParser.parseString(buf.toString()).getAsJsonObject();
		return json;
	}
	
}
