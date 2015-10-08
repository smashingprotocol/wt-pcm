package com.pcm.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class TableContainer {

	public static String tc;
	public static int rowNum;
	public static String cellValue;
	static Properties sys = System.getProperties(); 
	
	public static HSSFSheet getExcelSheet(String sheet) throws FileNotFoundException {
		File excel = new File(sys.getProperty("tableContainer"));
		InputStream myxls = new FileInputStream(excel);
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook(myxls);
			HSSFSheet ws = wb.getSheet(sheet);
			
			return ws;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
	}

	public static String getCellValue(int x, String colName) throws FileNotFoundException {
		
		File excel = new File(sys.getProperty("tableContainer"));
		InputStream myxls = new FileInputStream(excel);
		
		try {
			
			HSSFWorkbook wb = new HSSFWorkbook(myxls);  //Declare the workbook
			HSSFSheet ws = wb.getSheetAt(0); //Declare the worksheet as the first sheet
			HSSFRow rowHeader = ws.getRow(0); //Fixed row for the header.
			int colNum = ws.getRow(0).getLastCellNum(); // Get column count.
			HSSFRow row = ws.getRow(x); //Row specified in the method
			
			for(int i = 0; i <= colNum; i++){
				HSSFCell cell = rowHeader.getCell(i); // Fixed row in getting the cell header.
				String cellHeader = cellToString(cell); //Get the header name
				
				//Look for the column that matches the specified header
				if(cellHeader.equals(colName)){
					
					//if match, get that cell value of that column
					HSSFCell cellData = row.getCell(i); 
					cellData = row.getCell(i);
					cellValue = cellToString(cellData);
					System.out.println("[TABLE CONTAINER] Getting the value of column " + cellHeader + ": " + cellValue);
					return cellValue;
					
				}
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private static String cellToString(HSSFCell cell) {
		
		Object result = null;
		cell.setCellType(Cell.CELL_TYPE_STRING); //convert all data to string
		result = cell.getStringCellValue();
		
		return result.toString();
	}

	public static int getRowCount() throws FileNotFoundException {
		
		File excel = new File(sys.getProperty("tableContainer"));
		InputStream myxls = new FileInputStream(excel);
		
		try {
					
			HSSFWorkbook wb = new HSSFWorkbook(myxls);
			HSSFSheet ws = wb.getSheetAt(0);
			
			rowNum = ws.getLastRowNum();
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return rowNum;
				
		
	}
	
public static void insertIntoCellValue(String fileDir, int rowIndex, String colName, String cellvalue) {
		
		//row is automatically inserted after the last row.

		try{
			
			File excel = new File(fileDir);
			InputStream myxls = new FileInputStream(excel);
			
			HSSFWorkbook wbook = new HSSFWorkbook(myxls);
			HSSFSheet wsheet = wbook.getSheetAt(0);
			
			HSSFRow rowHeader = wsheet.getRow(0); //Fixed row for the header.
			int colNum = wsheet.getRow(0).getLastCellNum(); // Get column count.
			
			HSSFRow row = wsheet.getRow(rowIndex);
			
			//check if row is existing, else create a row
			if (row == null)
		    {
				row = wsheet.createRow(rowIndex);
		    }

			for(int i = 0; i <= colNum; i++){
				HSSFCell cell = rowHeader.getCell(i); // Fixed row in getting the cell header.
				String cellHeader = cellToString(cell); //Get the header name
				
				//Look for the column that matches the specified header
				if(cellHeader.equals(colName)){
					
					//if match, get that cell value of that column
					
					Cell cellData = row.createCell(i); 
					cellData.setCellValue(cellvalue);
					System.out.println("[TABLE CONTAINER] Cell value: " + cellvalue + " is added in " + fileDir);
					
					FileOutputStream out = new FileOutputStream(excel);
			        wbook.write(out);
			        out.close();
					
				} //end if
				
			} //end for
			
			wbook.close();
			
			
		} catch(Exception e) {
			
		} //end try
		
	} //end void

	public static int getRowCountinExcelFile(String fileDir) throws FileNotFoundException {
		
		File excel = new File(fileDir);
		InputStream myxls = new FileInputStream(excel);
		
		try {
					
			HSSFWorkbook wb = new HSSFWorkbook(myxls);
			HSSFSheet ws = wb.getSheetAt(0);
			
			rowNum = ws.getLastRowNum();
			
			wb.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return rowNum;

	} //end static getRowCountinExcelFile

}
