package ftpUtil;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * @auther 陈郑游
 * @create 2016-11-23-19:27
 * @功能描述 ftp工具类
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class FtpUtil implements Serializable {

	/**
	 * Description: 向FTP服务器上传文件
	 * @param host FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径。例如分日期存放：/2016/11/23。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String host, int port, String username,
	                                 String password, String basePath,
                                     String filePath, String filename,
                                     InputStream input) {

		boolean result = false;
        //获取FTPClient对象
		FTPClient ftp = new FTPClient();

		try {

			int reply;
            // 连接FTP服务器
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            // 登录
			ftp.login(username, password);
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}

			//切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath+filePath)) {
				//如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir)) {
                        continue;
                    };
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}

			//设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			//上传文件
			if (!ftp.storeFile(filename, input)) {
				return result;
			}

			input.close();
			ftp.logout();
			result = true;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * @param host FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return
	 */
	public static boolean downloadFile(String host, int port, String username,
                                       String password, String remotePath,
                                       String fileName, String localPath) {

		boolean result = false;
        //获取FTPClient对象
		FTPClient ftp = new FTPClient();

		try {

			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            // 登录
			ftp.login(username, password);
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}

            // 转移到FTP服务器目录
			ftp.changeWorkingDirectory(remotePath);
			FTPFile[] fs = ftp.listFiles();

			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	/**
     * Description：测试FTP工具类
     *
     * */
	public static void main(String[] args) {
		try {

	        FileInputStream in=new FileInputStream(new File("D:\\temp\\image\\gaigeming.jpg"));
	        boolean flag = uploadFile("192.168.25.133", 21, "ftpuser", "ftpuser",
                    "/home/ftpuser/www/images","/2015/01/21", "gaigeming.jpg", in);

	        System.out.println(flag);

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
}
