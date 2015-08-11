package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TableContainer;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.verifyXPath;



public class SearchAdditionalInCartSavingsPriceDisplay {

	public String sku;
	public String withSlashed;
	public String withMIR;
	public String priceStrike;
	public String unitPrice;
	public String FinalPrice;
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
	public void Search_Additional_InCart_Savings_Price_Display() throws Exception{
		
		try{
			
			//setup driver,properties and includes
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost"); //prod or stage
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
			
			//Define properties
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
	
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				
				sku = TableContainer.getCellValue(i, "sku");
				withSlashed = TableContainer.getCellValue(i, "withSlashed");
				withMIR = TableContainer.getCellValue(i, "withMIR");
				priceStrike = TableContainer.getCellValue(i, "priceStrike");
				unitPrice = TableContainer.getCellValue(i, "unitPrice");
				FinalPrice = TableContainer.getCellValue(i, "FinalPrice");
				title = TableContainer.getCellValue(i, "title");
				
				
				//Quick add the sku
				Search.keyword(Config.driver, sku);
				
				//Verify the Additional in-cart Savings in the imaeg
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_IMG_ADDINCART_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Additional in-cart savings in item image is display.",testStatus);
				
				//Verify the Strikethrough Price
				if(Boolean.valueOf(withSlashed)){
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='lprice str prod-lprice' and contains(text(),'" + priceStrike + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected Strikethrough Price " + priceStrike + " is display.",testStatus);
				} else{
					//No slashed
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='lprice prod-lprice' and contains(text(),'" + priceStrike + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected No slashed Price " + priceStrike + " is display.",testStatus);
				}
				
				//Verify the Additional in-cart savings text
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_LABEL_ADDINCART_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected Additional in-cart text is display.",testStatus);
				
				//Verify the Click Here Button display
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_BTN_CLICKHERE_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected Click Here button is display.",testStatus);
				
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
