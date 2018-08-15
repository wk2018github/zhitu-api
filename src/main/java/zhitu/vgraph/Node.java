package zhitu.vgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Node {
	

	
	//UUID for Node
	public String id;
	public String text;
	public String type;
	
	public Node parent;
	public ArrayList<Node> children = new ArrayList<>();
	
	public synchronized static String generateId(){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return String.format("N_%s", System.currentTimeMillis());
	}

	
	public Node(String text, String type) {
		this.id = generateId();
		this.text = text;
		this.type = type;
		Graphs.idNodeMap.put(id, this);
	}
	
	
	
	
	
	public Node(Node parent, String text, String type) {
		this(text, type);
		this.setParent(parent);
	}


	//	public abstract String getTitle();
	public ArrayList<String> getMenu(){
		ArrayList<String> menu = new ArrayList<>();
		menu.add("功能科目");
		menu.add("预算项目");
		menu.add("支付方式");
		return menu;
	}
	
//	public abstract String getType();
	
	public void traverse(NodeVisitor visitor){
		visitor.visit(this);
		for(Node child: children){
			child.traverse(visitor);
		}
	}
	
	public ArrayList<Node> getNodesOfTree(){
		ArrayList<Node> nodeList = new ArrayList<>();
		this.traverse(new NodeVisitor() {
			@Override
			public void visit(Node node) {
				nodeList.add(node);
			}
		});
		return nodeList;
	}
	
	public void addChild(Node child){
		child.removeParent();
		child.parent = this;
		this.children.add(child);
	}
	
	public void removeParent(){
		if(parent != null){
			parent.removeChild(this);
		}
	}
	
	public void removeChild(Node child){
		this.children.remove(child);
	}
	
	public void setParent(Node parent){
		parent.addChild(this);
	}
	

	

	
	public JsonObject toJsonObject(){
		JsonObject jo = new JsonObject();
		jo.addProperty("id", id);
		jo.addProperty("text", text);
		jo.addProperty("type", type);
		jo.addProperty("msg", new JsonObject().toString());
		return jo;
	}
	

	public NodeInfo toNodeInfo(){
		return new NodeInfo(this);
	}
	
	
	public JsonObject convertTreeToJsonObject(){
		JsonObject jo = new JsonObject();
		
		JsonArray nodesJa = new JsonArray();
		JsonArray edgesJa = new JsonArray();
		
		jo.add("nodes", nodesJa);
		jo.add("edges", edgesJa);
		
		for(Node node: getNodesOfTree()){
			NodeInfo nodeInfo = node.toNodeInfo();
			nodesJa.add(nodeInfo.nodeJo);
			edgesJa.addAll(nodeInfo.edgesJa);
		}
		
		return jo;
	}
	
}
