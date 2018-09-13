package zhitu.util;


public class TableSqlUtil {
	
	/**
	 * 创建Activity表
	 * @param tableName
	 * @return
	 */
	public static String tableActivity(String tableName){
		String sql = "create table "+tableName
				+"(`auditReporter` varchar(100) DEFAULT NULL COMMENT '审计发起单位',"
				+"`auditTime2Event` varchar(100) DEFAULT NULL COMMENT '审计发生时间',"
				+"`auditTarget` varchar(100) DEFAULT NULL COMMENT '被审计对象', "
				+"`auditTime` varchar(50) DEFAULT NULL COMMENT '被审计年度',"
				+"`auditIndustry` varchar(50) DEFAULT NULL COMMENT '审计行业',"
				+"`auditClassification` varchar(50) DEFAULT NULL COMMENT '审计分类 ',"                                                                                                                                           
				+"`auditLaws` varchar(100) DEFAULT NULL COMMENT '法律法规'"
				+")ENGINE=InnoDB DEFAULT CHARSET=utf8";
		return sql;
	}

	/**
	 * 创建Underlying表
	 * @param tableName
	 * @return
	 */
	public static String tableUnderlying(String tableName){
		String sql = "create table "+tableName
				+"(`auditName` varchar(100) DEFAULT NULL COMMENT '名称： 省民族宗教事务局',"
				+"`budgetUnit` varchar(50) DEFAULT NULL COMMENT '预算单位： 2',"
				+"`theTotalNumberOfPeople` varchar(50) DEFAULT NULL COMMENT '编制总人数： 75', "
				+"`Administrate` varchar(50) DEFAULT NULL COMMENT '行政编制： 30',"
				+"`fullSubsidyCareeaStaffing` varchar(50) DEFAULT NULL COMMENT '全额补助事业编制： 45',"
				+"`theActualNumber` varchar(50) DEFAULT NULL COMMENT '实有人数：61 ',"  
				+"`functionsDepartment` varchar(50) DEFAULT NULL COMMENT '职能处室: 6',"
				+"`fd_Administrate` varchar(50) DEFAULT NULL COMMENT '行政编制： 30',"
				+"`fd_Careea` varchar(50) DEFAULT NULL COMMENT '全额补助事业编制： 45',"
				+"`fd_theActualNumber` varchar(50) DEFAULT NULL COMMENT '实有人数：61 '" 
				+")ENGINE=InnoDB DEFAULT CHARSET=utf8";
		return sql;
	}
	
	/**
	 * 创建Enforcement表
	 * @param tableName
	 * @return
	 */
	public static String tableEnforcement(String tableName){
		String sql = "create table "+tableName
				+"(`auditType` varchar(100) DEFAULT NULL ,"
				+"`auditTime` varchar(100) DEFAULT NULL , "
				+"`auditTarget` varchar(100) DEFAULT NULL ,"
				+"`auditItems` text NULL"  
				+")ENGINE=InnoDB DEFAULT CHARSET=utf8";
		return sql;
	}
	
	/**
	 * 创建Opinion表
	 * @param tableName
	 * @return
	 */
	public static String tableOpinion(String tableName){
		String sql = "create table "+tableName
				+"(`auditTarget` varchar(100) DEFAULT NULL COMMENT '被审计单位',"
				+"`auditCoverage` varchar(100) DEFAULT NULL COMMENT '审计覆盖面',"
				+"`auditTotalFunds` varchar(100) DEFAULT NULL COMMENT '审计资金总额' "
				+")ENGINE=InnoDB DEFAULT CHARSET=utf8";
		return sql;
	}
	
	/**
	 * 创建Problem表
	 * @param tableName
	 * @return
	 */
	public static String tableProblem(String tableName){
		String sql = "create table "+tableName
				+"(`auditProblemsAndLaws`text NULL COMMENT '审计问题'"
				+")ENGINE=InnoDB DEFAULT CHARSET=utf8";
		return sql;
	}
	
	/**
	 * 创建Advice表
	 * @param tableName
	 * @return
	 */
	public static String tableAdvice(String tableName){
		String sql = "create table "+tableName
				+"(`advices` text NULL COMMENT '审计建议'"
				+")ENGINE=InnoDB DEFAULT CHARSET=utf8";
		return sql;
	}
}
