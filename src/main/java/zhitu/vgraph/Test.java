package zhitu.vgraph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;


public class Test {
	


	public static void main(String[] args){

		Node node1 = new Node("科处室指标", NodeTypes.PROCESS);
		Node node2 = new Node(node1, "单位指标", NodeTypes.PROCESS);
		Node node3 = new Node(node2, "用款计划", NodeTypes.PROCESS);
		Node node4 = new Node(node3, "用款计划", NodeTypes.PROCESS);
		
		String id = node1.id;
		
		Node root = Graphs.findNodeById(id);
		System.out.println(node1.convertTreeToJsonObject());
		
		
//		System.out.println(node1.convertTreeToJsonObject());
		
//		JsonArray 
		
		
//		List<Node> result = node1.getNodesOfTree().stream().map(node->{
//			Node newNode = new Node(node.text+"--hehe", node.type);
//			return newNode;
//		}).collect(Collectors.toList());
		
//		ArrayList<Integer> list = new ArrayList<>();
//		list.add(0);
//		list.add(1);
//		list.add(2);
//		
//		
//		List<String> newList = list.parallelStream().map(value->{
//			return value + " this is a string";
//		}).collect(Collectors.toList());
//		
//		System.out.println(list);
//		System.out.println(newList);
		
		
//		.stream().forEach(node->{
//			System.out.println(node.toNodeInfo());
//		});
		
		
//		ArrayList<Node> nodes = node1.getNodesOfTree();
//		for(Node node:nodes){
//			System.out.println(node.toNodeInfo());
//		}
	}
}
