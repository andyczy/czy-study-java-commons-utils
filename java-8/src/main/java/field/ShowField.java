package field;

import java.lang.reflect.Field;

public class ShowField {
	
		//第一种：方法  接收传递过来的class对象
		@SuppressWarnings("rawtypes")
		public void show(Class c){
			//getFields只能获取共有属性    getDeclaredFields可以获取私有属性
			Field[] fi = c.getDeclaredFields();
			for(Field ff : fi){
				System.out.println(ff.getName());
				System.out.println(ff.getType());
			}
		}
		
		//第二种： 接收传递过来的实体类对象   对象的属性  和 值
		@SuppressWarnings("rawtypes")
		public void obshow(Object ob){
			Class  cs = ob.getClass();
			Field[] fo = cs.getDeclaredFields();
			
			for(Field e : fo ){
				try {
					//私有属性可见
					e.setAccessible(true);
					System.out.println("名："+e.getName()+ "  "  + "值："+e.get(ob));
					
					
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			  }
		}	
}
