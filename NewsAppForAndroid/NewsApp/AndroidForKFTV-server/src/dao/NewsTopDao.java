package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.NewsTop;
import service.NewsTopService;

public class NewsTopDao implements NewsTopService {

	@Override
	public NewsTop getTop(String newstype) {
		// TODO Auto-generated method stub
		NewsTop newsTop=new NewsTop();
		List<Object> params=new ArrayList<Object>();
		params.add(newstype);
		String sql="SELECT newstop.newstitle FROM newstop WHERE newstop.newstype=?";
		newsTop=JdbcUtils.findSimpleRefResult(sql, params, NewsTop.class);
		return newsTop;
	}

	

}
