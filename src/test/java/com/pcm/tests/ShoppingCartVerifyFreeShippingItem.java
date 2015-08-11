package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Homepage;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TableContainer;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.verifyXPath;



public class ShoppingCartVerifyFreeShippingItem {

	public String sku;
	public String withSlashed;
	public String withIcon;
	public String withMIR;
	public String priceStrike;
	public String unitPrice;
	public String FinalPrice;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean teststatus;
	public boolean tcStatus = true;
	public String edpno;
	public String cartOrderTotal;
	public String zipCode;
	public String title;
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Shopping_Cart_Additional_InCart_Savings_Price_Display() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies

			//Define properties
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			zipCode = pr.getProperty("CHECKOUT_ZIP_RECYCFEE");
	
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				
					sku = TableContainer.getCellValue(i, "sku");
					withIcon = TableContainer.getCellValue(i, "withIcon");
					withSlashed = TableContainer.getCellValue(i, "withSlashed");
					withMIR = TableContainer.getCellValue(i, "withMIR");
					priceStrike = TableContainer.getCellValue(i, "priceStrike");
					unitPrice = TableContainer.getCellValue(i, "unitPrice");
					FinalPrice = TableContainer.getCellValue(i, "FinalPrice");
					title = TableContainer.getCellValue(i, "title");
					
					
					//Quick add the sku
					Cart.clearcart(Config.driver);
					Cart.quickaddsku(Config.driver, sku);
					
					
					//Verify the Free Shipping text with or no icon
					if(Boolean.valueOf(withIcon)){
						
						teststatus = verifyXPath.isfound(Config.driver, pr.getProperty("CART_LABEL_FREESHIPPINGICONTEXT_XPATH"));
						StatusLog.printlnPassedResultTrue(Config.driver,"[CART] Expected Free Shipping Text with icon is displayed. ",teststatus);
						
						teststatus = verifyXPath.isfound(Config.driver, pr.getProperty("CART_LABEL_FREESHIPPINGICON_XPATH"));
						StatusLog.printlnPassedResultTrue(Config.driver,"[CART] Expected Free Shipping Text with icon is displayed. ",teststatus);
						
					} else{
						
						//Verify the Free Shipping Text only
						teststatus = verifyXPath.isfound(Config.driver, pr.getProperty("CART_LABEL_FREESHIPPINGTEXT_XPATH"));
						StatusLog.printlnPassedResultTrue(Config.driver,"[CART] Expected Free Shipping Text only is displayed. ",teststatus);
					}//end if
					
					Cart.estimateShippingSubmitZip(Config.driver, zipCode);
					teststatus = verifyXPath.isfound(Config.driver, pr.getProperty("CART_SELECTOPTION_FREESHIPMTD_XPATH"));
					StatusLog.printlnPassedResultTrue(Config.driver,"[CART] Expected Free Shipping Method is in the Estimate Shipping dropdown. ",teststatus);
					
				
				
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
