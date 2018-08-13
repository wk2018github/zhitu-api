package zhitu.util;

import java.util.Properties;

public class Test {
	
	public static void main(String[] args) {
		
		Properties props = System.getProperties();
        System.out.println("操作系统的名称：" + props.getProperty("os.name"));
		
	}

}
