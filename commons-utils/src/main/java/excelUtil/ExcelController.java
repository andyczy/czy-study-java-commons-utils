package excelUtil;

/**
 * @author Chenzhengyou
 * @date 2018-06-28
 * @description: 主要是web开发，方便下次使用,伪代码
 */
public class ExcelController {

//    /**
//     * Excel数据导入到表
//     *
//     * @param book
//     * @param inputStream
//     * @return
//     */
//    @RequestMapping(value = "/importExcelToEmploee", method = RequestMethod.POST)
//    @ResponseBody
//    public Result importExcelToEmploee(@RequestParam("proFile") MultipartFile proFile) {
//        //导入的Excel单元表格名称顺序
//        String[] sheetNames = new String[]{"职工信息", "家庭成员信息", "职工住房信息"};
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        Workbook book = null;
//        try {
//            if (!proFile.isEmpty()) {
//                InputStream inputStream = proFile.getInputStream();
//                long fileSize = proFile.getSize();
//                String fileName = proFile.getOriginalFilename();
//                String extName = fileName.substring(fileName.lastIndexOf('.') + 1);
//                if (fileSize > 10 * 1024 * 1024) {
//                    return new Result(-1001, "模板文件大于 10M 了!");
//                }
//                if (StringUtils.equals("xls", extName)) {
//                    book = new HSSFWorkbook(inputStream);
//                } else if (StringUtils.equals("xlsx", extName)) {
//                    book = new XSSFWorkbook(inputStream);
//                } else {
//                    return new Result(-1001, "无法识别的Excel文件,请下载模板文件,进行导入!");
//                }
//                return iBzfEmployeeService.importExcelToEmploee(book, user.getId(), sheetNames);
//            } else {
//                return new Result(-1001, "模板文件为空!");
//            }
//        } catch (Exception e) {
//            return new Result(-1001, "数据异常、请下载模本重新导入!");
//        }
//    }


//    /**
//     * Excel表模板下载
//     *
//     * @param response
//     */
//    @RequestMapping(value = "/downloadExcelModel")
//    public void downloadExcelModel(  HttpServletResponse response) {
//        List<String[]> arrayList = new ArrayList<>();
//        String[] employeeMemberList = new String[]{"姓名(必填)", "身份证号码(必填：15/18位)", "家庭成员姓名(必填)"};
//
//        String[] houseInfoList = new String[]{"姓名(必填)", "身份证号码(必填：15/18位)"};
//        String[] employeeList = new String[]{"姓名(必填)"};
//        arrayList.add(employeeList);
//        arrayList.add(employeeMemberList);
//        arrayList.add(houseInfoList);
//
//        String[] sheetNames = new String[]{"信息", "家庭成员信息", "住房信息"};
//        List<List<String[]>> employeeDictionary = iBzfEmployeeService.execleEmployeeDictionary();
//        ExcelUtils.exportForExcelModel(response, arrayList, "信息数据导入模板(2.2版本)", sheetNames, employeeDictionary, null);
//    }


//    /**
//     * Excel信息导出
//     * ---多单元表格
//     * @param response
//     * @throws Exception
//     */
//    @RequestMapping("/exportEmployeeForExcel")
//    @ResponseBody
//    public void exportEmployeeExcel(HttpServletResponse response) throws Exception {
//
//        List<List<LinkedHashMap<String, String>>> linkedHashMap ;
//        String[] sheetNames = new String[]{"信息", "家庭成员信息", "住房信息"};
//        List<List<String[]>> employeeDictionary = iBzfEmployeeService.execleEmployeeDictionary();
//        ExcelUtils.exportForExcel(response, linkedHashMap, "信息相关表-3v", sheetNames,employeeDictionary);
//    }


//    /**
//     * execle导出导入所需要下拉列表的值
//     *
//     * @return
//     */
//    @Override
//    public List<List<String[]>> execleEmployeeDictionary() {
//        List<String[]> sheet1 = new ArrayList<>();
//        List<String[]> sheet2 = new ArrayList<>();
//        List<String[]> sheet3 = new ArrayList<>();
//
//        String[] sex = {"男,女"};
//        //是否享受过政策性住房
//        String[] policyHousing = {"是,否,未核查"};
//        String[] is = {"是,否"};
//
//
//        //有下拉列表的列：放在第一(excel从零行开始数)
//        String[] sheetColumn1 = new String[]{"1","2","4", "5", "8", "11", "12", "20", "21", "22", "23", "24"};
//        String[] sheetColumn2 = new String[]{"4"};
//        String[] sheetColumn3 = new String[]{"6"};
//
//        //放在第一
//        sheet1.add(sheetColumn1);
//        sheet1.add(sex);
//
//        //放在第一
//        sheet2.add(sheetColumn2);
//        sheet2.add(sex);
//
//        //放在第一
//        sheet3.add(sheetColumn3);
//        sheet3.add(policyHousing);
//
//        dropDownListData.add(sheet1);
//        dropDownListData.add(sheet2);
//        dropDownListData.add(sheet3);
//        long endTime = System.currentTimeMillis();
//        System.out.println("------------------------------- 字典表运行时间:  " + (endTime - startTime) + "ms -------------------------------");
//        return dropDownListData;
//    }

}
