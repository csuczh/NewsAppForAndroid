package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.SingleComment;
import service.SingleCommentService;

public class SingleCommentDao implements SingleCommentService{

	@Override
	public boolean insertcomment(SingleComment single) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		params.add(single.getUsername());
		params.add(single.getNewstitle());
		params.add(single.getCommentdate());
		params.add(single.getCommentcontent());
		
		String sql="INSERT INTO newscomments(newscomments.username,newscomments.newstitle,newscomments.commentdate,newscomments.commentcontent) VALUES(?,?,?,?)";
	    boolean flag= JdbcUtils.updateBySql(sql, params);
	    return flag;
	}

	@Override
	public boolean updatecomment(SingleComment singleComment) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		
		params.add(singleComment.getCommentcontent());
		params.add(singleComment.getCommentdate());
		
		params.add(singleComment.getUsername());
		params.add(singleComment.getNewstitle());
		String sql="update newscomments set newscomments.commentcontent =?,newscomments.commentdate=? WHERE newscomments.username=? AND newscomments.newstitle=?";
	    boolean flag=JdbcUtils.updateBySql(sql, params);
	    return flag;
	}

	@Override
	public boolean search(String username, String newstitle) {
		// TODO Auto-generated method stub
		boolean flag=false;
		List<Object> params=new ArrayList<Object>();
		params.add(username);
		params.add(newstitle);
		String sql="SELECT newscomments.username,newscomments.newstitle,newscomments.commentcontent,newscomments.commentdate FROM newscomments WHERE newscomments.username=? AND newscomments.newstitle=?";
		SingleComment singleComment=JdbcUtils.findSimpleRefResult(sql, params, SingleComment.class);
		if(singleComment==null)
		{
			flag=true;
		}
		
		return flag;
	}

}
