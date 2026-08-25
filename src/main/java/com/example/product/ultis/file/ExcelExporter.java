package com.example.product.ultis.file;

import com.example.product.entity.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelExporter {

    public byte[] exportProducts(List<Product> products) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Products");

            createHeader(sheet);

            int rowIndex = 1;

            for (Product product : products) {
                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(
                        product.getId() != null ? product.getId() : 0
                );

                row.createCell(1).setCellValue(
                        product.getProductCode() != null
                                ? product.getProductCode()
                                : ""
                );

                row.createCell(2).setCellValue(
                        product.getName() != null
                                ? product.getName()
                                : ""
                );

                row.createCell(3).setCellValue(
                        product.getDescription() != null
                                ? product.getDescription()
                                : ""
                );

                row.createCell(4).setCellValue(
                        product.getPrice() != null
                                ? product.getPrice()
                                : 0
                );

                row.createCell(5).setCellValue(
                        product.getQuantity() != null
                                ? product.getQuantity()
                                : 0
                );

                row.createCell(6).setCellValue(
                        product.getStatus() != null
                                ? product.getStatus()
                                : ""
                );

                row.createCell(7).setCellValue(
                        product.getCreatedBy() != null
                                ? product.getCreatedBy()
                                : ""
                );

                row.createCell(8).setCellValue(
                        product.getModifiedBy() != null
                                ? product.getModifiedBy()
                                : ""
                );
            }

            autoSizeColumns(sheet);

            workbook.write(outputStream);

            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new FileValidationException("Failed to export products to Excel");
        }
    }

    private void createHeader(Sheet sheet) {

        Row header = sheet.createRow(0);

        String[] headers = {
                "ID",
                "Product Code",
                "Name",
                "Description",
                "Price",
                "Quantity",
                "Status",
                "Created By",
                "Modified By"
        };

        CellStyle style = sheet.getWorkbook().createCellStyle();

        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);

        style.setFont(font);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void autoSizeColumns(Sheet sheet) {

        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}