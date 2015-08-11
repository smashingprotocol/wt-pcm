package com.pcm.utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class StatusLog {

	public static boolean tcStatus = true;
	public static String errMsg = "";
	
	
	public static void printlnPassedResult(WebDriver driver,String string) throws IOException {
		
		String now = new SimpleDateFormat("mmddhhmmss").format(new Date());
		TakeScreenShot.CurrentPage(driver,string.replaceAll("[^\\w]", "") + now + "PASSED");
		System.out.println(string + " :PASSED");
		
	}

	public static void printlnFailedResult(WebDriver driver, String string) throws IOException {
		
		String now = new SimpleDateFormat("mmddhhmmss").format(new Date());
		TakeScreenShot.CurrentPage(driver,string.replaceAll("[^\\w]", "") + now + "FAILED");
		String failedMsg = string + " :FAILED";
		System.out.println(failedMsg);
		errMsg = errMsg + "\n" + failedMsg;
		tcStatus = false;
	}

	public static void printlnPassedResultTrue(WebDriver driver, String message,
			boolean testStatus) {
		
		try{
			
			Assert.assertTrue(message, testStatus);
			System.out.println(message + " :PASSED");
			
		} catch(Throwable e){
			try {
				StatusLog.printlnFailedResult(driver, message);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		
		
		
	}

	public static void printlnPassedResultFalse(WebDriver driver,
			String message, boolean testStatus) {
		
try{
			
			Assert.assertFalse(message, testStatus);
			System.out.println(message + " :PASSED");
			
		} catch(Throwable e){
			try {
				StatusLog.printlnFailedResult(driver, message);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		
		
	}

}
