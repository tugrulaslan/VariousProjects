package com.tugrulaslan;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.tugrulaslan.dao.DatabaseDAO;

import com.tugrulaslan.domain.Country;

public class App {
	public static void main(String[] args) {
		DatabaseDAO dao = new DatabaseDAO();
		ArrayList<Country> countryList = new ArrayList<Country>();
		countryList = dao.countryList();

		final String filePath = "C:\\";
		final String fileName = "CountryList.xls";

		Workbook workbook = null;
		workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("List of countries");
		Iterator<Country> iterator = countryList.iterator();
		Row row = null;
		Cell cell = null;

		// set headers
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("Code");
		cell = row.createCell(1);
		cell.setCellValue("Name");
		cell = row.createCell(2);
		cell.setCellValue("Continent");
		cell = row.createCell(3);
		cell.setCellValue("Region");
		cell = row.createCell(4);
		cell.setCellValue("Population");

		// filling the cells with data acquired from database
		int rowIndex = 1;
		while (iterator.hasNext()) {
			Country country = (Country) iterator.next();
			row = sheet.createRow(rowIndex++);
			cell = row.createCell(0);
			cell.setCellValue(country.getCode());
			cell = row.createCell(1);
			cell.setCellValue(country.getName());
			cell = row.createCell(2);
			cell.setCellValue(country.getContinent());
			cell = row.createCell(3);
			cell.setCellValue(country.getRegion());
			cell = row.createCell(4);
			cell.setCellValue(country.getPopulation());
		}

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filePath
					+ fileName);
			workbook.write(fileOutputStream);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
