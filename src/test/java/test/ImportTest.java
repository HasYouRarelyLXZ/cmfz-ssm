package test;

import com.baizhi.entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportTest {
    /*导入表格数据*/
    @Test
    public void importUserTest() throws Exception {
        List<User> list = new ArrayList<User>();
        Field[] declaredFields = User.class.getDeclaredFields();//反射获取属性名
        Workbook workbook = new HSSFWorkbook(new FileInputStream("f:/新建 XLS 工作表.xls"));
        Sheet sheet = workbook.getSheet("sheet1");//工作区
        int lastRowNum = sheet.getLastRowNum();  //获取有效数据行
        System.out.println(lastRowNum);
        //只遍历不为空的行
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            User user = new User();
            int lastCellNum = (int) row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                String paramName = declaredFields[j].getName();
                String setParamName = "set" + paramName.substring(0, 1).toUpperCase() + paramName.substring(1);
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
    }

}
