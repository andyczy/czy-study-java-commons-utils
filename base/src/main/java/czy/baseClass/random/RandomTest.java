package czy.baseClass.random;

import java.util.Random;

//  随机数��������
public class RandomTest {
	public static void main(String[] args) {

		Random dom = new Random();
		int i = dom.nextInt(10);

		System.out.println(i);
	}

}
