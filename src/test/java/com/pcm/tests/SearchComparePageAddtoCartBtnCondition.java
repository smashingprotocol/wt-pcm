package com.pcm.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.verifyXPath;



public class SearchComparePageAddtoCartBtnCondition {

	public String sku1;
	public String sku2;
	public String sku3;
	public String sku4;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String title;
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Search_Compare_Page_AddtoCart_Btn_Condition() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
			//Define properties
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			sku1 = pr.getProperty("SEARCH_SKU_ADDINCARTSAVINGS");
			sku2 = pr.getProperty("SEARCH_SKU_CALLUS");
			sku3 = pr.getProperty("SEARCH_SKU_WHATFINALPRICE_WITHSLASH");
			sku4 = pr.getProperty("SEARCH_SKU_FREESHIP");
			
			ArrayList<String> compareSkus = new ArrayList<String>();
			
			compareSkus.add(0,sku1);
			compareSkus.add(1,sku2);
			compareSkus.add(2,sku3);
			compareSkus.add(3,sku4);
			
			Search.compareSkus(Config.driver,compareSkus);
			
			//Verify the Call for Price Button in Compare Page
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("COMPARE_BTN_CALLFORPRICE_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Compare page Call for Price button is display.",testStatus);
			
			//Verify the See Lowest Price Button in Compare Page
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("COMPARE_BTN_SEELOWESTPRICE_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Compare page See Lowest Price button is display.",testStatus);
			
			//Verify the Free Shipping text in Compare Page
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("COMPARE_LBL_FREESHIPTEXT_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Compare page Free Shipping text is display.",testStatus);
			
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
