package zhitu.util;


public class Test2 {
	
	public static void main(String[] args) {
		String path = "e:\\aa\\ddd\\ea.txt";
		
		System.out.println(path);
		
		path.replaceAll("\\", "\\\\");
		
		String p2 = "e:\\\\aa\\\\ddd\\\\ea.txt";
		System.out.println(p2);
		
	}
	
	
	
}
