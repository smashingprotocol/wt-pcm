package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Checkout;
import com.pcm.includes.Homepage;
import com.pcm.includes.PDP;
import com.pcm.includes.Search;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.VerifyText;

public class VerifyOrderReviewDeleteItemRedirectstoCart {

	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String verifyOrderSubtTotal;
	public String sku;
	public String qty;
	public String zipCode;
	public String itemid;
	
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	
	@Test
	public void Verify_Order_Review_Delete_Item_Redirects_to_Cart() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
			PDP pdp = new PDP();
			email = pr.getProperty("CHECKOUT_USER_TAX_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			sku = pr.getProperty("SEARCH_SKU_DFLT");
			qty = "1";
			
			//Search sku and add to cart
			Cart.clearcart(Config.driver);
			Search.keyword(Config.driver, sku);
			Search.addtocart(Config.driver, sku, qty);
			
			//get the edpno in PDP
			edpno = pdp.getEDPNo(Config.driver,sku);
			
			//Go to cart and Proceed to Checkout.
			Cart.navigate(Config.driver);
			itemid = Cart.getItemID(Config.driver, edpno);
			
			Cart.guestCheckout(Config.driver);	
			Checkout.updateOrderItemQTY(Config.driver, itemid,"0");
			
			testStatus = VerifyText.isfound(Config.driver, "Your Shopping Cart is empty");
			Assert.assertTrue("[ASSERT] Verify Shopping Cart is empty text is found in cart.",testStatus);
			StatusLog.printlnPassedResult(Config.driver,"[CHECKOUT] Order Review empty after deleted item redirects to empty Shopping cart.");
			
		} catch (Exception e){
			Assert.fail("[CHECKOUT] Order Review empty after deleted item redirects to Shopping cart.");
		}
	}

	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
		
	}
	
	
}
