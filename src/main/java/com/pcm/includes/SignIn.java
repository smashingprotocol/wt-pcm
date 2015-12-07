package com.pcm.includes;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.grund.engine.Config;
import com.grund.form.SetInputField;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.verify.VerifyDocumentURL;
import com.grund.verify.verifyXPath;


public class SignIn {

	public static Properties properties;
	static boolean login = true;
	
	public static void login(WebDriver driver, String email, String password) throws Exception{
		// TODO Auto-generated method stub
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try {
			properties.load(reader);
			
			//Check first if user is logged in, click sign out.
			if (login = verifyXPath.isfound(driver,Header.LINK_SIGNOUT_XPATH)){
				System.out.println("[PCM] Logging out user...");
				ClickElement.byXPath(driver, Header.LINK_SIGNOUT_XPATH);
			}
			
			//ClickElement.byXPath(driver, properties.getProperty("HEADER_LINK_SIGNIN_XPATH"));
			ClickElement.byXPath(driver, properties.getProperty("FOOTER_LINK_MYACT_LOGIN_XPATH"));
			SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_EMAIL_XPATH"),email);
			SetInputField.byXPath(driver,properties.getProperty("LOGIN_INPUT_PSWD_XPATH"),password);
			try {
				ClickElement.byXPath(driver, properties.getProperty("LOGIN_BTN_SIGNIN_XPATH"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void logout(WebDriver driver) throws Exception{
		
		System.out.println("[STEP] LOGGED OUT USER");
		if (verifyXPath.isfound(driver,Header.LINK_USER_XPATH)){
			
			ClickElement.byXPath(driver, Header.LINK_USER_XPATH);
			ClickElement.byXPath(driver, Header.LINK_SIGNOUT_XPATH);
		}
		
	}

	public static void storeSelect(WebDriver driver, String siteLogin) throws Exception {
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		Boolean testStatus = VerifyDocumentURL.containsText(driver, properties.getProperty("LOGIN_NAVPOINT_SITESELECTOR"));
		StatusLog.printlnPassedResultTrue(Config.driver,"[VERIFY] Site Selector URL", testStatus);
		
		if(siteLogin.equals("bd")){
			ClickElement.byXPath(driver, properties.getProperty("LOGIN_LINK_SITESELECT_BD_XPATH"));
		}
		
		if(siteLogin.equals("pcm")){
			ClickElement.byXPath(driver, properties.getProperty("LOGIN_LINK_SITESELECT_PCM_XPATH"));
		}
		
	}
	
}
