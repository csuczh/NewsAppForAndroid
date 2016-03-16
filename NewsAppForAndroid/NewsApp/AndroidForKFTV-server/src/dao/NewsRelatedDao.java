package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.NewsRelated;
import service.NewsRelatedService;

public class NewsRelatedDao implements NewsRelatedService{

	@Override
	public List<NewsRelated> getRelateds(String title) {
		// TODO Auto-generated method stub
		List<NewsRelated> list=new ArrayList<NewsRelated>();
		List<Object> params=new ArrayList<Object>();
		params.add(title);
		String sql="SELECT relatednews.newsrelated FROM relatednews where relatednews.newstitle=?";
		list=JdbcUtils.findMoreRefResult(sql, params, NewsRelated.class);
		if (list.size()==0) {
			System.out.println("list wei 0");
		}
		return list;
	}


}
