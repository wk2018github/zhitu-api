package zhitu.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import zhitu.sq.dataset.model.Rdb;

public class JdbcDbUtils {

	/**
	 * 远程连接数据库mysql查询表显示需要字段
	 * @param rdb
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static List<Map<String, Object>> jdbcTable(Rdb rdb,int pageIndex,int pageSize) throws Exception{
		
		List<Map<String, Object>> list = new ArrayList<>();
		String url = "jdbc:mysql://"+rdb.getHost()+":"+rdb.getPort()+"/"+rdb.getDbName();//定义mysql数据库的URL	
		String user = rdb.getUser();	//定义访问数据库的帐号	
		String password = rdb.getPassword();	//定义访问数据库的密码	
		String sql = "select"+rdb.getColumnNames()+"from"+rdb.getTableName();	//定义查询的SQL语句	
		Connection conn = null;	//定义数据库的连接conn	
		PreparedStatement pStmt = null;	//定义盛装SQL语句的载体stmt	
		ResultSet rs = null;	//定义查询结果集rs
		try{
			//校验参数
	        if(pageIndex <= 0){
	            pageIndex = 1;
	        }
	        if(pageSize <= 0){
	            pageSize = 10;
	        }
			Class.forName("com.mysql.jdbc.Driver");	//<第2步>注册mysql数据库驱动	
			conn = DriverManager.getConnection(url,user,password);//<第3步>获取数据库连接	
			pStmt = conn.prepareStatement(sql);	//<第4步>获取盛装SQL语句的载体stmt	
	        pStmt.setMaxRows(pageIndex*pageSize);//设置最大查询到第几条记录
			rs = pStmt.executeQuery();	//<第5步>获取查询结果集rs	
	        rs.relative((pageIndex-1)*pageSize);//游标移动到要输出的第一条记录
			
	        if(rs != null){
	        	try {
	        		//数据库列名
	    	        ResultSetMetaData data= rs.getMetaData();
	    	        Map<String, Object> map = new HashMap<>();
	    	        //遍历结果   getColumnCount 获取表列个数
	    	        while (rs.next()) {
	    	        	for(int i=1;i<data.getColumnCount()+1;i++){
	    					System.out.println("***rs***="+data.getColumnName(i));	//输出结果集rs,若rs不为null,即获取连接
	    					System.out.println("***rs***="+rs.getString(i));	//输出结果集rs,若rs不为null,即获取连接
	    					map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
	    				}
	    				list.add(map);
	    	        }
	        	}finally{
					rs.close();	//<第6步>关闭结果集	
					pStmt.close();	//<第7步>关闭盛装SQL语句的载体	
					conn.close();	//<第8步>关闭数据库连接
				}
	        }
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return list;
	}
	

	
	/**
	 * 测试main
	 * @param args
	 */
	public static void main(String[] args) {
		List<Map<String, Object>> list = new ArrayList<>();
		String url = "jdbc:mysql://localhost:3306/db_kg";//定义mysql数据库的URL	
		String user = "root";	//定义访问数据库的帐号	
		String password = "123456";	//定义访问数据库的密码	
		String sql = "select id,name from zt_sys_dataset";	//定义查询的SQL语句	
		Connection conn = null;	//定义数据库的连接conn	
		Statement stmt = null;	//定义盛装SQL语句的载体stmt	
		ResultSet rs = null;	//定义查询结果集rs
		try {	
		Class.forName("com.mysql.jdbc.Driver");	//<第2步>注册mysql数据库驱动	
		conn = DriverManager.getConnection(url,user,password);//<第3步>获取数据库连接	
		stmt = conn.createStatement();	//<第4步>获取盛装SQL语句的载体stmt	
		rs = stmt.executeQuery(sql);	//<第5步>获取查询结果集rs	
		while (rs.next()) {
			Map<String, Object> map = new HashMap<>();
			for(int i=1;i<rs.getMetaData().getColumnCount()+1;i++){
				System.out.println("***rs***="+rs.getMetaData().getColumnName(i));	//输出结果集rs,若rs不为null,即获取连接
				System.out.println("***rs***="+rs.getString(i));	//输出结果集rs,若rs不为null,即获取连接
				map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
			}
			list.add(map);
		}

		} catch (Exception e) {	
			e.printStackTrace();
		}finally{
			try {	
				rs.close();	//<第6步>关闭结果集	
				stmt.close();	//<第7步>关闭盛装SQL语句的载体	
				conn.close();	//<第8步>关闭数据库连接
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(list);
		}
		
	}





}
