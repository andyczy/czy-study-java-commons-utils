package excelUtils;


import com.github.andyczy.java.excel.LocalExcelUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Chen zheng you
 * CreateTime: 2019-02-19 14:44
 * Description:
 */
public class ExcelUtilsTest {


    public static void main(String[] args) {

        List<List<String[]>> dataList = new ArrayList<>();
        List<String[]> stringList = new ArrayList<>();
        String[] valueString = null;
        String[] headers = {"No", "编码", "时间", "小数点", "是否"};
        stringList.add(headers);
        for (int i = 0; i < 1000; i++) {
            valueString = new String[]{(i + 1) + "", Math.random() * 10 + "", getNeededDateStyle(null, null),
                    1 + Math.random() * 10 + "", i % 2 == 0 ? "是" : "否"};
            stringList.add(valueString);
        }
        dataList.add(stringList);

        HashMap<Integer, HashMap<Integer, Integer>> mapColumnWidth = new HashMap<>();
        HashMap<Integer, Integer> mapColumn = new HashMap<>();
        //自定义列宽
        mapColumn.put(0, 3);
        mapColumn.put(1, 20);
        mapColumn.put(2, 15);
        mapColumn.put(3, 15);
        mapColumn.put(4, 15);
        mapColumnWidth.put(1, mapColumn);

        HashMap stylesRow = new HashMap();
        List listRow = new ArrayList();
        //第几行样式
        listRow.add(new Boolean[]{true, false, false, false, true});
        listRow.add(new Integer[]{0});
        listRow.add(new Integer[]{10,14});
        stylesRow.put(1, listRow);

        LocalExcelUtils noResponseExcelUtils = LocalExcelUtils.initialization();
        noResponseExcelUtils.setDataLists(dataList);
        noResponseExcelUtils.setRowStyles(stylesRow);
        noResponseExcelUtils.setMapColumnWidth(mapColumnWidth);
        noResponseExcelUtils.setSheetName(new String[]{"Andyczy Excel中文" + Math.random()});
        noResponseExcelUtils.setFilePath("Andyczy Excel中文" + Math.random()+".xlsx");
//        noResponseExcelUtils.localNoStyleNoResponse();
        noResponseExcelUtils.localNoResponse();
    }


    public static String getNeededDateStyle(Date date, String style) {
        if (date == null) {
            date = new Date();
        }
        if (style == null) {
            style = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(style);
        return sdf.format(date);
    }

}
