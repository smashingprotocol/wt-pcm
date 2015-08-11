package com.pcm.tests;


import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.Header;
import com.pcm.includes.Homepage;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TableContainer;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.VerifyDocumentURL;

public class MyAccountSwapAccountRedirect {

	public String env;
	public String username;
	public String password;
	public String login;
	public String siteLogin;
	public String title;
	public String bdNavLink;	
	public boolean testStatus;
	public boolean tcStatus = true;
	
	static Properties sys = System.getProperties();
	
	
	@Test
	public void My_Account_Swap_Account_Redirect() throws Exception{

		try 
		{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
			bdNavLink = pr.getProperty("BD_NAVLINK_prod");
			Long wait = Long.parseLong(pr.getProperty("WAIT_SEC"));
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				
					username = TableContainer.getCellValue(i, "username");
					password = TableContainer.getCellValue(i, "password");
					login = TableContainer.getCellValue(i, "login");
					siteLogin = TableContainer.getCellValue(i, "siteLogin");
					title = TableContainer.getCellValue(i, "title");
					
					Header.signIn(Config.driver,username,password);
					Header.swapAccount(Config.driver,username,siteLogin);
					Thread.sleep(wait);
					testStatus = VerifyDocumentURL.containsText(Config.driver, bdNavLink);
					StatusLog.printlnPassedResultTrue(Config.driver,"[MY ACCOUNT] " + title,testStatus);

			} //end for	
			
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
