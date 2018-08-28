package czy.reflection.calss;



/* reflection反射
 */
public class Test {
	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args) {
		Class dom1 = null;
		Class dom2 = null;
		Class dom3 = null;
	
		try {
			
			//base.reflection
            dom1 = Class.forName("base.reflection.calss.Book");
            System.out.println("reflection获取对象输出地址："+ dom1);
			
			Book book = new Book();
			//Object bk = book.getClass();
			Class<?> bk = book.getClass();
			System.out.println("Class<?>获取对象输出地址："+ bk);
			
			dom3 = Book.class;
			System.out.println("Book.class获取对象输出地址："+ dom3);
			
			Book book3 = (Book) dom3.newInstance();
			System.out.println("newInstance输出对象输出地址："+ book3);

            Book bo = (Book) Class.forName("base.reflection.calss.Book").newInstance();
            System.out.println("Class newInstance输出对象输出地址：" + bo);

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
