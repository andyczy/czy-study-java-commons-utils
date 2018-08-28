package com.syiti.bzf.util.support;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.syiti.bzf.util.support.DateUtil.TimeStamp2Date;
import static org.apache.poi.ss.usermodel.BorderStyle.DOUBLE;
import static org.apache.poi.ss.usermodel.BorderStyle.MEDIUM;
import static org.apache.poi.ss.usermodel.BorderStyle.SLANTED_DASH_DOT;
import static org.apache.poi.ss.util.CellUtil.createCell;

/**
 * @author Chenzy
 * @date 2018-05-03
 * @description: Excel 操作相关工具类
 */
public class ExcelUtils {


    /**
     * 日期格式yyyy-mm-dd
     * 数字格式，防止长数字成为科学计数法形式，或者int变为double形式
     */
    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    private static final DecimalFormat df = new DecimalFormat("0");
    private static final String DataValidationError1 = "国有企业职工住房信息管理系统——提醒您！";
    private static final String DataValidationError2 = "数据不规范，请选择表格列表中的数据！";


    /**
     * * excel 导出模版
     *
     * @param response
     * @param exportDataList   数据标题
     * @param fileName         文件名称
     * @param sheetName        sheet名称
     * @param dropDownListData 下拉列表要显示的列和对应的值
     * @param lastRow          显示下拉列表最后的行：为空默认是500
     */
    @SuppressWarnings({"deprecation", "rawtypes"})
    public static void exportForExcelModel(HttpServletResponse response, List<String[]> exportDataList, String fileName,
                                           String[] sheetName, List<List<String[]>> dropDownListData, Integer lastRow) {
        try {
            SXSSFWorkbook sxssfWbook = new SXSSFWorkbook();
            OutputStream outputStream = null;
            SXSSFRow sxssfWrow = null;

            for (int k = 0; k < exportDataList.size(); k++) {
                int iRows = 0;
                int titleRows = 0;
                SXSSFSheet xssfWsheet = sxssfWbook.createSheet();
                xssfWsheet.setDefaultColumnWidth((short) 23);
                sxssfWbook.setSheetName(k, sheetName[k]);
                sxssfWrow = xssfWsheet.createRow(titleRows);
                sxssfWrow.setHeight((short) (2 * 288));


                //写入标题
                int j = 0;
                String[] valueList = exportDataList.get(k);
                for (int i = 0; i < valueList.length; i++) {
                    createCell(sxssfWrow, iRows, valueList[i]);
                    titleRows++;
                    iRows++;
                }

                /**
                 * 下拉列表:开始行，结束行，开始列，结束列
                 */
                if (dropDownListData != null) {
                    for (int col = 0; col < dropDownListData.get(k).get(0).length; col++) {
                        Integer colv = Integer.parseInt(dropDownListData.get(k).get(0)[col]);
                        setDataValidation(xssfWsheet, dropDownListData.get(k).get(col + 1), 1, lastRow == null ? 500 : lastRow, colv, colv);
                    }
                }
            }
            // 设置下载类型
            response.setHeader("Charset", "UTF-8");
            response.setHeader("Content-Type", "application/force-download");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf8") + ".xlsx");
            response.flushBuffer();
            outputStream = response.getOutputStream();
            sxssfWbook.write(outputStream);
            sxssfWbook.dispose();
        } catch (Exception e) {
            System.out.println("------------------------------- Excel 工具类导出模板异常！ -------------------------------");
            e.printStackTrace();
        }
    }

