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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
	        SimpleEmail email = new SimpleEmail();
	        email.setHostName("smtp.126.com");
	        email.setAuthentication("xxx@126.com", "xxx");//�ʼ���������֤���û���/����
	        email.setCharset("UTF-8");// �������ǰ�棬��������
	        email.addTo(to);
	        email.addCc("xxx@126.com");
	        email.setFrom("xxx@126.com", "��֪����ʲô�̳�");
	        email.setSubject("��֪����ʲô�̳Ƕ���");
	        StringBuilder msgInfo = new StringBuilder();
	        msgInfo.append("�û���"+id+" "+nickname+" ��ã�").append("\r\n\t");
	        msgInfo.append("��Ķ�����Ϣ���£�").append("\r\n\t");
	        msgInfo.append("=================================================").append("\r\n\t");
	        msgInfo.append(msg.toString()).append("\r\n\t");
	        msgInfo.append("=================================================").append("\r\n\t");
	        msgInfo.append("����ʱ�䣺").append(sdf.format(new Date()));
	        email.setMsg(msgInfo.toString());
	        email.send();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}

