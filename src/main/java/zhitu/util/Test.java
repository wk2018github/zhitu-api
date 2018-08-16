package zhitu.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Test {
	
	public static void main(String[] args) throws ConfigurationException {
		
		Configuration con = new PropertiesConfiguration("file.properties");
		String config = con.getString("CODE_TABLE_PAY");
//		String[] strs = config.split("，");
		System.out.println(config);
		
//		List<String> ls = Arrays.asList(strs);
//		
//		for (String s : ls) {
//			System.out.println(s+"\t");
//		}
		
	}

}
