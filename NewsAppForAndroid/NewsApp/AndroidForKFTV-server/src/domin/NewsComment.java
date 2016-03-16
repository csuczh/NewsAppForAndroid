package domin;

public class NewsComment {
	private String username;
	private String newstitle;
	private String commentcontent;
	private String headpicture;
	private String commentdate;
	private String address;
	public NewsComment() {
		super();
	}
	public NewsComment(String username, String newstitle, String commentcontent,
			String headpicture, String commentdate,String address) {
		super();
		this.username = username;
		this.newstitle = newstitle;
		this.commentcontent = commentcontent;
		this.headpicture = headpicture;
		this.commentdate = commentdate;
		this.address=address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNewstitle() {
		return newstitle;
	}
	public void setNewstitle(String newstitle) {
		this.newstitle = newstitle;
	}
	public String getCommentcontent() {
		return commentcontent;
	}
	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}
	public String getHeadpicture() {
		return headpicture;
	}
	public void setHeadpicture(String headpicture) {
		this.headpicture = headpicture;
	}
	public String getCommentdate() {
		return commentdate;
	}
	public void setCommentdate(String commentdate) {
		this.commentdate = commentdate;
	}
	public String getAddress(){
		return address;
	}
	public void setAddress(String address)
	{
		this.address=address;
	}



}
