package domin;

public class User {

	private String username;
	private String password;
	private String headpicture;
	private String address;
	private String nickname;
	public User(String username, String password, String headpicture,
			String address, String nickname) {
		super();
		this.username = username;
		this.password = password;
		this.headpicture = headpicture;
		this.address = address;
		this.nickname = nickname;
	}
	public User() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHeadpicture() {
		return headpicture;
	}
	public void setHeadpicture(String headpicture) {
		this.headpicture = headpicture;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

}
