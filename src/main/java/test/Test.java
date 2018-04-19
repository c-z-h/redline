package test;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		Date date=new Date();
		Message message=new Message("FSpKyWVj6", new Timestamp(date.getTime()), "吔屎", 0);
		
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("test/Message-Datasource.xml");
	    	 
		MessageDAO mDao = (MessageDAO) context.getBean("messageDAO");
		mDao.insertMessage(message);
		mDao.selectMessage();
	}
}
