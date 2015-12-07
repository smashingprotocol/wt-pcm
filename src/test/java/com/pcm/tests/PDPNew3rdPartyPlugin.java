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



public class PDPNew3rdPartyPlugin {

	public String sku;
	public String qty;
	public String pluginCode;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String title;
	public String baseURL;
	public String pdpURL;
	
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void PDP_New_3rd_Party_Plugin() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//Define properties
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
	
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				
				sku = TableContainer.getCellValue(i, "sku");
				pluginCode = TableContainer.getCellValue(i, "pluginCode");
				title = TableContainer.getCellValue(i, "title");
				baseURL = TableContainer.getCellValue(i, "baseURL");
				
				//Open PDp
				Search.openPDPbySearchSku(Config.driver, sku);
				
				
				//Set Recommended Warranty
				
					testStatus = PDP.is3rdPartyPluginFound(Config.driver, pluginCode);
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] " + title,testStatus);
					
				
				
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
