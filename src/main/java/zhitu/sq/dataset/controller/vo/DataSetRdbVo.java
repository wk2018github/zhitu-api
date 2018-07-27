package zhitu.sq.dataset.controller.vo;

public class DataSetRdbVo {
	
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
    
    /**
     * 类型（本地数据库，远程数据库）
     */
    private String typeId;
    
    /**
     * 项目ID
     */
    private String projectId;
    
    /**
     * 数据集名称
     */
    private String name;
    
    /**
     * 数据集描述
     */
    private String description;
    
    /**
     * 数据库类型
     * @return
     */
    private String databaseType;
    
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
    

}
