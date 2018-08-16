package zhitu.util;

import java.io.IOException;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class DataSourceUtil {

	public static String HIVE_DRIVER_CLASS = "org.apache.hive.jdbc.HiveDriver";
	public static final String HIVE_URL_PREFIX = "jdbc:hive2://";

	private final static Log logger = LogFactory.getLog(DataSourceUtil.class);

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 * @throws ConfigurationException
	 */
	public FTPClient getFTPClient() {
		FTPClient ftpClient = null;
		try {
			Configuration config = new PropertiesConfiguration("file.properties");
			ftpClient = new FTPClient();
			ftpClient.connect(config.getString("ftp.ip"), config.getInt("ftp.port"));// 连接FTP服务器
			ftpClient.login(config.getString("ftp.username"), config.getString("ftp.password"));// 登陆FTP服务器
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding("GBK");
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				logger.info("FTP连接成功。");
			}
		} catch (SocketException e) {
			logger.info("FTP的IP地址可能错误，请正确配置。", e);
		} catch (IOException e) {
			logger.info("FTP的端口错误,请正确配置。", e);
		} catch (ConfigurationException e) {
			logger.info("new 配置文件出问题", e);
		}
		return ftpClient;
	}

	/**
	 * 获取mysql数据库连接
	 * 
	 * @param map
	 * @param ip
	 *            数据库ip
	 * @param port
	 *            端口
	 * @param databasename
	 *            数据库名称
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public Connection getMySqlConn(Map<String, Object> map) throws Exception {
		// jdbc:mysql://192.168.100.111:30620/ldp_dev
		String ip = StringHandler.objectToString(map.get("ip"));
		String port = StringHandler.objectToString(map.get("port"));
		String databasename = StringHandler.objectToString(map.get("databasename"));
		String adress = "jdbc:mysql://" + ip + ":" + port + "/" + databasename;
		String driverName = "com.mysql.jdbc.Driver";

		// 加载驱动
		Class.forName(driverName);
		String username = StringHandler.objectToString(map.get("username"));
		String password = StringHandler.objectToString(map.get("password"));
		Connection connection = DriverManager.getConnection(adress, username, password);
		if (null != connection) {
			System.out.println("connection success");
			return connection;
		}
		return null;
	}

	/**
	 * 获取oracle数据库连接
	 * 
	 * @param map
	 * @param ip
	 *            数据库ip
	 * @param port
	 *            端口
	 * @param databasename
	 *            数据库名称
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public Connection getOracleConn(Map<String, Object> map) throws Exception {
		// jdbc:Oracle:thin:@10.127.130.58:1521/orcl
		String ip = StringHandler.objectToString(map.get("ip"));
		String port = StringHandler.objectToString(map.get("port"));
		String databasenaem = StringHandler.objectToString(map.get("databasename"));
		String adress = "jdbc:Oracle:thin:@" + ip + ":" + port + "/" + databasenaem;
		String driverName = "oracle.jdbc.driver.OracleDriver";

		// 加载驱动
		Class.forName(driverName);
		String username = StringHandler.objectToString(map.get("username"));
		String password = StringHandler.objectToString(map.get("password"));
		Connection connection = DriverManager.getConnection(adress, username, password);
		if (null != connection) {
			System.out.println("connection success");
			return connection;
		}
		return null;
	}

	/**
	 * 获取sqlserver数据库连接
	 * 
	 * @param map
	 * @param ip
	 *            数据库ip
	 * @param port
	 *            端口
	 * @param databasename
	 *            数据库名称
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public Connection getSqlServerConn(Map<String, Object> map) throws Exception {
		// jdbc:sqlserver://localhost:1433;DatabaseName=People
		String ip = StringHandler.objectToString(map.get("ip"));
		String port = StringHandler.objectToString(map.get("port"));
		String databasenaem = StringHandler.objectToString(map.get("databasename"));
		String url = "jdbc:sqlserver://" + ip + ":" + port + ";DatabaseName=" + databasenaem;
		String username = StringHandler.objectToString(map.get("username"));
		String password = StringHandler.objectToString(map.get("password"));
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		// 1.加载驱动程序
		Class.forName(driverName);
		// 2.获得数据库的连接
		Connection conn = DriverManager.getConnection(url, username, password);
		if (null != conn) {
			System.out.println("connection success");
			return conn;
		}
		return null;
	}

	/**
	 * 查询hive中的数据库
	 * 
	 * @param hostIp
	 * @param port
	 * @param username
	 * @param password
	 * @param instancesOrdataBaseName
	 * @return
	 * @throws Exception
	 */
	public List<String> queryDataBaseInHive(String hostIp, int port, String username, String password,
			String instancesOrdataBaseName) throws Exception {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			String class_driver = HIVE_DRIVER_CLASS;
			Class.forName(class_driver); // 加载驱动
			String url = HIVE_URL_PREFIX + hostIp + ":" + port + "/";
			con = DriverManager.getConnection(url, username, password); // 链接大数据
			stmt = con.createStatement();
			res = stmt.executeQuery("show databases;");
			List<String> list = new ArrayList<String>();
			while (res.next()) {
				list.add(res.getString(1));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeConnect(con, stmt, res);
		}
	}

	/**
	 * 查询HIVE指定数据库中的表
	 * 
	 * @param hostIp
	 * @param port
	 * @param username
	 * @param password
	 * @param instancesOrdataBaseName
	 * @return
	 * @throws Exception
	 */
	public List<String> queryDataBaseTableInHive(String hostIp, int port, String username, String password,
			String instancesOrdataBaseName) throws Exception {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			Class.forName(HIVE_DRIVER_CLASS); // 加载驱动
			String url = HIVE_URL_PREFIX + hostIp + ":" + port + "/" + instancesOrdataBaseName;
			con = DriverManager.getConnection(url, username, password); // 链接大数据
			stmt = con.createStatement();
			res = stmt.executeQuery("show tables;");

			List<String> list = new ArrayList<String>();
			while (res.next()) {
				list.add(res.getString(1));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeConnect(con, stmt, res);
		}
	}

	/**
	 * 查询hive中指定表字段
	 * 
	 * @param hostIp
	 * @param port
	 * @param username
	 * @param password
	 * @param dataBaseName
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryHibeTableColInHive(String hostIp, int port, String username, String password,
			String dataBaseName, String tableName) throws Exception {
		Connection con = null; // 定义一个链接对象
		Statement stmt = null; // 创建声明
		ResultSet res = null;
		try {
			Class.forName(HIVE_DRIVER_CLASS); // 加载驱动
			String url = HIVE_URL_PREFIX + hostIp + ":" + port + "/" + dataBaseName;//
			if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
				con = DriverManager.getConnection(url, username, password); // 链接大数据
			} else {
				con = DriverManager.getConnection(url); // 链接大数据
			}
			String sql = "select column_name as colname,column_type as typename from system.columns_v where database_name='"
					+ dataBaseName + "' AND table_name = '" + tableName + "' ORDER BY table_name,column_id ASC";
			stmt = con.createStatement();
			res = stmt.executeQuery(sql);
			Map<String, Object> map = new HashMap<String, Object>();
			while (res.next()) {
				map.put(res.getString("colname"), res.getObject("typename").toString());
			}
			return map;
		} catch (Exception e) {
			logger.error(e.toString());
			throw e;
		} finally {
			closeConnect(con, stmt, res);
		}
	}

	/**
	 * 查询Mysql指定数据库中的表
	 * 
	 * @param hostIp
	 * @param port
	 * @param username
	 * @param password
	 * @param dbname
	 * @return
	 * @throws Exception
	 */
	public List<String> queryDataBaseTableInMysql(String ip, int port, String username, String password,
			String databaseName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ip", ip);
			map.put("port", port);
			map.put("databasename", databaseName);
			map.put("username", username);
			map.put("password", password);
			con = getMySqlConn(map);
			if (null != con) {
				stmt = con.createStatement();
				res = stmt.executeQuery("show tables;");
				List<String> list = new ArrayList<String>();
				while (res.next()) {
					list.add(res.getString(1));
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnect(con, stmt, res);
		}
		return null;
	}

	/**
	 * 关闭连接
	 * 
	 * @param con
	 * @param stmt
	 * @param res
	 * @throws SQLException
	 */
	private void closeConnect(Connection con, Statement stmt, ResultSet res) {
		try {
			if (null != con) {
				con.close();
			}
			if (null != res) {
				res.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		} catch (SQLException e) {
			logger.error("DataSourceUtil/closeConnect", e);
		}
	}

	/**
	 * 查询mysql数据库指定表中的数据
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @param databaseName
	 * @param tableName
	 * @return
	 */
	public Map<String, Object> queryTableData(String ip, int port, String userName, String password,
			String databaseName, String tableName, Integer start, Integer end) {
		Map<String, Object> result = new HashMap<String, Object>();

		List<String> list = null;
		Connection conn = null;
		Statement stmt = null;
		Statement stmtCount = null;

		String sql = "select * from " + tableName + " LIMIT " + start + "," + end;
		String sqlCount = "select count(*) from " + tableName;

		ResultSet res = null;
		ResultSet count = null;
		JSONArray array = null;

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ip", ip);
			map.put("port", port);
			map.put("databasename", databaseName);
			map.put("username", userName);
			map.put("password", password);
			conn = getMySqlConn(map);
			stmt = conn.createStatement();
			stmtCount = conn.createStatement();

			count = stmtCount.executeQuery(sqlCount);
			// 获取列数
			ResultSetMetaData metaDataCount = count.getMetaData();
			// 遍历ResultSet中的每条数据
			while (count.next()) {
				// 遍历列
				String columnName = metaDataCount.getColumnLabel(1);
				Integer value = count.getInt(columnName);
				logger.info(tableName + "中数据总条数:" + value);
				// if(value<1){
				// result.put("total", 0);
				// return result;
				// }
				result.put("total", value);
			}

			res = stmt.executeQuery(sql);
			// json数组
			array = new JSONArray();
			// 获取列数
			ResultSetMetaData metaData = res.getMetaData();
			int columnCount = metaData.getColumnCount();
			// 遍历ResultSet中的每条数据
			while (res.next()) {
				JSONObject jsonObj = new JSONObject();
				// 遍历每一列
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i);
					String value = res.getString(columnName);
					jsonObj.put(columnName, value);
				}
				array.put(jsonObj);
			}
			list = new ArrayList<String>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				list.add(obj.toString());
			}

			result.put("list", list);

			conn.close();
			stmt.close();
			stmtCount.close();
			res.close();
			count.close();

			return result;
		} catch (Exception e) {
			logger.error("dataSourceUtil/queryTableData", e);
			return null;
		}
	}

	/**
	 * 查询mysql数据库指定表的所有列
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @param databaseName
	 * @param tableName
	 * @return
	 */
	public List<String> queryTableColumn(String ip, int port, String userName, String password, String databaseName,
			String tableName) {
		List<String> list = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "SHOW COLUMNS FROM " + tableName;

		ResultSet res = null;
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ip", ip);
			map.put("port", port);
			map.put("databasename", databaseName);
			map.put("username", userName);
			map.put("password", password);
			conn = getMySqlConn(map);

			stmt = conn.createStatement();
			res = stmt.executeQuery(sql);

			// 获取列数
			ResultSetMetaData metaData = res.getMetaData();
			// int columnCount = metaData.getColumnCount();
			list = new ArrayList<String>();
			// 遍历ResultSet中的每条数据
			while (res.next()) {
				String columnName = metaData.getColumnLabel(1);
				String value = res.getString(columnName);
				list.add(value);
			}
			return list;
		} catch (Exception e) {
			logger.error("dataSourceUtil/queryTableColumn", e);
			return null;
		} finally {
			closeConnect(conn, stmt, res);
		}
	}

	/**
	 * 查询远程mysql数据库指定表的指定的列的数据
	 * 
	 * @param ip
	 * @param port
	 * @param userName
	 * @param password
	 * @param databaseName
	 * @param tableName
	 * @param columnNames
	 * @return
	 */
	public Map<String, Object> queryTableColumnData(String ip, int port, String userName, String password,
			String databaseName, String tableName, String columnNames, Integer start, Integer end) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> list = null;
		Connection conn = null;
		Statement stmt = null;
		Statement stmtCount = null;
		String sql = "select " + columnNames + " from " + tableName + " LIMIT " + start + "," + end;
		String sqlCount = "select count(*) from " + tableName;

		ResultSet res = null;
		ResultSet count = null;
		JSONArray array = null;

		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ip", ip);
			map.put("port", port);
			map.put("databasename", databaseName);
			map.put("username", userName);
			map.put("password", password);
			conn = getMySqlConn(map);

			stmtCount = conn.createStatement();
			count = stmtCount.executeQuery(sqlCount);
			ResultSetMetaData metaDataCount = count.getMetaData();
			// 遍历ResultSet中的每条数据
			while (count.next()) {
				// 遍历列
				String columnName = metaDataCount.getColumnLabel(1);
				Integer value = count.getInt(columnName);
				logger.info(tableName + "中数据总条数:" + value);
				result.put("total", value);
			}

			stmt = conn.createStatement();
			res = stmt.executeQuery(sql);

			// json数组
			array = new JSONArray();

			// 获取列数
			ResultSetMetaData metaData = res.getMetaData();
			int columnCount = metaData.getColumnCount();

			// 遍历ResultSet中的每条数据
			while (res.next()) {
				JSONObject jsonObj = new JSONObject();
				// 遍历每一列
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i);
					String value = res.getString(columnName);
					jsonObj.put(columnName, value);
				}
				array.put(jsonObj);
			}

			list = new ArrayList<String>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				list.add(obj.toString());
			}

			result.put("list", list);
			return result;
		} catch (Exception e) {
			logger.error("dataSourceUtil/queryTableData", e);
			return null;
		} finally {
			closeConnect(conn, stmt, res);
			closeConnect(null, stmtCount, count);
		}
	}

	/**
	 * 查询本地mysql数据库指定表的指定的列的数据
	 * 
	 * @param ip
	 * @param port
	 * @param userName
	 * @param password
	 * @param databaseName
	 * @param tableName
	 * @param columnNames
	 * @return
	 */
	public Map<String, Object> queryLocalTableColumnData(String url, String userName, String password, String tableName,
			String columnNames, Integer start, Integer end) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> list = null;
		Connection conn = null;
		Statement stmt = null;
		Statement stmtCount = null;
		String sql = "select " + columnNames + " from " + tableName + " LIMIT " + start + "," + end;
		String sqlCount = "select count(*) from " + tableName;
		ResultSet res = null;
		ResultSet count = null;
		JSONArray array = null;

		try {

			String driverName = "com.mysql.jdbc.Driver";

			// 加载驱动
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, userName, password);
			if (null == conn) {
				return null;
			}
			stmtCount = conn.createStatement();
			count = stmtCount.executeQuery(sqlCount);
			ResultSetMetaData metaDataCount = count.getMetaData();
			// 遍历ResultSet中的每条数据
			while (count.next()) {
				// 遍历列
				String columnName = metaDataCount.getColumnLabel(1);
				Integer value = count.getInt(columnName);
				logger.info(tableName + "中数据总条数:" + value);
				result.put("total", value);
			}

			stmt = conn.createStatement();
			res = stmt.executeQuery(sql);

			// json数组
			array = new JSONArray();

			// 获取列数
			ResultSetMetaData metaData = res.getMetaData();
			int columnCount = metaData.getColumnCount();

			// 遍历ResultSet中的每条数据
			while (res.next()) {
				JSONObject jsonObj = new JSONObject();
				// 遍历每一列
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i);
					String value = res.getString(columnName);
					jsonObj.put(columnName, value);
				}
				array.put(jsonObj);
			}
			list = new ArrayList<String>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				list.add(obj.toString());
			}
			result.put("list", list);

			return result;
		} catch (Exception e) {
			logger.error("dataSourceUtil/queryLocalTableColumnData", e);
			return null;
		} finally {
			closeConnect(conn, stmt, res);
			closeConnect(null, stmtCount, count);
		}
	}

	/**
	 * 查询本地mysql数据库指定表的指定的列的数据(不分页)
	 * 
	 * @param ip
	 * @param port
	 * @param userName
	 * @param password
	 * @param databaseName
	 * @param tableName
	 * @param columnNames
	 * @return
	 */
	public List<String> queryLocalTableColumnDataNoLimit(String url, String userName, String password, String tableName,
			String columnNames) {
		List<String> list = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		if(null == columnNames){
			sql = "select * from " + tableName;
		} else {
			sql = "select " + columnNames + " from " + tableName;
		}

		ResultSet res = null;
		JSONArray array = null;

		try {

			String driverName = "com.mysql.jdbc.Driver";

			// 加载驱动
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, userName, password);
			if (null == conn) {
				return null;
			}

			stmt = conn.createStatement();
			res = stmt.executeQuery(sql);

			// json数组
			array = new JSONArray();

			// 获取列数
			ResultSetMetaData metaData = res.getMetaData();
			int columnCount = metaData.getColumnCount();

			// 遍历ResultSet中的每条数据
			while (res.next()) {
				JSONObject jsonObj = new JSONObject();
				// 遍历每一列
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i);
					String value = res.getString(columnName);
					jsonObj.put(columnName, value);
				}
				array.put(jsonObj);
			}
			list = new ArrayList<String>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				list.add(obj.toString());
			}

			return list;
		} catch (Exception e) {
			logger.error("dataSourceUtil/queryLocalTableColumnDataNoLimit", e);
			return null;
		} finally {
			// closeConnect(conn, stmt, res);
		}
	}

	/**
	 * 查询远程mysql数据库指定表的指定的列的数据(不分页)
	 * 
	 * @param ip
	 * @param port
	 * @param userName
	 * @param password
	 * @param databaseName
	 * @param tableName
	 * @param columnNames
	 * @return
	 */
	public List<String> queryTableColumnDataNoLimit(String ip, int port, String userName, String password,
			String databaseName, String tableName, String columnNames) {

		List<String> list = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = "select " + columnNames + " from " + tableName;

		ResultSet res = null;
		JSONArray array = null;

		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ip", ip);
			map.put("port", port);
			map.put("databasename", databaseName);
			map.put("username", userName);
			map.put("password", password);
			conn = getMySqlConn(map);

			stmt = conn.createStatement();
			res = stmt.executeQuery(sql);

			// json数组
			array = new JSONArray();

			// 获取列数
			ResultSetMetaData metaData = res.getMetaData();
			int columnCount = metaData.getColumnCount();

			// 遍历ResultSet中的每条数据
			while (res.next()) {
				JSONObject jsonObj = new JSONObject();
				// 遍历每一列
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i);
					String value = res.getString(columnName);
					jsonObj.put(columnName, value);
				}
				array.put(jsonObj);
			}

			list = new ArrayList<String>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				list.add(obj.toString());
			}

			return list;
		} catch (Exception e) {
			logger.error("dataSourceUtil/queryTableColumnDataNoLimit", e);
			return null;
		} finally {
			closeConnect(conn, stmt, res);
		}
	}

	public void main(String[] args) {
//		FTPClient ftpClient = getFTPClient();
		// ftpClient.r
	}
}
