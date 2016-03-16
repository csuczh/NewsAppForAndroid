package com.czh.androidforkftvrelease.domins;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * 用来封装歌词信息的类
 * @author Administrator
 *
 */
public class LrcInfo {
    private String title;//歌曲名
	private String singer;//演唱者
	private String album;//专辑	
	private Queue<Long> time;
	private Queue<String> message;
   //以下为getter()  setter()
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public Queue<Long> getTime() {
		return time;
	}
	public void setTime(Queue<Long> time) {
		this.time = time;
	}
	public Queue<String> getMessage() {
		return message;
	}
	public void setMessage(Queue<String> message) {
		this.message = message;
	}
	
	
	
	
}

