package indi;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import lite.Lite;

public class Indi {
	private String indiID;
	private int indiRk;
	private String indiRptWd1;
	private String indiRptWd2;
	private String indiRptWd3;
	private String indiRptWd4;
	private String indiRptWd5;
	
	public String getIndiID() {
		return indiID;
	}

	public int getIndiRk() {
		return indiRk;
	}

	public String getIndiRptWd1() {
		return indiRptWd1;
	}

	public String getIndiRptWd2() {
		return indiRptWd2;
	}

	public String getIndiRptWd3() {
		return indiRptWd3;
	}

	public String getIndiRptWd4() {
		return indiRptWd4;
	}

	public String getIndiRptWd5() {
		return indiRptWd5;
	}

	public void setIndiID(String indiID) {
		this.indiID = indiID;
	}

	public void setIndiRk(int indiRk) {
		this.indiRk = indiRk;
	}

	public void setIndiRptWd1(String indiRptWd1) {
		this.indiRptWd1 = indiRptWd1;
	}

	public void setIndiRptWd2(String indiRptWd2) {
		this.indiRptWd2 = indiRptWd2;
	}

	public void setIndiRptWd3(String indiRptWd3) {
		this.indiRptWd3 = indiRptWd3;
	}

	public void setIndiRptWd4(String indiRptWd4) {
		this.indiRptWd4 = indiRptWd4;
	}

	public void setIndiRptWd5(String indiRptWd5) {
		this.indiRptWd5 = indiRptWd5;
	}

	public Indi(String indiID, int indiRk, String indiRptWd1, 
			String indiRptWd2, String indiRptWd3, String indiRptWd4, String indiRptWd5){
		this.indiID=indiID;
		this.indiRk=indiRk;
		this.indiRptWd1=indiRptWd1;
		this.indiRptWd2=indiRptWd2;
		this.indiRptWd3=indiRptWd3;
		this.indiRptWd4=indiRptWd4;
		this.indiRptWd5=indiRptWd5;
	}
	
	static String toJSON(List<Indi> list){
		JSONObject jObject=new JSONObject();	
		JSONArray indis=new JSONArray();
		for (Indi indi : list) {
			JSONObject liteObject=new JSONObject();
			liteObject.put("title", indi.indiID);
			if (indi.indiRk!=0)
				liteObject.put("rank", indi.indiRk);
			liteObject.put("rptwd1", indi.indiRptWd1);
			if (indi.indiRptWd2!=null)
				liteObject.put("rptwd2", indi.indiRptWd2);
			if (indi.indiRptWd3!=null)
				liteObject.put("rptwd3", indi.indiRptWd3);
			if (indi.indiRptWd4!=null)
				liteObject.put("rptwd4", indi.indiRptWd4);
			if (indi.indiRptWd5!=null)
				liteObject.put("rptwd5", indi.indiRptWd5);
			indis.put(liteObject);			
		}
		jObject.put("indis", indis);
		return jObject.toString();
	}
}
