package zhitu.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class FileUpload {

	private static Logger logger = LoggerFactory.getLogger(FileUpload.class);

	public static List<String> upload(HttpServletRequest request) {

		long startTime = System.currentTimeMillis();
		long size = 0; // 单个文件大小
		List<String> paths = new ArrayList<String>();// 文件最终路径+文件名称
		// 获取当前系统 根据系统获取文件上传路径
		Configuration con = null;
		try {
			con = new PropertiesConfiguration("file.properties");
		} catch (ConfigurationException e1) {
			logger.error("获取配置文件file.properties异常");
			logger.error("FileUpload/upload", e1);
		}
		String direPath = "";
		Properties p = System.getProperties();
		String sys = p.getProperty("os.name").toLowerCase();
		if (sys.contains("windows")) {
			direPath = con.getString("FILE_UPLOAD_PATH_W") + File.separator + startTime;
		} else {
			direPath = con.getString("FILE_UPLOAD_PATH_L") + File.separator + startTime;
		}
		// 目录不存在 则创建
		File f = new File(direPath);
		if (!(f.exists() && f.isDirectory())) {
			logger.info("目录或文件夹不存在,正在创建...");
			f.mkdir();
		}

		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					String path = direPath + File.separator + file.getOriginalFilename();
					size = file.getSize();
					// 上传
					try {
						file.transferTo(new File(path));
					} catch (IllegalStateException | IOException e) {
						logger.error("FileUpload/upload", e);
					}
					if (checkUpload(path, size)) {
						paths.add(path);
					} else {
						paths.add(file.getOriginalFilename());
					}
				}
			}
		}
		long endTime = System.currentTimeMillis();
		logger.info("上传时间：" + String.valueOf(endTime - startTime) + "ms");

		return paths;

	}

	public static boolean checkUpload(String filePath, long size) {
		File file = new File(filePath);
		// 文件不存在 又或者 是文件夹 或者大小不对应
		if (!file.exists() || file.isDirectory() || !(file.length() == size)) {
			return false;
		}
		return true;
	}

}
