package com.pcm.tests;


import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Header;
import com.pcm.includes.Homepage;
import com.pcm.request.ClickElement;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.verifyXPath;

public class HeaderLastClickedTab {

	public String env;
	public String email;
	public String password;
	public boolean testStatus;
	static Properties sys = System.getProperties();
	
	
	@Test
	public void Header_Last_Clicked_Tab() throws Exception{

		try 
		{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
			//Startups/Entrepreneurs
			Header.clickMenubyXPath(Config.driver,pr.getProperty("HEADER_LINK_MENU_STARTUP_XPATH"));
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("HEADER_LBL_GREET_STARTUPS_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HEADER] Verify the Startups / Entrepreneur Greetings Header",testStatus);
			
			//Check header in cart.
			Cart.navigate(Config.driver);
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("HEADER_LINK_MENU_STARTUP_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HEADER] Verify the Startups / Entrepreneur Greetings Header in cart.",testStatus);
			
			
			//Check header in homepage.
			ClickElement.byXPath(Config.driver, pr.getProperty("HEADER_LINK_PCMLOGO_XPATH"));
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("HEADER_LBL_GREET_STARTUPS_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HEADER] Verify the Startups / Entrepreneur Greetings Header in homepage.",testStatus);
			
			//MEDIUM/LARGE BUSINESS
			Header.clickMenubyXPath(Config.driver,pr.getProperty("HEADER_LINK_MENU_MEDIUM_XPATH"));
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("HEADER_LBL_GREET_MEDIUM_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HEADER] Verify the Medium Business Greetings Header in homepage.",testStatus);
			
			//Check header in cart.
			Cart.navigate(Config.driver);
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("HEADER_LBL_GREET_MEDIUM_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HEADER] Verify the SMedium Business Greetings Header in cart.",testStatus);
			
			//Check header in homepage.
			ClickElement.byXPath(Config.driver, pr.getProperty("HEADER_LINK_PCMLOGO_XPATH"));
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("HEADER_LBL_GREET_MEDIUM_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HEADER] Verify the SMedium Business Greetings Header in homepage.",testStatus);
			
			//ENTERPRISE
			Header.clickMenubyXPath(Config.driver,pr.getProperty("HEADER_LINK_MENU_ENTERPRISE_XPATH"));
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("HEADER_LBL_GREET_ENTERPRISE_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HEADER] Verify the Enterprise Greetings Header in homepage.",testStatus);
			
			//HEALTHCARE
			Header.clickMenubyXPath(Config.driver,pr.getProperty("HEADER_LINK_MENU_HEALTHCARE_XPATH"));
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("HEADER_LBL_GREET_HEALTHCARE_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HEADER] Verify the Healthcare Greetings Header in homepage.",testStatus);
			
			//Check header in cart.
			Cart.navigate(Config.driver);
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("HEADER_LBL_GREET_HEALTHCARE_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HEADER] Verify the Healthcare Greetings Header in cart.",testStatus);
			
			//Check header in homepage.
			ClickElement.byXPath(Config.driver, pr.getProperty("HEADER_LINK_PCMLOGO_XPATH"));
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("HEADER_LBL_GREET_HEALTHCARE_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[HEADER] Verify the Healthcare Greetings Header in homepage.",testStatus);
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
			
		}
		
			catch (Exception e)
			{
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
