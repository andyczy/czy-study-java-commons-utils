# czy-nexus-commons-utils
   是JAVA操作Excel导入导出的工具类，目的是简化逻辑操作、可拓展Excel导入导出配置。                
            
   (教程博客)[https://blog.csdn.net/JavaWebRookie/article/details/80843653]                 
   (工具类集库)[https://github.com/andyczy/czy-study-java-commons-utils]         
   
   是发布到 [search.maven](https://search.maven.org/)  、 [mvnrepository](https://mvnrepository.com/)公共仓库的管理库。                  
        
   maven 使用：        
        
        <dependency>        
            <groupId>com.github.andyczy</groupId>       
            <artifactId>java-excel-utils</artifactId>       
            <version>3.2</version>      
        </dependency> 
        
  [版本-2.0之前教程](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-2.0.md)   
        
  [版本-3.1教程](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-3.0.md)   
     
  [版本-3.2教程](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-3.2.md)   
  
  
## 更新日志       
### 版本 3.1 更新说明
       1、导出函数式编程换成对象编程。                    
       2、可保存到指定本地路径。                 
       3、保存2.0版本之前的 exportForExcel(...) 函数[2.0之前版本]。            
       4、新增 exportForExcelsOptimize()  函数[版本3系]。            
       
### 版本 3.2 更新说明     
       1、去除 ExcelPojo 对象、把属性写到 ExcelUtils 类中。
         ExcelUtils excelUtils = ExcelUtils.setExcelUtils();
         excelUtils.setDataLists(dataList);
         excelUtils.setFileName(excelName);
         excelUtils.setResponse(response);
    
         excelUtils.exportForExcelsOptimize();
         
       2、修复输出流为空异常bug。

  
 
                    
### License
java-excel-utils is Open Source software released under the Apache 2.0 license.     


  
### 如果喜欢，支持一下哈

感谢                
你是我的眼 5元           
xin       3元       


![](https://github.com/andyczy/czy-study-py-ml-deepLearning/blob/master/zhifubao.png "有你的支持、我更加努力！")
