package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.NewsRelated;
import domin.VideoRelated;
import service.VideoRelatedService;

public class VideoRelatedDao implements VideoRelatedService {

	@Override
	public List<VideoRelated> getRelateds(String title) {
		// TODO Auto-generated method stub
		List<VideoRelated> list=new ArrayList<VideoRelated>();
		List<Object> params=new ArrayList<Object>();
		params.add(title);
		String sql="SELECT videorelate.videorelatetitle from videorelate WHERE videorelate.videotitle=?";
		list=JdbcUtils.findMoreRefResult(sql, params, VideoRelated.class);
		
		return list;
	}

}
