package service.source;

public class ColumnBean {

	public String name;
	public String type; // varchar,int,datetime,text
	public long length;
	public String comments;
	
	public ColumnBean(String name, String type, long length, String comments) {
		super();
		this.name = name;
		this.type = type;
		this.length = length;
		this.comments = comments;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
