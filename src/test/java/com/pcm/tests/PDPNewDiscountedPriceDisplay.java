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



public class PDPNewDiscountedPriceDisplay {

	public String sku;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String title;
	public String dPrice;
	public String listPrice;
	public String finalPrice;
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void PDP_New_Discounted_Price_Display() throws Throwable {
		
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
				dPrice = TableContainer.getCellValue(i, "dPrice");
				listPrice = TableContainer.getCellValue(i, "listPrice");
				finalPrice = TableContainer.getCellValue(i, "finalPrice");
				
				
				//Open PDp
				Search.openPDPbySearchSku(Config.driver, sku);
				
				//Verify Discounted Price
				if(!dPrice.equals("")){
					
					PDP.displayPriceDetailClickLowestPrice(Config.driver);
					String discountAmt = PDP.getDiscountAmt(Config.driver,listPrice,finalPrice);
					String discountPercent = PDP.getDiscountPercentage(Config.driver,listPrice,discountAmt);
					
				
					testStatus = PDP.discountAmtFound(Config.driver,discountAmt);
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify Discount Amount is correct.",testStatus);
					
					testStatus = PDP.discountPercentageFound(Config.driver,discountPercent);
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify Discount Percentage is correct.",testStatus);
					
					testStatus = PDP.discountedPrice(Config.driver,dPrice);
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify Discounted Price is Display",testStatus);
					
				} else {
					
					testStatus = false;
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] No Discounted Price value set",testStatus);
					
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
