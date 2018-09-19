package com.syiti.bzf.util.support;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
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
 * @date 2018-05-03
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
     * 1.response.reset();注释掉reset，否在会出现跨域错误。
     * 2.新增导出多个单元。
     * 3.poi官方建议大数据量解决方案：SXSSFWorkbook。
     * 4.有下拉列表和校验。
     * 5.数据遍历方式换成数组(效率较高)。
     * 6.可提供模板下载。
     * 7.合并单元格、大标题和样式[2018-09-14]
     * 8.每个单元格自定义列宽[2018-09-18]
     * <p>
     * 版  本:
     * 1.apache poi 3.17
     * 2.apache poi-ooxml  3.17
     *
     * @param response
     * @param exportDataList   导出的数据、(不可为空：如果只有标题就导出模板、则数据导出)
     * @param columnHashMap    对每个单元格自定义列宽（可为空）
     * @param fileName         文件名称(可为空、为空就文件名称默认是：excel数据信息表)
     * @param sheetName        sheet名称（不可为空）
     * @param dropDownListData 下拉列表要显示的列和对应的值、（可为空：为空就不显示下拉列表数据）
     * @param labelName        大标题（可为空）
     * @return
     */
    @SuppressWarnings({"deprecation", "rawtypes"})
    public static Boolean exportForExcelOptimize(HttpServletResponse response, List<List<String[]>> exportDataList, HashMap columnHashMap,
                                                 List<List<String[]>> dropDownListData, String fileName, String[] sheetName, String labelName) {
        long startTime = System.currentTimeMillis();
        //  内存中保留 1000 条数据，以免内存溢出，其余写入硬盘。
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(1000);
        OutputStream outputStream = null;
        SXSSFRow sxssfRow = null;
        try {
            if (exportDataList.size() == 0) {
                System.out.println("======= 导出数据为空！");
                return false;
            }

            int k = 0;
            for (List<String[]> dataList : exportDataList) {
                SXSSFSheet sxssfSheet = sxssfWorkbook.createSheet();
                sxssfSheet.setDefaultColumnWidth((short) 26);
                sxssfWorkbook.setSheetName(k, sheetName[k]);


                int jRow = 0;
                //  大标题。
                if (StringUtils.isNotBlank(labelName)) {
                    sxssfRow = sxssfSheet.createRow(jRow);
                    setMergedRegion(sxssfSheet, 0, 0, 0, dataList.get(0).length - 1);
                    Cell cell = createCell(sxssfRow, jRow, labelName);
                    setExcelStyles(cell, sxssfRow, sxssfWorkbook, 26, false, 0, true, true);
                    jRow = 1;
                }
                //   写入标题与数据。
                for (String[] listValue : dataList) {
                    int columnIndex = 0;
                    sxssfRow = sxssfSheet.createRow(jRow);
                    //   写入标题与数据。
                    for (int j = 0; j < listValue.length; j++) {
                        Cell cell = createCell(sxssfRow, columnIndex, listValue[j]);
                        columnIndex++;
                        //  标题样式。
                        if (StringUtils.isBlank(labelName) && jRow == 0 || StringUtils.isNotBlank(labelName) && jRow == 1) {
                            setExcelStyles(cell, sxssfRow, sxssfWorkbook, null, false, 0, true, false);
                        }
                    }
                    jRow++;
                }
                //  对每个单元格自定义列宽
                if (columnHashMap != null) {
                    setColumnWidth(sxssfSheet, (HashMap) columnHashMap.get(k));
                }
                //  下拉列表:开始行，结束行，开始列，结束列。
                if (dropDownListData != null) {
                    for (int col = 0; col < dropDownListData.get(k).get(0).length; col++) {
                        Integer colv = Integer.parseInt(dropDownListData.get(k).get(0)[col]);
                        setDataValidation(sxssfSheet, dropDownListData.get(k).get(col + 1), 1, dataList.size() < 100 ? 500 : dataList.size(), colv, colv);
                    }
                }
                k++;
            }
            //  设置响应头信息。
            response.setHeader("Charset", "UTF-8");
            response.setHeader("Content-Type", "application/force-download");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName == null ? "excel数据信息表" : fileName, "utf8") + ".xlsx");
            response.flushBuffer();
            outputStream = response.getOutputStream();
            sxssfWorkbook.write(outputStream);
            //  处理在磁盘上支持此工作簿的临时文件。
            sxssfWorkbook.dispose();
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
        System.out.println("======= Excel 工具类导出运行时间:  " + (endTime - startTime) + " ms");
        return true;
    }


    /**
     * 功能描述:
     * 1.excel 模板数据导入。
     * <p>
     * 更新日志:
     * 1.共用获取Excel表格数据。
     * 2.多单元数据获取。
     * <p>
     * 版  本:
     * 1.apache poi 3.17
     * 2.apache poi-ooxml  3.17
     *
     * @param book
     * @param sheetName
     * @return
     */
    @SuppressWarnings({"deprecation", "rawtypes"})
    public static List<List<LinkedHashMap<String, String>>> importForExcelData(Workbook book, String[] sheetName) {
        long startTime = System.currentTimeMillis();
        try {
            List<List<LinkedHashMap<String, String>>> returnDataList = new ArrayList<>();
            for (int k = 0; k <= sheetName.length - 1; k++) {
                //  得到第一个工作表对象、得到第一个工作表中的总行数。
                Sheet sheet = book.getSheetAt(k);
                int rowCount = sheet.getLastRowNum() + 1;
                Row valueRow = null;
                Cell valueCell = null;

                List<LinkedHashMap<String, String>> rowListValue = new ArrayList<>();
                LinkedHashMap<String, String> cellHashMap = null;

                //  数据获取(从第二行开始获取)。
                for (int i = 1; i < rowCount; i++) {
                    valueRow = sheet.getRow(i);
                    if (valueRow == null) {
                        continue;
                    }
                    cellHashMap = new LinkedHashMap<>();

                    //  获取列数据。
                    for (int j = 0; j < valueRow.getLastCellNum(); j++) {
                        cellHashMap.put(Integer.toString(j), getCellVal(valueRow.getCell(j)));
                    }
                    if (cellHashMap.size() > 0) {
                        rowListValue.add(cellHashMap);
                    }
                }
                returnDataList.add(rowListValue);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("======= Excel 工具类导入运行时间:  " + (endTime - startTime) + " ms");
            return returnDataList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能描述: 自定义列宽
     * 使用的方法：
     *  HashMap mapSheet = new HashMap();
     *  HashMap mapColumn = new HashMap();
     *  mapColumn.put(0,5);           //第一列，列宽为5
     *  mapColumn.put(3,5);           //第四列，列宽为5
     *  mapSheet.put(0, hashMap2);    //第一个元格列宽
     * @param sxssfSheet
     * @param map
     */
    public static void setColumnWidth(SXSSFSheet sxssfSheet, HashMap map) {
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            sxssfSheet.setColumnWidth((int) key, (int) val * 512);
        }
    }

    /**
     * 功能描述:
     * 1.excel 合并单元格
     * 用法说明：
     * 1.String[] rowColList = new String[]{0, 1, 0, 2}   代表起始行号，终止行号， 起始列号，终止列号
     *
     * @param sheet
     * @param rowColList
     */
    public static void setMergedRegion(SXSSFSheet sheet, ArrayList<String[]> rowColList) {
        if (rowColList != null && rowColList.size() > 0) {
            for (int i = 0; i < rowColList.size(); i++) {
                String[] str = (String[]) rowColList.get(i);
                if (str.length > 0 && str.length == 4) {
                    Integer firstRow = Integer.parseInt(str[0]);
                    Integer lastRow = Integer.parseInt(str[1]);
                    Integer firstCol = Integer.parseInt(str[2]);
                    Integer lastCol = Integer.parseInt(str[3]);
                    setMergedRegion(sheet, firstRow, lastRow, firstCol, lastCol);
                }
            }
        }
    }

    /**
     * 功能描述: excel 合并单元格
     *
     * @param sheet    SXSSFSheet对象。
     * @param firstRow 起始行号。
     * @param lastRow  终止行号。
     * @param firstCol 起始列号。
     * @param lastCol  终止列号。
     */
    public static void setMergedRegion(SXSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * 功能描述:下拉列表
     *
     * @param xssfWsheet SXSSFSheet对象。
     * @param list       下拉数据。
     * @param firstRow   第一行。
     * @param lastRow    最后一行。
     * @param firstCol   第一列。
     * @param lastCol    最后一列。
     */
    public static void setDataValidation(SXSSFSheet xssfWsheet, String[] list, Integer firstRow, Integer lastRow, Integer firstCol, Integer lastCol) {
        DataValidationHelper helper = xssfWsheet.getDataValidationHelper();
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        //  如果带双引号超过30个, 打开excel的时候就会提示错误 而且下拉框不生效,
        //  如果不带双引号就没有问题(测试心得)
        //  设置下拉框数据
        DataValidationConstraint constraint = helper.createExplicitListConstraint(list);
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        dataValidation.createErrorBox(DataValidationError1, DataValidationError2);
        //  处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        xssfWsheet.addValidationData(dataValidation);
    }

    /**
     * 功能描述: 设置单元格样式
     *
     * @param cell      Cell对象。
     * @param sxssfWrow SXSSFRow对象。
     * @param wb        SXSSFWorkbook对象。
     * @param fontSize  字体大小。
     * @param isheight  是否自定义行高。
     * @param height    行高大小。
     * @param bold      是否加粗。
     * @param center    是否左右上下居中。
     */
    public static void setExcelStyles(Cell cell, SXSSFRow sxssfWrow, SXSSFWorkbook wb, Integer fontSize, Boolean isheight, Integer height, Boolean bold, Boolean center) {
        CellStyle cellStyle = wb.createCellStyle();
        if (center) {
            //  左右居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            //  上下居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        if (isheight) {
            sxssfWrow.setHeight((short) ((height == null ? 1 : height) * 288));
        }
        // 设置单元格字体样式
        XSSFFont font = (XSSFFont) wb.createFont();
        font.setBold(bold);
        font.setFontName("宋体");
        font.setFontHeight(fontSize == null ? 14 : fontSize);
        // 将字体填充到表格中去
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }


    /**
     * 功能描述: 获取Excel单元格中的值并且转换java类型格式
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

}

