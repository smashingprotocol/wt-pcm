package com.pcm.includes;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.grund.request.ClickElement;
import com.grund.verify.verifyXPath;


public class Category {
	
	public static Properties properties;
	
	public static void gotoCategoryviaHeader(WebDriver driver, String category) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("HEADER_MENU_PRODUCTS_XPATH"));
		ClickElement.byXPath(driver, "//a[@title='" + category + "']");
		
	}

	public static void clickClearanceOffer(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("CATEGORY_LINK_CLEARANCEOFFER_XPATH"));
	}

	public static String getClearanceOfferCount(WebDriver driver,
			String category) throws Exception {
		
		String catCount;
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		catCount = verifyXPath.getText(driver, properties.getProperty("CATEGORY_LINK_CLEARANCEOFFER_XPATH"));
		catCount = catCount.replaceAll("[^\\d.]", "");
	
		return catCount;
	}

	public static void clickRefurbishedOffer(WebDriver driver) throws Exception {
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("CATEGORY_LINK_REFURBISHEDOFFER_XPATH"));
		
	}

	public static String getRefurbishedOfferCount(WebDriver driver,
			String category) throws Exception {
		
		String catCount;
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		catCount = verifyXPath.getText(driver, properties.getProperty("CATEGORY_LINK_REFURBISHEDOFFER_XPATH"));
		catCount = catCount.replaceAll("[^\\d.]", "");
	
		return catCount;
	}

	public static void clickOpenBoxOffer(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("CATEGORY_LINK_OPENBOXOFFER_XPATH"));
		
		
	}

	public static String getOpenBoxOfferCount(WebDriver driver, String category) throws IOException {
		
		String catCount;
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		catCount = verifyXPath.getText(driver, properties.getProperty("CATEGORY_LINK_OPENBOXOFFER_XPATH"));
		catCount = catCount.replaceAll("[^\\d.]", "");
	
		return catCount;
		
	}

}
