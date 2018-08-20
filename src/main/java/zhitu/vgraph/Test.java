package zhitu.vgraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import zhitu.util.JacksonUtil;


public class Test {
	


	public static void main(String[] args) throws Exception{

		Node node1 = new Node(null, "科处室指标", NodeTypes.PROCESS);
		Node node2 = new Node(node1, "单位指标", NodeTypes.PROCESS);
		Node node3 = new Node(node2,  "计划", NodeTypes.PROCESS);
		Node node4 = new Node(node3,  "支付", NodeTypes.PROCESS);
		
		Node node15 = new Node(node1, "计划父级",NodeTypes.PROCESS);
		node3.setParent(node15);
		
		System.out.println(node1.convertTreeToJsonObject().toString());
		
		ArrayList<Node> children = node1.children;
		for (Node node : children) {
			System.out.println(node.id);
			System.out.println(node.text);
		}
		
		NodeInfo no = new NodeInfo(node1);
		
		System.out.println(no.toString());
		
//		Node node5 = new Node("功能科目",NodeTypes.PROCESS);
//		node1.addChild(node5);
//		node1.removeChild(node2);
//		node2.setParent(node5);
//		
//		
//		String json = node1.convertTreeToJsonObject().toString();
//		Map<String, Object> json2Map = JacksonUtil.Builder().json2Map(json);
//		String string = json2Map.get("links").toString();
//		
//		String[] split = string.split("},");
//		List<String> ls = new ArrayList<>();
//		
//		for (String s : split) {
//			String a = s.toString();
//			a = a.replace('[', '{');
// 			a = a.replace(']', '{');
// 			a = a.replace('}', '{');
//			
// 			ls.add(reAll(a).trim());
//		}
//		
//		System.out.println(ls.get(0)+"\n"+ls.get(1));
		
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
	
	public static String reAll(String str){
		if(StringUtils.isEmpty(str)){
			return str;
		}
		StringBuffer s = new StringBuffer(str);
		int index = s.indexOf("{");
		if(index>-1){
			s.deleteCharAt(index);
			str = reAll(s.toString());
		}
		
		return str; 
	}
}
