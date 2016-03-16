package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.Mp3Info;
import domin.VideoListItem;
import service.MusicListItem;

public class MusicListItemDao implements MusicListItem{

	@Override
	public List<Mp3Info> getInfos() {
		// TODO Auto-generated method stub
		List<Mp3Info> list=new ArrayList<Mp3Info>();
		List<Object> params=new ArrayList<Object>();
		String sql="SELECT music.musicname,music.musicauthor,music.musiclrc,music.musicfile FROM music";
		list=JdbcUtils.findMoreRefResult(sql,params,Mp3Info.class);
		return list;
	}

	

}
