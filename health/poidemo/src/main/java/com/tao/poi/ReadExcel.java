package com.tao.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/9 17:27
 */
public class ReadExcel {

    @Test
    public void readFromExcel() throws IOException {
        //创建工作簿，构造方法文件路径
        Workbook workbook = new XSSFWorkbook("d:\\read.xlsx");
        // 获取工作表
        Sheet sht = workbook.getSheetAt(0);
        // sht.getPhysicalNumberOfRows(); // 物理行数
        // sht.getLastRowNum(); // 最后一行的下标
        // fori遍历时使用getLastrowNum()
        // 遍历工作表获得行对象
        for (Row row : sht) {
            // 遍布行对象获取单元格
            for (Cell cell : row) {
                // 单元格的类型
                int cellType = cell.getCellType();
                if(Cell.CELL_TYPE_NUMERIC == cellType){
                    // 数值类型的单元格
                    System.out.print(cell.getNumericCellValue() + ",");
                }else {
                    // 从单元格取值
                    System.out.print(cell.getStringCellValue() + ",");
                }
            }
            System.out.println();
        }
        // 关闭工作簿
        workbook.close();
    }


}
