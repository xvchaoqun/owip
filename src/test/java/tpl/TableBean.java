package tpl;

public class TableBean {

	private String name;
	private String comments;
	
	public TableBean(String name, String comments) {
		super();
		this.name = name;
		this.comments = comments;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
