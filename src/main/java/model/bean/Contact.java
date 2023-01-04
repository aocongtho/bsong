package model.bean;

public class Contact {
	private int id;
	private String name;
	private String email;
	private String webside;
	private String message;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebside() {
		return webside;
	}
	public void setWebside(String webside) {
		this.webside = webside;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Contact(String name, String email, String message) {
		super();
		this.name = name;
		this.email = email;
		this.message = message;
	}
	public Contact(int id, String name, String email, String webside, String message) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.webside = webside;
		this.message = message;
	}
	
	

}
