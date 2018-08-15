package zhitu.util;

import java.util.Properties;

public class Test {
	
	public static void main(String[] args) {
		
		StringBuffer sb = new StringBuffer("'ksd_asdd','digh_1234','rigji_12424',");
		sb.deleteCharAt(sb.length()-1);
		System.out.println(sb.toString());
		Properties props = System.getProperties();
        System.out.println("操作系统的名称：" + props.getProperty("os.name"));
		
	}

}
