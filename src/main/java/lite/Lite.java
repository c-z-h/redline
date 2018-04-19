package lite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Lite {
	private String title;
	private String book;
	private int year;
	private String au1;
	private String au2;
	private String au3;
	private String kwd1;
	private String kwd2;
	private String kwd3;
	
	public String getTitle() {
		return title;
	}

	public String getBook() {
		return book;
	}

	public int getYear() {
		return year;
	}

	public String getAu1() {
		return au1;
	}

	public String getAu2() {
		return au2;
	}

	public String getAu3() {
		return au3;
	}

	public String getKwd1() {
		return kwd1;
	}

	public String getKwd2() {
		return kwd2;
	}

	public String getKwd3() {
		return kwd3;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setAu1(String au1) {
		this.au1 = au1;
	}

	public void setAu2(String au2) {
		this.au2 = au2;
	}

	public void setAu3(String au3) {
		this.au3 = au3;
	}

	public void setKwd1(String kwd1) {
		this.kwd1 = kwd1;
	}

	public void setKwd2(String kwd2) {
		this.kwd2 = kwd2;
	}

	public void setKwd3(String kwd3) {
		this.kwd3 = kwd3;
	}

	public Lite(String title, String book, int year, 
				String au1, String au2, String au3,
				String kwd1, String kwd2, String kwd3){
		this.title=title;
		this.book=book;
		this.year=year;
		this.au1=au1;
		this.au2=au2;
		this.au3=au3;
		this.kwd1=kwd1;
		this.kwd2=kwd2;
		this.kwd3=kwd3;
	}
	
	static public String toJSON(List<Lite> list){
		JSONObject jObject=new JSONObject();	
		JSONArray lites=new JSONArray();
		for (Lite lite : list) {
			JSONObject liteObject=new JSONObject();
			liteObject.put("title", lite.title);
			if (lite.book!=null)
				liteObject.put("book", lite.book);
			if (lite.year!=0)
				liteObject.put("year", lite.year);
			liteObject.put("au1", lite.au1);
			if (lite.au2!=null)
				liteObject.put("au2", lite.au2);
			if (lite.au3!=null)
				liteObject.put("au3", lite.au3);
			liteObject.put("kwd1", lite.kwd1);
			liteObject.put("kwd2", lite.kwd2);
			liteObject.put("kwd3", lite.kwd3);
			lites.put(liteObject);			
		}
		jObject.put("lites", lites);
		return jObject.toString();
	}
}
