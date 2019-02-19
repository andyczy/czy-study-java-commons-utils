package conllection.list.list_array.ourseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * List 集合     选课程
 * 添加  addAll ()
 * */ 
public class ListTest {  
	
   public List<Course> cursesToSelect;
   public ListTest(){
	   //用于存放备选课程的    实例化List集合   构造方法
	   this.cursesToSelect = new ArrayList<Course>();
   }
   
   public void testAdd(){                      
	    //添加List集合元素方法	   	      
	    Course cr1 = new Course("1" , "数据结构");
	    //添加到集合   放入容器
	    cursesToSelect.add(cr1);	     
	    //取出元素  	
	    Course temp = (Course)cursesToSelect.get(0);
	    System.out.println("添加课程" +"序号为："+ temp.id + "  " + temp.name );	  	    
	    
	    //添加备选的课程	  
	    Course cr2 = new Course("2", "java面向程序设计");
	    cursesToSelect.add(1,cr2);
	    Course temp2 = (Course)cursesToSelect.get(1);
	    System.out.println("添加课程" +"序号为："+ temp2.id + "  " + temp2.name );	   	    
	    
	    //添加多个备选的课程
	    Course[] course ={ new Course("3", "离散数学"),new Course("4", "高级语言")};
	    cursesToSelect.addAll(2, Arrays.asList(course)); 	   
	    Course temp3 = (Course)cursesToSelect.get(2);
	    Course temp4 = (Course)cursesToSelect.get(3);
	    System.out.println("添加两门课程" +"序号为："+ temp3.id + "  " + temp3.name + ";" + temp4.id + "  " + temp4.name);	   	    
	    
	    //添加多个备选的课程
	    Course[] course2 ={ new Course("5", "C语言"),new Course("6", "大学英语")};
	    cursesToSelect.addAll(4,Arrays.asList(course2));  	   
	    Course temp5 = (Course)cursesToSelect.get(4);
	    Course temp6 = (Course)cursesToSelect.get(5);
	    System.out.println("添加两门课程" +"序号为："+ temp5.id + "  " + temp5.name + ";" + temp6.id + "  " + temp6.name);
	   
	    
   }
   
   // 遍历全部元素方法
   public void testGet(){                
   	  int size = cursesToSelect.size();  
      System.out.println("所有课程如下：");
   	     for(int i=0; i<size; i++){
   		    Course cr = (Course)cursesToSelect.get(i);
   		    System.out.println("课程："+cr.id + ";" + cr.name);
   	     }
   }
   
   // 修改List集合元素方法
   public void testModify(){         
	   cursesToSelect.set(4, new Course("7", "毛概"));
	   System.out.println( );
	   System.out.println("修改位置为4的元素后剩下的元素有：");     //  修改 List 中的元素       
	   testGet();                    // 调用  testGet()方法
   }                               
   
   
   // 删除List集合元素方法
   public void testRmove(){         
	   cursesToSelect.remove(4);    
	   System.out.println( );
	   System.out.println("删除位置为4的元素后剩下的元素有：");
	   testGet();                    
   }   
}
