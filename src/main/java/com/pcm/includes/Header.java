package com.pcm.includes;

import java.io.FileReader;
import java.util.Properties;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;

import com.grund.engine.Config;
import com.grund.form.SetInputField;
import com.grund.request.ClickElement;
import com.grund.verify.verifyXPath;


public class Header {

	public static final String MENU_PRODUCTS_XPATH = "//a[@class='ga' and @title='Products']";
	public static final String INPUT_SUBSCRIBE_XPATH = "//input[@name='email-sub']";
	public static final String BTN_SUBSCRIBE_XPATH = "//input[@class='email-sub-btn']";
	public static final String LINK_SIGNIN_XPATH = "//a[@id='signin-btn-21']";
	private static final String BTN_SIGNIN_XPATH = "(//a[contains(@href,'/o/Login-Selector/msc-login-selector')]) [position()=2]";
	private static final String LINK_REGISTER_XPATH = "(//a[contains(@href,'/o/Create-New-Account-With-Billing-and-Shipping/msc-135')]) [position()=2]";
	public static final String LINK_SIGNOUT_XPATH = "(//a[@href='/o/msc-login?op=mall.web.common.signInLink.signInLink&reqOp=logOut']) [position()=2]";
	public static final String LINK_USER_XPATH = "//a[@id='user-signin-btn-21']";
	public static final String LINK_CART_XPATH = "//a[@href='/n/Shopping-Cart/msc-cart']";
	
	public static Properties properties;
	
	public static void subscribeSubmitEmail(WebDriver driver, String email) throws Exception {
		System.out.println("[STEP] IN HEADER, ENTER EMAIL AND SUBSCRIBE");
		SetInputField.byXPath(driver, INPUT_SUBSCRIBE_XPATH, email);
		ClickElement.byXPath(driver, BTN_SUBSCRIBE_XPATH);
		
	}

	public static void signIn(WebDriver driver, String email, String password) throws Exception {
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		System.out.println("[STEP] IN HEADER, SIGN IN");
		
		boolean textxpath = verifyXPath.isfoundwithWait(driver, LINK_SIGNIN_XPATH, "2");
		if(textxpath){
			ClickElement.byXPath(driver, LINK_SIGNIN_XPATH);
		}
		
		ClickElement.byXPath(driver, BTN_SIGNIN_XPATH);
		
		SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_EMAIL_XPATH"),email);
		SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_PSWD_XPATH"),password);
		ClickElement.byXPath(driver, properties.getProperty("LOGIN_BTN_SIGNIN_XPATH"));
		
		Boolean testStatus = verifyXPath.isfoundwithWait(Config.driver, Header.LINK_USER_XPATH,"2");
		Assert.assertTrue("[VERIFY] User is logged in.",testStatus);
		
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
		System.out.println("[STEP] IN HEADER, NAVIGATE IN CREATE ACCOUNT PAGE.");
		ClickElement.byXPath(driver, LINK_SIGNIN_XPATH);
		ClickElement.byXPath(driver, LINK_REGISTER_XPATH);
		
	}

	public static void signIntoSiteSelector(WebDriver driver, String username,
			String password, String siteLogin) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		System.out.println("[STEP] IN HEADER, SIGN IN TO SITE SELECTOR FORM");
		ClickElement.byXPath(driver, LINK_SIGNIN_XPATH);
		ClickElement.byXPath(driver, BTN_SIGNIN_XPATH);
		
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
		System.out.println("[STEP] IN HEADER, SIGN IN");
		ClickElement.byXPath(driver, LINK_SIGNIN_XPATH);
		ClickElement.byXPath(driver, BTN_SIGNIN_XPATH);
		
		SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_EMAIL_XPATH"),username);
		SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_PSWD_XPATH"),password);
		ClickElement.byXPath(driver, properties.getProperty("LOGIN_BTN_SIGNIN_XPATH"));
		
		
		
	}

}
