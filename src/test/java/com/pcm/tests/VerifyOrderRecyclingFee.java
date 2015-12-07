package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Header;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.pcm.includes.SignIn;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class VerifyOrderRecyclingFee {

	public String sku;
	public String qty;
	public String email;
	public String password;
	public String env;
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Verify_Order_Recycling_Fee() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//set test data
			sku = pr.getProperty("SEARCH_SKU_RECYCFEE");
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			
			//Login user via header
			SignIn.login(Config.driver,email,password);
			boolean testStatus = verifyXPath.isfoundwithWait(Config.driver, Header.LINK_SIGNOUT_XPATH,"2");
			Assert.assertTrue("User is logged in (Sign out link is display)",testStatus);
			
			//Clear the cart first.
			Cart.clearcart(Config.driver);
			
			//Search for the Sku to checkout and add to cart
			Search.keyword(Config.driver,sku);
			Search.addtocart(Config.driver,sku,qty);
			Cart.navigate(Config.driver);
			Cart.verifySkuinCart(Config.driver, sku);
			ClickElement.byXPath(Config.driver,pr.getProperty("CART_BTN_CHECKOUT_XPATH"));
		
			StatusLog.printlnPassedResultTrue(Config.driver,"CA Recycling Fee label is found at Verify Order", verifyXPath.isfound(Config.driver,pr.getProperty("CHECKOUT_LABEL_RECYCFEE_XPATH")));
			
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
