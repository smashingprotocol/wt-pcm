package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Homepage;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class VerifyOrderAddtoCartfoLowerPriceSubTotal {

	public String sku;
	public String withSlashed;
	public String withMIR;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Verify_Order_AddtoCart_fo_Lower_Price_SubTotal() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies

			//Define properties
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
				
			//Clear the cart first.
			Cart.clearcart(Config.driver);
			
	
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				sku = TableContainer.getCellValue(i, "sku");
				withSlashed = TableContainer.getCellValue(i, "withSlashed");
				withMIR = TableContainer.getCellValue(i, "withMIR");
				Cart.quickaddsku(Config.driver, sku);		
			} //end for
			
			//Store the Cart Order Total
			cartOrderTotal = verifyXPath.getText(Config.driver,pr.getProperty("CART_LABEL_ORDERTOTAL_XPATH"));
			cartOrderTotal = cartOrderTotal.replace("$","");
			
			//Proceed to Guest Checkout and Verify Order Review Subtotal
			Cart.guestCheckout(Config.driver);
			testStatus = verifyXPath.isfound(Config.driver,"//p[@id='orderReviewSubTotal' and contains(text(),'" + cartOrderTotal + "')]");  //Verify the Order review sub total is equivalent to the Cart.
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Order Review Sub Total of Guest is equals to the cart's Sub Total.",testStatus);
			
			//Proceed to New Customer Checkout and Verify Order Review Subtotal
			ClickElement.byXPath(Config.driver, pr.getProperty("CHECKOUT_LINK_CREATEACT_XPATH"));
			testStatus = verifyXPath.isfound(Config.driver,"//p[@id='orderReviewSubTotal' and contains(text(),'" + cartOrderTotal + "')]");  //Verify the Order review sub total is equivalent to the Cart.	
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Order Review Sub Total of New Customer is equals to the cart's Sub Total.",testStatus);
		
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
