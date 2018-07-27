package zhitu.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import zhitu.sq.dataset.model.Rdb;

public class SparkSql {

    public static void main(String[] args){
        /*String url = "jdbc:mysql://172.18.32.91:3306/db_kg?useUnicode=true";
        String encoding = "UTF-8";
        String user = "root";//数据库用户名
        String password = "142536";//数据库密码
        String fromTable = "zt_sys_user";//远程数据库表名
        String field = "id,createTime,email";//导入的字段*/
        String targetTable = "result";//导入到本地之后的数据库表名
        Rdb rdb = new Rdb();
        rdb.setHost("172.18.32.91");
        rdb.setPort(3306);
        rdb.setDbName("db_kg");
        rdb.setCharset("UTF-8");
        rdb.setUser("root");
        rdb.setPassword("142536");
        rdb.setTableName("zt_sys_user");
        rdb.setColumnNames("id,createTime,email");
        migration(rdb,targetTable);
    }

    /**
     * 数据迁移
     * @param  rdb
     * @param  targetTable
     */
    public static void migration(Rdb rdb,String targetTable){
        try {
            String url = "jdbc:mysql://" + rdb.getHost() + ":" + rdb.getPort() + "/" + rdb.getDbName() + "?useUnicode=true";
            String encoding = rdb.getCharset();
            String user = rdb.getUser();//数据库用户名
            String password = rdb.getPassword();//数据库密码
            String fromTable = rdb.getTableName();//远程数据库表名
            String field = rdb.getColumnNames();//导入的字段

            String pythonExePath = "C:\\\\Spark\\bin\\spark-submit.cmd";
            CommandLine cmdLine = CommandLine.parse(pythonExePath);
            cmdLine.addArgument("--packages");
            cmdLine.addArgument("mysql:mysql-connector-java:5.1.46");
            cmdLine.addArgument("G:\\\\testpy\\etl.py");
            cmdLine.addArgument("--url").addArgument(url);
            cmdLine.addArgument("--encoding").addArgument(encoding);
            cmdLine.addArgument("--user").addArgument(user);
            cmdLine.addArgument("--password").addArgument(password);
            cmdLine.addArgument("--fromTable").addArgument(fromTable);
            cmdLine.addArgument("--toTable").addArgument(targetTable);
            cmdLine.addArgument("--field").addArgument(field);



            DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler(){
                @Override
                public void onProcessComplete(int exitValue) {
                    super.onProcessComplete(exitValue);
                    System.out.println("my success");
                }

                @Override
                public void onProcessFailed(ExecuteException e) {
                    super.onProcessFailed(e);
                    System.out.println("my fail");
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
