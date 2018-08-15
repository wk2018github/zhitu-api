package zhitu.vgraph;

import java.util.HashMap;

public class Graphs {
	public static HashMap<String, Node> idNodeMap = new HashMap<>();
	
	public static Node findNodeById(String nodeId){
		return idNodeMap.get(nodeId);
	}
}
