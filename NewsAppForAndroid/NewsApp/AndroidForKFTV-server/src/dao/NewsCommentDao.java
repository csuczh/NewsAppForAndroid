package dao;

import java.util.ArrayList;
import java.util.List;
import jdbc.*;
import domin.NewsComment;
import service.NewsCommentService;

public class NewsCommentDao implements NewsCommentService{

	@Override
	public List<NewsComment> getComments(String title) {
		// TODO Auto-generated method stub
		List<NewsComment> list=new ArrayList<NewsComment>();
		List<Object> params=new ArrayList<Object>();
		params.add(title);
		String sql="SELECT newscomments.username,newscomments.commentdate,newscomments.commentcontent,users.address ,users.headpicture  FROM newscomments,users  WHERE newscomments.newstitle=? AND newscomments.username=users.username and newscomments.newscheck=1";
		list=JdbcUtils.findMoreRefResult(sql, params, NewsComment.class);
		return list;
	}

	@Override
	public List<NewsComment> getAllComments(String title) {
		// TODO Auto-generated method stub
		List<NewsComment> list=new ArrayList<NewsComment>();
		List<Object> params=new ArrayList<Object>();
		params.add(title);
		String sql="SELECT newscomments.username,newscomments.commentdate,newscomments.commentcontent,users.address ,users.headpicture  FROM newscomments,users  WHERE newscomments.newstitle=? AND newscomments.username=users.username and newscomments.newscheck=1";
		list=JdbcUtils.findMoreRefResult(sql, params, NewsComment.class);
		return list;
	}

	


}
