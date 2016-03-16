package domin;

public class PictureItem {
private String pnewtitle;
private String picturesource;
private String pnewdate;
private String pic1;
private String pic2;
private String pic3;
private String picturecount;
private String picturecommentcount;

public PictureItem() {
	super();
	// TODO Auto-generated constructor stub
}
public PictureItem(String pnewtitle, String picturesource, String pnewdate,
		String pic1, String pic2, String pic3, String picturecount,String picturecommentcount) {
	super();
	this.pnewtitle = pnewtitle;
	this.picturesource = picturesource;
	this.pnewdate = pnewdate;
	this.pic1 = pic1;
	this.pic2 = pic2;
	this.pic3 = pic3;
	this.picturecount = picturecount;
	this.picturecommentcount=picturecommentcount;
}
public String getPicturecommentcount() {
	return picturecommentcount;
}
public void setPicturecommentcount(String picturecommentcount) {
	this.picturecommentcount = picturecommentcount;
}
public String getPnewtitle() {
	return pnewtitle;
}
public void setPnewtitle(String pnewtitle) {
	this.pnewtitle = pnewtitle;
}
public String getPicturesource() {
	return picturesource;
}
public void setPicturesource(String picturesource) {
	this.picturesource = picturesource;
}
public String getPnewdate() {
	return pnewdate;
}
public void setPnewdate(String pnewdate) {
	this.pnewdate = pnewdate;
}
public String getPic1() {
	return pic1;
}
public void setPic1(String pic1) {
	this.pic1 = pic1;
}
public String getPic2() {
	return pic2;
}
public void setPic2(String pic2) {
	this.pic2 = pic2;
}
public String getPic3() {
	return pic3;
}
public void setPic3(String pic3) {
	this.pic3 = pic3;
}
public String getPicturecount() {
	return picturecount;
}
public void setPicturecount(String picturecount) {
	this.picturecount = picturecount;
}

}
