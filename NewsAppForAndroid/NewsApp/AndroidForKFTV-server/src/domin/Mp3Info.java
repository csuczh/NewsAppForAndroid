package domin;

public class Mp3Info {

	private String musicname;
	private String musicauthor;
	private String musiclrc;
	private String musicfile;
	
	public Mp3Info() {
		super();
	}
	public Mp3Info(String musicname, String musicauthor, String musiclrc,
			String musicfile) {
		super();
		this.musicname = musicname;
		this.musicauthor = musicauthor;
		this.musiclrc = musiclrc;
		this.musicfile = musicfile;
	}
	public String getMusicname() {
		return musicname;
	}
	public void setMusicname(String musicname) {
		this.musicname = musicname;
	}
	public String getMusicauthor() {
		return musicauthor;
	}
	public void setMusicauthor(String musicauthor) {
		this.musicauthor = musicauthor;
	}
	public String getMusiclrc() {
		return musiclrc;
	}
	public void setMusiclrc(String musiclrc) {
		this.musiclrc = musiclrc;
	}
	public String getMusicfile() {
		return musicfile;
	}
	public void setMusicfile(String musicfile) {
		this.musicfile = musicfile;
	}
	

}
