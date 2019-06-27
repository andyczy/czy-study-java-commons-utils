# czy-nexus-commons-utils
   (本库)[https://github.com/andyczy/czy-nexus-commons-utils]，是发布到 [search.maven](https://search.maven.org/)  、 [mvnrepository](https://mvnrepository.com/)公共仓库的管理库。    
   (csdn教程博客)[https://blog.csdn.net/JavaWebRookie/article/details/80843653]、可通过maven方式下载源码查看注释。                
   (github工具类集库)[https://github.com/andyczy/czy-study-java-commons-utils]    
   (开源中国)[https://www.oschina.net/p/java-excel-utils]          
   
   
   推荐使用最新版本：        
          
         <!--
            maven：https://mvnrepository.com/artifact/com.github.andyczy/java-excel-utils
            教程文档：https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-Andyczy.md
         -->
        <dependency>        
            <groupId>com.github.andyczy</groupId>       
            <artifactId>java-excel-utils</artifactId>       
            <version>4.0</version>      
        </dependency> 

  [教程说明](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README-Andyczy.md)   
   
   
  亲自测试：WPS、office 07、08、09、10、11、12、16 能正常打开。其他版本待测试！                       
  注:POI SXSSFWorkbook 最高限制1048576行,16384列               
   
            
###  方式一：导出使用函数 ExcelUtils.exportForExcelsOptimize() 和  LocalExcelUtils.exportForExcelsOptimize() 
        //【推荐使用该方式】【建议大数据量下不要过多设置样式】
             
        ExcelUtils excelUtils = ExcelUtils.initialization();
        // 必填项--导出数据（参数请看下面的格式）
        excelUtils.setDataLists(dataLists);   
        // 必填项--sheet名称（如果是多表格导出、sheetName也要是多个值！）
        excelUtils.setSheetName(sheetNameList);
        // 文件名称(可为空，默认是：sheet 第一个名称)
        excelUtils.setFileName(excelName);
        
        // web项目response响应输出流：必须填 【ExcelUtils 对象】
        excelUtils.setResponse(response);
        
        // 输出本地【LocalExcelUtils 对象】
        // excelUtils.setFilePath("F://test.xlsx");

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
 
###  方式三:导出函数 ExcelUtils.exportForExcelsNoStyle()  和  LocalExcelUtils.exportForExcelsNoStyle()  
    无样式（行、列、单元格样式）推荐使用这个函数、样式设置过多会影响速度   
        
       
### 导入使用函数： ExcelUtils.importForExcelData(......)  和  LocalExcelUtils.importForExcelData(......)
        * 获取多单元数据         
        * 自定义：多单元从第几行开始获取数据【看本文最底下参数说明】            
        * 自定义：多单元根据那些列为空来忽略行数据【看本文最底下参数说明】         

  
###  ExcelUtils 对象与 LocalExcelUtils 区别。
    ExcelUtils：     web响应有  response
    LocalExcelUtils：本地输出没 response
    
    
###  Test 测试【新增本地测试】

 
###  方式四:导出函数 ExcelUtils.exportForExcel(......)   过期注解
        * 可提供模板下载           
        * 自定义下拉列表：对每个单元格自定义下拉列表         
        * 自定义列宽：对每个单元格自定义列宽         
        * 自定义样式：对每个单元格自定义样式  
        * 自定义样式：单元格自定义某一列或者某一行样式            
        * 自定义单元格合并：对每个单元格合并 
        * 自定义：每个表格的大标题          
        * 自定义：对每个单元格固定表头    


        
### 数据格式
   1、导出数据：参数 dataLists
   
        @Override
           public List<List<String[]>> exportBill(String deviceNo,String snExt,Integer parentInstId,String startDate, String endDate){
               List<List<String[]>> dataLists = new ArrayList<>();
               List<String[]> oneList = new ArrayList<>();  // 表格一数据
               PageInfo<BillInfo> pagePageInfo = getBillPage(1,10000,null,snExt,deviceNo,parentInstId,startDate,endDate);
               String[] valueString = null;
 
               String[] headers = {"序号","标题一","标题一","标题二","标题三","标题四","标题五","标题六"};
               String[] headersTwo = {" ","标题一小标题（合并用）","标题一小标题（合并用）"," "," "," "," "};
               oneList.add(headers);
               oneList.add(headersTwo);
                
               for (int i = 0; i < pagePageInfo.getList().size(); i++) {
                   valueString = new String[]{(i + 1) + "", pagePageInfo.getList().get(i).getSnExt(),
                           getNeededDateStyle(pagePageInfo.getList().get(i).getPayTime(),"yyyy-MM-dd hh:mm:ss"),
                           pagePageInfo.getList().get(i).getInstName(),pagePageInfo.getList().get(i).getStatisticsPrice()+"",
                           pagePageInfo.getList().get(i).getDeviceNo(),
                           pagePageInfo.getList().get(i).getWarning()==1?"是":"否"};
                   oneList.add(valueString);
               }
               
               List<String[]> twoList = new ArrayList<>();  // 表格二数据（和表一相同）
               
               
               listArray.add(oneList);   // 多个表格导出就是多个 
               listArray.add(twoList);   // 多个表格导出就是多个 
               return dataLists;
           }  
           
   1.1、sheetName：参数
   
       需注意的是：如果是多表格导出、sheetName也要是多个值！
       如上面有两个数据导出： String[] sheetNameList = new String[]{"表格一数据","表格二数据"};
       excelUtils.setSheetName(sheetNameList);
       
       
       
   2、自定义列宽：参数 mapColumnWidth
   
       参数说明：
       HashMap<Integer, HashMap<Integer, Integer>> mapColumnWidth = new HashMap<>();
       HashMap<Integer, Integer> mapColumn = new HashMap<>();
       //第一列、宽度为 3[3的大小就是两个12号字体刚刚好的列宽]（注意：excel从零行开始数）
       mapColumn.put(0, 3);  
       mapColumn.put(1, 20);
       mapColumn.put(2, 15);
       //第一个单元格列宽
       mapColumnWidth.put(1, mapColumn);
       
   3、自定义固定表头：参数 paneMap
   
       参数说明：
       HashMap paneMap = new HashMap();
       //第一个表格、第一行开始固定表头
       paneMap.put(1, 1); 
       
   
   4、自定义合并单元格：参数 regionMap
   
        参数说明：
        HashMap regionMap = new HashMap();                  
        List<List<Integer[]>> region  = new ArrayList<>();
        List<Integer[]> regionList = new ArrayList<>();                  
        //代表起始行号，终止行号， 起始列号，终止列号进行合并。（注意：excel从零行开始数）
        regionList.add(new Integer[]{1, 1, 0, 10});
        regionList.add(new Integer[]{2, 3, 1, 1});
        //第一个表格设置。
        region.add(regionList);             
        regionMap.put(1, region);
                                      
        
   5、自定义每个表格第几行或者是第几列的样式：参数 rowStyles / columnStyles
           
        参数说明：
        HashMap columnStyles = new HashMap();
        List list = new ArrayList();
        //1、样式（是否居中？，是否右对齐？，是否左对齐？， 是否加粗？，是否有边框？ ）
        list.add(new Boolean[]{true, false, false, false, true}); 
        //2、第几行或者是第几列（注意：excel从零行开始数）       
        list.add(new Integer[]{1, 3});   
        //3、颜色值（8是黑色、10红色等） 、颜色、字体、行高？（可不设置）                                        
        list.add(new Integer[]{10,14,null});    
        //第一表格                                 
        columnStyles.put(1,list);                                                     
        
   6、自定义每一个单元格样式：参数 styles
        
       参数说明：
       HashMap styles = new HashMap();
       List< List<Object[]>> stylesList = new ArrayList<>();
       List<Object[]> stylesObj = new ArrayList<>();
       List<Object[]> stylesObjTwo = new ArrayList<>();
       
       //1、样式一（是否居中？，是否右对齐？，是否左对齐？， 是否加粗？，是否有边框？ ）
       stylesObj.add(new Boolean[]{true, false, false, false, true});      
       //1、颜色值（8是黑色、10红色等） 、颜色、字体、行高？（可不设置）（必须放第二）
       stylesObj.add(new Integer[]{10, 12});                             
       //1、第五行、第一列（注意：excel从一开始算）
       stylesObj.add(new Integer[]{5, 1});                                  
       stylesObj.add(new Integer[]{6, 1});                                
       
       //2、样式二（必须放第一）
       stylesObjTwo.add(new Boolean[]{false, false, false, true, true}); 
       //2、颜色值（8是黑色、10红色等） 、颜色、字体、行高？（可不设置）（必须放第二）  
       stylesObjTwo.add(new Integer[]{10, 12,null});    
       //2、第二行第一列（注意：excel从一开始算）                 
       stylesObjTwo.add(new Integer[]{2, 1});                              
       
       stylesList.add(stylesObj);
       stylesList.add(stylesObjTwo);
       //第一个表格所有自定义单元格样式 
       styles.put(1, stylesList);                                             
             
   
   7、自定义忽略边框：参数 notBorderMap
   
       HashMap notBorderMap = new HashMap();
       //忽略边框（1行、5行）、默认是数据（除大标题外）是全部加边框的。
       notBorderMap.put(1, new Integer[]{1, 5});   
   
   
   8、自定义下拉列表值：参数 dropDownMap
      
       参数说明：
       HashMap dropDownMap = new HashMap();
       List<String[]> dropList = new ArrayList<>();                  
       //必须放第一：设置下拉列表的列（excel从零行开始数）
       String[] sheetDropData = new String[]{"1", "2", "4"};
       //下拉的值放在 sheetDropData 后面。        
       String[] sex = {"男,女"};                                   
       dropList.add(sheetDropData);
       dropList.add(sex);
       //第一个表格设置。
       dropDownMap.put(1,dropList); 
   
   9、导入配置：(第几行开始获取数据)  参数 indexMap
       
       参数说明：多单元从第几行开始获取数据，默认从第二行开始获取（可为空)
       HashMap hashMapIndex = new HashMap();
       hashMapIndex.put(1,3);  //  第一个表格从第三行开始获取
       
  
   10、导入配置：(列为空来忽略行数据)  参数 continueRowMap
       
       参数说明：多单元根据那些列为空来忽略行数据（可为空)
       HashMap mapContinueRow = new HashMap();
       mapContinueRow.put(1,new Integer[]{1, 3});  // 第一个表格第1、3列为空就忽略这行数据
       
   11、导入时间格式（默认：yyyy-MM-dd）、导入数字保留的小数点（默认：#.###### 六位）
        
        ExcelUtils excelUtils = ExcelUtils.initialization();
        excelUtils.setNumeralFormat("#.####");                  // (可为空)期望保留小数的位数（#.####）这样保留四位。
        // (可为空) （poi 只接受无中文的日期格式、如果你想转换别的格式，这个参数要和导入表中日期格式类似，如表格中为：2019年02月14日 12时12分）。
        excelUtils.setDateFormatStr("yyyy年MM月dd日 HH时mm分"); 
        excelUtils.setExpectDateFormatStr("yyyy-MM-dd HH-mm");  // (可为空、默认的值是：dateFormatStr 参数值) 期望转换后的日期格式。
        // 执行导入函数   ExcelUtils.importForExcelData()
   
   
   
# 感谢支持、感谢你们（排名不分先后）
蒙蒙的雨（3元微信）、阿星支付宝（100支付宝）、李凯（5元微信）、blue（5元微信2019-03-28）、鹏飞（50支付宝2019-06-05）、啊哈（3元微信19-06-26）                  
                  
![支持一下](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/sqm.png)                        
![支持一下](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/wx.jpg)  
                    
### License
java-excel-utils is Open Source software released under the Apache 2.0 license.     