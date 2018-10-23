package excelUtil;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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
public class ExcelUtils {


    /**
     * 日期格式yyyy-mm-dd
     * 数字格式，防止长数字成为科学计数法形式，或者int变为double形式
     */
    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    private static final DecimalFormat df = new DecimalFormat("#.###");
    private static final String DataValidationError1 = "本系统——提醒您！";
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
     * 4.自定义下拉列表：对每个单元格自定义下拉列表。
     * 5.数据遍历方式换成数组(效率较高)。
     * 6.可提供模板下载。
     * 7.每个表格的大标题[2018-09-14]
     * 8.自定义列宽：对每个单元格自定义列宽[2018-09-18]
     * 9.自定义样式：对每个单元格自定义样式[2018-10-22]
     * 10.自定义单元格合并：对每个单元格合并[2018-10-22]
     * 11.固定表头[2018-10-23]
     * <p>
     * 版  本:
     * 1.apache poi 3.17
     * 2.apache poi-ooxml  3.17
     *
     * @param response
     * @param exportDataList   导出的数据(不可为空：如果只有标题就导出模板)
     * @param columnMap        自定义：自定义列宽：对每个单元格自定义列宽（可为空）
     * @param dropDownListData 自定义：自定义下拉列表：对每个单元格自定义下拉列表（可为空：为空就不显示下拉列表数据）
     * @param setStylesMap     自定义：单元格自定义样式（可为空）
     * @param mergedRegionMap  自定义：自定义单元格合并（可为空）
     * @param setPaneMap       固定表头（可为空）
     * @param labelNameList    每个表格的大标题（可为空）
     * @param fileName         文件名称(可为空，为空就文件名称默认是：excel数据信息表)
     * @param sheetName        sheet名称（不可为空）
     * @return
     */
    public static Boolean exportForExcel(HttpServletResponse response, List<List<String[]>> exportDataList, List<List<String[]>> dropDownListData,
                                         HashMap mergedRegionMap, HashMap columnMap, HashMap setStylesMap, HashMap setPaneMap, String fileName,
                                         String[] sheetName, String[] labelNameList) {
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
                // 默认列宽
                sxssfSheet.setDefaultColumnWidth((short) 10);
                sxssfWorkbook.setSheetName(k, sheetName[k]);

                int jRow = 0;
                //  大标题和样式。参数说明：new String[]{"表格数据一", "表格数据二", "表格数据三"}
                if (labelNameList != null && labelNameList.length - 1 >= k) {
                    sxssfRow = sxssfSheet.createRow(jRow);
                    Cell cell = createCell(sxssfRow, jRow, labelNameList[k]);
                    setMergedRegion(sxssfSheet, 0, 0, 0, dataList.get(0).length - 1);
                    setExcelStyles(cell, sxssfRow, sxssfWorkbook, 18, true, true, false);
                    jRow = 1;
                }
                //  每个单元格自定义合并单元格：对每个单元格自定义合并单元格（看该方法说明）-已验证可用。
                if (mergedRegionMap != null && mergedRegionMap.size() > 0) {
                    setMergedRegion(sxssfSheet, (ArrayList<Integer[]>) mergedRegionMap.get(k + 1));
                }
                //  每个单元格自定义列宽：对每个单元格自定义列宽（看该方法说明）。
                if (columnMap != null && columnMap.size() > 0 && columnMap.size() - 1 >= k) {
                    setColumnWidth(sxssfSheet, (HashMap) columnMap.get(k));
                }
                //  每个单元格自定义下拉列表：对每个单元格自定义下拉列表（看该方法说明）。
                if (dropDownListData != null && dropDownListData.size() > 0 && dropDownListData.size() - 1 >= k) {
                    setDataValidation(sxssfSheet, dropDownListData.get(k), dataList);
                }
                //  每个单元格固定表头（看该方法说明）。
                if (setPaneMap != null && setPaneMap.size() > 0 && setPaneMap.size() - 1 >= k) {
                    createFreezePane(sxssfSheet, (Integer) setPaneMap.get(k + 1));
                }

                //  写入标题与数据。
                for (String[] listValue : dataList) {
                    int columnIndex = 0;
                    sxssfRow = sxssfSheet.createRow(jRow);
                    //  写入标题与数据。
                    for (int j = 0; j < listValue.length; j++) {
                        Cell cellStyles = sxssfRow.createCell(j);
                        Cell cell = createCell(sxssfRow, columnIndex, listValue[j]);
                        columnIndex++;
                        //  所有单元个样式。
                        setExcelStyles(cell, sxssfRow, sxssfWorkbook, null, false, true, true);

                        //  每个单元格自定义单元格样式（看该方法说明）。
                        if (setStylesMap != null && setStylesMap.size() > 0) {
                            HashMap stylesMap = (HashMap) setStylesMap.get(k + 1);
                            if (stylesMap != null && stylesMap.size() > 0) {
                                HashMap setMap = (HashMap) setStylesMap.get(2);
                                setExcelStyles(cellStyles, sxssfRow, sxssfWorkbook, (Integer[]) setMap.get(1), (String[]) setMap.get(2), j, jRow);
                            }
                        }
                    }
                    jRow++;
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
     * 3.多单元从第几行开始获取数据[2018-09-20]
     * 4.多单元根据那些列为空来忽略行数据[2018-10-22]
     * <p>
     * 版  本:
     * 1.apache poi 3.17
     * 2.apache poi-ooxml  3.17
     *
     * @param book           Workbook对象（不可为空）
     * @param sheetName      多单元数据获取（不可为空）
     * @param hashMapIndex   多单元从第几行开始获取数据，默认从第二行开始获取（可为空，如 hashMapIndex.put(1,3); 第一个表格从第三行开始获取）
     * @param mapContinueRow 多单元根据那些列为空来忽略行数据（可为空，如 mapContinueRow.put(1,new Integer[]{1, 3}); 第一个表格从1、3列为空就忽略）
     * @return
     */
    @SuppressWarnings({"deprecation", "rawtypes"})
    public static List<List<LinkedHashMap<String, String>>> importForExcelData(Workbook book, String[] sheetName, HashMap hashMapIndex, HashMap mapContinueRow) {
        long startTime = System.currentTimeMillis();
        try {
            List<List<LinkedHashMap<String, String>>> returnDataList = new ArrayList<>();
            for (int k = 0; k <= sheetName.length - 1; k++) {
                //  得到第K个工作表对象、得到第K个工作表中的总行数。
                Sheet sheet = book.getSheetAt(k);
                int rowCount = sheet.getLastRowNum() + 1;
                Row valueRow = null;

                List<LinkedHashMap<String, String>> rowListValue = new ArrayList<>();
                LinkedHashMap<String, String> cellHashMap = null;

                int irow = 1;
                //  第K个工作表:从开始获取数据、默认第二行开始获取。
                if (hashMapIndex != null && StringUtils.isNotBlank(hashMapIndex.get(k + 1).toString())) {
                    irow = Integer.valueOf(hashMapIndex.get(k + 1).toString()) - 1;
                }
                //  数据获取。
                for (int i = irow <= 0 ? 1 : irow; i < rowCount; i++) {
                    valueRow = sheet.getRow(i);
                    if (valueRow == null) {
                        continue;
                    }

                    //  第K个工作表:从开始列忽略数据、为空就跳过。
                    if (mapContinueRow != null && StringUtils.isNotBlank(mapContinueRow.get(k + 1).toString())) {
                        int continueRowCount = 0;
                        Integer[] continueRow = (Integer[]) mapContinueRow.get(k + 1);
                        for (int w = 0; w <= continueRow.length - 1; w++) {
                            Cell valueRowCell = valueRow.getCell(continueRow[w] - 1);
                            if (valueRowCell == null || StringUtils.isBlank(valueRowCell.toString())) {
                                continueRowCount = continueRowCount + 1;
                            }
                        }
                        if (continueRowCount == continueRow.length) {
                            continue;
                        }
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
            System.out.println("======= Excel 工具类导入异常");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 锁定行（固定表头）
     * HashMap setPaneMap = new HashMap();
     * setPaneMap.put(1,3);  //第一个表格、第三行开始固定表头
     *
     * @param sxssfSheet
     * @param row
     */
    public static void createFreezePane(SXSSFSheet sxssfSheet, Integer row) {
        sxssfSheet.createFreezePane(0, row, 0, 1);
    }

    /**
     * 功能描述: 自定义列宽
     * 使用的方法：
     * HashMap<Integer,HashMap<Integer,Integer>>  mapSheet = new HashMap();
     * HashMap<Integer,Integer> mapColumn = new HashMap();
     * mapColumn.put(0,5);           //第一列，列宽为5
     * mapColumn.put(3,5);           //第四列，列宽为5
     * mapSheet.put(0, mapColumn);    //第一个元格列宽
     *
     * @param sxssfSheet
     * @param map
     */
    public static void setColumnWidth(SXSSFSheet sxssfSheet, HashMap map) {
        if (map != null) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                sxssfSheet.setColumnWidth((int) key, (int) val * 512);
            }
        }
    }


    /**
     * 功能描述:
     * 1.excel 合并单元格
     * 参数说明：
     * List<List<Integer[]>> setMergedRegion = new ArrayList<>();
     * List<Integer[]> sheet1 = new ArrayList<>();                  //第一个表格设置。
     * Integer[] sheetColumn1 = new Integer[][]{0, 1, 0, 2}         //代表起始行号，终止行号， 起始列号，终止列号进行合并。
     * setMergedRegion.add(sheet1);
     *
     * @param sheet
     * @param rowColList
     */
    public static void setMergedRegion(SXSSFSheet sheet, ArrayList<Integer[]> rowColList) {
        if (rowColList != null && rowColList.size() > 0) {
            for (int i = 0; i < rowColList.size(); i++) {
                Integer[] str = rowColList.get(i);
                if (str.length > 0 && str.length == 4) {
                    Integer firstRow = str[0];
                    Integer lastRow = str[1];
                    Integer firstCol = str[2];
                    Integer lastCol = str[3];
                    setMergedRegion(sheet, firstRow, lastRow, firstCol, lastCol);
                }
            }
        }
    }

    /**
     * 合并单元格
     *
     * @param sheet
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public static void setMergedRegion(SXSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }


    /**
     * 功能描述:下拉列表
     *
     * @param xssfWsheet
     * @param dropDownListData
     * @param dataList         参数说明：
     *                         List<List<String[]>> dropDownListData = new ArrayList<>();
     *                         List<String[]> sheet1 = new ArrayList<>();                  //第一个表格设置。
     *                         String[] sheetColumn1 = new String[]{"1", "2", "4"};        //设置下拉列表的列。
     *                         dropDownListData.add(sheet1);
     */
    public static void setDataValidation(SXSSFSheet xssfWsheet, List<String[]> dropDownListData, List<String[]> dataList) {
        if (dropDownListData.size() > 0) {
            for (int col = 0; col < dropDownListData.get(0).length; col++) {
                Integer colv = Integer.parseInt(dropDownListData.get(0)[col]);
                setDataValidation(xssfWsheet, dropDownListData.get(col + 1), 1, dataList.size() < 100 ? 500 : dataList.size(), colv, colv);
            }
        }
    }

    /**
     * 功能描述:下拉列表
     *
     * @param xssfWsheet
     * @param list
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
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
     * 设置单元格样式
     *
     * @param cell      Cell对象。
     * @param sxssfWrow SXSSFRow对象。
     * @param wb        SXSSFWorkbook对象。
     * @param fontSize  字体大小。
     * @param bold      是否加粗。
     * @param center    是否左右上下居中。
     */
    public static void setExcelStyles(Cell cell, SXSSFRow sxssfWrow, SXSSFWorkbook wb, Integer fontSize, Boolean bold, Boolean center, Boolean isSetBorder) {
        CellStyle cellStyle = wb.createCellStyle();
        if (center) {
            //  左右居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            //  上下居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        //边框
        setBorder(cellStyle, isSetBorder);
        // 设置单元格字体样式
        XSSFFont font = (XSSFFont) wb.createFont();
        font.setBold(bold);
        font.setFontName("宋体");
        font.setFontHeight(fontSize == null ? 12 : fontSize);
        // 将字体填充到表格中去
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }


    /**
     * @param cell
     * @param sxssfWrow
     * @param wb
     * @param stylesList 是否居中？，是否右对齐？，是否左对齐？， 是否加粗？，字体大小是多少？
     *                   HashMap setStylesMap = new HashMap();           //第几个表格要设置样式（如）
     *                   Integer[] rowCellList = new Integer[]{2, 2};    //要设置样式的行与列（如第二行第二列）
     *                   String[] styles = new String[]{"false", "true", "false", "true", "14"};  //设置的样式风格（参考 stylesList 说明）
     *                   HashMap map = new HashMap();                    //存放表格设置样式的参数
     *                   map.put(1, rowCellList);
     *                   map.put(2, styles);
     *                   setStylesMap.put(2, map);                       //第几个表格要设置样式（如第二个）
     */
    public static void setExcelStyles(Cell cell, SXSSFRow sxssfWrow, SXSSFWorkbook wb, Integer[] cellRowList, String[] stylesList, int cellIndex, int rowIndex) {
        if (cellIndex == (cellRowList[1] - 1) && rowIndex == (cellRowList[0] - 1)) {
            CellStyle cellStyle = wb.createCellStyle();

            Boolean centerBoolean = Boolean.valueOf(stylesList[0]);
            //左右居中、上下居中
            if (centerBoolean) {
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            }
            //右对齐
            Boolean rightBoolean = Boolean.valueOf(stylesList[1]);
            if (rightBoolean) {
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
            }
            //左对齐
            Boolean leftBoolean = Boolean.valueOf(stylesList[2]);
            if (leftBoolean) {
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
            }
            //边框
            Boolean isSetBorder = Boolean.valueOf(stylesList[5]);
            setBorder(cellStyle, isSetBorder);
            // 设置单元格字体样式
            XSSFFont font = (XSSFFont) wb.createFont();
            Boolean boldBoolean = Boolean.valueOf(stylesList[3]);
            font.setBold(boldBoolean);
            font.setFontName("宋体");
            font.setFontHeight(Integer.valueOf(stylesList[4]) == null ? 12 : Integer.valueOf(stylesList[4]));
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
        }
    }


    /**
     * 设置边框
     *
     * @param cellStyle
     * @param isSetBorder
     */
    private static void setBorder(CellStyle cellStyle, Boolean isSetBorder) {
        if (isSetBorder) {
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        }
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

