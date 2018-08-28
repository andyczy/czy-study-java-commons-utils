package czy.baseClass.math;

/*
* 返回最接近参数的 int。结果将舍入为整数：加上 1/2，
* 对结果调用 floor 并将所得结果强制转换为 int 类型。
* 换句话说，结果等于以下表达式的值：(int)Math.floor(a + 0.5f)
*
* */
public class MathDom {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a = (int) Math.round(19.5);
		int b = (int) Math.round(-19.5);
		int c = (int) Math.round(-19.3);
		int d = (int) Math.round(-19.51);

		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
	}

}
