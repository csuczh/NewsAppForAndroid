package com.czh.androidforkftvrelease.domins;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * ������װ�����Ϣ����
 * @author Administrator
 *
 */
public class LrcInfo {
    private String title;//������
	private String singer;//�ݳ���
	private String album;//ר��	
	private Queue<Long> time;
	private Queue<String> message;
   //����Ϊgetter()  setter()
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

