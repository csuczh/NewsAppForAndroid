package service;

import domin.SingleComment;

public interface SingleCommentService {
  public boolean insertcomment(SingleComment single);
  public boolean updatecomment(SingleComment singleComment);
  public boolean search(String username,String newstitle);
}
