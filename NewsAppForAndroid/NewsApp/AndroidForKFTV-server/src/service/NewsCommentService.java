package service;

import java.util.List;

import domin.NewsComment;

public interface  NewsCommentService {

	public List<NewsComment> getComments(String title);
    public List<NewsComment> getAllComments(String title);
}
