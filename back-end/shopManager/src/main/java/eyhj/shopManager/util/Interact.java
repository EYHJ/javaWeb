package eyhj.shopManager.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Interact {
	public int code;
	public String message;
	public Object data;
	
	public Interact() {
		code=400;
		message="«Î«Û¥ÌŒÛ";
		data=null;
	}
	
	public static void sendJson(HttpServletResponse res,Interact ins) throws IOException {
		res.setContentType("application/json");
		PrintWriter writer=res.getWriter();
		writer.write(new Gson().toJson(ins));
	}
	
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