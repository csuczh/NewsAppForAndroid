package com.czh.androidforkftvrelease.domins;
public class PictureDetalist {
private String picturetitle;
private String picturedescription;
public PictureDetalist() {
	super();
	// TODO Auto-generated constructor stub
}
public PictureDetalist(String picturetitle, String picturedescription) {
	super();
	this.picturetitle = picturetitle;
	this.picturedescription = picturedescription;
}
public String getPicturetitle() {
	return picturetitle;
}
public void setPicturetitle(String picturetitle) {
	this.picturetitle = picturetitle;
}
public String getPicturedescription() {
	return picturedescription;
}
public void setPicturedescription(String picturedescription) {
	this.picturedescription = picturedescription;
}

}
