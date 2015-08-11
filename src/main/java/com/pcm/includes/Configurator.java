package com.pcm.includes;

import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.pcm.request.ClickElement;
import com.pcm.utility.Wait;
import com.pcm.verify.verifyXPath;

public class Configurator {

	public static Properties properties;
	
	public static void addToCart(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("CONFIGURATOR_BTN_ADDTOCART_XPATH"));
		ClickElement.byXPath(driver, properties.getProperty("CONFIGURATOR_BTN_NEXTSP_XPATH"));
		
	}

	public static String getFinalPrice(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		Wait.waitforXPath(driver, properties.getProperty("CONFIGURATOR_LBL_FINALPRICE_XPATH"),"10");
		
		String finalPrice = verifyXPath.getText(driver, properties.getProperty("CONFIGURATOR_LBL_FINALPRICE_XPATH"));
		
		return finalPrice;
		
	}

}
