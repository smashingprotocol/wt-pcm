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
import com.pcm.includes.Search;
import com.pcm.includes.SignIn;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.verifyXPath;

public class VerifyOrderCheckoutCreditCard {

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
	public String cardNo;
	public String exMonth;
	public String exYear;
	public String securityCode;
	
	
	
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	
	@Test
	public void Verify_Order_Checkout_CreditCard() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies

			email = pr.getProperty("CHECKOUT_USER_TAX_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			sku = pr.getProperty("SEARCH_SKU_DFLT");
			qty = "1";
			cardNo = pr.getProperty("CHECKOUT_CCNO_VISA");
			exMonth = pr.getProperty("CHECKOUT_EXPMONTH");
			exYear = pr.getProperty("CHECKOUT_EXPYEAR");
			securityCode = pr.getProperty("CHECKOUT_SCODE_VISA");
			
			//Login user via header
			SignIn.login(Config.driver,email,password);
			testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("HEADER_LINK_SIGNOUT_XPATH"));
			Assert.assertTrue("User is logged in (Sign out link is display)",testStatus);
			
			//Search sku and add to cart
			//Cart.clearcart(Config.driver);
			Search.keyword(Config.driver, sku);
			Search.addtocart(Config.driver, sku, qty);
			
			
			//Go to cart and Proceed to checkout.
			Cart.navigate(Config.driver);
			Cart.proceedtocheckout(Config.driver);
			
			Checkout.enterCreditCardExisting(Config.driver,cardNo,exMonth,exYear,securityCode);
			Checkout.placeOrderAccept(Config.driver);
			
			String OrderNumber = verifyXPath.getText(Config.driver, pr.getProperty("CHECKOUT_ORDERCONFIRMATION_ORNO_XPATH"));
			System.out.println("[PCM] Verify Order Checkout CreditCard with Order Number: " + OrderNumber + " ..PASSED");
			
			
		} catch (Exception e){
			System.out.println(e);
		}
	}
	

	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
		
	}
	
	
}
