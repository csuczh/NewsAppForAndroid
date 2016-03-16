package service;

import java.util.List;

import domin.VideoItem;
import domin.VideoListItem;

public interface VideoListItemService {
public List<VideoListItem> getItems(String type);
}
