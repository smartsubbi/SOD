package executionEngine;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import config.ActionMethods;
import config.Constants;
import excelutility.ExcelUtility;


public class DriverScript 
{
	public static boolean aResult;
	public static ActionMethods actionKeywords;
	public static Method[] method;
	public static Properties OR;
	public static String actionKeyWord;
	public static String pageObject;
	public static String data;
	public static int getTestCaseFirstStep;
	public static int getTestCaseLastStep;
	public static String testStepNum;
	
	
	
	public DriverScript()
	{
		actionKeywords = new ActionMethods();
		method = actionKeywords.getClass().getDeclaredMethods();
		System.out.println("Total methods in Action class is : "+method.length);
	}
	
	@Test
	public static void driverMethodScript() throws Exception
	{
		
		ExcelUtility.setExcel(Constants.excelPath);		
		FileInputStream fis = new FileInputStream(Constants.orPath);
		OR = new Properties(System.getProperties());
		OR.load(fis);	
		executeTestCase();
	}
	
	private static void executeTestCase() throws IllegalArgumentException, InvocationTargetException, Exception
	{
		List<Boolean> eR = new ArrayList<Boolean>();
		eR.clear();
		int totalTestCases = ExcelUtility.getTotalUsedRow(Constants.testCaseSheetName);
		Reporter.log("Total Number of test cases is : "+totalTestCases, true);
		
		for(int iTestCase=1;iTestCase<=totalTestCases;iTestCase++)
		{
			aResult=true;
			String getTestCaseName = ExcelUtility.getCellData(Constants.testCaseSheetName, Constants.testCaseNameColNum, iTestCase);
			Reporter.log("Test Case Name is : "+getTestCaseName, true);
			
			String getRunMode = ExcelUtility.getCellData(Constants.testCaseSheetName, Constants.testCaseRunModeColNum, iTestCase);
			Reporter.log("Run Mode is : "+getRunMode,true);	
			
			if(getRunMode.equalsIgnoreCase("yes"))
			{
				
				getTestCaseFirstStep = ExcelUtility.getFirstRowContains(Constants.testStepsSheetName, getTestCaseName, Constants.testCaseNameColNum);
				Reporter.log("First Row with test Case name "+getTestCaseName+" is : "+getTestCaseFirstStep,true);
				
				getTestCaseLastStep = ExcelUtility.getTotalTestSteps(getTestCaseFirstStep, getTestCaseName, Constants.testCaseNameColNum, Constants.testStepsSheetName);
				Reporter.log("Test case last Step is : "+getTestCaseLastStep, true);
				System.out.println();
				
				for(;getTestCaseFirstStep<=getTestCaseLastStep;getTestCaseFirstStep++)
				{
					
					actionKeyWord = ExcelUtility.getCellData(Constants.testStepsSheetName, Constants.actionKeyWordColNum, getTestCaseFirstStep);
					Reporter.log("Action Key word is : "+actionKeyWord,true);
					
					pageObject = ExcelUtility.getCellData(Constants.testStepsSheetName, Constants.pageObjectColNum, getTestCaseFirstStep);
					Reporter.log("Page object is : "+pageObject, true);
					
					data = ExcelUtility.getCellData(Constants.testStepsSheetName, Constants.dataColNum, getTestCaseFirstStep);
					Reporter.log("Data is : "+data, true);
					
					testStepNum = ExcelUtility.getCellData(Constants.testStepsSheetName, Constants.testStepsColNum, getTestCaseFirstStep);
					Reporter.log("Test Step number is : "+testStepNum, true);
					
					executeActions();
					if(aResult==false)
					{			
						
						eR.add(aResult);					   
						ExcelUtility.setCellData(Constants.testCaseSheetName, Constants.testCaseResultColNum, iTestCase, Constants.FAIL);						
						break;
					}					
				}
				if(aResult==true)
				{
					ExcelUtility.setCellData(Constants.testCaseSheetName, Constants.testCaseResultColNum, iTestCase, Constants.PASS);					
				}
			}
		}
		System.out.println("Total number of errors are : "+eR.size());
		Assert.assertEquals(eR.size(), 0);
	}

	private static void executeActions() throws Exception, IllegalArgumentException, InvocationTargetException
	{
		for(int i=0;i<method.length;i++)
		{
			if(method[i].getName().equalsIgnoreCase(actionKeyWord))
			{
				method[i].invoke(actionKeyWord, pageObject, data);
				ActionMethods.pause();
				System.out.println(getTestCaseFirstStep);
				System.out.println(getTestCaseLastStep);
				if(aResult==true)
				{
					
					ExcelUtility.setCellData(Constants.testStepsSheetName, Constants.testStepResultColNum, getTestCaseFirstStep, Constants.PASS);					
					break;
				}
				else
				{					
					ExcelUtility.setCellData(Constants.testStepsSheetName, Constants.testStepResultColNum, getTestCaseFirstStep, Constants.FAIL);
					if(getTestCaseFirstStep!=getTestCaseLastStep)
					{
						getTestCaseFirstStep=getTestCaseFirstStep+1;
						for(;getTestCaseFirstStep<=getTestCaseLastStep;getTestCaseFirstStep++)
						{
							ExcelUtility.setCellData(Constants.testStepsSheetName, Constants.testStepResultColNum, getTestCaseFirstStep, Constants.FAIL);	
						}						
					}
					ActionMethods.closeBrowser(pageObject,data);
					break;
				}
			}
		}	
	}		
}