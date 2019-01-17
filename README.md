# czy-nexus-commons-utils
   本库是发布到 [search.maven](https://search.maven.org/)  、 [mvnrepository](https://mvnrepository.com/)公共仓库的管理库。    
   (教程博客)[https://blog.csdn.net/JavaWebRookie/article/details/80843653]、可通过maven方式下载源码查看注释。                
   (工具类集库)[https://github.com/andyczy/czy-study-java-commons-utils]    
   (开源中国)[https://www.oschina.net/]          
   
   
   推荐使用最新版本：        
          
         <!--
            maven：https://mvnrepository.com/artifact/com.github.andyczy/java-excel-utils
            教程文档：https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-3.2.md
         -->
        <dependency>        
            <groupId>com.github.andyczy</groupId>       
            <artifactId>java-excel-utils</artifactId>       
            <version>3.2</version>      
        </dependency> 
   
  [javadoc 文档](https://oss.sonatype.org/service/local/repositories/releases/archive/com/github/andyczy/java-excel-utils/3.2/java-excel-utils-3.2-javadoc.jar/!/com/github/andyczy/java/excel/ExcelUtils.html)
       
  [版本-2.0之前教程](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-2.0.md)   
        
  [版本-3.2教程](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-3.2.md)   
  
  亲自测试：WPS、office 08、10、11、12、16 能正常打开。
    
      
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
        excelUtils.setColumnMap(mapColumnWidth);
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
        
### 2.0之前版本 :导出配置 ExcelUtils.exportForExcel(...)
        * 可提供模板下载           
        * 自定义下拉列表：对每个单元格自定义下拉列表         
        * 自定义列宽：对每个单元格自定义列宽         
        * 自定义样式：对每个单元格自定义样式  
        * 自定义样式：单元格自定义某一列或者某一行样式            
        * 自定义单元格合并：对每个单元格合并 
        * 自定义：每个表格的大标题          
        * 自定义：对每个单元格固定表头    
        
        
### 导入配置 ExcelUtils.importForExcelData(...)
        * 获取多单元数据         
        * 自定义：多单元从第几行开始获取数据            
        * 自定义：多单元根据那些列为空来忽略行数据         
        
### License
java-excel-utils is Open Source software released under the Apache 2.0 license.     