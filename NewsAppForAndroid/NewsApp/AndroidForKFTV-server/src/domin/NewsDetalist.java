package domin;

import java.util.List;

public class NewsDetalist {
	
	private String newsdate;
	private String newssource;
	private String newscontent;
	private String commentcount;
	
	
	public NewsDetalist() {
		super();
	}
	public NewsDetalist(String newsdate, String newssource, String newscontent,
			String commentcount) {
		super();
		this.newsdate = newsdate;
		this.newssource = newssource;
		this.newscontent = newscontent;
		this.commentcount = commentcount;
	}
	public String getNewsdate() {
		return newsdate;
	}
	public void setNewsdate(String newsdate) {
		this.newsdate = newsdate;
	}
	public String getNewssource() {
		return newssource;
	}
	public void setNewssource(String newssource) {
		this.newssource = newssource;
	}
	public String getNewscontent() {
		return newscontent;
	}
	public void setNewscontent(String newscontent) {
		this.newscontent = newscontent;
	}
	public String getCommentcount() {
		return commentcount;
	}
	public void setCommentcount(String commentcount) {
		this.commentcount = commentcount;
	}
	
	
   
	
	
	
	
	
}
