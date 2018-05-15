package sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javassist.expr.NewArray;
import lite.Lite;
import lite.LiteQueryDAO;
import test.MessageDAO;

public class TestLite {
	public static void main(String[] args) throws Exception {
		BufferedReader br=new BufferedReader(new FileReader(new File("1.txt")));
		String st=null;
		List<Lite> list=new LinkedList<>();
		Lite lite=new Lite();
		while ((st=br.readLine())!=null){			
			if (st.equals("")){
				list.add(lite);
				lite=new Lite();
				continue;
			}
			else if (st.indexOf("【篇名】")>=0){
				lite.setTitle(st.substring(4));
			}
			else if (st.indexOf("【作者】")>=0){
				lite.setAuthor(st.substring(4));
			}
			else if (st.indexOf("【作者单位】")>=0){
				lite.setCorp(st.substring(6));
			}
			else if (st.indexOf("【出处】")>=0){
				lite.setBook(st.substring(4));
			}
			else if (st.indexOf("【ISSN】")>=0){
				lite.setIssn(st.substring(6));
			}
			else if (st.indexOf("【页码】")>=0){
				lite.setPage(st.substring(4));
			}
			else if (st.indexOf("【摘要】")>=0){
				lite.set_abstract(st.substring(4));
			}
			else if (st.indexOf("【关键词】")>=0){
				lite.setKeyword(st.substring(5));
			}
			else if (st.indexOf("【DOI】")>=0){
				lite.setDoi(st.substring(5));
			}
			br.readLine();
		}
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("questionnaire/applicationContext-SQL.xml");
	    	 
		LiteQueryDAO dao = (LiteQueryDAO) context.getBean("liteQueryDAO");
		dao.insertLite(list);
	}
}
