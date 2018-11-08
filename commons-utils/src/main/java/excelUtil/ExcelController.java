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
//        String[] sheetNames = new String[]{"信息表1", "信息表2", "信息表3"};
//        List<List<String[]>> employeeDictionary = iBzfEmployeeService.execleEmployeeDictionary();
//        ExcelUtils.exportForExcel(response, linkedHashMap, "信息相关表-3v", sheetNames,employeeDictionary);
//    }




}
