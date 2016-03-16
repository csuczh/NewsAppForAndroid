package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;
import domin.NewsDetalist;
import domin.VideoItem;
import service.SingleVideoService;

public class SingleVideoDao implements SingleVideoService {

	@Override
	public VideoItem getItem(String title) {
		// TODO Auto-generated method stub
		VideoItem videoItem=new VideoItem();
		String sql="SELECT video.videotitle,video.videodate,video.videosummary,video.videocontent,video.videosource,video.videopicture,video.videopath ,COUNT(newscomments.username) videocommentcount from video ,newscomments WHERE  video.videotitle=newscomments.newstitle AND  video.videotitle=?";
		List<Object> params=new ArrayList<Object>();
		params.add(title);
		videoItem =JdbcUtils.findSimpleRefResult(sql,params,VideoItem.class);
		
		return videoItem;
	}

}
