package com.baizhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.entity.User;
import com.baizhi.entity.UserModel;
import com.baizhi.service.UserService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /*展示用户列表*/
    @ResponseBody
    @RequestMapping("/showAllUser")
    public Map<String, Object> showAllUser(int page, int rows) {
        int total = userService.getUserNum();
        List<User> users = userService.showUserAll(page, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("rows", users);
        return map;
    }

    /*更改用户状态*/
    @ResponseBody
    @RequestMapping("/islockUser")
    public void islockUser(String id, Boolean flag) {
        //判断是要冻结用户还是解冻
        if (flag) {
            userService.changeUserStatus(id, "冻结");
        } else {
            userService.changeUserStatus(id, "正常");
        }
    }

    /*柱形图统计*/
    @ResponseBody
    @RequestMapping("/getBySexGroup")
    public List<JSONObject> getBySexGrouptwo(String sex) {
        List<UserModel> userModels = null;
        JSONObject jsonObject = null;
        if (sex.equals("女")) {
            userModels = userService.queryBySexGroup(sex);
        } else {
            userModels = userService.queryBySexGroup(sex);
        }
        List<JSONObject> pojo = new ArrayList<JSONObject>();
        for (UserModel userModel : userModels) {
            jsonObject = new JSONObject();
            jsonObject.put("name", userModel.getProvince());
            jsonObject.put("value", userModel.getCount());
            pojo.add(jsonObject);
        }
        return pojo;
    }

    /*用户分布图表统计*/
    @ResponseBody
    @RequestMapping("/getJsonByDate")
    public JSONObject getJsonByDate() {
        int day1 = 7, day2 = 14, day3 = 25, day4 = 30;
        int count1 = userService.queryByDate(day1);
        int count2 = userService.queryByDate(day2);
        int count3 = userService.queryByDate(day3);
        int count4 = userService.queryByDate(day4);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("intervals", new String[]{"7天", "二周内", "前三周", "一个月内"});
        jsonObject.put("counts", new int[]{count1, count2, count3, count4});
        return jsonObject;
    }

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
        int userNum = userService.getUserNum();
        List<User> users = userService.showUserAll(1, userNum);
        for (int i = 0; i < users.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            for (int j = 0; j < filed.length; j++) {
                Cell cell = dataRow.createCell(j);
                //填充属性值内容
                //1.构建方法名
                Class<? extends User> userClass = users.get(i).getClass();
                String methodName = "get" + filed[j].substring(0, 1).toUpperCase() + filed[j].substring(1);
                try {
                    Object invoke = userClass.getDeclaredMethod(methodName, null).invoke(users.get(i), null);
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
        String name = "用户自定义导出的数据表.xls";
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

    /*导入表格数据*/
    @ResponseBody
    @RequestMapping("/import")
    public void importUser() throws Exception {
        List<User> list = new ArrayList<User>();
        Field[] declaredFields = User.class.getDeclaredFields();

        Workbook workbook = new HSSFWorkbook(new FileInputStream("f:/用户.xls"));  //表名
        Sheet sheet = workbook.getSheet("批量导入用户数量");   //工作区

        //只遍历不为空的行
        for (int i = 1; sheet.getRow(i) != null; i++) {
            Row row = sheet.getRow(i);
            User user = new User();
            int lastCellNum = (int) row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {   //遍历每一行
                String paramName = declaredFields[j].getName();   //获取属性名
                String setParamName = "set" + paramName.substring(0, 1).toUpperCase() + paramName.substring(1);  //拼接set方法名
                Method method = null;
                if (j == 11) {
                    Date dateCellValue = row.getCell(j).getDateCellValue();
                    method = user.getClass().getDeclaredMethod(setParamName, Date.class);
                    method.invoke(user, dateCellValue);
                } else {
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    String dateCellValue = row.getCell(j).getStringCellValue();
                    method = user.getClass().getDeclaredMethod(setParamName, String.class);
                    method.invoke(user, dateCellValue);
                }
            }
            list.add(user);
        }
        for (User user : list) {
            System.out.println(user);
        }
        userService.save(list);
    }

    /*手机号登录验证*/
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(String phone, String password) {
        Map<String, Object> map = userService.login(phone, password);
        return map;
    }

    /*手机号注册验证*/
    @ResponseBody
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public Map<String, Object> regist(String phone, String password) {
        Map<String, Object> map = userService.login(phone, password);
        return map;
    }

    /*手机号注册验证*/
    @ResponseBody
    @RequestMapping(value = "/mofify", method = RequestMethod.POST)
    public Map<String, Object> mofify(User user) {
        Map<String, Object> map = userService.mofify(user);
        return map;
    }

    /*获取金刚道友其他修行用户*/
    @ResponseBody
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public Map<String, Object> member(String uid) {
        Map<String, Object> map = userService.member(uid);
        return map;
    }


}