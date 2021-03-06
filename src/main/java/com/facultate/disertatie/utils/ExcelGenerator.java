package com.facultate.disertatie.utils;

import com.facultate.disertatie.entity.DicUserLevel;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelGenerator {

    @Deprecated
    public static ByteArrayInputStream createExcel(List<? extends Object> lista, boolean hasValidator) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Customers");


        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        addBorder(headerCellStyle);
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        List<String> header = new ArrayList<>();
        try {
            Class<?> clazz = lista.get(0).getClass();
            Row headerRow = sheet.createRow(0);

            int columnsNo=hasValidator? clazz.getDeclaredFields().length-1: clazz.getDeclaredFields().length;

            for (int i = 0; i < columnsNo; i++){
                Field f = clazz.getDeclaredFields()[i];
                f.setAccessible(true);
                Cell headerCell= headerRow.createCell(i);
                String propertyName=f.getName();
                headerCell.setCellValue(propertyName);
                headerCell.setCellStyle(headerCellStyle);

                for (int j=0; j<lista.size(); j++){
                    Row contentRow;
                    if (sheet.getRow(j+1) != null){
                        contentRow = sheet.getRow(j+1);
                    }
                    else {
                        contentRow = sheet.createRow(j+1);
                    }

                    Class<?> contentLine = lista.get(j).getClass();
                    Field contentField = contentLine.getDeclaredField(propertyName);
                    contentField.setAccessible(true);
                    Cell contentCell = contentRow.createCell(i);
                    String cellValue = contentField.get(lista.get(j)) == null? "": contentField.get(lista.get(j)).toString();

                    CellStyle cellStyle = workbook.createCellStyle();

                    if (contentField.get(lista.get(j))!=null && contentField.getType().isAssignableFrom(Integer.class)){
                        contentCell.setCellValue(Integer.parseInt(cellValue));
                        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
                    }
                    else if(contentField.get(lista.get(j))!=null && contentField.getType().isAssignableFrom(Double.class)){
                        contentCell.setCellValue(Double.parseDouble(cellValue));
                        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
                    }
                    else if(contentField.get(lista.get(j))!=null && contentField.getType().isAssignableFrom(LocalDateTime.class)){
                        contentCell.setCellValue(Date.from(LocalDateTime.parse(cellValue).atZone(ZoneId.systemDefault()).toInstant()));
                        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
                    }
                    else{
                        contentCell.setCellValue(cellValue);
                    }

                    addBorder(cellStyle);


                    if (hasValidator && contentLine.getDeclaredField("validate").getBoolean(lista.get(j)) == true){
                        setAsInvalid(cellStyle);
                    }
                    contentCell.setCellStyle(cellStyle);


                }
                sheet.autoSizeColumn(i);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }



        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void addBorder (CellStyle style){
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
    }

    private static void setAsInvalid (CellStyle style){
        style.setFillForegroundColor(IndexedColors.RED1.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }
}
