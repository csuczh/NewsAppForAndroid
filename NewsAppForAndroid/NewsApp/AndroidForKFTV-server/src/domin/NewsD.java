package domin;

public class NewsD {
private String newstitle;
private String newssummary;
private String newsdate;
private String newssource;
private String pic;
private String newscount;
public NewsD()
{}
public NewsD(String newstitle, String newssummary, String newsdate,
		String newssource, String pic,String newscount) {
	super();
	this.newstitle = newstitle;
	this.newssummary = newssummary;
	this.newsdate = newsdate;
	this.newssource = newssource;
	this.pic = pic;
	this.newscount=newscount;
}
public String getNewscount() {
	return newscount;
}
public void setNewscount(String newscount) {
	this.newscount = newscount;
}
public String getNewstitle() {
	return newstitle;
}
public void setNewstitle(String newstitle) {
	this.newstitle = newstitle;
}
public String getNewssummary() {
	return newssummary;
}
public void setNewssummary(String newssummary) {
	this.newssummary = newssummary;
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
public String getPic() {
	return pic;
}
public void setPic(String pic) {
	this.pic = pic;
}


}
