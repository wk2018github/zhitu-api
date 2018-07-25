package zhitu.sq.dataset.model;

import java.io.Serializable;
import java.util.Date;

public class Rdb implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一ID，必须以`RDB_毫秒时间戳`为格式
	 */
    private String id;
    
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 数据库host
     */
    private String host;

    /**
     * 数据库port
     */
    private Integer port;
    
    /**
     * 数据字符集
     */
    private String charset;

    /**
     * 数据库用户名
     */
    private String user;
    
    /**
     * 数据库密码
     */
    private String password;
    
    /**
     * 数据库名
     */
    private String dbName;
    
    /**
     * 数据库表名
     */
    private String tableName;
    
    /**
     * 选取的列，多列用英文逗号分隔
     */
    private String columnNames;

	public Rdb() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rdb(String id, Date createTime, String host, Integer port, String charset, String user, String password,
			String dbName, String tableName, String columnNames) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.host = host;
		this.port = port;
		this.charset = charset;
		this.user = user;
		this.password = password;
		this.dbName = dbName;
		this.tableName = tableName;
		this.columnNames = columnNames;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	
    
    

}
