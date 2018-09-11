package zhitu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;

import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.util.JacksonUtil;

public class Neo4jTest {
	
	private static final Logger logger = Logger.getLogger(Neo4jTest.class);

	public static Driver driver;

	static {
		try {
			Configuration config = new PropertiesConfiguration("config.properties");
			String neo4jUrl = config.getString("neo4j.datasource.url");
			driver = GraphDatabase.driver(neo4jUrl);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		// driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password
		// ) );
	}

	/**
	 * 
	 * @Author: qwm @Description:get所有节点标签 @return: List<String> @throws
	 */
	public static List<String> getLabels() {
		List<String> list = new ArrayList<String>();

		try (Session session = driver.session()) {

			StatementResult result = session.run("Match (n) return distinct labels(n)");

			while (result.hasNext()) {
				
				List<Record> list2 = result.list();
				for (Record r : list2) {
					Map<String, Object> asMap = r.asMap();
					Object object = asMap.get("labels(n)");
					StringBuffer sb = new StringBuffer(object.toString());
					sb.deleteCharAt(sb.length()-1);
					sb.deleteCharAt(0);
					list.add(sb.toString());
				}
				
			}
			logger.info("getLabels: SUCCESS");
		} catch (Exception e) {
			logger.error("getLabels: ERROR");
		}

		return list;
	}

	/**
	 * 
	 * @Author: qwm @Description:get所有关系类型 @return: List<String> @throws
	 */
	public static List<String> getTypes() {
		List<String> list = new ArrayList<String>();

		try (Session session = driver.session()) {

			StatementResult results = session.run("match ()-[r]-() return distinct(type(r))");

			while (results.hasNext()) {
				
				List<Record> list2 = results.list();
				for (Record r : list2) {
					List<Value> values = r.values();
					for (Value v : values) {
						list.add(v.toString());
					}
				}
				
			}
			logger.info("getTypes: SUCCESS");
		} catch (Exception e) {
			logger.error("getTypes: ERROR");
		}


		return list;
	}

	/**
	 * 
	 * @Author: qwm @Description:删除节点标签 @return: boolean @throws
	 */
	public static boolean delLabels(String node) {
		boolean flag = false;

		try (Session session = driver.session()) {

			session.run("match (n:" + node + ") detach delete n");
			
			logger.info("delLabels: SUCCESS");
		} catch (Exception e) {
			logger.error("delLabels: ERROR");
		}

		return true;
	}

	/**
	 * 
	 * @Author: qwm @Description:删除关系类型 @return: boolean @throws
	 */
	public static boolean delTypes(String type) {
		boolean flag = false;

		try (Session session = driver.session()) {

			session.run("match ()-[r:" + type + "]-() delete r");
			
			logger.info("delTypes: SUCCESS");
		} catch (Exception e) {
			logger.error("delTypes: ERROR");
		}

		return true;
	}

	/**
	 * 
	 * @throws Exception
	 * @Author: qwm @Description:插入数据至neo4j @return: boolean @throws
	 */
	public static boolean addToNeo4j(String id, String tableName, String columns, List<String> list) {
		boolean flag = false;

		try (Session session = driver.session()) {

			for (int i = 0; i < list.size(); i++) {
				String str = list.get(i);
				str = changeJson(str);

				String cypher = "create (n:" + id + ":" + tableName + " " + str + ")";
//				String cypher2 = String.format("create (n:%s:%s)", tableName, "graphId");
				// String cypher = "create (n:" + tableName + ":"+图谱id+" " + str
				// + ")";
				//merge 
				session.run(cypher);

			}
			logger.info("addToNeo4j: SUCCESS");
		} catch (Exception e) {
			logger.error("addToNeo4j: ERROR");
		}

		return true;
	}

	/**
	 * 
	 * @Author: qwm @Description:创建两个数据集的关系 @return: boolean @throws
	 */
	public static boolean createRelationship(String sourceTable, String targetTable, String sourceKey, String targetKey,
			String relationship) {
		boolean flag = false;

		try (Session session = driver.session()) {

			String cypher = "match (n:" + sourceTable + "),(m:" + targetTable + ") where n." + sourceKey + " " + "= m."
					+ targetKey + "  create (n)-[r:" + relationship + "]->(m)";
			session.run(cypher);
			
			logger.info("createRelationship: SUCCESS");
		} catch (Exception e) {
			logger.error("createRelationship: ERROR");
		}

		return true;
	}

	/**
	 * 
	 * @Author: qwm @Description:查询节点详情 @return: List<String> @throws
	 */
	public static List<NodeDetail> queryNodeDetails(String cypher) {
		List<NodeDetail> list = new ArrayList<NodeDetail>();

		try (Session session = driver.session()) {

			StatementResult results = session.run(cypher);

			while (results.hasNext()) {
				
				
			}

		}

		return list;
	}

	/**
	 * 
	 * @Author: qwm @Description:{"name":"数据集cc","description":"c数据集的描述"} to
	 * {name:"数据集cc",description:"c数据集的描述"} @return: String @throws
	 */
	public static String changeJson(String json) throws Exception {
		Map<String, Object> map = JacksonUtil.Builder().json2Map(json);

		StringBuffer str = new StringBuffer("{");
		for (Entry<String, Object> e : map.entrySet()) {
			str.append(e.getKey()).append(":\"").append(e.getValue()).append("\",");
		}
		str.deleteCharAt(str.lastIndexOf(","));
		str.append("}");

		return str.toString();
	}

	public static void main(String... args) throws Exception {

		String tableName = "zt_sys_dataset";
		List<String> list = new ArrayList<String>();
		list.add("{\"name\":\"数据集cc\",\"id\":\"DATASET_1533179158417\",\"description\":\"c数据集的描述\"}");
		list.add("{\"name\":\"数据集dd\",\"id\":\"DATASET_1533179364589\",\"description\":\"d数据集的描述\"}");

		try (Session session = driver.session()) {

			for (int i = 0; i < list.size(); i++) {
				String str = list.get(i);
				Map<String, Object> map = JacksonUtil.Builder().json2Map(str);

				StringBuffer json = new StringBuffer("{");
				for (Entry<String, Object> e : map.entrySet()) {
					json.append(e.getKey()).append(":\"").append(e.getValue()).append("\",");
				}
				json.deleteCharAt(json.lastIndexOf(","));
				json.append("}");
				System.out.println(json);

				String cypher = "create (n:" + tableName + " " + json + ")";
				System.out.println(cypher);
				session.run(cypher);

			}

		}

		driver.close();

	}

	// public static void main(String[] args){
	// String cypher = "match
	// (n:"+"zt_sys_dataset"+"),(m:"+"zt_data_ftp_file"+") where n."+"id"+" "
	// + "= m."+"datasetId"+" create (n)-[r:"+"外键关系"+"]->(m)";
	// System.out.println(cypher);
	// session.run(cypher);
	//
	// }
	//
	//
	// import static org.neo4j.driver.v1.Values.parameters;
	// *
	// private final
	//
	// public HelloWorldExample( String uri, String user, String password )
	// {
	//
	// }
	//
	// @Override
	// public void close() throws Exception
	// {
	// driver.close();
	// }
	//
	// public void printGreeting( final String message )
	// {
	// try ( Session session = driver.session() )
	// {
	// String greeting = session.writeTransaction( new TransactionWork<String>()
	// {
	// @Override
	// public String execute( Transaction tx )
	// {
	// StatementResult result = tx.run( "CREATE (a:Greeting) " +
	// "SET a.message = $message " +
	// "RETURN a.message + ', from node ' + id(a)",
	// parameters( "message", message ) );
	// return result.single().get( 0 ).asString();
	// }
	// } );
	// System.out.println( greeting );
	// }
	// }
	//

	// }

}
