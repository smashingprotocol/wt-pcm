package com.pcm.includes;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.pcm.form.SetInputField;
import com.pcm.request.ClickElement;

public class CreateAccount {
	
	public static Properties properties;
	
	public static void enterCreateAcountFields(WebDriver driver,
			ArrayList<String> userInfoFields) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try{
			properties.load(reader);
			
			SetInputField.byXPath(driver, properties.getProperty("CREATEACCOUNT_INPUT_EMAIL_XPATH"), userInfoFields.get(0));
			SetInputField.byXPath(driver, properties.getProperty("CREATEACCOUNT_INPUT_CONFIRMEMAIL_XPATH"), userInfoFields.get(1));
			SetInputField.byXPath(driver, properties.getProperty("CREATEACCOUNT_INPUT_PSWD_XPATH"), userInfoFields.get(2));
			SetInputField.byXPath(driver, properties.getProperty("CREATEACCOUNT_INPUT_CONFIRMPSWD_XPATH"), userInfoFields.get(3));
			SetInputField.byXPath(driver, properties.getProperty("CREATEACCOUNT_INPUT_FIRSTNAME_XPATH"), userInfoFields.get(4));
			SetInputField.byXPath(driver, properties.getProperty("CREATEACCOUNT_INPUT_LASTNAME_XPATH"), userInfoFields.get(5));
		
			ClickElement.byXPath(driver, properties.getProperty("CREATEACCOUNT_BTN_CREATEACCOUNT_XPATH"));
			
			
		}catch(Exception e) {
			
		}
		
		
	}

}
