package com.exmple.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @version 1.0
 * @Date 2022/9/22 9:18 下午
 * @Description 使用POI读取Excel文件中的数据或向Excel文件中写入数据
 * @Author Sxy
 */

public class POITest {
    @Test
    public void test1() throws IOException {
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("/Users/Sxy/Desktop/工作簿1.xlsx")));
        //读取excel文件中的第一个sheet标签页
        XSSFSheet sheet = excel.getSheetAt(0);
        //遍历sheet标签页，获取每一行数据
        for (Row row : sheet) {
            //遍历行，获得每个单元格对象
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }
        //关闭资源
        excel.close();
    }

    @Test
    public void test2() throws IOException {
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("/Users/Sxy/Desktop/工作簿1.xlsx")));
        XSSFSheet sheet = excel.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                System.out.println(row.getCell(j).getStringCellValue());
            }
        }
        excel.close();
    }

    @Test
    public void test3() throws IOException {
        //在内存中创建一个Excel文件(工作薄)
        XSSFWorkbook excel = new XSSFWorkbook();
        //创建一个工作对象
        XSSFSheet sheet = excel.createSheet("POI学习");
        //在工作表中创建行对象
        XSSFRow title = sheet.createRow(0);
        //在行中创建单元/Users/Sxy/Desktop格对象
        title.createCell(0).setCellValue("姓名");
        title.createCell(1).setCellValue("性别");
        title.createCell(2).setCellValue("地址");
        XSSFRow data = sheet.createRow(1);
        data.createCell(0).setCellValue("张三");
        data.createCell(1).setCellValue("男");
        data.createCell(2).setCellValue("武汉");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/Sxy/Desktop/study.xlsx"));
        excel.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        excel.close();
    }
}
