package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdbc.JdbcUtils;
import domin.User;
import service.UserService;

public class usersDao implements UserService {

	@Override
	public User getuser(String username) {
		// TODO Auto-generated method stub
		User user=new User();
		List<Object> params=new ArrayList<Object>();
		params.add(username);
		String sql="SELECT users.username,users.address,users.headpicture,users.`password`,users.nickname from users WHERE users.username=?";
		
		user=JdbcUtils.findSimpleRefResult(sql, params, User.class);
		return user;
	}

	@Override
	public Map<String, Object> getpwd(String username) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		String sql="SELECT users.`password` FROM users WHERE users.username=?";
		
		List<Object> params=new ArrayList<Object>();
		params.add(username);
		
		map=JdbcUtils. findSimpleResult(sql,params);
		return map;
	}

	@Override
	public boolean inseruser(User user) {
		// TODO Auto-generated method stub
		
		List<Object> params=new ArrayList<Object>();
		params.add(user.getUsername());
		params.add(user.getPassword());
		params.add(user.getHeadpicture());
		params.add(user.getAddress());
		params.add(user.getNickname());
		
		String sql="INSERT INTO users VALUES(?,?,?,?,?);";
		boolean flag=JdbcUtils.updateBySql(sql, params);
		if(flag)
			System.out.println("insert user sucess!!!!!!");
		
		return flag;
	}

	

	

	

}
