package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class VerifyOrderSavedCreditCard {

	public String sku;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Verify_Order_Saved_CreditCard() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//set test data
			sku = pr.getProperty("SEARCH_SKU_DFLT");
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_SAVEDCC_EMAIL");
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			
			//Search for the Sku to checkout and add to cart
			Search.keyword(Config.driver,sku);
			Search.addtocart(Config.driver,sku,qty);
			Cart.navigate(Config.driver);
			
			Cart.existingLogin(Config.driver, email, password); //Login user in cart.
			
			//Verify the Card Numbe field is not display
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("CHECKOUT_INPUT_CARDNO_XPATH"));
			StatusLog.printlnPassedResultFalse(Config.driver,"[CHECKOUT] Credit Card field should not be display.",testStatus);
			
			//Verify the Security code field is display
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("CHECKOUT_INPUT_SECCODE_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Security Code for Saved Card is found.",testStatus);
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
		
		} catch (Exception e){
			StatusLog.printlnFailedResult(Config.driver,"[PCM] Verify Order Saved Credit Cart");
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
