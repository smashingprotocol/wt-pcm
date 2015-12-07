package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class PDPNewFreeShippingItem {

	public String sku;
	public String withSlashed;
	public String withMIR;
	public String availability;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String title;
	public String baseURL;
	public String pdpURL;
	public String withIcon;
	
	
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void PDP_New_Free_Shipping_Item() throws Throwable {
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//Define properties
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
	
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				
				sku = TableContainer.getCellValue(i, "sku");
				title = TableContainer.getCellValue(i, "title");
				baseURL = TableContainer.getCellValue(i, "baseURL");
				withIcon = TableContainer.getCellValue(i, "withIcon");
				
				//Open PDp
				Search.openPDPbySearchSku(Config.driver, sku);
				
				//Verify Availability
				if(withIcon.equals("true")){
					
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_NEW_FREESHIPVIOLATOR_WITHICON_XPATH"));
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify the Free Shipping violator is display",testStatus);
					
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_NEW_SPECIALOFFER_FREEGROUNDSHIP_XPATH"));
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify the Special Offer - Free Ground Shipping.",testStatus);
					
					
				} else {
					
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_NEW_FREESHIPVIOLATOR_XPATH"));
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify the Free Shipping violator is display",testStatus);
					
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_NEW_FREESHIPSPECIALOFFER_XPATH"));
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify the Free Shipping in Special Offer is display",testStatus);
					
				}
				
				
				
			} //end for i
			
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
		} catch (Exception e){
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