    /**
     * * excel 导出  poi 3.17
     * 更新日志:
     * 1.response.reset();注释掉reset，否在会出现跨域错误
     * 2.新增导出多个单元
     * 3.poi官方建议大数据量解决方案：SXSSFWorkbook
     * 4.有下拉列表和校验
     * 5.数据遍历方式换成数组和只修改第一行样式
     *
     * @param response
     * @param exportDataList   导出的数据
     * @param fileName         文件名称
     * @param sheetName        sheet 名称
     * @param dropDownListData 下拉列表要显示的列和对应的值
     * @return
     */
    @SuppressWarnings({"deprecation", "rawtypes"})
    public static Boolean exportForExcelOptimize(HttpServletResponse response, List<List<String[]>> exportDataList, String fileName,
                                                 String[] sheetName, List<List<String[]>> dropDownListData) {
        long startTime = System.currentTimeMillis();
        System.out.println("--------------- Excel 工具类导出运行开始时间:  " + TimeStamp2Date(startTime + "", null) + " ---------------");
        //内存中保留 1000 条数据，以免内存溢出，其余写入硬盘
        SXSSFWorkbook sxssfWbook = new SXSSFWorkbook(1000);
        OutputStream outputStream = null;
        SXSSFRow sxssfWrow = null;
        try {
            //sheet单元数
            int k = 0;
            for (List<String[]> dataList : exportDataList) {
                SXSSFSheet xssfWsheet = sxssfWbook.createSheet();
                xssfWsheet.setDefaultColumnWidth((short) 26);
                sxssfWbook.setSheetName(k, sheetName[k]);

                /**
                 * 写入数据:第一行是标题、其他是数据
                 */
                int JRow = 0;
                for (String[] listValue : dataList) {
                    int columnIndex = 0;
                    sxssfWrow = xssfWsheet.createRow(JRow);
                    /**
                     * 设置样式:第一行
                     */
                    if (JRow == 0) {
                        setExcelStyle(sxssfWrow, sxssfWbook, null, true, listValue.length);
                    }
                    for (int j = 0; j < listValue.length; j++) {
                        createCell(sxssfWrow, columnIndex, listValue[j]);
                        columnIndex++;
                    }
                    JRow++;
                }
                /**
                 * 下拉列表:开始行，结束行，开始列，结束列
                 */
                if (dropDownListData != null) {
                    for (int col = 0; col < dropDownListData.get(k).get(0).length; col++) {
                        Integer colv = Integer.parseInt(dropDownListData.get(k).get(0)[col]);
                        setDataValidation(xssfWsheet, dropDownListData.get(k).get(col + 1), 1, dataList.size() < 100 ? 500 : dataList.size(), colv, colv);
                    }
                }
                k++;
            }
            // 设置下载类型
            response.setHeader("Charset", "UTF-8");
            response.setHeader("Content-Type", "application/force-download");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf8") + ".xlsx");
            response.flushBuffer();
            outputStream = response.getOutputStream();
            sxssfWbook.write(outputStream);
            //处理在磁盘上支持此工作簿的临时文件
            sxssfWbook.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("--------------- Excel 工具类导出运行结束时间:  " + TimeStamp2Date(endTime + "", null) + "  ---------------");
        System.out.println("--------------- Excel 工具类导出运行时间:  " + (endTime - startTime) + "ms ---------------");
        return true;
    }


    /**
     * * excel 导出  poi 3.17
     * 更新日志:
     * 1.response.reset();注释掉reset，否在会出现跨域错误
     * 2.新增导出多个单元
     * 3.poi官方建议大数据量解决方案：SXSSFWorkbook
     * 4.有下拉列表和校验
     *
     * @param response
     * @param exportDataList   导出的数据
     * @param fileName         文件名称
     * @param sheetName        sheet 名称
     * @param dropDownListData 下拉列表要显示的列和对应的值
     * @return
     */
    @SuppressWarnings({"deprecation", "rawtypes"})
    @Deprecated
    public static Boolean exportForExcel(HttpServletResponse response, List<List<LinkedHashMap<String, String>>> exportDataList, String fileName,
                                         String[] sheetName, List<List<String[]>> dropDownListData) {
        long startTime = System.currentTimeMillis();
        System.out.println("--------------- Excel 工具类导出运行开始时间:  " + TimeStamp2Date(startTime + "", null) + " ---------------");
        //内存中保留 1000 条数据，以免内存溢出，其余写入硬盘
        SXSSFWorkbook sxssfWbook = new SXSSFWorkbook(1000);
        OutputStream outputStream = null;
        SXSSFRow sxssfWrow = null;
        try {
            //sheet单元数
            int k = 0;
            for (List<LinkedHashMap<String, String>> dataList : exportDataList) {
                int sxssfWrowInt = 0;
                SXSSFSheet xssfWsheet = sxssfWbook.createSheet();
                xssfWsheet.setDefaultColumnWidth((short) 23);
                sxssfWbook.setSheetName(k, sheetName[k]);
                sxssfWrow = xssfWsheet.createRow(sxssfWrowInt);
                sxssfWrow.setHeight((short) (2 * 288));
                int dataCount = exportDataList.size();

                if (dataList.size() == 0) {
                    continue;
                }
                /**
                 * 写入标题
                 */
                //行数
                int iRow = 0;
                LinkedHashMap<String, String> titleHashMap = dataList.get(0);
                for (Map.Entry entry : titleHashMap.entrySet()) {
                    createCell(sxssfWrow, iRow, entry.getKey().toString());
                    sxssfWrowInt++;
                    iRow++;
                }
                /**
                 * 写入数据
                 */
                //行数
                int JRow = 1;
                for (LinkedHashMap<String, String> valueHashMap : dataList) {
                    int columnIndex = 0;
                    sxssfWrow = xssfWsheet.createRow(JRow);
                    for (Iterator it = valueHashMap.keySet().iterator(); it.hasNext(); ) {
                        createCell(sxssfWrow, columnIndex, valueHashMap.get(it.next()));
                        columnIndex++;
                    }
                    JRow++;
                }
                /**
                 * 下拉列表:开始行，结束行，开始列，结束列
                 */
                if (dropDownListData != null) {
                    for (int col = 0; col < dropDownListData.get(k).get(0).length; col++) {
                        Integer colv = Integer.parseInt(dropDownListData.get(k).get(0)[col]);
                        setDataValidation(xssfWsheet, dropDownListData.get(k).get(col + 1), 1, dataList.size() < 100 ? 500 : dataList.size(), colv, colv);
                    }
                }
                k++;
            }
            // 设置下载类型
            response.setHeader("Charset", "UTF-8");
            response.setHeader("Content-Type", "application/force-download");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf8") + ".xlsx");
            response.flushBuffer();
            outputStream = response.getOutputStream();
            sxssfWbook.write(outputStream);
            //处理在磁盘上支持此工作簿的临时文件
            sxssfWbook.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("--------------- Excel 工具类导出运行结束时间:  " + TimeStamp2Date(endTime + "", null) + "  ---------------");
        System.out.println("--------------- Excel 工具类导出运行时间:  " + (endTime - startTime) + "ms ---------------");
        return true;
    }


    /**
     * 模板数据导入Excel
     * 更新日志:
     * 1.共用获取Excel表格数据
     * 2.多单元数据获取
     *
     * @param book
     * @param sheetIndex 要导入的单元数量
     * @return
     */
    @SuppressWarnings({"deprecation", "rawtypes"})
    public static List<List<LinkedHashMap<String, String>>> importForExcelData(Workbook book, String[] sheetName) {
        try {
            List<List<LinkedHashMap<String, String>>> newList = new ArrayList<>();
            for (int k = 0; k <= sheetName.length - 1; k++) {
                // 得到第一个工作表对象、得到第一个工作表中的总行数
                Sheet sheet = book.getSheetAt(k);
                int rowCount = sheet.getLastRowNum() + 1;
                Row valueRow = null;
                Cell valueCell = null;


                //三个sheet单元校验
                if (!sheet.getSheetName().equals(sheetName[k])) {
                    System.out.println("-------------------------------  sheet单元校验，单元格命名不规范!  -------------------------------");
                    return null;
                }
                int numberOfSheets = book.getNumberOfSheets();
                if (numberOfSheets == 0 || numberOfSheets > sheetName.length) {
                    System.out.println("-------------------------------  sheet单元个数校验!  -------------------------------");
                    return null;
                }

                List<LinkedHashMap<String, String>> rowListValue = new ArrayList<>();
                LinkedHashMap<String, String> cellHashMap = null;
                //数据获取：从第二行开始
                for (int i = 1; i < rowCount; i++) {
                    valueRow = sheet.getRow(i);
                    if (valueRow == null) {
                        continue;
                    }
                    cellHashMap = new LinkedHashMap<>();
                    if (k != 2) {
                        if (valueRow.getCell(0) == null || StringUtils.isBlank(valueRow.getCell(0).toString())) {
                            continue;
                        }
                    } else {
                        if (valueRow.getCell(1) == null || StringUtils.isBlank(valueRow.getCell(1).toString())) {
                            continue;
                        }
                    }
                    for (int j = 0; j < valueRow.getLastCellNum(); j++) {
                        cellHashMap.put(j + "", getCellVal(valueRow.getCell(j)));
                    }
                    if (cellHashMap.size() > 0) {
                        rowListValue.add(cellHashMap);
                    }
                }
                newList.add(rowListValue);
            }
            return newList;
        } catch (Exception e) {
            System.out.println("-------------------------------  Excel 工具类导入异常，获取数据为空!  -------------------------------");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 下拉列表
     *
     * @param xssfWsheet
     * @param list       下拉数据
     * @param firstRow   第一行
     * @param lastRow    最后一行
     * @param firstCol   第一列
     * @param lastCol    最后一列
     */
    public static void setDataValidation(SXSSFSheet xssfWsheet, String[] list, Integer firstRow, Integer lastRow, Integer firstCol, Integer lastCol) {
        //下拉列表:开始行，结束行，开始列，介绍列
        DataValidationHelper helper = xssfWsheet.getDataValidationHelper();
        //设置行列范围：开始行，结束行，开始列，介绍列
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        //如果带双引号超过30个, 打开excel的时候就会提示错误 而且下拉框不生效,
        //如果不带双引号就没有问题(测试心得)
        //设置下拉框数据
        DataValidationConstraint constraint = helper.createExplicitListConstraint(list);
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        dataValidation.createErrorBox(DataValidationError1, DataValidationError2);
        //处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        xssfWsheet.addValidationData(dataValidation);
    }


    /**
     * 获取Excel单元格中的值并且转换java类型格式
     *
     * @param cell
     * @return
     */
    public static String getCellVal(Cell cell) {
        String val = null;
        if (cell != null) {
            CellType cellType = cell.getCellTypeEnum();
            switch (cellType) {
                case NUMERIC:
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        val = fmt.format(cell.getDateCellValue());
                    } else {
                        val = df.format(cell.getNumericCellValue());
                    }
                    break;
                case STRING:
                    val = cell.getStringCellValue();
                    break;
                case BOOLEAN:
                    val = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    val = cell.getStringCellValue();
                    break;
                case ERROR:
                    val = "错误";
                    break;
                case FORMULA:
                    try {
                        val = String.valueOf(cell.getStringCellValue());
                    } catch (IllegalStateException e) {
                        val = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                default:
                    val = cell.getRichStringCellValue() == null ? null : cell.getRichStringCellValue().toString();
            }
        } else {
            val = "";
        }
        return val;
    }


    /**
     * 设置单元格样式
     *
     * @param sxssfWrow
     * @param wb
     * @param fontSize
     * @param bold
     * @param column
     */
    public static void setExcelStyle(SXSSFRow sxssfWrow, SXSSFWorkbook wb, Double fontSize, Boolean bold, int column) {
        CellStyle cellStyle = wb.createCellStyle();
        sxssfWrow.setHeight((short) (2 * 288));  //宽度
        Cell cellRowStyle = sxssfWrow.createCell(column);

        // 设置单元格字体样式
        XSSFFont font = (XSSFFont) wb.createFont();
        font.setBold(bold);// 这是字体加粗
        font.setFontName("宋体");// 设置字体的样式
        font.setFontHeight(fontSize == null ? 14 : 16);// 设置字体的大小
        cellStyle.setFont(font);// 将字体填充到表格中去
        cellRowStyle.setCellStyle(cellStyle);
    }

}
