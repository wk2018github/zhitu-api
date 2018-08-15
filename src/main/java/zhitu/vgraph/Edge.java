package zhitu.vgraph;

import com.google.gson.JsonObject;

public class Edge {
	
	Node from;
	Node to;
	
	
	
	public Edge(Node from, Node to) {
		super();
		this.from = from;
		this.to = to;
	}

	public JsonObject toJsonObject(){
		JsonObject jo = new JsonObject();
		jo.addProperty("from", from.id);
		jo.addProperty("to", to.id);
		return jo;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toJsonObject().toString();
	}
	
	
	

}
