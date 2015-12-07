package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.PDP;
import com.pcm.includes.Search;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;



public class PDPNewSpecialOfferSection {

	public String sku;
	public String withMIR;
	public String withIR;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String title;
	public String baseURL;
	public String pdpURL;
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void PDP_New_Special_Offer_Section() throws Exception{
		
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
				withMIR = TableContainer.getCellValue(i, "withMIR");
				withIR = TableContainer.getCellValue(i, "withIR");
				baseURL = TableContainer.getCellValue(i, "baseURL");
				
				//Open PDp
				Search.openPDPbySearchSku(Config.driver, sku);
				
				
				//Verify Special Offer section
				if(withMIR.equals("true")){	
					testStatus = PDP.mailinRebateSpecialOfferFound(Config.driver);
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify the Special Offer - Mail-in Rebates.",testStatus);
				}
				
				if(withIR.equals("true")){	
					testStatus = PDP.instantRebateSpecialOfferFound(Config.driver);
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify the Special Offer - Instant Rebates.",testStatus);
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
