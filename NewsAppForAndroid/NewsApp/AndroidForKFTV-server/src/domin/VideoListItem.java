package domin;

public class VideoListItem {
	private String videotitle;
	private String videodate;
	private String videosummary;
	private String videosource;
	private String videopicture;
	private String videocommentcount;
	public VideoListItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoListItem(String videotitle, String videodate,
			String videosummary, String videosource, String videopicture,
			String videocommentcount) {
		super();
		this.videotitle = videotitle;
		this.videodate = videodate;
		this.videosummary = videosummary;
		this.videosource = videosource;
		this.videopicture = videopicture;
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
	public String getVideocommentcount() {
		return videocommentcount;
	}
	public void setVideocommentcount(String videocommentcount) {
		this.videocommentcount = videocommentcount;
	}
	
}
