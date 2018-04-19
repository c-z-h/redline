package knowledgeGraph;

public class Entity{
	
	Entity (String name, String hierarchy,String comment){
		this.ename = name;
		this.hierarchy = hierarchy;
		this.comment = comment;
	}
	
	private String ename;
	private String hierarchy;
	private String comment;
	
	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getEname()
	{
		return ename;
	}

	public void setEname(String ename)
	{
		this.ename = ename;
	}

	public String getHierarchy()
	{
		return hierarchy;
	}

	public void setHierarchy(String hierarchy)
	{
		this.hierarchy = hierarchy;
	}

}