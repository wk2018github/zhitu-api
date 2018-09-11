package zhitu.vgraph;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NodeInfo {
	
	Node node;
	JsonObject nodeJo;
	JsonArray edgesJa;
	
	public NodeInfo(Node node, String label){
		this.node = node;
		
		this.nodeJo = node.toJsonObject();
		
		this.edgesJa = new JsonArray();
		for(Node child: node.children){
			Edge edge = new Edge(node, child, label);
			edgesJa.add(edge.toJsonObject());
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("node:\n").append(nodeJo);
		sb.append("\nedges:\n").append(edgesJa);
		return sb.toString();
	}
	
//	public static JsonObject convertListToJsonObject(List<Node> nodeList){
//		List<NodeInfoList>
//		
//	}
	

	
	

}
