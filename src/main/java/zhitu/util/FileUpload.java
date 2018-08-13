package zhitu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.ProgressListener;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileUpload {
	
	static long size = 0;
	
	private static Logger logger = LoggerFactory.getLogger(FileUpload.class);

	public static String upload (HttpServletRequest request){
		// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全   继承 HttpServlet的上传文件
//		String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		// 上传时生成的临时文件保存目录
//		String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		String path = "";//文件最终路径+文件名称
		//获取当前系统  根据系统获取文件上传路径
		Configuration con = null;
		try {
			con = new PropertiesConfiguration("file.properties");
		} catch (ConfigurationException e1) {
			logger.error("获取配置文件file.properties异常");
			logger.error("FileUpload/upload",e1);
		}
		String filePath = "";
		Properties p = System.getProperties();
		String sys = p.getProperty("os.name").toLowerCase();
		if(sys.contains("windows")){
			filePath = con.getString("FILE_UPLOAD_PATH_W");
		} else {
			filePath = con.getString("FILE_UPLOAD_PATH_L");
		}
		
		File file = new File(filePath);
		if (!(file.exists() && file.isDirectory())) {
			logger.info("目录或文件夹不存在！");
			file.mkdir();
		}
		try {
			// 使用Apache文件上传组件处理文件上传步骤：
			// 1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
			diskFileItemFactory.setSizeThreshold(1024 * 1024 * 20);
			// 设置上传时生成的临时文件的保存目录
			diskFileItemFactory.setRepository(file);
			// 2、创建一个文件上传解析器
			ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
			// 解决上传文件名的中文乱码
			fileUpload.setHeaderEncoding("UTF-8");
			// 监听文件上传进度
			fileUpload.setProgressListener(new ProgressListener() {
				public void update(long pBytesRead, long pContentLength, int arg2) {
					FileUpload.size = pContentLength;
					logger.info("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
				}
			});
			// 3、判断提交上来的数据是否是上传表单的数据
//			if (!fileUpload.isMultipartContent(request)) {
//				// 按照传统方式获取数据
//				return;
//			}
			// 设置上传单个文件的大小的最大值，目前是100MB
			fileUpload.setFileSizeMax(1024 * 1024 * 100);
			// 设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为1G
			fileUpload.setSizeMax(1024 * 1024 * 1024);
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = fileUpload.parseRequest((RequestContext) request);
			for (FileItem item : list) {
				// 如果fileitem中封装的是上传文件，得到上传的文件名称，
				String fileName = item.getName();
				System.out.println(fileName);
				if (fileName == null || fileName.trim().equals("")) {
					continue;
				}
				// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
				// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
				// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
				fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
				// 得到上传文件的扩展名
//				String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
//					if ("zip".equals(fileExtName) || "rar".equals(fileExtName) || "tar".equals(fileExtName)
//							|| "jar".equals(fileExtName)) {
//						request.setAttribute("message", "上传文件的类型不符合！！！");
//						request.getRequestDispatcher("/message.jsp").forward(request, response);
//						return;
//					}
				// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
//					System.out.println("上传文件的扩展名为:" + fileExtName);
				// 获取item中的上传文件的输入流
				InputStream fis = null;
				try {
					fis = item.getInputStream();
				} catch (IOException e) {
					logger.error("获取fileitem  input 流异常");
					logger.error("FileUpload/upload",e);
				}
				// 得到文件保存的路径
				path = mkFilePath(fileName);
				logger.info("保存路径为:" + path);
				// 创建一个文件输出流
//				FileOutputStream fos = new FileOutputStream(savePathStr + File.separator + fileName);
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(path);
				} catch (FileNotFoundException e) {
					logger.error("获取文件 outputStream 流异常");
					logger.error("FileUpload/upload",e);
				}
				// 获取读通道
				FileChannel readChannel = ((FileInputStream) fis).getChannel();
				// 获取读通道
				FileChannel writeChannel = fos.getChannel();
				// 创建一个缓冲区 1M
				ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
				// 判断输入流中的数据是否已经读完的标识
//				int length = 0;
				// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
				while (true) {
					buffer.clear();
					int len = readChannel.read(buffer);
					// 读入数据
					if (len < 0) {
						break;// 读取完毕
					}
					buffer.flip();
					writeChannel.write(buffer);
				}
				// 关闭输入流
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("关闭 FileInputStream  流异常");
					logger.error("FileUpload/upload",e);
				}
				// 关闭输出流
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("关闭 FileOutputStream  流异常");
					logger.error("FileUpload/upload",e);
				}
				// 删除处理文件上传时生成的临时文件
				item.delete();
			}
			
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			logger.error("单个文件超出最大值！！！");
			logger.error("FileUpload/upload",e);
		} catch (FileUploadBase.SizeLimitExceededException e) {
			logger.error("上传文件的总的大小超出限制的最大值！！！");
			logger.error("FileUpload/upload",e);
		} catch (Exception e) {
			logger.error("FileUpload/upload",e);
		}
		if(checkUpload(path)){
			return path;
		} else {
			return null;
		}
		
	}


	public static String mkFilePath(String fileName) {
		// 得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = fileName.hashCode();
		int dir1 = hashcode & 0xf;
		int dir2 = (hashcode & 0xf0) >> 4;
		// 构造新的保存目录
		String dir = System.currentTimeMillis() + "\\" + dir1 + "\\" + dir2;
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		return dir;
	}
	public static boolean checkUpload(String filePath){
		File file = new File(filePath);
		//文件不存在  又或者  是文件夹  或者大小不对应
		if(!file.exists() || file.isDirectory() || !(file.length() == size)){
			return false;
		}
		return true;
	}

}
