package exceptionUtil;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;


/**
 * @auther 陈郑游
 * @create 2016/3/10 0020
 * @功能   异常的堆栈信息工具类
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class ExceptionUtil implements Serializable {

	/**
	 * Description：获取异常的堆栈信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {

			t.printStackTrace(pw);
			return sw.toString();

		} finally {
			pw.close();
		}
	}
}
