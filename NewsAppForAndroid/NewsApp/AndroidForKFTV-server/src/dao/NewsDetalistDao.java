package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.NewsDetalist;
import service.NewsDetalistservice;

public class NewsDetalistDao implements NewsDetalistservice {

	@Override
	public NewsDetalist getDetalists(String title) {
		// TODO Auto-generated method stub
		NewsDetalist newsDetalist=new NewsDetalist();
		String sql="SELECT news.newsdate,news.newssource,news.newscontent , COUNT(newscomments.username)  commentcount  "+
 "FROM news,newscomments  WHERE news.newstitle= ? AND news.newstitle=newscomments.newstitle";
		List<Object> params=new ArrayList<Object>();
		params.add(title);
		newsDetalist =JdbcUtils.findSimpleRefResult(sql,params,NewsDetalist.class);
		
		return newsDetalist;
	}

	

}
