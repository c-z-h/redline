package indi;

public class IndiSys {
	private String name;
	private int id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public IndiSys(String name, int id){
		this.setName(name);
		this.setId(id);
	}
}
