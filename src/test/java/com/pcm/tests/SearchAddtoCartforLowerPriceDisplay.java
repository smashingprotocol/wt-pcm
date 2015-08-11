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



public class SearchAddtoCartforLowerPriceDisplay {

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
	public void Search_AddtoCart_for_Lower_Price_Display() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
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
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_IMG_ADDCARTLOWERPRICE_XPATH"));
				Assert.assertTrue("[SEARCH] Add to Cart for Lowere Price in item image is display.",testStatus);
				
				//Verify the Strikethrough Price
				if(Boolean.valueOf(withSlashed)){
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='lprice str prod-lprice' and contains(text(),'" + priceStrike + "')]");
					Assert.assertTrue("[SEARCH] Expected Strikethrough Price " + priceStrike + " is display.",testStatus);
				} else{
					//No slashed
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='lprice prod-lprice' and contains(text(),'" + priceStrike + "')]");
					Assert.assertTrue("[SEARCH] Expected No slashed Price " + priceStrike + " is display.",testStatus);
				}
				
				//Verify the Add to Cart for Lower Price text
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_LABEL_ADDCARTLOWER_XPATH"));
				Assert.assertTrue("[SEARCH] Expected Add to cart for Lower Price text is display.",testStatus);
				
				//Verify the Click Here Button display
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_BTN_CLICKHERE_XPATH"));
				Assert.assertTrue("[SEARCH] Expected Click Here button is display.",testStatus);
				
				StatusLog.printlnPassedResult(Config.driver,"[SEARCH] " + title);
				
			} //end for i
			
			
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
