package service;

import java.util.Map;

import domin.User;

public interface UserService {

 public User getuser(String username);
 public Map<String, Object> getpwd(String username);
 public boolean inseruser(User user);
}
