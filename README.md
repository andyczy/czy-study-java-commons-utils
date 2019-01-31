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
            <version>3.2.3</version>      
        </dependency> 
   
  [javadoc 文档](https://oss.sonatype.org/service/local/repositories/releases/archive/com/github/andyczy/java-excel-utils/3.2/java-excel-utils-3.2-javadoc.jar/!/com/github/andyczy/java/excel/ExcelUtils.html)
 
  [版本-3.X 教程](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-3.2.md)   
  
  亲自测试：WPS、office 08、10、11、12、16 能正常打开。

### 更新日志
### 3.2.3 
    单表百万数据量导出时样式设置过多，导致速度慢（行、列、单元格样式暂时控制10万行、超过无样式）                          
    大数据量情况下一般不会每个单元格设置样式、不然很难解决内存溢出等问题。                 
    修改输出流（只能输出一次、如 response 响应输出，则不会输出到本地路径的。）                                   
    修改注释                            
    新增函数【本地测试：输出到本地路径、testLocalNoStyleNoResponse 】                  
    新增函数【无样式（行、列、单元格样式）、exportForExcelsNoStyle 】                
    初始化函数：ExcelUtils.setExcelUtils() 更改为 ExcelUtils.initialization()          
    属性：columnMap 更改为 setMapColumnWidth
    
    目前导出速度：
    （单表）1万行、20列：1.6秒            
    （单表）10万行、20列：12秒                 
    （单表）20万行、20列：37秒            
    
    （4张表）1*4万行、20列：6秒           
    （4张表）10*4万行、20列：35秒                     
    （4张表）20*4万行、20列：66秒         
    
### 下次准备更新
    单表超过百万数据、自动分表。      
    优化速度和内存溢出问题。                   
      
### 版本 3.2 【推荐使用】:导出配置 ExcelUtils.exportForExcelsOptimize()
             
        ExcelUtils excelUtils = ExcelUtils.setExcelUtils();
        // 必填项--导出数据
        excelUtils.setDataLists(dataLists);      //   参数请看下面的格式 
        // 必填项--sheet名称
        excelUtils.setSheetName(sheetNameList);
        // 文件名称(可为空，默认是：sheet 第一个名称)
        excelUtils.setFileName(excelName);
        
        // 输出流：response 响应（输出流：必须选一）
        excelUtils.setResponse(response);
        // 输出流：可直接输出本地路径（输出流：必须选一）
        // excelUtils.setFilePath("F:\\test.xlsx"); 
 
        // 每个表格的大标题（可为空）
        excelUtils.setLabelName(labelName);
        // 自定义：固定表头（可为空）
        excelUtils.setPaneMap(setPaneMap);
        // 自定义：单元格合并（可为空）
        excelUtils.setRegionMap(regionMap);
        
        // 自定义：对每个单元格自定义列宽（可为空）
        excelUtils.setMapColumnWidth(mapColumnWidth);
        // 自定义：某一行样式（可为空）
        excelUtils.setRowStyles(stylesRow);
        // 自定义：某一列样式（可为空）
        excelUtils.setColumnStyles(columnStyles);
        // 自定义：每一个单元格样式（可为空）
        excelUtils.setStyles(styles);
                
        // 自定义：对每个单元格自定义下拉列表（可为空）
        excelUtils.setDropDownMap(dropDownMap);
        // 自定义：忽略边框(可为空：默认是有边框)
        excelUtils.setNotBorderMap(notBorderMap);       
            
        // 执行导出
        excelUtils.exportForExcelsOptimize();       
 
### 导入配置 ExcelUtils.importForExcelData(...)
        * 获取多单元数据         
        * 自定义：多单元从第几行开始获取数据            
        * 自定义：多单元根据那些列为空来忽略行数据         

### [POI](http://poi.apache.org/components/spreadsheet/how-to.html#sxssf)     
### License
java-excel-utils is Open Source software released under the Apache 2.0 license.     