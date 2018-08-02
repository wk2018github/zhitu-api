package zhitu.util;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zhitu.sq.dataset.model.Rdb;
import zhitu.sq.dataset.model.TaskInfo;
import zhitu.sq.dataset.service.TaskInfoService;

@Component
public class SparkSql {
	
	public static TaskInfoService staticTaskInfoService;
	@Autowired
	public void setStaticTaskInfoService(TaskInfoService taskInfoService) {
		staticTaskInfoService = taskInfoService;
	}
	
    public static void main(String[] args){
        /*String url = "jdbc:mysql://172.18.32.91:3306/db_kg?useUnicode=true";
        String encoding = "UTF-8";
        String user = "root";//数据库用户名
        String password = "142536";//数据库密码
        String fromTable = "zt_sys_user";//远程数据库表名
        String field = "id,createTime,email";//导入的字段*/
        String targetTable = "ss";//导入到本地之后的数据库表名
        String userId = "";
        Rdb rdb = new Rdb();
        rdb.setHost("192.168.100.111");
        rdb.setPort(30620);
        rdb.setDbName("ldp_test");
        rdb.setCharset("UTF-8");
        rdb.setUser("root");
        rdb.setPassword("123456");
        rdb.setTableName("ldp_asset_object");
        rdb.setColumnNames("id,code,name");
        migration(rdb,targetTable,userId);
    }

    /**
     * 数据迁移
     * @param  rdb
     * @param  targetTable
     */
    public static void migration(Rdb rdb,String targetTable,String taskId){

//    	TaskInfoService taskInfoService = (TaskInfoService) SpringContextUtil.getBean("taskInfoService");
    	
        String host = rdb.getHost();//远程数据库主机IP
        Integer port = rdb.getPort();//远程数据库端口号
        String dbName = rdb.getDbName();//远程数据库库名
        String encoding = rdb.getCharset();//远程数据库编码方式
        String user = rdb.getUser();//数据库用户名
        String password = rdb.getPassword();//数据库密码
        String fromTable = rdb.getTableName();//远程数据库表名
        String field = rdb.getColumnNames();//导入的字段
        if(StringHandler.isEmptyOrNull(host)||StringHandler.isEmptyOrNull(port)||StringHandler.isEmptyOrNull(dbName)||
                StringHandler.isEmptyOrNull(encoding)||StringHandler.isEmptyOrNull(user)||StringHandler.isEmptyOrNull(password)||
                StringHandler.isEmptyOrNull(fromTable)||StringHandler.isEmptyOrNull(field)){
            System.out.println("远程数据库必填信息项有为空的项！！");
            return;
        }
        //task新增接口，任务启动后向zt_sys_task_info表中新增一条记录。
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useUnicode=true";

        try {
            /*String url = "jdbc:mysql://" + rdb.getHost() + ":" + rdb.getPort() + "/" + rdb.getDbName() + "?useUnicode=true";
            String encoding = rdb.getCharset();
            String user = rdb.getUser();//数据库用户名
            String password = rdb.getPassword();//数据库密码
            String fromTable = rdb.getTableName();//远程数据库表名
            String field = rdb.getColumnNames();//导入的字段*/

            String pythonExePath = "E:\\\\Spark\\bin\\spark-submit.cmd";
            CommandLine cmdLine = CommandLine.parse(pythonExePath);
            cmdLine.addArgument("--packages");
            cmdLine.addArgument("mysql:mysql-connector-java:5.1.46");
            cmdLine.addArgument("E:\\zhitu-etl\\etl.py");
            cmdLine.addArgument("--url").addArgument(url);
            cmdLine.addArgument("--encoding").addArgument(encoding);
            cmdLine.addArgument("--user").addArgument(user);
            cmdLine.addArgument("--password").addArgument(password);
            cmdLine.addArgument("--fromTable").addArgument(fromTable);
            cmdLine.addArgument("--toTable").addArgument(targetTable);
            cmdLine.addArgument("--field").addArgument(field);

            System.out.println("=====================");
            System.out.println(cmdLine.toString());


            DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler(){
                @Override
                public void onProcessComplete(int exitValue) {
                    super.onProcessComplete(exitValue);
                    System.out.println("my success");
                    TaskInfo taskInfo = new TaskInfo();
                    taskInfo.setId(taskId);
                    taskInfo.setStatus("2");
                    SparkSql.staticTaskInfoService.updateTask(taskInfo);
                }

                @Override
                public void onProcessFailed(ExecuteException e) {
                    super.onProcessFailed(e);
                    System.out.println("my fail");
                    TaskInfo taskInfo = new TaskInfo();
                    taskInfo.setId(taskId);
                    taskInfo.setStatus("3");
                    SparkSql.staticTaskInfoService.updateTask(taskInfo);
                }
            };

            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(0);
            executor.execute(cmdLine, handler);
//            handler.waitFor();
            System.out.println("started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
