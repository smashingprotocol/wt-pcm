package com.pcm.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {

	public static void waitforXPath(WebDriver driver, String xpath,String duration) {
		
		try{
		Long lduration = Long.parseLong(duration);
		
		WebDriverWait wait = new WebDriverWait(driver, lduration);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		
		System.out.println("[WAIT] Waiting for xpath: " + xpath + " ...");
		
		} catch (Throwable e){
			System.out.println("[WAIT] Waiting for xpath: " + xpath + " to be visible failed.");
		}
		
	}

	public static void waitforTitle(WebDriver driver, String pageTitle,
			String duration) {
		
		Long lduration = Long.parseLong(duration);
		
		WebDriverWait wait = new WebDriverWait(driver, lduration);
		wait.until(ExpectedConditions.titleContains(pageTitle));
		
		
	}

	public static void waitforXPathToBeClickable(WebDriver driver,
			String xpath, String duration) {
		
		Long lduration = Long.parseLong(duration);
		
		WebDriverWait wait = new WebDriverWait(driver, lduration);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
	}
	
	public static void sleep(String seconds) throws InterruptedException {
		Long secL = Long.parseLong(seconds) * 1000;
		
		System.out.println("[WAIT] Sleep for " + seconds + " Seconds ...");
		Thread.sleep(secL);
		
	}
	
}
