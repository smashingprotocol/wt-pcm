package com.pcm.tests;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.CreateAccount;
import com.pcm.includes.Header;
import com.pcm.includes.Homepage;
import com.pcm.includes.MyAccount;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TableContainer;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.verifyXPath;

public class MyAccountCreateNewAccountDefaultAddresses {

	public String EmailPrefix;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String verifyOrderSubtTotal;
	public String sku;
	public String qty;
	public String zipCode;
	public String itemid;
	public String cardNo;
	public String exMonth;
	public String exYear;
	public String securityCode;
	public boolean tcStatus = true;
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	
	@Test
	public void My_Account_Create_New_Account_Default_Addresses() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
			
			//Create array list for the billing fields.
			ArrayList<String> userInfoFields = new ArrayList<String>();
			String now = new SimpleDateFormat("mmddhhmmss").format(new Date());
			int rowCount = TableContainer.getRowCount();
			for(int i = 1; i <= rowCount; i++){
				
				Header.navigateCreateAccount(Config.driver);
				
				String NewEmail = TableContainer.getCellValue(i, "EmailPrefix") + now +  "_" + i +  "@gmail.com";
				
				userInfoFields.add(0, NewEmail);
				userInfoFields.add(1, NewEmail);
				userInfoFields.add(2, TableContainer.getCellValue(i, "Password"));
				userInfoFields.add(3, TableContainer.getCellValue(i, "ConfirmPassword"));
				userInfoFields.add(4, TableContainer.getCellValue(i, "FirstName"));
				userInfoFields.add(5, TableContainer.getCellValue(i, "LastName"));
				
				CreateAccount.enterCreateAcountFields(Config.driver,userInfoFields);
				
				testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("HEADER_LINK_SIGNOUT_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[CREATE ACCOUNT] User is logged in after created account (Sign out link is display)",testStatus);
				
				
				MyAccount.navigateAccountSettings(Config.driver);
				
				testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("MYACCOUNT_LBL_NOSHIPADD_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[CREATE ACCOUNT] Account Settings default shipping address text display",testStatus);
				
				testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("MYACCOUNT_LBL_NOBILLADD_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[CREATE ACCOUNT] Account Settings default billing address text display",testStatus);
				
				
			
			} //end for
			
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
		} catch (Exception e){
			Assert.fail("[MY ACCOUNT] Error occurs on My_Account_Create_New_Account_Default_Addresses");
		}
	}
	

	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
		
	}
	
	
}
