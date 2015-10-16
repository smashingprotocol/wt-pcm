package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Homepage;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class ShoppingCartVerifyServicePlanUpdateQTY {

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
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String zipCode;
	
	
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Shopping_Cart_Verify_ServicePlan_Update_QTY() throws Exception{
		
		try{
			
			//setup driver,properties and includes
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost"); //prod or stage
			Properties pr = Config.properties("pcm.properties"); //
			
			//Define properties
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			zipCode = pr.getProperty("CHECKOUT_ZIP_RECYCFEE");
			
			//Clear the cart first.
			Cart.clearcart(Config.driver);
			
	
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
				
				//Quick add the sku
				Cart.quickaddsku(Config.driver, sku);
				
				
				//Verify the Free Shipping text with or no icon
				if(Boolean.valueOf(withIcon)){
					
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("CART_LABEL_FREESHIPPINGICONTEXT_XPATH"));
					Assert.assertTrue("[CART] Expected Free Shipping Text with icon is displayed. ",testStatus);
					
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("CART_LABEL_FREESHIPPINGICON_XPATH"));
					Assert.assertTrue("[CART] Expected Free Shipping Text with icon is displayed. ",testStatus);
					
				} else{
					
					//Verify the Free Shipping Text only
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("CART_LABEL_FREESHIPPINGTEXT_XPATH"));
					Assert.assertTrue("[CART] Expected Free Shipping Text only is displayed. ",testStatus);
				}//end if
				
				Cart.estimateShippingSubmitZip(Config.driver, zipCode);
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("CART_SELECTOPTION_FREESHIPMTD_XPATH"));
				Assert.assertTrue("[CART] Expected Free Shipping Method is in the Estimate Shipping dropdown. ",testStatus);
				
				Cart.clearcart(Config.driver);
				
			} //end for
			
			System.out.println("[PCM] Shopping Cart Additional InCart Savings Price Display...PASSED");
		
		} catch (Exception e){
			System.out.println("[PCM] Shopping Cart Additional InCart Savings Price Display...FAILED");
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
