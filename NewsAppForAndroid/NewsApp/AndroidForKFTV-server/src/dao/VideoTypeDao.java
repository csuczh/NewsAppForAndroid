package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.PictureType;
import domin.VideoType;
import service.VideoTypeService;

public class VideoTypeDao implements VideoTypeService{

	@Override
	public List<VideoType> getTypes() {
		// TODO Auto-generated method stub
		List<VideoType> list = new ArrayList<VideoType>();
		List<Object> params = null;
		String sql = "SELECT videokind.videotype FROM videokind";
		list = JdbcUtils.findMoreRefResult(sql, params, VideoType.class);
		return list;

	}

}
