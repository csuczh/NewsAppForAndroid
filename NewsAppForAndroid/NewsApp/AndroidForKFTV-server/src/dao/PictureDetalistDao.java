package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.NewsDetalist;
import domin.NewsPictures;
import domin.PictureDetalist;
import service.PictureDetalistService;

public class PictureDetalistDao implements PictureDetalistService {

	@Override
	public List<PictureDetalist> getList(String pnewtitle) {
		// TODO Auto-generated method stub
		List<PictureDetalist> list=new ArrayList<PictureDetalist>();
		List<Object> params=new ArrayList<Object>();
		params.add(pnewtitle);
		String sql="SELECT picture.picturetitle,picture.picturedescription FROM picture WHERE picture.pnewtitle=?";
		list=JdbcUtils.findMoreRefResult(sql,params,PictureDetalist.class);
		return list;
	}

}
