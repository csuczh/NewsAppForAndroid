package com.czh.androidforkftvrelease.domins;
public class VideoItem {
private String videotitle;
private String videodate;
private String videosummary;
private String videocontent;
private String videosource;
private String videopicture;
private String videopath;
private String videocommentcount;
public VideoItem() {
	super();
	// TODO Auto-generated constructor stub
}
public VideoItem(String videotitle, String videodate, String videosummary,
		String videocontent, String videosource, String videopicture,
		String videopath, String videocommentcount) {
	super();
	this.videotitle = videotitle;
	this.videodate = videodate;
	this.videosummary = videosummary;
	this.videocontent = videocontent;
	this.videosource = videosource;
	this.videopicture = videopicture;
	this.videopath = videopath;
	this.videocommentcount = videocommentcount;
}
public String getVideotitle() {
	return videotitle;
}
public void setVideotitle(String videotitle) {
	this.videotitle = videotitle;
}
public String getVideodate() {
	return videodate;
}
public void setVideodate(String videodate) {
	this.videodate = videodate;
}
public String getVideosummary() {
	return videosummary;
}
public void setVideosummary(String videosummary) {
	this.videosummary = videosummary;
}
public String getVideocontent() {
	return videocontent;
}
public void setVideocontent(String videocontent) {
	this.videocontent = videocontent;
}
public String getVideosource() {
	return videosource;
}
public void setVideosource(String videosource) {
	this.videosource = videosource;
}
public String getVideopicture() {
	return videopicture;
}
public void setVideopicture(String videopicture) {
	this.videopicture = videopicture;
}
public String getVideopath() {
	return videopath;
}
public void setVideopath(String videopath) {
	this.videopath = videopath;
}
public String getVideocommentcount() {
	return videocommentcount;
}
public void setVideocommentcount(String videocommentcount) {
	this.videocommentcount = videocommentcount;
}

}
