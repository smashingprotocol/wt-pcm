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



public class PDPNewAddtoCartforLowerPriceDisplay {

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
	public String baseURL;
	public String pdpURL;
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void PDP_New_Add_to_Cart_for_Lower_Price_Display() throws Exception{
		
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
				baseURL = TableContainer.getCellValue(i, "baseURL");
				withSlashed = TableContainer.getCellValue(i, "withSlashed");
				withMIR = TableContainer.getCellValue(i, "withMIR");
				priceStrike = TableContainer.getCellValue(i, "priceStrike");
				unitPrice = TableContainer.getCellValue(i, "unitPrice");
				FinalPrice = TableContainer.getCellValue(i, "FinalPrice");
				title = TableContainer.getCellValue(i, "title");
				
				//Open PDp
				Search.openPDPbySearchSku(Config.driver, sku);
				
				//Verify the Add to cart for lower price label
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_NEW_LBL_ADDTOCARTFORLOWERPRICE_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Add to Cart for Lower Price label is display in PDP",testStatus);
				
				//Verify Why don't we show price link
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_NEW_LINK_WHYDONTWESHOWLOWERPRICE__XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Why don't we show price link is display in PDP.",testStatus);
				
				//Verify the Strikethrough Price in Search and UPP Modal
				if(withSlashed.equals("true")){
					//strikethrough price
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='list-price strike' and contains(text(),'" + priceStrike + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Expected Strikethrough Price " + priceStrike + " is display in PDP",testStatus);	
					
				} else{
					//No slashed
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='list-price' and contains(text(),'" + priceStrike + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Expected No slashed Price " + priceStrike + " is display in PDP",testStatus);
						
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
