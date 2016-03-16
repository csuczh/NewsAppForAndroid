package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.NewsD;
import domin.NewsPictures;
import service.NewsPicturesService;

public class NewsPictruesDao implements NewsPicturesService{

	@Override
	public List<NewsPictures> getNewsPictures(String title) {
		// TODO Auto-generated method stub
		List<NewsPictures> list=new ArrayList<NewsPictures>();
		List<Object> params=new ArrayList<Object>();
		params.add(title);
		String sql="select newspictures.newspicture,newspictures.newspicturetitle FROM newspictures WHERE newspictures.newstitle=? ";
		list=JdbcUtils.findMoreRefResult(sql,params,NewsPictures.class);
		return list;
	}

	

}
