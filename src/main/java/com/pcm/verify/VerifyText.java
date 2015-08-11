package com.pcm.verify;

import org.openqa.selenium.WebDriver;

public class VerifyText {

	public static boolean isfound(WebDriver driver, String text) {
		
		if (driver.getPageSource().toLowerCase().contains(text.toLowerCase())){
			return true;
		} else{
			return false;
		}
		
	}

}
