package zhitu.util;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import zhitu.vgraph.Node;
import zhitu.vgraph.NodeTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyTest {

    class Filter{
        public String label;
        public String value;
    }


    public static class WhereClause{
        String cypher;
        HashMap<String, Object> dataMap = new HashMap<>();


    }


    public static class RuleNode {
        public ArrayList<RuleNode> parents = new ArrayList<>();
        public String id;
        public ArrayList<String> labels = new ArrayList<>();
        public ArrayList<String> parentRelationTypes = new ArrayList<>();


        HashMap<String, String> filterMap = new HashMap<>();
//        public HashMap<String, String> getFilterMapWithParents(){
//
//            RuleNode currentRuleNode = this;
//            HashMap<String, String> allFilterMap = new HashMap<>();
//            while(currentRuleNode != null){
//                HashMap<String, String> currentFilterMap = currentRuleNode.filterMap;
//                for(Map.Entry<String, String> entry: currentFilterMap.entrySet()){
//                    if(!allFilterMap.containsKey(entry)){
//                        allFilterMap.put(entry.getKey(), entry.getValue());
//                    }
//                }
//                currentRuleNode = currentRuleNode.parent;
//            }
//            return allFilterMap;
//        }

        public String getNodeVariableName(){
            return String.format("n_%s", id);
        }

        public ArrayList<String> createCypherList(int limit){
            ArrayList<String> resultsWithoutReturn = new ArrayList<>();
            traverse(null, null, null, null, resultsWithoutReturn);
            ArrayList<String> results = new ArrayList<>();
            for(String resultWithoutReturn: resultsWithoutReturn){
                StringBuilder sb = new StringBuilder(resultWithoutReturn);
                sb.append("RETURN ").append(getNodeVariableName()).append("\n");
                sb.append("LIMIT ").append(limit);
                results.add(sb.toString());
            }
            return results;
        }

        public void traverse(String childNodeVariableName, String relationType, String childMatchClause, String childWhereClause, ArrayList<String> results){
            String labelsCondition = String.join(":", labels);
            StringBuilder matchClauseSb = new StringBuilder();
            if(childMatchClause != null){
                matchClauseSb.append(childMatchClause).append("-[:").append(relationType).append("]->");
            }
            matchClauseSb.append("(").append(getNodeVariableName()).append(":").append(labelsCondition).append(")");

            StringBuilder whereClauseSb = new StringBuilder();
//            whereClauseSb.append(getNodeVariableName()).append(":").append(labelsCondition).append("\n");


            for(Map.Entry<String, String> entry: filterMap.entrySet()){
                if(whereClauseSb.length() > 0){
                    whereClauseSb.append("AND\n");
                }
                whereClauseSb.append(getNodeVariableName()).append(".").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"\n");
            }

//            if(childNodeVariableName != null){
//                if(whereClauseSb.length() > 0){
//                    whereClauseSb.append("AND\n");
//                }
//                whereClauseSb.append("(")
//                        .append(childNodeVariableName).append(")-[:")
//                        .append(relationType).append("]->(")
//                        .append(getNodeVariableName())
//                        .append(")\n");
//            }
            if(childWhereClause != null){
                if(whereClauseSb.length() > 0 && childWhereClause.length() > 0){
                    whereClauseSb.append("AND\n");
                }
                whereClauseSb.append(childWhereClause);
            }

            String matchClause = matchClauseSb.toString();
            String whereCluase = whereClauseSb.toString();

            if(parents.isEmpty()){
                StringBuilder cypherSb = new StringBuilder("MATCH ").append(matchClause).append("\n");
                if(whereCluase.length() > 0) {
                    cypherSb.append("WHERE ").append(whereCluase);
                }
                results.add(cypherSb.toString());
            }else {
                for (int i = 0; i < parents.size(); i++) {
                    RuleNode parent = parents.get(i);
                    String parentRelationType = parentRelationTypes.get(i);
                    parent.traverse(this.getNodeVariableName(), parentRelationType, matchClause, whereCluase, results);
                }
            }
        }

        public static final String KEY_LABEL = "label";

        public void addLabel(String label){
            labels.add(label);
        }

        public void addParent(RuleNode parent, String parentRelationType){
            parents.add(parent);
            parentRelationTypes.add(parentRelationType);
        }


    }

