package com.czh.androidforkftvrelease.domins;

public class SingleComment {

	private String username;
	private String newstitle;
	private String commentdate;
	private String commentcontent;
	
	public SingleComment() {
		super();
	}
	public SingleComment(String username, String newstitle, String commentdate,
			String commentcontent) {
		super();
		this.username = username;
		this.newstitle = newstitle;
		this.commentdate = commentdate;
		this.commentcontent = commentcontent;
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
	public String getCommentdate() {
		return commentdate;
	}
	public void setCommentdate(String commentdate) {
		this.commentdate = commentdate;
	}
	public String getCommentcontent() {
		return commentcontent;
	}
	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}
	 
	

}
