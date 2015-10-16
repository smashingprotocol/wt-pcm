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



public class SearchFreeAfterRebatesItem {

	public String sku;
	public String withIcon;
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
	public void Search_Free_After_Rebates_Item() throws Exception{
		
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
				withSlashed = TableContainer.getCellValue(i, "withSlashed");
				withMIR = TableContainer.getCellValue(i, "withMIR");
				priceStrike = TableContainer.getCellValue(i, "priceStrike");
				unitPrice = TableContainer.getCellValue(i, "unitPrice");
				FinalPrice = TableContainer.getCellValue(i, "FinalPrice");
				title = TableContainer.getCellValue(i, "title");
				
				//Quick add the sku
				Search.keyword(Config.driver, sku);
				
				if(Boolean.valueOf(withSlashed)){
					//Verify the SLASHED PRICE
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='lprice str' and contains(text(),'" + priceStrike + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected Strikethrough Price of " + priceStrike + " is display.",testStatus);
					
					//Verify the Free after rebate
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_LABEL_FREEAFTERREBATES_XPATH"));
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Free after rebate Text is display.",testStatus);
					
				} //end if
			} //end for
			
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
