package indi;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import lite.Lite;

public class Indi {
	private String name;
	private int sys;
	private double weight;
	private String source;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSys() {
		return sys;
	}

	public void setSys(int sys) {
		this.sys = sys;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Indi(int sys) {
		this.sys=sys;
	}
	
	public Indi(String name, double weight, String source){
		this.setName(name);
		this.setWeight(weight);
		this.setSource(source);
	}
	
}
