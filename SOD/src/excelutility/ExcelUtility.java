package excelutility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility 
{
	public static XSSFWorkbook excelWorkBook;
	public static XSSFSheet excelSheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static XSSFRow newRow;
	public static XSSFCell newCell;
	
	
	public static String excelFilePath = "D://Selenium Excels//TestData.xlsx";
	public static String sheetName = "Sheet1";
	
	public static int rowNum;
	public static int colNum;
	
	public static void setExcel(String path) throws Exception
	{		
		FileInputStream fis = new FileInputStream(path);		
		excelWorkBook = new XSSFWorkbook(fis);				
	}
	
	public static int getTotalUsedRow(String sheetName)
	{
		excelSheet = excelWorkBook.getSheet(sheetName);
		int totalRowUsed = excelSheet.getLastRowNum();
		return totalRowUsed;
	}
	
	public static String getCellData(String sheetName, int colNum, int rowNum)
	{
		final DataFormatter df = new DataFormatter();
		excelSheet = excelWorkBook.getSheet(sheetName);
		cell = excelSheet.getRow(rowNum).getCell(colNum, XSSFRow.RETURN_BLANK_AS_NULL);		
		if(cell==null)
		{
			return "Empty Cell";
		}
		else
		{
			String cellData = df.formatCellValue(cell);
			
			return cellData;		
		}		
	}
	
	public static int getFirstRowContains(String SheetName, String searchData, int colNum)
	{
		int i = 0;
		int usedRowCount = getTotalUsedRow(SheetName);
		for(;i<usedRowCount;i++)
		{				
			if(getCellData(SheetName,colNum,i).equalsIgnoreCase(searchData))
			{
				break;
			}			
		}
		return i;
	}
	
	public static int getTotalTestSteps(int firstStep,String data,int colNum, String sheetName)
	{
		int usedRowCount = getTotalUsedRow(sheetName);
		for(;firstStep<usedRowCount;firstStep++)
		{			
			if(!data.equals(getCellData(sheetName,colNum,firstStep)))
			{
				break;
			}
		}
		return firstStep;
	}
	
	public static void setCellData(String sheetName, int colNum, int rowNum, String result) throws Exception
	{
		excelSheet = excelWorkBook.getSheet(sheetName);				
		cell = excelSheet.getRow(rowNum).getCell(colNum, XSSFRow.RETURN_BLANK_AS_NULL);
		if(cell==null)
		{
			cell = excelSheet.getRow(rowNum).getCell(colNum);
			cell.setCellValue(result);
		}
		else
		{
			cell.setCellValue(result);			
		}
		
		FileOutputStream fos = new FileOutputStream(excelFilePath);		
		excelWorkBook.write(fos);
		fos.close();		
		//excelWorkBook = new XSSFWorkbook(new FileInputStream(excelFilePath));		
	}
	
	public static void setCellDataNew(String sheetName, int colNum, String result) throws Exception
	{
		excelSheet = excelWorkBook.getSheet(sheetName);
		int rowCount = excelSheet.getLastRowNum()-excelSheet.getFirstRowNum();
		System.out.println(rowCount);
		row = excelSheet.getRow(rowCount+1);
		try
		{
			cell = row.getCell(colNum);	
		}
		catch(NullPointerException npe)
		{
			newRow = excelSheet.createRow(rowCount+1);
			newCell= newRow.createCell(colNum);
			newCell.setCellValue(result);
			FileOutputStream fos = new FileOutputStream(excelFilePath);		
            excelWorkBook.write(fos);
			fos.flush();
			fos.close();		
			excelWorkBook = new XSSFWorkbook(new FileInputStream(excelFilePath));	
		}				
	}	
}