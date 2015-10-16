package com.pcm.tests;


import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Header;
import com.pcm.includes.Homepage;
import com.pcm.includes.MyAccount;
import com.pcm.includes.SignIn;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;

public class MyAccountSubscribedEmailDisplay {

	public String env;
	public String email;
	public String password;
	public boolean testStatus;
	static Properties sys = System.getProperties();
	
	
	@Test
	public void My_Account_Subscribed_Email_Display() throws Exception{

		try 
		{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			email = pr.getProperty("HEADER_USER_SUBSCRIBE_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			
			//Verify the xpath of the Subscribe box is found.
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("HEADER_INPUT_SUBSCRIBE_XPATH"));
			Assert.assertTrue(testStatus);
			
			SignIn.login(Config.driver, email, password);
			MyAccount.navigate(Config.driver);
			
			Header.subscribeSubmitEmail(Config.driver,email);
		
			//Verify the modal after subsribe email is submit.
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("MODAL_SUBSCRIBE_THANKYOU_XPATH"));
			Assert.assertTrue(testStatus);
			
			//Close the modal and login.
			ClickElement.byXPath(Config.driver, pr.getProperty("MODAL_SUBSCRIBE_THANKYOU_CLOSE_XPATH"));
			
			//Verify the xpath of the Subscribe box is not found after logged in.
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("HEADER_INPUT_SUBSCRIBE_XPATH"));
			Assert.assertFalse(testStatus);
			
			StatusLog.printlnPassedResult(Config.driver,"[PCM] Verify subscribe header is not display after logged in.");
			
			//Unsubscribe the user.
			MyAccount.unsubscribe(Config.driver,email);
			
			//Verify the xpath of the Subscribe box is found after Unsubscribed.
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("HEADER_INPUT_SUBSCRIBE_XPATH"));
			Assert.assertTrue(testStatus);
			
			StatusLog.printlnPassedResult(Config.driver,"[PCM] Verify subscribe header display after unsubsribed.");
			
			
		}
		
			catch (Exception e)
			{
				System.out.println("[PCM] Verify Homepage...FAILED");
				Assert.fail(e.getMessage());
				
			}
		
	}
	
	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
	}
	
	
}
