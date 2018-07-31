package zhitu.sq.dataset.controller.vo;

public class RdbVo {
	/**
     * 数据库host
     */
    private String host;

    /**
     * 数据库port
     */
    private Integer port;
    
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
     * 数据库类型
     * @return
     */
    private String databaseType;
    
    /**
	 * 当前第几页
	 */
    private Integer page;
    
    /**
	 * 显示多少条数据
	 */
    private Integer rows;

    
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

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
    
    
}
