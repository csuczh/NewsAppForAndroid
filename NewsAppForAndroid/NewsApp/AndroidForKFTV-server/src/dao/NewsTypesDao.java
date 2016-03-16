package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.NewsType;
import service.NewsTypeService;

public class NewsTypesDao implements NewsTypeService {

	@Override
	public List<NewsType> getTypes() {
		// TODO Auto-generated method stub
		List<NewsType> list=new ArrayList<NewsType>();
		List<Object> params=null;
		String sql="SELECT newstypes.newstype from newstypes";
		list=JdbcUtils.findMoreRefResult(sql, params, NewsType.class);
		return list;
	}

	

}
