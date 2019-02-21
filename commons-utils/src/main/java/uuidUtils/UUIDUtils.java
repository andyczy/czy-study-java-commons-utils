package uuidUtils;

import java.util.Random;
import java.util.UUID;


/**
 * @auther 陈郑游
 * @create 2016-11-23-19:27
 * @功能描述 ID生成策略
 * @问题
 * @说明
 * @URL地址  http://blog.csdn.net/javawebrookie/
 * @进度描述
 */
public class UUIDUtils {

	public static void main(String[] args) {
		for(int i=0; i< 10; i++){
			System.out.println(getRandom());
		}
		System.out.println(getUUID());
	}

	/**
	 * 获得一个UUID
	 * @return String UUID
	 */
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString();
		//去掉“-”符号
		return uuid.replaceAll("-", "");
	}



	public static long getRandom() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//加上两位随机数
		Random random = new Random();
		int end2 = random.nextInt(99);
		//如果不足两位前面补0
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return id;
	}

}
