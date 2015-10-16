package com.pcm.includes;

import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.grund.form.SetInputField;
import com.grund.request.ClickElement;


public class MyAccount {

	public static Properties properties;
	
	public static void unsubscribe(WebDriver driver, String email) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		//navigate in My Account.
		ClickElement.byXPath(driver, properties.getProperty("FOOTER_LINK_MYACT_LOGIN_XPATH"));
		ClickElement.byXPath(driver, properties.getProperty("MYACCOUNT_LINK_UNSUBSCRIBE_XPATH"));
		SetInputField.byXPath(driver, properties.getProperty("UNSUBSCRIBE_INPUT_EMAIL_XPATH"), email);
		ClickElement.byXPath(driver, properties.getProperty("UNSUBSCRIBE_BTN_UNSUBSCRIBE_XPATH"));
		
		//Select Remove all email publication
		ClickElement.byXPath(driver, properties.getProperty("UNSUBSCRIBE_CHK_UNSUBSCRIBEALL_XPATH"));
		ClickElement.byXPath(driver, properties.getProperty("UNSUBSCRIBE_BTN_UNSUBSCRIBE_XPATH"));
		
	}

	public static void navigate(WebDriver driver) throws Exception {
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		//navigate in My Account.
		ClickElement.byXPath(driver, properties.getProperty("FOOTER_LINK_MYACT_LOGIN_XPATH"));
		
	}

	public static void navigateAccountSettings(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		//navigate in My Account.
		MyAccount.navigate(driver);
		ClickElement.byXPath(driver, properties.getProperty("MYACCOUNT_TAB_ACTSETTINGS_XPATH"));
		
		
	}

}
