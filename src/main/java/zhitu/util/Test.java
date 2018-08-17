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
		
		String a = "[abcde";
		int i = a.indexOf('[');
		String b = a.replace('[', '{');
		System.out.println(b);
		
		StringBuffer sb = new StringBuffer(a);
		int j = sb.indexOf("[");
		if(j>-1){
			StringBuffer end = sb.deleteCharAt(j);
			System.out.println(end);
		}
		System.out.println(sb);
	}

}
