package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.NewsD;

import service.Newservice;

public class NewsDao implements Newservice{

	@Override
	public List<NewsD> getNewsDs() {
		// TODO Auto-generated method stub
		List<NewsD> list=new ArrayList<NewsD>();
		String sql="SELECT news.newstitle ,newssummary ,newsdate,newssource,(SELECT min(newspicture) FROM newspictures WHERE newspictures.newstitle = news.newstitle) pic , nc.count newscount from news LEFT JOIN (SELECT newscomments.newstitle as title,COUNT(newscomments.newstitle) as count FROM newscomments GROUP BY newscomments.newstitle)as nc ON news.newstitle=nc.title LIMIT 6;";
		List<Object> params=null;
		
		list=JdbcUtils.findMoreRefResult(sql,params,NewsD.class);
		return list ;
	}

	
}
