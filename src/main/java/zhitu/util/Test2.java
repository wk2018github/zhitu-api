package zhitu.util;


public class Test2 {
	
	public static void main(String[] args) {
		String id = "1,2,3,4";
		
		StringBuffer sb = new StringBuffer();
		for (String s : id.split(",")) {
			sb.append("'").append(s).append("',");
		}
		sb.deleteCharAt(sb.length()-1);
		System.out.println(sb.toString());
		
	}
	
	
	
}
