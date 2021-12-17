package eyhj.shop.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.mail.SimpleEmail;

public class Email {
	private StringBuilder msg;
	private String id;
	private String nickname;
	private String to;
	
	public Email(String to, String id, String nickname) {
		this.to=to;
		this.id=id;
		this.nickname=nickname;
		clearMsg();
	}
	
	public StringBuilder append(String str) {
		return msg.append(str);
	}
	
	public void clearMsg() {
		msg=new StringBuilder();
	}
	
	public boolean send() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	        SimpleEmail email = new SimpleEmail();
	        email.setHostName("smtp.126.com");
	        email.setAuthentication("xxx@126.com", "xxx");//邮件服务器验证：用户名/密码
	        email.setCharset("UTF-8");// 必须放在前面，否则乱码
	        email.addTo(to);
	        email.addCc("xxx@126.com");
	        email.setFrom("xxx@126.com", "不知道叫什么商城");
	        email.setSubject("不知道叫什么商城订单");
	        StringBuilder msgInfo = new StringBuilder();
	        msgInfo.append("用户："+id+" "+nickname+" 你好！").append("\r\n\t");
	        msgInfo.append("你的订单信息如下：").append("\r\n\t");
	        msgInfo.append("=================================================").append("\r\n\t");
	        msgInfo.append(msg.toString()).append("\r\n\t");
	        msgInfo.append("=================================================").append("\r\n\t");
	        msgInfo.append("订单时间：").append(sdf.format(new Date()));
	        email.setMsg(msgInfo.toString());
	        email.send();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}

