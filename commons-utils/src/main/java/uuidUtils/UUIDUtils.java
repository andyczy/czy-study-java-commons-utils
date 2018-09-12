package uuidUtils;

import java.util.Random;


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

	/**
     * Description：图片名生成ID
	 *
	 */
	public static String genImageName() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();

        //long millis = System.nanoTime();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);

		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		
		return str;
	}
	
	/**
	 * Description：商品id生成
	 */
	public static long genItemId() {

		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();

		//long millis = System.nanoTime();
		//加上两位随机数
		Random random = new Random();
		int end2 = random.nextInt(99);
		//如果不足两位前面补0
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);

		return id;
	}


    /**
     * Description：随机测试生成ID方法
     */
	public static void main(String[] args) {

		for(int i=0; i< 10; i++){
            System.out.println(genItemId());
        }
	}
}
