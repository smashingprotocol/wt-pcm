package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;



public class SearchSoldOutDisplay {

	public String sku;
	
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String title;
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Search_Sold_Out_Availability_and_Click_Here() throws Exception{
		
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
			
				//Quick add the sku
				Search.keyword(Config.driver, sku);
				
				//Verify the Click Here Button display
				testStatus = verifyXPath.isfound(Config.driver, Search.SEARCH_BTN_CLICKHERE_XPATH);
				StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected Click Here button is display.",testStatus);
				
				//Verify the What is My Final Price violator.
				testStatus = verifyXPath.isfound(Config.driver, "//div[@data-sku='" + sku + "']" + Search.LBL_SOLDOUT_XPATH);
				StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Availability is Sold Out.",testStatus);
				
				
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
