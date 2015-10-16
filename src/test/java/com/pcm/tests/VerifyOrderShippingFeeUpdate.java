package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Checkout;
import com.pcm.includes.Header;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class VerifyOrderShippingFeeUpdate {

	public String freeshipsku;
	public String sku;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String verifyOrderSubtTotal;
	public static boolean isfreeshipping;
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	
	@Test
	public void Verify_Order_Shipping_Fee_Update() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//set test data
			freeshipsku = pr.getProperty("SEARCH_SKU_FREESHIP");
			sku = pr.getProperty("SEARCH_SKU_FLATSCREEN");
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_TAX_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			
			//Login user via header
			Header.signIn(Config.driver, email, password);
		
			
			//Search SKU and Add to Cart.
			Cart.clearcart(Config.driver);
			Search.keyword(Config.driver, sku);
			Search.addtocart(Config.driver, sku, qty);
			
			Search.keyword(Config.driver, freeshipsku);
			Search.addtocart(Config.driver, freeshipsku, qty);
			
			//Proceed to checkout
			Cart.navigate(Config.driver);
			Cart.proceedtocheckout(Config.driver);
			
			//Verify Free Shipping is not found when mixed with non free ship item.
			isfreeshipping = verifyXPath.isfound(Config.driver, pr.getProperty("CHECKOUT_LABEL_FREESHIP_XPATH")); //Verify the Free label in shipping method amount.
			StatusLog.printlnPassedResultFalse(Config.driver,"[TESTCASE] Free Shipping Method is not found.",isfreeshipping);
			
			Checkout.deleteItem(Config.driver,sku);
			
			//Verify the Shipping method is found after deleting the no free shipping item
			isfreeshipping = verifyXPath.isfound(Config.driver, pr.getProperty("CHECKOUT_LABEL_FREESHIP_XPATH")); //Verify the Free label in shipping method amount.
			StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Free Shipping Method is found.",isfreeshipping);
			
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
