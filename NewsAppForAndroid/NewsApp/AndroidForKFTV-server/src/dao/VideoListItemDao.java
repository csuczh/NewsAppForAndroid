package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.PictureDetalist;
import domin.VideoItem;
import domin.VideoListItem;
import service.VideoListItemService;

public class VideoListItemDao implements VideoListItemService{

	@Override
	public List<VideoListItem> getItems(String type) {
		// TODO Auto-generated method stub
		List<VideoListItem> list=new ArrayList<VideoListItem>();
		List<Object> params=new ArrayList<Object>();
		params.add(type);
		String sql="SELECT video.videotitle,video.videodate,video.videosummary,video.videosource,video.videopicture,nc.count videocommentcount from video LEFT JOIN (SELECT newscomments.newstitle as title,COUNT(newscomments.newstitle) as count FROM newscomments GROUP BY newscomments.newstitle)as nc ON video.videotitle=nc.title WHERE video.videotype=? LIMIT 6";
		list=JdbcUtils.findMoreRefResult(sql,params,VideoListItem.class);
		return list;
	}

}
