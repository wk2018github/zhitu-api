package zhitu.vgraph;

import java.util.List;

import org.apache.tomcat.jni.Proc;

public class Test {


	public static void main(String[] args){
		ProcessNode node0 = new ProcessNode("科处室指标");
		ProcessNode node1 = new ProcessNode(node0, "单位指标");
		ProcessNode node2 = new ProcessNode(node1, "用款计划");
		
		System.out.println(node0.getNodesOfTree());
		

//		Node[] nodes = {
//				node0, node1, node2
//		};
//
//		for(Node node: nodes){
//			System.out.println(node.toNodeInfo());
//		}
		
		
		
		
	}
}