//    public static RuleNode getRuleNode(Node node){
//    	if(null != node.parent){
//    		
//    	}
//    	
//    }
    




    public static void main(String[] args) {
    	
    	Node proNode1 = new Node("科处室指标", NodeTypes.PROCESS);
    	Node filterNode1 = new Node(proNode1, "现金支票", NodeTypes.FILTER);
    	Node proNode2 = new Node(filterNode1, "单位指标", NodeTypes.PROCESS);
		Node proNode3 = new Node(proNode2,  "计划", NodeTypes.PROCESS);
		Node filterNode2 = new Node(proNode3, "转账支票", NodeTypes.FILTER);
		Node proNode4 = new Node(filterNode2,  "支付", NodeTypes.PROCESS);
		
		Node p1 = filterNode2.parent;
    	
    	RuleNode node0 = new RuleNode(); //做一个构造函数，直接把流程节点传进去
        node0.id = filterNode2.id;
        node0.addLabel(filterNode2.text);
        
        RuleNode node1 = new RuleNode();
        node1.id = p1.id;
        node1.addLabel(p1.text);
        node1.filterMap.put(p1.text, filterNode2.text);
        
        node0.addParent(node1, "");
        
        for(String cypher: node0.createCypherList(1)){
            System.out.println(cypher);
        }
        
//		Node p1 = filterNode1.parent;
//    	
//    	RuleNode node0 = new RuleNode(); //做一个构造函数，直接把流程节点传进去
//        node0.id = filterNode1.id;
//        node0.addLabel(filterNode1.text);
//        
//        RuleNode node1 = new RuleNode();
//        node1.id = p1.id;
//        node1.addLabel(p1.text);
//        node1.filterMap.put(p1.text, filterNode1.text);
//        
//        node0.addParent(node1, "");
//        
//        for(String cypher: node0.createCypherList(1)){
//            System.out.println(cypher);
//        }
    	
//        RuleNode node1 = new RuleNode();
//        node1.id = "b";
//        node1.addLabel("舒淇");
//        
//        
//        node1.addParent(node0, "拨款号");
    	
//        System.out.println(node1.createCypherList(5));
    	
    	
    	

//        String graphId = "G_1234234";
//
//        RuleNode node0 = new RuleNode();
//        node0.id = "a";
//        node0.addLabel("基础表预算管理数据分析科处室指标明细表");



//        RuleNode node1 = new RuleNode();
//        node1.id = "b";
//        node1.addLabel("基础表预算管理数据分析单位指标明细表");
//        node1.addParent(node0, "科处室指标编号");
//
//        RuleNode node2 = new RuleNode();
//        node2.id = "c";
//        node2.addLabel("基础表支付管理数据用款计划明细表");
//        node2.addParent(node1, "单位指标编号");
//
//        RuleNode node3 = new RuleNode();
//        node3.id = "d";
//        node3.addLabel("基础表支付管理数据支付凭证明细表");
//        node3.addParent(node2, "用款计划批复编号");
//
//        node3.addParent(node1, "单位指标编号");

//        node0.addLabel(graphId);
//        node1.addLabel(graphId);
//        node2.addLabel(graphId);
//        node3.addLabel(graphId);

//        for(String cypher: node1.createCypherList(1)){
//            System.out.println(cypher);
//        }


//        String user = "neo4j";
//        String password = "zhitu";
//        String uri = "bolt://172.18.32.91:7687";
//        Driver driver = GraphDatabase.driver( uri, AuthTokens.basic(user, password));
//
//
//
//        try(Session session = driver.session()){
//
//        }
    }
}
