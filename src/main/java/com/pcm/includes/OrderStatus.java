package com.pcm.includes;

import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.grund.form.SetInputField;
import com.grund.request.ClickElement;

public class OrderStatus {
	
	public static Properties properties;
	
	public static void orderLookup(WebDriver driver, String orderNumber,
			String zipCode) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("FOOTER_LINK_ORDERSTATUS_XPATH"));
		SetInputField.byXPath(driver, properties.getProperty("ORDERSTATUS_INPUT_OR_XPATH"), orderNumber);
		SetInputField.byXPath(driver, properties.getProperty("ORDERSTATUS_INPUT_ZIPCODE_XPATH"), zipCode);
		ClickElement.byXPath(driver, properties.getProperty("ORDERSTATUS_BTN_FINDORDER_XPATH"));
		
	}

}
