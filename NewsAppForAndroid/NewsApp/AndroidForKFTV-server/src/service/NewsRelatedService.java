package service;

import java.util.List;

import domin.NewsRelated;

public interface NewsRelatedService {

	public List<NewsRelated> getRelateds(String title);

}
