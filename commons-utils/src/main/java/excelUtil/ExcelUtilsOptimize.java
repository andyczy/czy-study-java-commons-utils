package com.syiti.bzf.util.support;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
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

import static org.apache.poi.ss.util.CellUtil.createCell;

/**
 * @author Chenzy
 * @date 2018-08-27
 * @description: Excel 操作相关工具类
 */
public class ExcelUtilsOptimize {


    /**
     * 日期格式yyyy-mm-dd
     * 数字格式，防止长数字成为科学计数法形式，或者int变为double形式
     */
    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    private static final DecimalFormat df = new DecimalFormat("0");
    private static final String DataValidationError1 = "国有企业职工住房信息管理系统——提醒您！";
    private static final String DataValidationError2 = "数据不规范，请选择表格列表中的数据！";


    /**
     * 功能描述:
     * 1.excel 数据导出
     * 2.excel 导出模板
     * <p>
     * 更新日志:
     * 1.response.reset();注释掉reset，否在会出现跨域错误
     * 2.新增导出多个单元
     * 3.poi官方建议大数据量解决方案：SXSSFWorkbook
     * 4.有下拉列表和校验
     * 5.数据遍历方式换成数组(效率较高)、只修改第一行样式
     * 6.可提供模板下载和返回值是json(Result对象)
     * <p>
     * 版  本:
     * 1.apache poi 3.17
     * 2.excel 导出格式是: xlsx
     *
     * @param response
     * @param exportDataList   导出的数据、(不可为空：如果只有标题就导出模板、则数据导出)
     * @param fileName         文件名称、(可为空：为空就文件名称默认是：excel数据信息表)
     * @param sheetName        sheet 名称、（不可为空）
     * @param dropDownListData 下拉列表要显示的列和对应的值、（可为空：为空就不显示下拉列表数据）
     * @return
     */
    @SuppressWarnings({"deprecation", "rawtypes"})
    public static Boolean exportForExcelOptimize(HttpServletResponse response, List<List<String[]>> exportDataList, String fileName, String[] sheetName, List<List<String[]>> dropDownListData) {
        long startTime = System.currentTimeMillis();

        //内存中保留 1000 条数据，以免内存溢出，其余写入硬盘
        SXSSFWorkbook sxssfWbook = new SXSSFWorkbook(1000);
        OutputStream outputStream = null;
        SXSSFRow sxssfWrow = null;
        try {
            if (exportDataList.size() == 0) {
                return false;
            }

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
                     * 设置标题样式
                     */
                    if (JRow == 0) {
                        setExcelStyle(sxssfWrow, sxssfWbook, null, false, listValue.length);
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
            /**
             * 设置响应头信息
             */
            response.setHeader("Charset", "UTF-8");
            response.setHeader("Content-Type", "application/force-download");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName == null ? "excel数据信息表" : fileName, "utf8") + ".xlsx");
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
        System.out.println("--------------- Excel 工具类导出运行时间:  " + (endTime - startTime) + "ms ---------------");
        return true;
    }


    /**
     * 功能描述:
     * 1.excel 模板数据导入
     * <p>
     * 更新日志:
     * 1.共用获取Excel表格数据
     * 2.多单元数据获取
     * <p>
     * 版  本:
     * 1.apache poi 3.17
     *
     * @param book
     * @param sheetName
     * @return
     */
    @SuppressWarnings({"deprecation", "rawtypes"})
    public static List<List<LinkedHashMap<String, String>>> importForExcelData(Workbook book, String[] sheetName) {
        long startTime = System.currentTimeMillis();
        try {
            List<List<LinkedHashMap<String, String>>> newDataList = new ArrayList<>();
            for (int k = 0; k <= sheetName.length - 1; k++) {
                // 得到第一个工作表对象、得到第一个工作表中的总行数
                Sheet sheet = book.getSheetAt(k);
                int rowCount = sheet.getLastRowNum() + 1;
                Row valueRow = null;
                Cell valueCell = null;


                /**特殊处理逻辑**/
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
                /**
                 * 数据获取:从第二行开始获取
                 */
                for (int i = 1; i < rowCount; i++) {
                    valueRow = sheet.getRow(i);
                    if (valueRow == null) {
                        continue;
                    }
                    cellHashMap = new LinkedHashMap<>();


                    /**
                     * 获取列数据
                     */
                    for (int j = 0; j < valueRow.getLastCellNum(); j++) {
                        cellHashMap.put(Integer.toString(j), getCellVal(valueRow.getCell(j)));
                    }
                    if (cellHashMap.size() > 0) {
                        rowListValue.add(cellHashMap);
                    }
                }
                newDataList.add(rowListValue);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("--------------- Excel 工具类导出运行结束时间:  " + endTime + "  ---------------");
            return newDataList;
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
        sxssfWrow.setHeight((short) (2 * 288));
        Cell cellRowStyle = sxssfWrow.createCell(column);
        // 设置单元格字体样式
        XSSFFont font = (XSSFFont) wb.createFont();
        font.setBold(bold);
        font.setFontName("宋体");
        font.setFontHeight(fontSize == null ? 14 : 16);
        // 将字体填充到表格中去
        cellStyle.setFont(font);
        cellRowStyle.setCellStyle(cellStyle);
    }

}

