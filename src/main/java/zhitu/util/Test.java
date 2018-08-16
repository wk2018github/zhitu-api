package zhitu.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Test {
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<>();
		
		list.add("abc/新文件.txt");
		list.add("cd/aa/new.txt");
		list.add("bcd/word.docx");
		list.add("aasd/asd/asdd.xlsx");
		for (int j = 0; j < list.size(); j++) {
			String[] p = list.get(j).split("/");
			if(p.length>1){
				
				int k = 1;
				System.out.println("上传成功一个文件");
				if(k>0){
					list.remove(j);
					j--;
				}
			}
		}
		
		String aa = String.join(",",list);
		System.out.println(aa);
		
	}

}
