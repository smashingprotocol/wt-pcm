package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Homepage;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class ShoppingCartFreeAfterRebatesItem {

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
	public void Shopping_Cart_Free_After_Rebates_Item() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies

			//Define properties
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			zipCode = pr.getProperty("CHECKOUT_ZIP_RECYCFEE");
	
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
					Cart.clearcart(Config.driver);
					Cart.quickaddsku(Config.driver, sku);
					
					
					//Verify the Free After Rebates text with or without MIR
					if(Boolean.valueOf(withMIR)){
						
						teststatus = verifyXPath.isfound(Config.driver, "//p[@class='itemTotalPrice' and contains(text(),'$" + unitPrice + "')]");
						StatusLog.printlnPassedResultTrue(Config.driver,"[CART] Final Price is display, not Free After rebates text when item has MIR",teststatus);						
						
						
					} else{
						
						teststatus = verifyXPath.isfound(Config.driver, pr.getProperty("CART_LABEL_FREEAFTERREBATES_XPATH"));
						StatusLog.printlnPassedResultTrue(Config.driver,"[CART] Free After rebates text is display",teststatus);	
						
					}//end if
			
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
