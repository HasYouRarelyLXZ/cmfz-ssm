package com.baizhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.entity.Log;
import com.baizhi.entity.User;
import com.baizhi.service.LogService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    @RequestMapping("/getLogJson")
    @ResponseBody
    public Map<String, Object> getLogJson(int page, int rows) {
        Map<String, Object> map = logService.queryLogAll(page, rows);
        return map;
    }

    //导出日志数据
    /*导出数据表格*/
    @ResponseBody
    @RequestMapping("export")
    public void userExport(String titles, String fileds, HttpServletResponse response) {
        //截取标签行
        String[] title = titles.split(",");
        String[] filed = fileds.split(",");
        Workbook workbooK = new HSSFWorkbook();
        Sheet sheet = workbooK.createSheet("工作表");
        DataFormat dataFormat = workbooK.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        CellStyle cellStyle = workbooK.createCellStyle();
        cellStyle.setDataFormat(format);
        Row row = sheet.createRow(0);
        //填充标题列
        for (int i = 0; i < title.length; i++) {
            String s = title[i];
            Cell cell = row.createCell(i);
            cell.setCellValue(s);
        }
        List<Log> logs = logService.queryList();
        for (int i = 0; i < logs.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            for (int j = 0; j < filed.length; j++) {
                Cell cell = dataRow.createCell(j);
                //填充属性值内容
                //1.构建方法名
                Class<? extends Log> logClass = logs.get(i).getClass();
                String methodName = "get" + filed[j].substring(0, 1).toUpperCase() + filed[j].substring(1);
                try {
                    Object invoke = logClass.getDeclaredMethod(methodName, null).invoke(logs.get(i), null);
                    if (invoke instanceof Date) {
                        //判断该属性值是不是日期
                        sheet.setColumnWidth(j, 5120);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue((Date) invoke);
                    } else {
                        cell.setCellValue(invoke.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //定义要导出表的表名
        String name = "日志数据表.xls";
        String fileName = "";
        try {
            fileName = new String(name.getBytes("UTF-8"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应头
        response.setHeader("content-disposition", "attachment;fileName=" + fileName);
        //设置响应类型
        response.setContentType("application/vnd.ms-excel");
        try {
            workbooK.write(response.getOutputStream());
            workbooK.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
