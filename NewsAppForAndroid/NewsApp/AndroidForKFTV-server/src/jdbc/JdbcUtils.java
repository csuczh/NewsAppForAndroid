package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.sql.*;


import java.lang.reflect.*;

public class JdbcUtils {

	
	public static Connection getConnection()
	{
		Connection connection=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/androidnews", "root", "123456");
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库连接失败！！！！");
		}
		return connection;
	}
	public static boolean updateBySql(String sql,List<Object> params) 
	{
		boolean flag=false;
		int result=-1;
		try {
			Connection connection=getConnection();
			PreparedStatement statement=connection.prepareStatement(sql);
			if(params!=null&&!params.isEmpty())
			{
				for(int i=0;i<params.size();i++)
				     statement.setObject(i+1, params.get(i));
				
			}
			result=statement.executeUpdate();
			flag=result>0?true:false;
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	public static Map<String, Object> findSimpleResult(String sql,List<Object> params)
	{
		Map<String, Object> map=new HashMap<String, Object>();
		Connection connection=getConnection();
		try {
			PreparedStatement statement=connection.prepareStatement(sql);
			if(params!=null&&!params.isEmpty())
			{
				for(int i=0;i<params.size();i++)
				{
				   statement.setObject(i+1, params.get(i));
				}
				
			}
			ResultSet resultSet=statement.executeQuery();
			ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
			while(resultSet.next())
			{
			   for(int i=0;i<resultSetMetaData.getColumnCount();i++)
			 {
				String name=resultSetMetaData.getColumnName(i+1);
				Object value=resultSet.getObject(name);
				if(value==null)
					value="";
				value=value.toString();
				map.put(name, value);
			 }
			}
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return map;
	}
	public static List<Map<String, Object>> findMoreResult(String sql,List<Object> params)
	{
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Connection connection=getConnection();
		try {
			PreparedStatement statement=connection.prepareStatement(sql);
			if(params!=null&&!params.isEmpty())
			{
				for(int i=0;i<params.size();i++)
				{
				   statement.setObject(i+1, params.get(i));
				}
				
			}
			ResultSet resultSet=statement.executeQuery();
			ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
			while(resultSet.next())
			{
				Map<String, Object> map=new HashMap<String, Object>();
			   for(int i=0;i<resultSetMetaData.getColumnCount();i++)
			 {
				String name=resultSetMetaData.getColumnName(i+1);
				Object value=resultSet.getObject(name);
				if(value==null)
					value="";
					value=value.toString();
				map.put(name, value);
			 }
			   list.add(map);
			}
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}
	public static <T> T findSimpleRefResult(String sql,List<Object> params,Class<T> clas)
	{
		T t=null;
		Connection connection=getConnection();
		try {
			PreparedStatement statement=connection.prepareStatement(sql);
			if(params!=null&&!params.isEmpty())
			{
				for(int i=0;i<params.size();i++)
				{
				   statement.setObject(i+1, params.get(i));
				}
				
			}
			ResultSet resultSet=statement.executeQuery();
			ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
			while(resultSet.next())
			{
				t=clas.newInstance();
			   for(int i=0;i<resultSetMetaData.getColumnCount();i++)
			 {
				String name=resultSetMetaData.getColumnName(i+1);
				Object value=resultSet.getObject(name);
				if(value==null)
					value="";
				value=value.toString();
		     	Field field=clas.getDeclaredField(name);
		     	field.setAccessible(true);
		     	field.set(t, value);
				
			 }
			 
			}
			statement.close();
			connection.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return t;
		
	}
	public static <T> List<T> findMoreRefResult(String sql,List<Object> params,Class<T> clas)
	{
		List<T> list=new ArrayList<T>();
		Connection connection=getConnection();
		try {
			PreparedStatement statement=connection.prepareStatement(sql);
			if(params!=null&&!params.isEmpty())
			{
				for(int i=0;i<params.size();i++)
				{
				   statement.setObject(i+1, params.get(i));
				}
				
			}
			ResultSet resultSet=statement.executeQuery();
			ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
			while(resultSet.next())
			{
			   T	t=clas.newInstance();
			   for(int i=0;i<resultSetMetaData.getColumnCount();i++)
			 {
				String name=resultSetMetaData.getColumnName(i+1);
				Object value=resultSet.getObject(name);
				if(value==null)
					value="";
				value=value.toString();
		     	Field field=clas.getDeclaredField(name);
		     	field.setAccessible(true);
		     	field.set(t, value);
				
			 }
			   list.add(t);
			 
			}
			statement.close();
			connection.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	

}
