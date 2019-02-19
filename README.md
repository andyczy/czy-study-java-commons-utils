# czy-nexus-commons-utils
   (本库)[https://github.com/andyczy/czy-nexus-commons-utils]是发布到 [search.maven](https://search.maven.org/)  、 [mvnrepository](https://mvnrepository.com/)公共仓库的管理库。    
   (csdn教程博客)[https://blog.csdn.net/JavaWebRookie/article/details/80843653]、可通过maven方式下载源码查看注释。                
   (github工具类集库)[https://github.com/andyczy/czy-study-java-commons-utils]    
   (开源中国)[https://www.oschina.net/p/java-excel-utils]          
   
   推荐使用最新版本：        
          
         <!--
            maven：https://mvnrepository.com/artifact/com.github.andyczy/java-excel-utils
            教程文档：https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-3.2.md
         -->
        <dependency>        
            <groupId>com.github.andyczy</groupId>       
            <artifactId>java-excel-utils</artifactId>       
            <version>4.0</version>      
        </dependency> 
   
  [教程说明](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-Andyczy.md)   
  
  亲自测试：WPS、office 07、08、09、10、11、12、16 能正常打开。其他版本待测试！
  注:POI SXSSFWorkbook 最高限制1048576行,16384列


![支持一下](https://github.com/andyczy/czy-study-java-commons-utils/blob/master/sqm.png)
### 更新日志
    单表百万数据量导出时样式设置过多，导致速度慢（行、列、单元格样式暂时控制10万行、超过无样式）                          
    大数据量情况下一般不会每个单元格设置样式、不然很难解决内存溢出等问题。                 
    修改输出流（只能输出一次、如 response 响应输出，则不会输出到本地路径的。）                                   
    修改注释                            
    新增函数【ExcelUtils.testLocalNoStyleNoResponse() 、本地测试：输出到本地路径】                  
    新增函数【ExcelUtils.exportForExcelsNoStyle()、无样式（行、列、单元格样式）推荐使用这个函数、提高速度】                
    初始化函数：ExcelUtils.setExcelUtils() 更改为 ExcelUtils.initialization()          
    属性：columnMap 更改为 setMapColumnWidth
    
    目前导出速度：
    （单表）1万行、20列：1.6秒            
    （单表）10万行、20列：11秒                 
    （单表）20万行、20列：27秒     
    （单表）104万行、20列：46秒            
    
    （4张表）1*4万行、20列：6秒           
    （4张表）10*4万行、20列：33秒                     
    （4张表）20*4万行、20列：61秒
    （4张表）100*4万行、20列：85秒
             
    【4.0】新增 LocalExcelUtils 对象、excelUtil.ExcelUtilsTest 本地测试、CommonsUtils工具类
    
### 下次准备更新
    单表超过百万数据、自动分表。      
    优化速度和内存溢出问题。                   
      
 