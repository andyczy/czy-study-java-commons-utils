package thread.example01;


/**
 * @auther 陈郑游
 * @create 2017/4/3 0003
 * @功能  synchronized的重入
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class SyncDubbo1 {

	public synchronized void method1(){
		System.out.println("method1..");
		method2();
	}
	public synchronized void method2(){
		System.out.println("method2..");
		method3();
		method4();
	}
	public synchronized void method3(){
		System.out.println("method3..");
	}

	public synchronized void method4(){
		System.out.println("method4..");
	}

	//main
	public static void main(String[] args) {
		final SyncDubbo1 sd = new SyncDubbo1();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				sd.method1();
			}
		});
		t1.start();
	}
}
