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

public class HomeSubscribeviaHeader {

	public String env;
	public String email;
	public String password;
	public boolean testStatus;
	static Properties sys = System.getProperties();
	
	
	@Test
	public void Home_Subscribe_via_Header() throws Exception{

		try 
		{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			email = pr.getProperty("HEADER_USER_SUBSCRIBE_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			
			//Verify the xpath of the Subscribe box is found.
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("HEADER_INPUT_SUBSCRIBE_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HOMEPAGE] Verify the Subscribtion field is display.",testStatus);
			
			Header.subscribeSubmitEmail(Config.driver,email);
		
			//Verify the modal after subsribe email is submit.
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("MODAL_SUBSCRIBE_THANKYOU_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HOMEPAGE] Verify the Thank You Modal is display after subscription is submitted.",testStatus);
			
			//Close the modal and login.
			ClickElement.byXPath(Config.driver, pr.getProperty("MODAL_SUBSCRIBE_THANKYOU_CLOSE_XPATH"));
			SignIn.login(Config.driver, email, password);
			
			//Verify the xpath of the Subscribe box is not found after logged in.
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("HEADER_INPUT_SUBSCRIBE_XPATH"));
			StatusLog.printlnPassedResultFalse(Config.driver,"[HOMEPAGE] Verify subscribe header is not display after logged in.",testStatus);
			
			//Unsubscribe the user.
			MyAccount.unsubscribe(Config.driver,email);
			
			//Verify the xpath of the Subscribe box is found after Unsubscribed.
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("HEADER_INPUT_SUBSCRIBE_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HOMEPAGE] Verify the Subscribtion field is display after Unsubscribed.",testStatus);
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
		}
		
			catch (Exception e)
			{
				System.out.println("[HOMEPAGE] Exception encountered.");
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
