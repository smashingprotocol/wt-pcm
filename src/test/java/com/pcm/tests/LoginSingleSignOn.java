package com.pcm.tests;


import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Header;
import com.pcm.includes.Homepage;
import com.pcm.includes.SignIn;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.utility.Wait;
import com.grund.verify.VerifyDocumentURL;

public class LoginSingleSignOn {

	public String env;
	public String username;
	public String password;
	public String isSiteSelector;
	public String siteLogin;
	public String title;
	public String bdNavLink;	
	public boolean testStatus;
	public boolean tcStatus = true;
	
	static Properties sys = System.getProperties();
	
	
	@Test
	public void Login_Single_SignOn() throws Exception{

		try 
		{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			bdNavLink = pr.getProperty("BD_NAVLINK_prod");
			//Long wait = Long.parseLong(pr.getProperty("WAIT_SEC"));
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
	
					Config.driver.navigate().to(sys.getProperty("host"));
					SignIn.logout(Config.driver);
					
					username = env + TableContainer.getCellValue(i, "username");
					password = TableContainer.getCellValue(i, "password");
					isSiteSelector = TableContainer.getCellValue(i, "isSiteSelector");
					siteLogin = TableContainer.getCellValue(i, "siteLogin");
					title = TableContainer.getCellValue(i, "title");
					
					
					
					
					
					
					if(siteLogin.equals("bd")){
						//Thread.sleep(wait);
						if(isSiteSelector.equals("true")){
							Header.signIntoSiteSelector(Config.driver,username,password,siteLogin);
						} else{
							
							Header.signInNoValidation(Config.driver,username,password);
							Wait.waitforXPath(Config.driver, "//div[@class='bdmainmenu']//input[@id='searchTextInput']", "10");
							testStatus = VerifyDocumentURL.containsText(Config.driver, bdNavLink);
							StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] " + title,testStatus);
						} //end if siteselector
						
						
					} //end if sitelogin
					
					if(siteLogin.equals("pcm")){
						
						if(isSiteSelector.equals("true")){
							Header.signIntoSiteSelector(Config.driver,username,password,siteLogin);
						} else{
							
							Header.signIn(Config.driver,username,password);
							
						} //end if siteselector
						
						testStatus = VerifyDocumentURL.containsText(Config.driver, sys.getProperty("host"));
						StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] " + title,testStatus);
						
					} //end if siteLogin


			} //end for	

			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
		}
		
			catch (Exception e)
			{
				Assert.fail("[LOGIN] An Error encountered on Single Sign On: " + e.getMessage());
				
			}
		
	}
	
	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
	}
	
	
}
