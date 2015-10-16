package com.pcm.includes;

import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.grund.engine.Config;
import com.grund.form.SetInputField;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.verify.verifyXPath;


public class Header {

	public static Properties properties;
	
	public static void subscribeSubmitEmail(WebDriver driver, String email) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		SetInputField.byXPath(driver, properties.getProperty("HEADER_INPUT_SUBSCRIBE_XPATH"), email);
		ClickElement.byXPath(driver, properties.getProperty("HEADER_BTN_SUBSCRIBE_XPATH"));
		
	}

	public static void signIn(WebDriver driver, String email, String password) throws Exception {
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("HEADER_LINK_SIGNIN_XPATH"));
		ClickElement.byXPath(driver, properties.getProperty("HEADER_BTN_SIGNIN_XPATH"));
		
		SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_EMAIL_XPATH"),email);
		SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_PSWD_XPATH"),password);
		ClickElement.byXPath(driver, properties.getProperty("LOGIN_BTN_SIGNIN_XPATH"));
		
		Boolean testStatus = verifyXPath.isfound(Config.driver,properties.getProperty("HEADER_LINK_USER_XPATH"));
		StatusLog.printlnPassedResultTrue(Config.driver,"[VERIFY] First Name is display in the header sign in.", testStatus);
		
	}

	public static void swapAccount(WebDriver driver, String username, String siteRedirect) throws Exception {
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("HEADER_LINK_USER_XPATH"));
		
		if(siteRedirect.equals("bd")){
			ClickElement.byXPath(driver, "(//a[@data-storeid='S128' and @data-userloginid='" + username + "']) [position()=2]");
		}
		
	}

	public static void clickMenubyXPath(WebDriver driver, String xpath) throws Exception {
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver,properties.getProperty("HEADER_LINK_MENU_XPATH"));
		ClickElement.byXPath(driver, xpath);
		
	}

	public static void navigateCreateAccount(WebDriver driver) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("HEADER_LINK_SIGNIN_XPATH"));
		ClickElement.byXPath(driver, properties.getProperty("HEADER_LINK_REGISTER_XPATH"));
		
	}

	public static void signIntoSiteSelector(WebDriver driver, String username,
			String password, String siteLogin) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("HEADER_LINK_SIGNIN_XPATH"));
		ClickElement.byXPath(driver, properties.getProperty("HEADER_BTN_SIGNIN_XPATH"));
		
		SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_EMAIL_XPATH"),username);
		SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_PSWD_XPATH"),password);
		ClickElement.byXPath(driver, properties.getProperty("LOGIN_BTN_SIGNIN_XPATH"));
		
		SignIn.storeSelect(driver,siteLogin);
		
		
	}

	public static void signInNoValidation(WebDriver driver, String username,
			String password) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		ClickElement.byXPath(driver, properties.getProperty("HEADER_LINK_SIGNIN_XPATH"));
		ClickElement.byXPath(driver, properties.getProperty("HEADER_BTN_SIGNIN_XPATH"));
		
		SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_EMAIL_XPATH"),username);
		SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_PSWD_XPATH"),password);
		ClickElement.byXPath(driver, properties.getProperty("LOGIN_BTN_SIGNIN_XPATH"));
		
		
		
	}

}
