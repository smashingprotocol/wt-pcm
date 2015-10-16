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
import com.pcm.includes.SignIn;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;


public class VerifyOrderOmnitureProperties {
	
	public String sku;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	
	Properties sys = System.getProperties();
	
	@Test
	public void Verify_Order_Omniture_Properties() throws Exception {
	
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//set test data
			sku = pr.getProperty("SEARCH_SKU_DFLT");
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			
			//logout first if logged in
			SignIn.logout(Config.driver);
			
			//Search for the Sku to checkout and add to cart
			Search.keyword(Config.driver,sku);
			Search.addtocart(Config.driver,sku,qty);
			System.out.println("[CART] " + Config.driver.getTitle());
			
			ClickElement.byXPath(Config.driver,pr.getProperty("CART_LINK_XPATH"));
			System.out.println("[CART] " + Config.driver.getTitle());
			verifyXPath.isfound(Config.driver, "//input[@class='cartQty cart-qty']");
			
			//New Customer Device Fingerprint
			ClickElement.byXPath(Config.driver,pr.getProperty("CART_BTN_NEW_XPATH"));
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("CHECKOUT_DEVICEFINGERPRINT_XPATH_" + env)); //Checkout New Customer Device Fingerprint
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Device Fingerprint Properties is found in New Customer", testStatus);
			
			//New Customer Omniture
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("CHECKOUT_SCRIPT_OMNI_XPATH")); //Checkout New Customer Omniture Properties
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Omniture Properties is found in New Customer", testStatus);
			
			ClickElement.byXPath(Config.driver,pr.getProperty("CHECKOUT_LINK_BACKTOCART_XPATH")); //Click the Logo to return to cart.
			
			//Guest Checkout Device fingerprint
			ClickElement.byXPath(Config.driver,pr.getProperty("CART_BTN_GUEST_XPATH"));
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("CHECKOUT_DEVICEFINGERPRINT_XPATH_" + env)); //Checkout New Customer Device Fingerprint
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Device Fingerprint Properties is found in Guest Checkout", testStatus);
			
			//Guest Checkout Omniture
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("CHECKOUT_SCRIPT_OMNI_XPATH")); //Checkout New Customer Omniture Properties
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Omniture Properties is found in Guest Checkout", testStatus);
			
			ClickElement.byXPath(Config.driver,pr.getProperty("CHECKOUT_LINK_BACKTOCART_XPATH")); //Click the Logo to return to cart.
			
			//Existing Customer Device FingerPrint
			Cart.existingLogin(Config.driver, email, password);
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("CHECKOUT_DEVICEFINGERPRINT_XPATH_" + env)); //Checkout New Customer Device Fingerprint
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Device Fingerprint Properties is found in Existing Customer", testStatus);
			
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("CHECKOUT_SCRIPT_OMNI_XPATH")); //Checkout New Customer Omniture Properties
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] Omniture Properties is found in Existing Customer", testStatus);
			
			
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
	

