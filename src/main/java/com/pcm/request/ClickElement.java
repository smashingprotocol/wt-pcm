package com.pcm.request;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pcm.utility.StatusLog;
import com.pcm.verify.verifyXPath;

public class ClickElement {

	public static void byXPath(WebDriver driver, String xpath) throws Exception {
		
		try{
			Boolean testStatus = verifyXPath.isfound(driver,xpath);
			StatusLog.printlnPassedResultTrue(driver,"[VERIFY] Verify the xpath:" + xpath + "is found: " ,testStatus);
			//Failed test case if xpath is not found.
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
			driver.findElement(By.xpath(xpath)).click();
			
			System.out.println("[CLICK] xpath: " + xpath + " in " + driver.getTitle());
			
		} catch (Exception e){
			Assert.fail("[CLICK] Error on clicking element with xpath: " + xpath + ":");
		}
	}	
}
