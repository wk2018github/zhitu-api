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
		Node node3 = new Node(node2, "计划", NodeTypes.PROCESS);
		Node node4 = new Node(node3, "支付", NodeTypes.PROCESS);
		
		String id = node1.id;
		
		Node root = Graphs.findNodeById(id);
		System.out.println(node1.convertTreeToJsonObject());
		
//		ArrayList<Integer> a = new ArrayList<>();
//		a.add(0);
//		a.add(1);
//		a.add(2);
//		
//		List<String> result = a.stream().map(node->{
//			String s = node+"呵呵";
//			return s;
//		}).collect(Collectors.toList());
//		
//		result.stream().forEach(str->{
//			System.out.println(str);
//		});
//		
//		ArrayList<Integer> b = new ArrayList<>();
//		b.add(0);
//		b.add(1);
//		b.add(2);
//		
//		List<String> newB = b.parallelStream().map(value->{
//			return value + " this is a string";
//		}).collect(Collectors.toList());
//		
//		System.out.println(b);
//		System.out.println(newB);
//		
//		ArrayList<String> c = new ArrayList<>();
//		c.add("one");
//		c.add("two");
//		c.add("three");
//		
//		c.stream().forEach(node->{
//			System.out.println(node);
//		});
//		
//		for(String cString:c){
//			System.out.println(cString);
//		}
	}
}
