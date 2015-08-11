package com.pcm.verify;

import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

public class VerifyDocumentURL {
	
	public static Properties properties;
	
	public static boolean containsText(WebDriver driver, String navlink) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		Boolean flag = false;
		String curURL = driver.getCurrentUrl();
		
		if(curURL.contains(navlink)){
			System.out.println("[VERIFY] " + navlink + " text is found in the current URL: " + curURL);
			flag = true;
		} else{
			System.out.println("[VERIFY] " + navlink + " text is not found in the current URL: " + curURL);
		}
		
		return flag;
		
	}

}
