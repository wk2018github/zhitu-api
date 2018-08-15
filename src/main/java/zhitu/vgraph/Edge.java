package zhitu.vgraph;

import com.google.gson.JsonObject;

public class Edge {
	
	Node source;
	Node target;
	
	String label;
	
	public Edge(Node source, Node target) {
		super();
		this.source = source;
		this.target = target;
	}
	
	public Edge(Node source, Node target, String label) {
		super();
		this.source = source;
		this.target = target;
		this.label = label;
	}

	public JsonObject toJsonObject(){
		JsonObject jo = new JsonObject();
		jo.addProperty("source", source.id);
		jo.addProperty("target", target.id);	
		return jo;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toJsonObject().toString();
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	

}
