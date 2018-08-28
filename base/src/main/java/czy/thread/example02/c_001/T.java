/**
 * synchronized�ؼ���
 * ��ĳ���������
 * @author mashibing
 */

package czy.thread.example02.c_001;

public class T {
	
	private int count = 10;
	private Object o = new Object();
	
	public void m() {
		synchronized(o) { //�κ��߳�Ҫִ������Ĵ��룬�������õ�o����
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
	
}

